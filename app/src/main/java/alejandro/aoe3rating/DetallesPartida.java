package alejandro.aoe3rating;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 2/2/17.
 */

public class DetallesPartida {
    private String id;
    private String tipo;
    private List<String> Jugadores_win ;
    private String [] Jugadores ;


    public String[] getJugadores() {
        return Jugadores;
    }

    public void setJugadores(String[] jugadores) {
        Jugadores = jugadores;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getJugadores_win() {
        return Jugadores_win;
    }

    public void setJugadores_win(List<String> jugadores_win) {
        Jugadores_win = jugadores_win;
    }
    public Boolean es_win(String jugador) {
        return getJugadores_win().contains(jugador);
    }
}
