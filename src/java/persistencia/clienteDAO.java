/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import negocio.Cliente;
import negocio.Genero;
import negocio.Nacionalidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class clienteDAO {
    conexion con = new conexion();
    
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
}
