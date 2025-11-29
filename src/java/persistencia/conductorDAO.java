/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import negocio.Conductor;

/**
 *
 * @author VivoBook
 */
public class conductorDAO {
    conexion con = new conexion();
    
    public int validarLogin(String correo, String clave, Conductor conductor) {
        Connection accesoConexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int count = 0; 
        String sql = "SELECT identificacion FROM conductor WHERE correo = ? AND clave = ?";

        try {
            accesoConexion = con.getConexion();
            if (accesoConexion != null) {
                ps = accesoConexion.prepareStatement(sql);
                ps.setString(1, correo);
                ps.setString(2, clave);

                rs = ps.executeQuery();

                while (rs.next()) {
                    count++;
                    conductor.setIdentificacion(rs.getInt("identificacion"));
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
    
    public boolean registrarConductor(Conductor c) {
        Connection acceso = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO conductor (identificacion, nombre, correo, clave, direccion, foto, id_nacionalidad, id_genero) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            acceso = con.getConexion();
            ps = acceso.prepareStatement(sql);

            ps.setInt(1, c.getIdentificacion());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getCorreo());
            ps.setString(4, c.getClave());
            ps.setString(5, c.getDireccion());
            ps.setString(6, c.getFoto());
            ps.setInt(7, c.getNacionalidad().getId());
            ps.setInt(8, c.getGenero().getId());

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
}
