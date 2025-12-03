package persistencia;

import negocio.Cliente;
import negocio.Genero;
import negocio.Nacionalidad;
import negocio.TelefonoCliente; 
import negocio.Servicio; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

public class clienteDAO {
    conexion con = new conexion();
    
    private telefonoClienteDAO telClienteDao = new telefonoClienteDAO();
    private servicioDAO servicioDao = new servicioDAO();
    
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
    
    public Cliente obtenerClientePorId(int identificacion) {
        Cliente cliente = null;
        Connection accesoConexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        
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
                int idCliente = rs.getInt("identificacion"); 
                
                cliente.setIdentificacion(idCliente);
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setClave(rs.getString("clave"));
                
                Genero g = new Genero();
                g.setId(rs.getInt("id_genero"));
                g.setGenero(rs.getString("genero_desc")); 
                cliente.setGenero(g);

                
                Nacionalidad n = new Nacionalidad();
                n.setId(rs.getInt("id_nacionalidad"));
                n.setNacionalidad(rs.getString("nacionalidad_pais")); 
                cliente.setNacionalidad(n);
                
                
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
    
    public List<Cliente> listarClientes() {
        Connection accesoConexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

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
                
                int identificacionCliente = rs.getInt("identificacion"); 
                
                
                c.setIdentificacion(identificacionCliente);
                c.setNombre(rs.getString("nombre"));
                c.setDireccion(rs.getString("direccion"));
                c.setCorreo(rs.getString("correo"));
                c.setClave(rs.getString("clave")); 
                
                
                Genero g = new Genero();
                g.setId(rs.getInt("id_genero")); 
                g.setGenero(rs.getString("genero_desc")); 
                c.setGenero(g);
                
                
                Nacionalidad n = new Nacionalidad();
                n.setId(rs.getInt("id_nacionalidad")); 
                n.setNacionalidad(rs.getString("nacionalidad_pais")); 
                c.setNacionalidad(n);
                
                
                List<TelefonoCliente> telefonos = telClienteDao.obtenerTelefonosPorCliente(identificacionCliente);
                c.setTelefonos(telefonos); 
                
                
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
