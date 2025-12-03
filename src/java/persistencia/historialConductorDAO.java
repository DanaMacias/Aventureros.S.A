package persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import negocio.Conductor;
import negocio.Vehiculo;
import negocio.Nacionalidad;
import negocio.Genero;

public class historialConductorDAO {

    conexion con = new conexion();

    // Obtener datos del conductor
    public Conductor obtenerConductor(int idConductor) {
        Conductor c = null;

        String sql = "SELECT * FROM conductor WHERE identificacion = ?";

        try (Connection cn = con.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idConductor);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Conductor();
                c.setIdentificacion(rs.getInt("identificacion"));
                c.setNombre(rs.getString("nombre"));
                c.setCorreo(rs.getString("correo"));
                c.setDireccion(rs.getString("direccion"));
                c.setFoto(rs.getString("foto"));
                // Nacionalidad
                Nacionalidad nac = new Nacionalidad();
                nac.setId(rs.getInt("id_nacionalidad"));
                c.setNacionalidad(nac);

// Genero
                Genero gen = new Genero();
                gen.setId(rs.getInt("id_genero"));
                c.setGenero(gen);

            }

        } catch (Exception e) {
            System.out.println("Error obtenerConductor: " + e.getMessage());
        }

        return c;
    }


    // Obtener teléfonos del conductor
    public List<String> obtenerTelefonos(int idConductor) {
        List<String> lista = new ArrayList<>();

        String sql = "SELECT numero FROM telefono_conductor WHERE identificacion_conductor = ?";

        try (Connection cn = con.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idConductor);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(rs.getString("numero"));
            }

        } catch (Exception e) {
            System.out.println("Error obtenerTelefonos: " + e.getMessage());
        }

        return lista;
    }


    // Contenido del archivo historialConductorDAO.java (SOLO MÉTODO OBTENERVEHICULOS)

    public List<Vehiculo> obtenerVehiculos(int idConductor) {
        List<Vehiculo> lista = new ArrayList<>();

        // Consulta SQL corregida: Hacemos JOINs a las tablas intermedias (*_vehiculo)
        // y luego a las tablas de catálogo (marca, modelo, tipo_servicio) para obtener los nombres.
        String sql = "SELECT v.placa, m.marca, mo.modelo, ts.tipo_servicio AS servicio "
                + "FROM vehiculo v "
                + "JOIN marca_vehiculo mv ON v.placa = mv.placa_vehiculo "
                + "JOIN marca m ON mv.id_marca = m.id "
                + "JOIN modelo_vehiculo mov ON v.placa = mov.placa_vehiculo "
                + "JOIN modelo mo ON mov.id_modelo = mo.id "
                + "JOIN tipo_servicio_vehiculo tsv ON v.placa = tsv.placa_vehiculo "
                + "JOIN tipo_servicio ts ON tsv.id_tipo_servicio = ts.id " // Corregido a 'id_tipo_servicio' según tu esquema
                + "WHERE v.identificacion_conductor = ?";

        Connection conx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conx = con.getConexion();
            ps = conx.prepareStatement(sql);
            ps.setInt(1, idConductor);
            rs = ps.executeQuery();

            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setPlaca(rs.getString("placa"));
                // Asignamos los valores usando los nombres de las columnas/alias del SELECT
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setServicio(rs.getString("servicio"));
                v.setIdentificacion_conductor(idConductor);

                lista.add(v);
            }

        } catch (SQLException e) {
            System.out.println("ERROR OBTENIENDO VEHÍCULOS: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
            try {
                if (conx != null) {
                    conx.close();
                }
            } catch (Exception e) {
            }
        }

        return lista;
    }


    
    
}


