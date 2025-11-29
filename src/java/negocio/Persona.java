/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author VivoBook
 */
abstract public class Persona {
   protected int identificacion;
   protected String nombre;
   protected String correo;
   protected String clave;

    public Persona() {
    }

    public Persona(int identificacion, String nombre, String correo, String clave) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "Persona{" + "identificacion=" + identificacion + ", nombre=" + nombre + ", correo=" + correo + ", clave=" + clave + '}';
    }
}
