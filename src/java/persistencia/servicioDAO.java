package persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import negocio.Servicio;
import negocio.Conductor; 
import negocio.TipoServicio; 

public class servicioDAO {
    conexion con = new conexion();

    public List<Servicio> obtenerServiciosPorCliente(int identificacionCliente) {
        List<Servicio> servicios = new ArrayList<>();
        Connection accesoConexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT s.identificacion_servicio, s.direccion_origen, s.direccion_destino, "
                   + "s.estado, "
                   + "c.nombre AS nombre_conductor, "
                   + "ts.nombre AS tipo_servicio_nombre, "
                   + "fp.valor_pago "
                   + "FROM servicio s "
                   + "LEFT JOIN conductor c ON s.identificacion_conductor = c.identificacion "
                   + "LEFT JOIN tiposervicio ts ON s.id_tipo_servicio = ts.id "
                   + "LEFT JOIN forma_pago fp ON s.identificacion_servicio = fp.identificacion_servicio "
                   + "WHERE s.identificacion_cliente = ?";

        try {
            accesoConexion = con.getConexion();
            if (accesoConexion != null) {
                ps = accesoConexion.prepareStatement(sql);
                ps.setInt(1, identificacionCliente);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Servicio s = new Servicio();
                    
                    s.setIdServicio(rs.getInt("identificacion_servicio"));
                    s.setDireccionOrigen(rs.getString("direccion_origen"));
                    s.setDireccionDestino(rs.getString("direccion_destino"));
                    s.setEstado(rs.getString("estado"));
                    
                    s.setNombreConductor(rs.getString("nombre_conductor"));
                    s.setTipoServicioNombre(rs.getString("tipo_servicio_nombre"));
                    s.setValorPago(rs.getDouble("valor_pago"));
                    
                    servicios.add(s);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener servicios de cliente: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (accesoConexion != null) accesoConexion.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return servicios;
    }

    public List<Servicio> obtenerServiciosPendientes(int idConductor) {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT s.identificacion_servicio, s.direccion_origen, s.direccion_destino, "
                   + "c.nombre AS nombreCliente, f.valor_pago, s.estado "
                   + "FROM servicio s "
                   + "LEFT JOIN cliente c ON s.identificacion_cliente = c.identificacion "
                   + "LEFT JOIN forma_pago f ON s.identificacion_servicio = f.identificacion_servicio "
                   + "WHERE s.identificacion_conductor = ? AND LOWER(s.estado) = 'pendiente'";

        try (Connection con = new conexion().getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idConductor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Servicio s = new Servicio();
                    s.setIdServicio(rs.getInt("identificacion_servicio"));
                    s.setDireccionOrigen(rs.getString("direccion_origen"));
                    s.setDireccionDestino(rs.getString("direccion_destino"));
                    s.setNombreCliente(rs.getString("nombreCliente"));
                    s.setValorPago(rs.getDouble("valor_pago"));
                    s.setEstado(rs.getString("estado"));
                    lista.add(s);
                }
            }
        } catch (Exception e) {
            System.out.println("Error obtenerServiciosPendientes: " + e.getMessage());
        }
        return lista;
    }

    public boolean aceptarServicio(int idServicio) {
        String sql = "UPDATE servicio SET estado = 'aceptado' WHERE identificacion_servicio = ?";
        try (Connection con = new conexion().getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idServicio);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error aceptarServicio: " + e.getMessage());
            return false;
        }
    }
    
    public boolean cancelarServicio(int idServicio) {
        String sql = "UPDATE servicio SET estado = 'cancelado' WHERE identificacion_servicio = ?";
        try (Connection con = new conexion().getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idServicio);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error cancelarServicio: " + e.getMessage());
            return false;
        }
    }
    
    public Servicio obtenerServicioPorId(int idServicio) {
        Servicio s = null;
        String sql = "SELECT s.identificacion_servicio, s.direccion_origen, s.direccion_destino, "
                   + "c.nombre AS nombreCliente, f.valor_pago, s.estado, s.id_categoria "
                   + "FROM servicio s "
                   + "JOIN cliente c ON s.identificacion_cliente = c.identificacion "
                   + "JOIN forma_pago f ON s.identificacion_servicio = f.identificacion_servicio "
                   + "WHERE s.identificacion_servicio = ?";

        try (Connection con = new conexion().getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idServicio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    s = new Servicio();
                    s.setIdServicio(rs.getInt("identificacion_servicio"));
                    s.setDireccionOrigen(rs.getString("direccion_origen"));
                    s.setDireccionDestino(rs.getString("direccion_destino"));
                    s.setNombreCliente(rs.getString("nombreCliente"));
                    s.setValorPago(rs.getDouble("valor_pago"));
                    s.setEstado(rs.getString("estado"));
                    s.setIdCategoria(rs.getInt("id_categoria"));
                    s.setDetalles(obtenerDetallesServicio(idServicio));
                }
            }
        } catch (Exception e) {
            System.out.println("Error obtenerServicioPorId: " + e.getMessage());
        }
        return s;
    }
    
    public List<String> obtenerDetallesServicio(int idServicio) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT descripcion FROM detalle_servicio WHERE id_servicio = ?";

        try (Connection con = new conexion().getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idServicio);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("descripcion"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error obtenerDetallesServicio: " + e.getMessage());
        }
        return lista;
    }

    public List<Servicio> obtenerServiciosConductor(int idConductor) {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT s.identificacion_servicio, s.direccion_origen, s.direccion_destino, "
                   + "c.nombre AS nombreCliente, f.valor_pago, s.estado "
                   + "FROM servicio s "
                   + "LEFT JOIN cliente c ON s.identificacion_cliente = c.identificacion "
                   + "LEFT JOIN forma_pago f ON s.identificacion_servicio = f.identificacion_servicio "
                   + "WHERE s.identificacion_conductor = ?";

        try (Connection con = new conexion().getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idConductor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Servicio s = new Servicio();
                    s.setIdServicio(rs.getInt("identificacion_servicio"));
                    s.setDireccionOrigen(rs.getString("direccion_origen"));
                    s.setDireccionDestino(rs.getString("direccion_destino"));
                    s.setNombreCliente(rs.getString("nombreCliente"));
                    s.setValorPago(rs.getDouble("valor_pago"));
                    s.setEstado(rs.getString("estado"));
                    lista.add(s);
                }
            }
        } catch (Exception e) {
            System.out.println("Error obtenerServiciosConductor: " + e.getMessage());
        }
        return lista;
    }
    
    public boolean finalizarServicio(int idServicio) {
        String sql = "UPDATE servicio SET estado = 'entregado' WHERE identificacion_servicio = ?";
        try (Connection con = new conexion().getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idServicio);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error finalizarServicio: " + e.getMessage());
            return false;
        }
    }
    
    public List<Servicio> obtenerServiciosHistorial(int idConductor) {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT s.identificacion_servicio, s.direccion_origen, s.direccion_destino, "
                   + "c.nombre AS nombreCliente, f.valor_pago, s.estado "
                   + "FROM servicio s "
                   + "LEFT JOIN cliente c ON s.identificacion_cliente = c.identificacion "
                   + "LEFT JOIN forma_pago f ON s.identificacion_servicio = f.identificacion_servicio "
                   + "WHERE s.identificacion_conductor = ? AND LOWER(s.estado) IN ('cancelado', 'entregado')";

        try (Connection con = new conexion().getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idConductor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Servicio s = new Servicio();
                    s.setIdServicio(rs.getInt("identificacion_servicio"));
                    s.setDireccionOrigen(rs.getString("direccion_origen"));
                    s.setDireccionDestino(rs.getString("direccion_destino"));
                    s.setNombreCliente(rs.getString("nombreCliente"));
                    s.setValorPago(rs.getDouble("valor_pago"));
                    s.setEstado(rs.getString("estado"));
                    lista.add(s);
                }
            }
        } catch (Exception e) {
            System.out.println("Error obtenerServiciosHistorial: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertarServicio(Servicio s) {
        String sqlServicio = "INSERT INTO servicio "
                           + "(identificacion_cliente, identificacion_conductor, direccion_origen, direccion_destino, id_categoria, estado) "
                           + "VALUES (?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_servicio (id_servicio, descripcion) VALUES (?, ?)";

        try (Connection con = new conexion().getConexion(); PreparedStatement psServicio = con.prepareStatement(sqlServicio, PreparedStatement.RETURN_GENERATED_KEYS)) {

            psServicio.setInt(1, s.getIdCliente());
            psServicio.setInt(2, s.getIdConductor());
            psServicio.setString(3, s.getDireccionOrigen());
            psServicio.setString(4, s.getDireccionDestino());
            psServicio.setInt(5, s.getIdCategoria());
            psServicio.setString(6, s.getEstado());

            int filas = psServicio.executeUpdate();
            if (filas == 0) {
                return false;
            }

            ResultSet rs = psServicio.getGeneratedKeys();
            if (rs.next()) {
                int idServicio = rs.getInt(1);

                try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle)) {
                    for (String desc : s.getDetalles()) {
                        psDetalle.setInt(1, idServicio);
                        psDetalle.setString(2, desc);
                        psDetalle.addBatch();
                    }
                    psDetalle.executeBatch();
                }
            }
            return true;

        } catch (Exception e) {
            System.out.println("Error insertarServicio: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarServicio(Servicio s) {
        String sqlServicio = "INSERT INTO servicio (identificacion_cliente, direccion_origen, direccion_destino, id_categoria, id_tipo_servicio, estado) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_servicio (id_servicio, descripcion) VALUES (?, ?)";

        try (Connection con = new conexion().getConexion(); PreparedStatement ps = con.prepareStatement(sqlServicio, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, s.getIdCliente());
            ps.setString(2, s.getDireccionOrigen());
            ps.setString(3, s.getDireccionDestino());
            ps.setInt(4, s.getIdCategoria());
            ps.setInt(5, s.getIdTipoServicio());
            ps.setString(6, s.getEstado());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                return false;
            }

            int idServicio;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idServicio = rs.getInt(1);
                } else {
                    return false;
                }
            }

            if (s.getDetalles() != null) {
                try (PreparedStatement psDet = con.prepareStatement(sqlDetalle)) {
                    for (String det : s.getDetalles()) {
                        if (det != null && !det.trim().isEmpty()) {
                            psDet.setInt(1, idServicio);
                            psDet.setString(2, det);
                            psDet.addBatch();
                        }
                    }
                    psDet.executeBatch();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
