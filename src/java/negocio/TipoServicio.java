/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

public class TipoServicio {
    private int id;
    private String tipoServicio;

    public TipoServicio() {}

    public TipoServicio(int id, String tipoServicio) {
        this.id = id;
        this.tipoServicio = tipoServicio;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }
}


