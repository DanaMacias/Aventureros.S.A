package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import negocio.Conductor;
import negocio.Genero;
import negocio.Nacionalidad;

public class conductorDAO {

    conexion con = new conexion();  

    // ======================================
    // VALIDAR LOGIN
    // ======================================
    public int validarLogin(String correo, String clave, Conductor conductor) {
        Connection accesoConexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int count = 0;
        String sql = "SELECT * FROM conductor WHERE correo = ? AND clave = ?";

        try {
            accesoConexion = con.getConexion();
            ps = accesoConexion.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, clave);

            rs = ps.executeQuery();

            if (rs.next()) {
                count++;
                conductor.setIdentificacion(rs.getInt("identificacion"));
                conductor.setNombre(rs.getString("nombre"));
                conductor.setCorreo(rs.getString("correo"));
                conductor.setDireccion(rs.getString("direccion"));
                conductor.setClave(rs.getString("clave"));
                conductor.setFoto(rs.getString("foto"));
            }

        } catch (SQLException e) {
            System.out.println("Error al validar el login: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try { if (accesoConexion != null) accesoConexion.close(); } catch (SQLException e) {}
        }

        return count;
    }

    // ======================================
    // REGISTRAR CONDUCTOR
    // ======================================
    public boolean registrarConductor(Conductor c) {
        Connection acceso = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO conductor "
                + "(identificacion, nombre, correo, clave, direccion, foto, id_nacionalidad, id_genero) "
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

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
            return false;

        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try { if (acceso != null) acceso.close(); } catch (SQLException e) {}
        }
    }

    // ======================================
    // ACTUALIZAR CONDUCTOR
    // ======================================
    public boolean actualizarConductor(Conductor c) {

        String sql = "UPDATE conductor SET "
                + "nombre=?, correo=?, direccion=?, id_genero=?, id_nacionalidad=?, clave=?, foto=? "
                + "WHERE identificacion=?";

        Connection acceso = null;
        PreparedStatement ps = null;

        try {
            acceso = con.getConexion();
            ps = acceso.prepareStatement(sql);

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getCorreo());
            ps.setString(3, c.getDireccion());
            ps.setInt(4, c.getGenero().getId());
            ps.setInt(5, c.getNacionalidad().getId());
            ps.setString(6, c.getClave());
            ps.setString(7, c.getFoto());
            ps.setInt(8, c.getIdentificacion());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL ACTUALIZAR CONDUCTOR: " + e.getMessage());
            return false;

        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try { if (acceso != null) acceso.close(); } catch (SQLException e) {}
        }
    }
    
    public Conductor obtenerConductorPorId(int id) {
    String sql = "SELECT * FROM conductor WHERE identificacion = ?";
    Connection acceso = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    Conductor c = new Conductor();
    
    try {
        acceso = con.getConexion();
        ps = acceso.prepareStatement(sql);
        ps.setInt(1, id);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            c.setIdentificacion(rs.getInt("identificacion"));
            c.setNombre(rs.getString("nombre"));
            c.setCorreo(rs.getString("correo"));
            c.setClave(rs.getString("clave"));
            c.setDireccion(rs.getString("direccion"));
            c.setFoto(rs.getString("foto"));
            
            //  Cargar nacionalidad
            Nacionalidad nac = new Nacionalidad();
            nac.setId(rs.getInt("id_nacionalidad"));
            c.setNacionalidad(nac);

            //  Cargar genero
            Genero g = new Genero();
            g.setId(rs.getInt("id_genero"));
            c.setGenero(g);
        }
    } catch (Exception e) {
        System.out.println("ERROR: " + e.getMessage());
    }
    
    return c;
}

}
