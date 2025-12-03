    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package persistencia;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.util.ArrayList;
    import java.util.List;
    import negocio.TipoServicio;

    public class tipoServicioDAO {

        Connection con;
        PreparedStatement ps;
        ResultSet rs;

        public List<TipoServicio> listarTiposServicio() {
            List<TipoServicio> lista = new ArrayList<>();
            String sql = "SELECT * FROM tipo_servicio";

            try {
                con = new conexion().getConexion();
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    TipoServicio t = new TipoServicio();
                    t.setId(rs.getInt("id"));
                    t.setTipoServicio(rs.getString("tipo_servicio"));
                    lista.add(t);
                }

            } catch (Exception e) {
                System.out.println("Error listarTiposServicio: " + e);
            }

            return lista;
        }
    }