package alejandro.aoe3rating;

/**
 * Created by Alex on 31/1/17.
 */

public class Jugador {

    private String nivelJugador;
    private String estado;
    private String ultimo_logueo;
    private String fecha_union;
    private String clan;

    public Jugador(String nivelJugador,String estado,String ultimo_logueo,String fecha_union,String clan) {
        this.nivelJugador = nivelJugador;
        this.estado = estado;
        this.ultimo_logueo = ultimo_logueo;
        this.fecha_union = fecha_union;
        this.clan = clan;
    }
    public Jugador() {

    }


    public String getNivelJugador() {
        return nivelJugador;
    }

    public void setNivelJugador(String nivelJugador) {
        this.nivelJugador = nivelJugador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUltimo_logueo() {
        return ultimo_logueo;
    }

    public void setUltimo_logueo(String ultimo_logueo) {
        this.ultimo_logueo = ultimo_logueo;
    }

    public String getFecha_union() {
        return fecha_union;
    }

    public void setFecha_union(String fecha_union) {
        this.fecha_union = fecha_union;
    }

    public String getClan() {
        return clan;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }
    public String rangoJugador(String nivel) {
        switch (Integer.parseInt(nivel)) {
            case 10:
            case 11:
            case 12:
            case 13:
                return "Cabo";
            case 14:
            case 15:
            case 16:
                return "Sargento";
            case 17:
            case 18:
            case 19:
                return "Sargento Primero";
            default:
                return "Rango no encontrado";

        }
    }
}
