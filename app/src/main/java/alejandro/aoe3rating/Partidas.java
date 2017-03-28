package alejandro.aoe3rating;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static alejandro.aoe3rating.Conexiones.getHtmlDocument;
import static alejandro.aoe3rating.Conexiones.getStatusConnectionCode;

/**
 * Created by Alex on 1/2/17.
 */

public class Partidas {
    private String Mapa;
    private String tiempo;
    private String id;
    private String [] jugadores;
    private DetallesPartida detalles;

    public Partidas() {}
    public String getMapa() {
        return Mapa;
    }

    public void setMapa(String mapa) {
        Mapa = mapa;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getJugadores() {
        return jugadores;
    }

    public void setJugadores(String[] jugadores) {
        this.jugadores = jugadores;
    }


    public DetallesPartida getDetalles() {
        return detalles;
    }

    public void setDetalles(DetallesPartida detalles) {
        this.detalles = detalles;
    }
}
