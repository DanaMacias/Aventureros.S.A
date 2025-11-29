/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import negocio.TelefonoCliente;

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

}
