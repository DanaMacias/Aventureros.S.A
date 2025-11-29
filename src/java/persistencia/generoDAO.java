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
import negocio.Genero;
/**
 *
 * @author VivoBook
 */
public class generoDAO {

    conexion con = new conexion();

    public List<Genero> listarGeneros() {
        List<Genero> lista = new ArrayList<>();
        Connection acceso = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, genero FROM genero";

        try {
            acceso = con.getConexion();
            ps = acceso.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Genero g = new Genero();
                g.setId(rs.getInt("id"));
                g.setGenero(rs.getString("genero"));
                lista.add(g);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar géneros: " + e.getMessage());
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
    
    public Genero obtenerPorId(int id) {
        Genero genero = null;
        Connection acceso = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, genero FROM genero WHERE id = ?";

        try {
            acceso = con.getConexion();
            ps = acceso.prepareStatement(sql);

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                genero = new Genero();
                genero.setId(rs.getInt("id"));
                genero.setGenero(rs.getString("genero"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return genero;
    }


}

