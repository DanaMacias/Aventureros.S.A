package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import negocio.Conductor;
import negocio.Genero;
import negocio.Nacionalidad;
import negocio.Vehiculo;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

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

    public List<Conductor> listarConductores() {
        List<Conductor> lista = new ArrayList<>();

        String sql = "SELECT * FROM conductor";
        Connection acceso = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            acceso = con.getConexion();
            ps = acceso.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Conductor c = new Conductor();

                c.setIdentificacion(rs.getInt("identificacion"));
                c.setNombre(rs.getString("nombre"));
                c.setCorreo(rs.getString("correo"));
                c.setDireccion(rs.getString("direccion"));
                c.setClave(rs.getString("clave"));
                c.setFoto(rs.getString("foto"));

                // nacionalidad
                Nacionalidad n = new Nacionalidad();
                n.setId(rs.getInt("id_nacionalidad"));
                c.setNacionalidad(n);

                // género
                Genero g = new Genero();
                g.setId(rs.getInt("id_genero"));
                c.setGenero(g);

                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL LISTAR CONDUCTORES: " + e.getMessage());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (acceso != null) {
                    acceso.close();
                }
            } catch (SQLException e) {
            }
        }

        return lista;
    }
    
    public List<Conductor> listarConductoresDetallado() {
        java.util.Map<Integer, Conductor> mapaConductores = new java.util.LinkedHashMap<>();

        String sql = "SELECT * FROM reporte_conductores_detallado ORDER BY id_conductor";

        Connection conx = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conx = con.getConexion();
            ps = conx.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idConductor = rs.getInt("id_conductor");

                Conductor c = mapaConductores.get(idConductor);
                if (c == null) {
                    c = new Conductor();
                    c.setIdentificacion(idConductor);
                    c.setNombre(rs.getString("nombre_conductor"));
                    c.setCorreo(rs.getString("correo"));
                    c.setDireccion(rs.getString("direccion"));

                    negocio.Nacionalidad nacionalidad = new negocio.Nacionalidad();
                    nacionalidad.setNacionalidad(rs.getString("nombre_nacionalidad"));
                    c.setNacionalidad(nacionalidad);

                    negocio.Genero genero = new negocio.Genero();
                    genero.setGenero(rs.getString("nombre_genero"));
                    c.setGenero(genero);

                    c.setVehiculos(new ArrayList<>());
                    mapaConductores.put(idConductor, c);
                }

                String placa = rs.getString("placa");
                if (placa != null) {
                    negocio.Vehiculo v = new negocio.Vehiculo();
                    v.setPlaca(placa);
                    v.setMarca(rs.getString("nombre_marca"));
                    v.setModelo(rs.getString("nombre_modelo"));
                    v.setServicio(rs.getString("nombre_servicio"));
                    v.setIdentificacion_conductor(idConductor);

                    c.getVehiculos().add(v);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error listarConductoresDetallado desde VIEW: " + e.getMessage());
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
        return new ArrayList<>(mapaConductores.values());
    }

    public void crearVistaReporteDetallado() {
        Connection acceso = null;
        Statement st = null;

        String dropSql = "DROP VIEW IF EXISTS reporte_conductores_detallado";

        String createSql = "CREATE VIEW reporte_conductores_detallado AS "
                + "SELECT "
                + "    c.identificacion AS id_conductor, "
                + "    c.nombre AS nombre_conductor, "
                + "    c.correo, "
                + "    c.direccion, "
                + "    n.nacionalidad AS nombre_nacionalidad, "
                + "    g.genero AS nombre_genero, "
                + "    v.placa, "
                + "    m.marca AS nombre_marca, "
                + "    mo.modelo AS nombre_modelo, "
                + "    ts.tipo_servicio AS nombre_servicio "
                + "FROM "
                + "    conductor c "
                + "JOIN nacionalidad n ON c.id_nacionalidad = n.id "
                + "JOIN genero g ON c.id_genero = g.id "
                + "LEFT JOIN vehiculo v ON c.identificacion = v.identificacion_conductor "
                + "LEFT JOIN marca_vehiculo mv ON v.placa = mv.placa_vehiculo "
                + "LEFT JOIN marca m ON mv.id_marca = m.id "
                + "LEFT JOIN modelo_vehiculo mov ON v.placa = mov.placa_vehiculo "
                + "LEFT JOIN modelo mo ON mov.id_modelo = mo.id "
                + "LEFT JOIN tipo_servicio_vehiculo tsv ON v.placa = tsv.placa_vehiculo "
                + "LEFT JOIN tipo_servicio ts ON tsv.id_tipo_servicio = ts.id";

        try {
            acceso = con.getConexion();
            st = acceso.createStatement();

            st.executeUpdate(dropSql);
            st.executeUpdate(createSql);

            System.out.println("✅ Vista reporte_conductores_detallado creada exitosamente.");

        } catch (SQLException e) {
            System.err.println("❌ Error al crear la vista SQL: " + e.getMessage());
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (acceso != null) {
                    acceso.close();
                }
            } catch (SQLException e) {
            }
        }
    }
}
