/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author VivoBook
 */
public class Conductor extends Persona{
    private String direccion;
    private String foto;
    private Genero genero;
    private Nacionalidad nacionalidad;

    public Conductor() {
    }

    public Conductor(String direccion, String foto, Genero genero, Nacionalidad nacionalidad, int identificacion, String nombre, String correo, String clave) {
        super(identificacion, nombre, correo, clave);
        this.direccion = direccion;
        this.foto = foto;
        this.genero = genero;
        this.nacionalidad = nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    
    
}
