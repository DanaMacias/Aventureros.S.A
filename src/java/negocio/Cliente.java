/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author VivoBook
 */
public class Cliente extends Persona{
    private String direccion;
    private Genero genero;
    private Nacionalidad nacionalidad;

    public Cliente() {
    }

    public Cliente(String direccion, Genero genero, Nacionalidad nacionalidad, int identificacion, String nombre, String correo, String clave) {
        super(identificacion, nombre, correo, clave);
        this.direccion = direccion;
        this.genero = genero;
        this.nacionalidad = nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
