/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import negocio.TelefonoConductor;

/**
 *
 * @author VivoBook
 */
public class telefonoConductorDAO {
    conexion con = new conexion();
   
   public TelefonoConductor obtenerPorId(int id) {
        TelefonoConductor tel = null;
        Connection acceso = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT identificacion_conductor, numero FROM telefono_conductor WHERE identificacion_conductor = ?";

        try {
            acceso = con.getConexion();
            ps = acceso.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                tel = new TelefonoConductor();
                tel.setIdentificacion_conductor(rs.getInt("identificacion_conductor"));
                tel.setNumero(rs.getString("numero"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return tel;
    }
    public boolean registrarTelefonoConductor(TelefonoConductor t) {
     Connection acceso = null;
     PreparedStatement ps = null;

     String sql = "INSERT INTO telefono_conductor (identificacion_conductor, numero) "
                + "VALUES (?, ?)";

     try {
         acceso = con.getConexion();
         ps = acceso.prepareStatement(sql);

         ps.setInt(1, t.getIdentificacion_conductor());
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

