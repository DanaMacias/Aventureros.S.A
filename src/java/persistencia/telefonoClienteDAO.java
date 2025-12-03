package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import negocio.TelefonoCliente; 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VivoBook
 */
public class telefonoClienteDAO {
    conexion con = new conexion();
    
    public TelefonoCliente obtenerPorId(int id) {
        TelefonoCliente tel = null;
        Connection acceso = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Esta consulta busca un teléfono específico (aunque la tabla sugiere que el ID es el cliente)
        String sql = "SELECT identificacion_cliente, numero FROM telefono_cliente WHERE identificacion_cliente = ?";

        try {
            acceso = con.getConexion();
            ps = acceso.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                tel = new TelefonoCliente();
                tel.setIdentificacion_cliente(rs.getInt("identificacion_cliente"));
                tel.setNumero(rs.getString("numero"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return tel;
    }
    
    public boolean registrarTelefonoCliente(TelefonoCliente t) {
      Connection acceso = null;
      PreparedStatement ps = null;

      String sql = "INSERT INTO telefono_cliente (identificacion_cliente, numero) "
                  + "VALUES (?, ?)";

      try {
           acceso = con.getConexion();
           ps = acceso.prepareStatement(sql);

           ps.setInt(1, t.getIdentificacion_cliente());
           ps.setString(2, t.getNumero());

           int rows = ps.executeUpdate();
           return rows > 0;

      } catch (Exception e) {
          System.out.println("Error al registrar teléfono: " + e.getMessage());
          return false;

      } finally {
          try {
              if (ps != null) ps.close();
              if (acceso != null) acceso.close();
          } catch (Exception e) {
              System.out.println("Error al cerrar recursos: " + e.getMessage());
          }
      }
    }
    
    // Método clave para obtener todos los teléfonos de un cliente
    public List<TelefonoCliente> obtenerTelefonosPorCliente(int identificacionCliente) {
        List<TelefonoCliente> telefonos = new ArrayList<>();
        Connection accesoConexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT numero FROM telefono_cliente WHERE identificacion_cliente = ?";

        try {
            accesoConexion = con.getConexion();
            if (accesoConexion != null) {
                ps = accesoConexion.prepareStatement(sql);
                ps.setInt(1, identificacionCliente);
                rs = ps.executeQuery();

                while (rs.next()) {
                    TelefonoCliente t = new TelefonoCliente();
                    t.setNumero(rs.getString("numero"));
                    t.setIdentificacion_cliente(identificacionCliente);
                    telefonos.add(t);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener teléfonos de cliente: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (accesoConexion != null) {
                    accesoConexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return telefonos;
    }

}