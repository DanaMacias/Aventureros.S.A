/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author VivoBook
 */
public class TelefonoCliente {
    private int identificacion_cliente;
    private String numero;

    public TelefonoCliente() {
    }

    public TelefonoCliente(int identificacion_cliente, String numero) {
        this.identificacion_cliente = identificacion_cliente;
        this.numero = numero;
    }

    public int getIdentificacion_cliente() {
        return identificacion_cliente;
    }

    public void setIdentificacion_cliente(int identificacion_cliente) {
        this.identificacion_cliente = identificacion_cliente;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    
}
