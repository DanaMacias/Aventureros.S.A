/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import negocio.Nacionalidad;

/**
 *
 * @author VivoBook
 */
public class nacionalidadDAO {

    conexion con = new conexion();

    public List<Nacionalidad> listarNacionalidades() {
        List<Nacionalidad> lista = new ArrayList<>();
        Connection acceso = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, nacionalidad FROM nacionalidad";

        try {
            acceso = con.getConexion();
            ps = acceso.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Nacionalidad n = new Nacionalidad();
                n.setId(rs.getInt("id"));
                n.setNacionalidad(rs.getString("nacionalidad"));
                lista.add(n);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar nacionalidades: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (acceso != null) acceso.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return lista;
    }
    
    public Nacionalidad obtenerPorId(int id) {
        Nacionalidad nacionalidad = null;
        Connection acceso = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, nacionalidad FROM nacionalidad WHERE id = ?";

        try {
            acceso = con.getConexion();
            ps = acceso.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                nacionalidad = new Nacionalidad();
                nacionalidad.setId(rs.getInt("id"));
                nacionalidad.setNacionalidad(rs.getString("nacionalidad"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return nacionalidad;
    }

}


