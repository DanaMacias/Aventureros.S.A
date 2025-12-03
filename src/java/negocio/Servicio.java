/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import java.util.List;

public class Servicio {

    private int idServicio;
    private int idCliente;
    private int idConductor;
    private String direccionOrigen;
    private String direccionDestino;
    private int idCategoria;
    private int idTipoServicio; 
    private String estado; 
    private List<String> detalles;
    private String nombreCliente;
    private double valorPago;
    private String nombreConductor; // Para el nombre del conductor
    private String tipoServicioNombre;

    public Servicio() {
    }

    public Servicio(int idServicio, int idCliente, int idConductor, String direccionOrigen,
                    String direccionDestino, int idCategoria, int idTipoServicio,
                    String estado, List<String> detalles) {
        this.idServicio = idServicio;
        this.idCliente = idCliente;
        this.idConductor = idConductor;
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
        this.idCategoria = idCategoria;
        this.idTipoServicio = idTipoServicio;
        this.estado = estado;
        this.detalles = detalles;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public String getDireccionOrigen() {
        return direccionOrigen;
    }

    public void setDireccionOrigen(String direccionOrigen) {
        this.direccionOrigen = direccionOrigen;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(int idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<String> detalles) {
        this.detalles = detalles;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }
    
    public void agregarDetalle(String detalle) {
        if (this.detalles != null && detalle != null && !detalle.trim().isEmpty()) {
            this.detalles.add(detalle);
        }
    }
    
    public String getNombreConductor() {
    return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getTipoServicioNombre() {
        return tipoServicioNombre;
    }

    public void setTipoServicioNombre(String tipoServicioNombre) {
        this.tipoServicioNombre = tipoServicioNombre;
    }

}

