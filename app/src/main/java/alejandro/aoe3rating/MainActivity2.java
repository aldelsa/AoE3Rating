package alejandro.aoe3rating;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static alejandro.aoe3rating.Conexiones.getHtmlDocument;
import static alejandro.aoe3rating.Conexiones.getStatusConnectionCode;
import static alejandro.aoe3rating.R.id.textView1;

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
                        puente2.sendMessage(msg );
                    } else {
                        System.out.println("Error");
                    }
            }
        }).start();
    }
    public void cargarPartidas() {
        
    }
}
