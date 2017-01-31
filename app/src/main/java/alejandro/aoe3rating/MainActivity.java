package alejandro.aoe3rating;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static alejandro.aoe3rating.Conexiones.getHtmlDocument;
import static alejandro.aoe3rating.Conexiones.getStatusConnectionCode;

public class MainActivity extends AppCompatActivity {
    private TextView texto2;
    private TextView texto3;
    private TextView texto4;
    private TextView texto1;
    private TextView texto_estado;
    private EditText caja_nombre;
    private TextView texto_nivel;
    public static final String url = "http://agecommunity.com/_server_status_/index.aspx";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto1 = (TextView)findViewById(R.id.textView1);
        texto2 = (TextView)findViewById(R.id.textView2);
        texto3 = (TextView)findViewById(R.id.textView3);
        texto4 = (TextView)findViewById(R.id.textView4);
        texto_estado = (TextView)findViewById(R.id.textView9);
        caja_nombre = (EditText)findViewById(R.id.editText);
        cambiarMensaje(new View(this));
    }
    private Handler puente = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String[] resultado = (String[]) msg.obj;
            texto2.setText("The War Chief: " + resultado[1]);
            texto1.setText("Age of Empires 3: " + resultado[0]);
            texto3.setText("The Asyan Dynasties: " + resultado[2]);
            texto4.setText("Total: " + resultado[3]);
            //resultado[3] = "9";
            if (Integer.parseInt(resultado[3]) > 10) {
                texto_estado.setTextColor(Color.parseColor("#FF669900"));
                texto_estado.setText("Correcto");
            }
            else {
                texto_estado.setTextColor(Color.RED);
                texto_estado.setText("Fallo");
            }
        }
    };


    public void cambiarMensaje(View v) {
        new Thread(new Runnable() {
            public void run() {
                if (getStatusConnectionCode(url) == 200) {
                    //System.out.println("Comprobando entradas de: "+url);
                    Document document = getHtmlDocument(url);
                    Elements entradas = document.select("table[id$=\"TotalUsersDetailsView\"]");
                    String titulo ="";
                    for (Element elem : entradas) {
                        titulo = elem.getElementsByTag("td").text();
                        if (titulo.contains("in past")) break;
                    }

                    String[] numerosComoArray = titulo.split(" ");
                    String[] valores = new String[4];
                    valores[0] = numerosComoArray[4];
                    valores[1] = numerosComoArray[10];
                    valores[2] = numerosComoArray[17];
                    int total = Integer.parseInt(numerosComoArray[4]) + Integer.parseInt(numerosComoArray[10]) + Integer.parseInt(numerosComoArray[17]);
                    valores[3] = String.valueOf(total);

                    Message msg = new Message();
                    msg.obj = valores;
                    puente.sendMessage(msg);
                }else{
                    System.out.println("Error");
                }
           }
        }).start();
    }
    public void nivelJugador(View v) {
        Intent intent = new Intent(v.getContext(), MainActivity2.class);
        intent.putExtra("jugador", caja_nombre.getText().toString());
        startActivityForResult(intent, 0);
    }

}
