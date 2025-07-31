package modelo;

public class EntregaComparada {

    private Entrega entrega;
    private double tiempoEntrega;

    public EntregaComparada(Entrega entrega, double tiempoEntrega) {
        this.entrega = entrega;
        this.tiempoEntrega = tiempoEntrega;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public double getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(double tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

}
