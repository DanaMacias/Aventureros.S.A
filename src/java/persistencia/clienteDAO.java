package persistencia;

import negocio.Cliente;
import negocio.Genero;
import negocio.Nacionalidad;
import negocio.TelefonoCliente; // Añadido
import negocio.Servicio; // Añadido
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

public class clienteDAO {
    // Declaramos la conexión solo para inicializar la clase de conexión,
    // pero manejaremos la variable Connection localmente dentro de los métodos.
    conexion con = new conexion();
    
    // 🔑 Instancias de DAOs para acceder a los datos relacionados
    private telefonoClienteDAO telClienteDao = new telefonoClienteDAO();
    private servicioDAO servicioDao = new servicioDAO();
    
    // ----------------------------------------------------------------------
    // VALIDAR LOGIN
    // ----------------------------------------------------------------------
    public int validarLogin(String correo, String clave, Cliente cliente) {
        Connection accesoConexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int count = 0;  
        String sql = "SELECT identificacion FROM cliente WHERE correo = ? AND clave = ?";

        try {
            accesoConexion = con.getConexion();
            if (accesoConexion != null) {
                ps = accesoConexion.prepareStatement(sql);
                ps.setString(1, correo);
                ps.setString(2, clave);

                rs = ps.executeQuery();

                while (rs.next()) {
                    count++;
                    cliente.setIdentificacion(rs.getInt("identificacion"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al validar el login: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (accesoConexion != null) accesoConexion.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return count;
    }
    
    // ----------------------------------------------------------------------
    // REGISTRAR CLIENTE
    // ----------------------------------------------------------------------
    public boolean registrarCliente(Cliente c) {
        Connection acceso = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO cliente (identificacion, nombre, direccion, correo, clave, id_genero, id_nacionalidad) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            acceso = con.getConexion();
            ps = acceso.prepareStatement(sql);

            ps.setInt(1, c.getIdentificacion());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getCorreo());
            ps.setString(5, c.getClave());
            ps.setInt(6, c.getGenero().getId());
            ps.setInt(7, c.getNacionalidad().getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (acceso != null) acceso.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    // ----------------------------------------------------------------------
    // OBTENER CLIENTE POR ID (USADO PARA HISTORIAL)
    // ----------------------------------------------------------------------
    public Cliente obtenerClientePorId(int identificacion) {
        Cliente cliente = null;
        Connection accesoConexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // La consulta trae los datos del cliente, género y nacionalidad
        String sql = "SELECT c.identificacion, c.nombre, c.direccion, c.correo, c.clave, "
                 + "g.id AS id_genero, g.genero AS genero_desc, " 
                 + "n.id AS id_nacionalidad, n.nacionalidad AS nacionalidad_pais " 
                 + "FROM cliente c "
                 + "JOIN genero g ON c.id_genero = g.id "
                 + "JOIN nacionalidad n ON c.id_nacionalidad = n.id "
                 + "WHERE c.identificacion = ?";

        try {
            accesoConexion = con.getConexion();
            ps = accesoConexion.prepareStatement(sql);
            ps.setInt(1, identificacion);

            rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                int idCliente = rs.getInt("identificacion"); // Captura la identificación
                
                cliente.setIdentificacion(idCliente);
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setClave(rs.getString("clave")); // Aunque no se muestre, se mapea

                // Mapear Genero
                Genero g = new Genero();
                g.setId(rs.getInt("id_genero"));
                g.setGenero(rs.getString("genero_desc")); 
                cliente.setGenero(g);

                // Mapear Nacionalidad
                Nacionalidad n = new Nacionalidad();
                n.setId(rs.getInt("id_nacionalidad"));
                n.setNacionalidad(rs.getString("nacionalidad_pais")); 
                cliente.setNacionalidad(n);
                
                // 🔑 Añadir teléfonos y servicios
                cliente.setTelefonos(telClienteDao.obtenerTelefonosPorCliente(idCliente));
                cliente.setServicios(servicioDao.obtenerServiciosPorCliente(idCliente));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener cliente por ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (accesoConexion != null) accesoConexion.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return cliente;
    }
    
    // ----------------------------------------------------------------------
    // LISTAR TODOS LOS CLIENTES (USADO POR ADMIN)
    // ----------------------------------------------------------------------
    public List<Cliente> listarClientes() {
        Connection accesoConexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Utilizamos la misma consulta que obtenerClientePorId, sin el filtro WHERE
        String sql = "SELECT c.identificacion, c.nombre, c.direccion, c.correo, c.clave, " +
                     "g.id AS id_genero, g.genero AS genero_desc, " + 
                     "n.id AS id_nacionalidad, n.nacionalidad AS nacionalidad_pais " + 
                     "FROM Cliente c " +
                     "JOIN Genero g ON c.id_genero = g.id " +
                     "JOIN Nacionalidad n ON c.id_nacionalidad = n.id";

        List<Cliente> clientes = new ArrayList<>();
        
        try {
            accesoConexion = con.getConexion();
            ps = accesoConexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                // 1. Obtener la identificación del cliente
                int identificacionCliente = rs.getInt("identificacion"); 
                
                // 2. Mapear datos del Cliente
                c.setIdentificacion(identificacionCliente);
                c.setNombre(rs.getString("nombre"));
                c.setDireccion(rs.getString("direccion"));
                c.setCorreo(rs.getString("correo"));
                c.setClave(rs.getString("clave")); 
                
                // Mapear Genero
                Genero g = new Genero();
                g.setId(rs.getInt("id_genero")); 
                g.setGenero(rs.getString("genero_desc")); 
                c.setGenero(g);
                
                // Mapear Nacionalidad
                Nacionalidad n = new Nacionalidad();
                n.setId(rs.getInt("id_nacionalidad")); 
                n.setNacionalidad(rs.getString("nacionalidad_pais")); 
                c.setNacionalidad(n);
                
                // 3. 🔑 Obtener y establecer Teléfonos 
                List<TelefonoCliente> telefonos = telClienteDao.obtenerTelefonosPorCliente(identificacionCliente);
                c.setTelefonos(telefonos); 
                
                // 4. 🔑 Obtener y establecer Servicios
                List<Servicio> servicios = servicioDao.obtenerServiciosPorCliente(identificacionCliente);
                c.setServicios(servicios);
                
                clientes.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (accesoConexion != null) accesoConexion.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return clientes;
    }
}