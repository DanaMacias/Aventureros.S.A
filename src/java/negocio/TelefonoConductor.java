/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author VivoBook
 */
public class TelefonoConductor {
    private int identificacion_conductor;
    private String numero;

    public TelefonoConductor() {
    }

    public TelefonoConductor(int identificacion_conductor, String numero) {
        this.identificacion_conductor = identificacion_conductor;
        this.numero = numero;
    }

    public int getIdentificacion_conductor() {
        return identificacion_conductor;
    }

    public void setIdentificacion_conductor(int identificacion_conductor) {
        this.identificacion_conductor = identificacion_conductor;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    
}
