package modelo;

public class EntregaComparada {

    private RegistroEntrega entrega;
    private double tiempoEntrega;

    public EntregaComparada(RegistroEntrega entrega, double tiempoEntrega) {
        this.entrega = entrega;
        this.tiempoEntrega = tiempoEntrega;
    }

    public RegistroEntrega getEntrega() {
        return entrega;
    }

    public void setEntrega(RegistroEntrega entrega) {
        this.entrega = entrega;
    }

    public double getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(double tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

}
