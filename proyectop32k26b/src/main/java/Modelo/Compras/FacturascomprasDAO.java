package Modelo.Compras;

import Controlador.Compras.clsFacturascompras;
import Modelo.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO de facturas de compras.
 *
 * Responsabilidad:
 * - CRUD de facturascompras
 * - Manejo de cabecera de factura
 *
 * Relación:
 * facturascompras (1) → facturadetallecompras (N)
 */
public class FacturascomprasDAO {

    // =========================
    // LISTAR TODAS LAS FACTURAS
    // =========================
    public List<clsFacturascompras> listar() {

        List<clsFacturascompras> lista = new ArrayList<>();

        String sql =
                "SELECT Faccomid, Faccomnumero, Faccomfecha, Procodigo, "
              + "Faccomsubtotal, Faccomiva, Faccomtotal, Faccomestado "
              + "FROM facturascompras";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                clsFacturascompras factura = new clsFacturascompras();

                factura.setFaccomid(rs.getInt("Faccomid"));
                factura.setFaccomnumero(rs.getString("Faccomnumero"));
                factura.setFaccomfecha(rs.getTimestamp("Faccomfecha"));
                factura.setProcodigo(rs.getInt("Procodigo"));
                factura.setFaccomsubtotal(rs.getDouble("Faccomsubtotal"));
                factura.setFaccomiva(rs.getDouble("Faccomiva"));
                factura.setFaccomtotal(rs.getDouble("Faccomtotal"));
                factura.setFaccomestado(rs.getString("Faccomestado"));

                lista.add(factura);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al listar facturas", e);
        }

        return lista;
    }

    // =========================
    // INSERTAR FACTURA
    // =========================
    /**
     * Inserta una factura y devuelve su ID generado.
     */
    public int insert(clsFacturascompras factura) {

        String sql =
                "INSERT INTO facturascompras "
              + "(Faccomnumero, Procodigo, Faccomsubtotal, Faccomiva, Faccomtotal, Faccomestado) "
              + "VALUES (?,?,?,?,?,?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, factura.getFaccomnumero());
            ps.setInt(2, factura.getProcodigo());
            ps.setDouble(3, factura.getFaccomsubtotal());
            ps.setDouble(4, factura.getFaccomiva());
            ps.setDouble(5, factura.getFaccomtotal());
            ps.setString(6, factura.getFaccomestado());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al insertar factura", e);
        }

        return -1;
    }

    // =========================
    // ACTUALIZAR FACTURA
    // =========================
    public void update(clsFacturascompras factura) {

        String sql =
                "UPDATE facturascompras SET "
              + "Faccomnumero=?, "
              + "Procodigo=?, "
              + "Faccomsubtotal=?, "
              + "Faccomiva=?, "
              + "Faccomtotal=?, "
              + "Faccomestado=? "
              + "WHERE Faccomid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, factura.getFaccomnumero());
            ps.setInt(2, factura.getProcodigo());
            ps.setDouble(3, factura.getFaccomsubtotal());
            ps.setDouble(4, factura.getFaccomiva());
            ps.setDouble(5, factura.getFaccomtotal());
            ps.setString(6, factura.getFaccomestado());
            ps.setInt(7, factura.getFaccomid());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("No se encontró la factura");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar factura", e);
        }
    }

    // =========================
    // ELIMINAR FACTURA
    // =========================
    public void delete(int idFactura) {

        String sql =
                "DELETE FROM facturascompras WHERE Faccomid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFactura);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("No se encontró la factura");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar factura", e);
        }
    }

    // =========================
    // CONSULTAR FACTURA POR ID
    // =========================
    public clsFacturascompras query(int idFactura) {

        clsFacturascompras factura = null;

        String sql =
                "SELECT * FROM facturascompras WHERE Faccomid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFactura);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    factura = new clsFacturascompras();

                    factura.setFaccomid(rs.getInt("Faccomid"));
                    factura.setFaccomnumero(rs.getString("Faccomnumero"));
                    factura.setFaccomfecha(rs.getTimestamp("Faccomfecha"));
                    factura.setProcodigo(rs.getInt("Procodigo"));
                    factura.setFaccomsubtotal(rs.getDouble("Faccomsubtotal"));
                    factura.setFaccomiva(rs.getDouble("Faccomiva"));
                    factura.setFaccomtotal(rs.getDouble("Faccomtotal"));
                    factura.setFaccomestado(rs.getString("Faccomestado"));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar factura", e);
        }

        return factura;
    }
}
