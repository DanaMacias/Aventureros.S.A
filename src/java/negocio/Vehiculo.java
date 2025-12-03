package negocio;

public class Vehiculo {

    private String placa;
    private int identificacion_conductor;
    private String marca;
    private String modelo;
    private String servicio;

    public Vehiculo() {
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getIdentificacion_conductor() {
        return identificacion_conductor;
    }

    public void setIdentificacion_conductor(int identificacion_conductor) {
        this.identificacion_conductor = identificacion_conductor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}
