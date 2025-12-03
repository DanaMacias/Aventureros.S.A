package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import negocio.Administrador;

public class administradorDAO {

    conexion con = new conexion();
    
    public int validarLogin(String correo, String clave, Administrador admin) {

        String sql = "SELECT id, nombre, correo, clave FROM administrador "
                   + "WHERE correo = ? AND clave = ?";

        try (Connection acceso = con.getConexion();
             PreparedStatement ps = acceso.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, clave);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    admin.setIdentificacion(rs.getInt("id"));
                    admin.setNombre(rs.getString("nombre"));
                    admin.setCorreo(rs.getString("correo"));
                    admin.setClave(rs.getString("clave"));
                    return 1;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error validarLogin admin: " + e.getMessage());
        }

        return 0;
    }

    // ---------------------------------------------------------------
    // OBTENER ADMIN POR ID
    // ---------------------------------------------------------------
    public Administrador obtenerPorId(int id) {

        String sql = "SELECT id, nombre, correo, clave FROM administrador WHERE id = ?";
        Administrador admin = null;

        try (Connection acceso = con.getConexion(); PreparedStatement ps = acceso.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    admin = new Administrador();
                    admin.setIdentificacion(rs.getInt("id"));
                    admin.setNombre(rs.getString("nombre"));
                    admin.setCorreo(rs.getString("correo"));
                    admin.setClave(rs.getString("clave"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error obtenerPorId admin: " + e.getMessage());
        }

        return admin;
    }

    // ---------------------------------------------------------------
    // ACTUALIZAR ADMIN
    // ---------------------------------------------------------------
    public boolean actualizarAdministrador(Administrador admin) {

        Connection acceso = null;
        PreparedStatement ps = null;

        String sql = "UPDATE administrador SET nombre = ?, correo = ?, clave = ? WHERE id = ?";

        try {
            acceso = con.getConexion();
            ps = acceso.prepareStatement(sql);

            ps.setString(1, admin.getNombre());
            ps.setString(2, admin.getCorreo());
            ps.setString(3, admin.getClave());
            ps.setInt(4, admin.getIdentificacion());

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar admin: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (acceso != null) {
                    acceso.close();
                }
            } catch (SQLException e) {
            }
        }
    }

    
    
}
