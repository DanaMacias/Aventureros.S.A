package negocio;

import java.util.List;
import java.util.ArrayList;

// Asume que también tienes importados Genero, Nacionalidad, TelefonoCliente y Servicio
// Si están en el mismo paquete 'negocio', no necesitas importarlos explícitamente, 
// pero si están en subpaquetes o paquetes diferentes, asegúrate de añadirlos.

public class Cliente {

    // --- Atributos Básicos del Cliente ---
    private int identificacion;
    private String nombre;
    private String direccion;
    private String correo;
    private String clave;
    private Genero genero; // Objeto de negocio
    private Nacionalidad nacionalidad; // Objeto de negocio

    // --- Atributos para las Colecciones de Objetos (Solución al problema) ---
    private List<TelefonoCliente> telefonos;
    private List<Servicio> servicios;

    // --- Constructor por defecto ---
    public Cliente() {
        // Inicializa las listas para evitar errores NullPointerException
        this.telefonos = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }

    // --- Constructor con parámetros (ejemplo) ---
    /*
    public Cliente(int identificacion, String nombre, String direccion, String correo, String clave, Genero genero, Nacionalidad nacionalidad) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.clave = clave;
        this.genero = genero;
        this.nacionalidad = nacionalidad;
        
        // Inicializa las listas en el constructor también
        this.telefonos = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }
    */
    
    // --- Getters y Setters de Atributos Básicos ---
    
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
    
    // ----------------------------------------------------------------------
    // 🔑 Getters y Setters Añadidos (Resolviendo el error de compilación)
    // ----------------------------------------------------------------------

    /**
     * Obtiene la lista de teléfonos asociados a este cliente.
     */
    public List<TelefonoCliente> getTelefonos() {
        return telefonos;
    }

    /**
     * Establece la lista de teléfonos asociados a este cliente.
     */
    public void setTelefonos(List<TelefonoCliente> telefonos) {
        this.telefonos = telefonos;
    }

    /**
     * Obtiene la lista de servicios solicitados por este cliente.
     */
    public List<Servicio> getServicios() {
        return servicios;
    }

    /**
     * Establece la lista de servicios solicitados por este cliente.
     */
    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    // --- Puedes añadir el método toString() si lo usas para depuración ---
    /*
    @Override
    public String toString() {
        return "Cliente{" + "identificacion=" + identificacion + ", nombre=" + nombre + ", correo=" + correo + ", num. telefonos=" + telefonos.size() + ", num. servicios=" + servicios.size() + '}';
    }
    */
}