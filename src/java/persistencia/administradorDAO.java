/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import negocio.Administrador;
/**
 *
 * @author VivoBook
 */
public class administradorDAO {
    conexion con = new conexion();
    
    public int validarLogin(String correo, String clave, Administrador administrador) {
        Connection accesoConexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int count = 0; 
        String sql = "SELECT id FROM administrador WHERE correo = ? AND clave = ?";

        try {
            accesoConexion = con.getConexion();
            if (accesoConexion != null) {
                ps = accesoConexion.prepareStatement(sql);
                ps.setString(1, correo);
                ps.setString(2, clave);

                rs = ps.executeQuery();

                while (rs.next()) {
                    count++;
                    administrador.setIdentificacion(rs.getInt("id"));
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
}
