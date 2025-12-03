package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import negocio.Vehiculo;

public class vehiculoDAO {

    conexion con = new conexion();

    // ---------------------------------------------------
    // LISTAR VEHÍCULOS (solo placa + id del conductor)
    // ---------------------------------------------------
    public List<Vehiculo> listarVehiculos() {
        List<Vehiculo> lista = new ArrayList<>();

        String sql = "SELECT placa, identificacion_conductor FROM vehiculo";

        try (Connection acceso = con.getConexion();
             PreparedStatement ps = acceso.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setPlaca(rs.getString("placa"));
                v.setIdentificacion_conductor(rs.getInt("identificacion_conductor"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Error listarVehiculos: " + e.getMessage());
        }

        return lista;
    }

    // ---------------------------------------------------
    // ELIMINAR VEHÍCULO
    // ---------------------------------------------------
    public boolean eliminarVehiculo(String placa) {

        String sql1 = "DELETE FROM marca_vehiculo WHERE placa_vehiculo = ?";
        String sql2 = "DELETE FROM modelo_vehiculo WHERE placa_vehiculo = ?";
        String sql3 = "DELETE FROM tipo_servicio_vehiculo WHERE placa_vehiculo = ?";
        String sql4 = "DELETE FROM vehiculo WHERE placa = ?";

        try (Connection acceso = con.getConexion()) {

            // eliminar relaciones
            PreparedStatement ps1 = acceso.prepareStatement(sql1);
            ps1.setString(1, placa);
            ps1.executeUpdate();

            PreparedStatement ps2 = acceso.prepareStatement(sql2);
            ps2.setString(1, placa);
            ps2.executeUpdate();

            PreparedStatement ps3 = acceso.prepareStatement(sql3);
            ps3.setString(1, placa);
            ps3.executeUpdate();

            // eliminar vehículo
            PreparedStatement ps4 = acceso.prepareStatement(sql4);
            ps4.setString(1, placa);

            int filas = ps4.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error eliminarVehiculo: " + e.getMessage());
        }

        return false;
    }
 
    public List<Vehiculo> listarVehiculosPorConductor(int idConductor) {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo WHERE identificacion_conductor = ?";

        Connection acceso = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            acceso = con.getConexion();
            ps = acceso.prepareStatement(sql);
            ps.setInt(1, idConductor);
            rs = ps.executeQuery();

            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setPlaca(rs.getString("placa"));
                v.setIdentificacion_conductor(rs.getInt("identificacion_conductor"));
                lista.add(v);
            }

        } catch (Exception e) {
            System.out.println("Error listarVehiculosPorConductor: " + e.getMessage());
        }

        return lista;
    }

}

