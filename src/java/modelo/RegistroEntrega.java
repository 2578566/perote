package modelo;

public class RegistroEntrega {

    private String fecha;
    private int idVehiculo;
    private int idCliente;
    private int idDestino;
    private int idProducto;
    private double cantidad;


    public String getFecha() {
        return fecha;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public double getCantidad() {
        return cantidad;
    }

   
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

}
