package alejandro.aoe3rating;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static alejandro.aoe3rating.Conexiones.getHtmlDocument;
import static alejandro.aoe3rating.Conexiones.getStatusConnectionCode;
import static alejandro.aoe3rating.R.id.action_context_bar;
import static alejandro.aoe3rating.R.id.activity_main;
import static alejandro.aoe3rating.R.id.activity_main2;
import static alejandro.aoe3rating.R.id.textView1;
import static android.R.attr.backgroundTint;
import static android.R.attr.drawable;
import static android.R.attr.layout;
import static android.R.attr.layout_alignParentStart;
import static android.R.attr.layout_marginTop;
import static android.R.drawable.ic_notification_overlay;

public class MainActivity2 extends AppCompatActivity {
    private TextView texto_nivel;
    private TextView texto_estado;
    private TextView texto_clan;
    private TextView texto_ultimo_log;
    private TextView texto_fecha_union;
    private String jugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        texto_nivel = (TextView)findViewById(R.id.textView1);
        texto_estado = (TextView)findViewById(R.id.textView2);
        texto_clan = (TextView)findViewById(R.id.textView3);
        texto_fecha_union = (TextView)findViewById(R.id.textView4);
        texto_ultimo_log = (TextView)findViewById(R.id.textView5);


        jugador = (String)extras.get("jugador");
        cargarJugador();
        cargarPartidas();
    }
    private Handler puente2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Jugador resultado = (Jugador) msg.obj;

            texto_nivel.setText(resultado.getNivelJugador() + " - " + resultado.rangoJugador(resultado.getNivelJugador()));
            texto_estado.setText(resultado.getEstado());
            texto_clan.setText(resultado.getClan());
            texto_ultimo_log.setText(resultado.getUltimo_logueo());
        }
    };
    private Handler puentePartida = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<Partidas> resultado = (List<Partidas>) msg.obj;
            int top = 120;
            for (Partidas par : resultado) {
                if (par.getDetalles().es_win(jugador)) {
                    CreaBotones("V",top);
                }
                else {
                    CreaBotones("D",top);
                }
                top = top + 170;
            }
        }
    };
    public void CreaBotones(String win,int top) {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_main2);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(125,130);
        params.leftMargin = top;
        params.topMargin = 930;

        Button tv = new Button(this);
        tv.setText(win);
        tv.setBackgroundResource(R.drawable.boton_redonde);
        tv.setTextColor(Color.WHITE);
        if (win == "V") tv.setBackgroundResource(R.drawable.boton_redonde2);


        rl.addView(tv, params);
    }
    public void cargarJugador() {
        new Thread(new Runnable()    {
            public void run() {
                    String url2 = "http://www.agecommunity.com/query/query.aspx?name=" + jugador + "&md=user";
                    if (getStatusConnectionCode(url2) == 200) {

                        Document document = getHtmlDocument(url2);
                        Elements entrada1 = document.select("age3");

                        Jugador nuevo = new Jugador();

                        for (Element elem : entrada1) {
                            String [] niveles = (elem.getElementsByTag("skillLevel").text()).split(" ");
                            nuevo.setNivelJugador(niveles[0]);

                            nuevo.setEstado(elem.getElementsByTag("presence").text());
                            nuevo.setClan(elem.getElementsByTag("clanName").text());
                            nuevo.setUltimo_logueo(elem.getElementsByTag("lastLogin").text());;
                        }
                        Message msg = new Message();
                        msg.obj = nuevo;
                        puente2.sendMessage(msg);
                    } else {
                        System.out.println("Error");
                    }
            }
        }).start();
    }

    public void cargarPartidas() {
        new Thread(new Runnable()    {
            public void run() {
                long time_start, time_end;
                time_start = System.currentTimeMillis();
                int num_partidas = 5;
                String url2 = "http://www.agecommunity.com/query/games.aspx?name= " + jugador.toUpperCase() + " &md=supremacy&max="+num_partidas;
                if (getStatusConnectionCode(url2) == 200) {

                    Document document = getHtmlDocument(url2);
                    Elements entrada = document.select("game");

                    List<Partidas> lista = new ArrayList<Partidas>();
                    for (Element elem : entrada) {

                        if (lista.size() >= num_partidas) break;

                        Partidas nueva = new Partidas();
                        nueva.setId(elem.getElementsByTag("id").text());
                        nueva.setMapa(elem.getElementsByTag("map").text());
                        nueva.setTiempo(elem.getElementsByTag("length").text());
                        String [] jugadores = (elem.getElementsByTag("n").text()).split(" ");
                        nueva.setJugadores(jugadores);

                        String url3 = "http://www.agecommunity.com/query/games.aspx?gameid=" + nueva.getId();

                        if (getStatusConnectionCode(url2) == 200) {

                            Document document2 = getHtmlDocument(url3);
                            Elements entrada2 = document2.select("players");

                            for (Element elem2 : entrada2) {
                                DetallesPartida nueva2 = new DetallesPartida();
                                List<String> jugadores_win = new ArrayList<String>();

                                String [] nombres = (elem2.getElementsByTag("n").text()).split(" ");
                                String [] valores = (elem2.getElementsByTag("win").text()).split(" ");

                                for (int a = 0;a < nombres.length;a++) {
                                    if (valores[a].toString().equals("1")) jugadores_win.add(nombres[a].toString());
                                }
                                nueva2.setJugadores_win(jugadores_win);
                                nueva.setDetalles(nueva2);
                            }
                        } else
                            System.out.println("Error");
                        lista.add(nueva);
                    }

                    Message msg = new Message();
                    msg.obj = lista;
                    time_end = System.currentTimeMillis();
                    System.out.println("Tiempo "+ ( time_end - time_start )/1000 +" seconds");
                    puentePartida.sendMessage(msg);

                } else {
                    System.out.println("Error");
                }
            }
        }).start();
    }
//    private Handler puentewin = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            Boolean es_win = (Boolean) msg.obj;
//            System.out.println(es_win);
//        }
//    };
//    private void getJugadores_win(final String jugador) {
//        new Thread(new Runnable()    {
//            public void run() {
//                String url2 = "http://www.agecommunity.com/query/games.aspx?gameid=" + id;
//                if (getStatusConnectionCode(url2) == 200) {
//                    Boolean es_win = false;
//                    Document document = getHtmlDocument(url2);
//                    Elements entrada = document.select("players");
//
//                    for (Element elem : entrada) {
//                        if ((elem.getElementsByTag("n").text()).equals(jugador) ) {
//                            if ((elem.getElementsByTag("win").text()).equals("1") ) {
//                                es_win = true;
//                            }
//                        }
//                    }
//                    Message msg = new Message();
//                    msg.obj = es_win;
//                    puentewin.sendMessage(msg);
//                } else {
//                    System.out.println("Error");
//                }
//            }
//        }).start();
//    }
}
