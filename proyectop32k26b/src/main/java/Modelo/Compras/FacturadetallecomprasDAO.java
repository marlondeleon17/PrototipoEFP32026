package Modelo.Compras;

import Controlador.Compras.clsFacturadetallecompras;
import Modelo.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la gestión de los detalles de facturas de compras.
 * Maneja operaciones CRUD sobre la tabla facturadetallecompras.
 *
 * Relación:
 * - facturadetallecompras → facturascompras (FK Faccomid)
 * - facturadetallecompras → productos (FK Prodid)
 */
public class FacturadetallecomprasDAO {

    // =========================
    // INSERTAR DETALLE
    // =========================
    public void insert(clsFacturadetallecompras detalle) {

        String sql =
                "INSERT INTO facturadetallecompras "
              + "(Faccomid, Prodid, Faccomcantidad, Faccomprecio, Faccomsubtotal) "
              + "VALUES (?,?,?,?,?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, detalle.getFaccomid());
            ps.setInt(2, detalle.getProdid());
            ps.setDouble(3, detalle.getFaccomcantidad());
            ps.setDouble(4, detalle.getFaccomprecio());
            ps.setDouble(5, detalle.getFaccomsubtotal());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error al insertar detalle de factura", e);
        }
    }

    // =========================
    // ACTUALIZAR DETALLE
    // =========================
    public void update(clsFacturadetallecompras detalle) {

        String sql =
                "UPDATE facturadetallecompras SET "
              + "Faccomid=?, "
              + "Prodid=?, "
              + "Faccomcantidad=?, "
              + "Faccomprecio=?, "
              + "Faccomsubtotal=? "
              + "WHERE Faccomdetid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, detalle.getFaccomid());
            ps.setInt(2, detalle.getProdid());
            ps.setDouble(3, detalle.getFaccomcantidad());
            ps.setDouble(4, detalle.getFaccomprecio());
            ps.setDouble(5, detalle.getFaccomsubtotal());
            ps.setInt(6, detalle.getFaccomdetid());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("No se encontró el detalle a actualizar");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar detalle de factura", e);
        }
    }

    // =========================
    // ELIMINAR DETALLE POR ID
    // =========================
    public void delete(int idDetalle) {

        String sql =
                "DELETE FROM facturadetallecompras WHERE Faccomdetid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDetalle);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("No se encontró el detalle a eliminar");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar detalle de factura", e);
        }
    }

    // =========================
    // CONSULTAR DETALLE POR ID
    // =========================
    public clsFacturadetallecompras query(int idDetalle) {

        clsFacturadetallecompras detalle = null;

        String sql =
                "SELECT * FROM facturadetallecompras WHERE Faccomdetid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDetalle);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    detalle = new clsFacturadetallecompras();

                    detalle.setFaccomdetid(rs.getInt("Faccomdetid"));
                    detalle.setFaccomid(rs.getInt("Faccomid"));
                    detalle.setProdid(rs.getInt("Prodid"));
                    detalle.setFaccomcantidad(rs.getDouble("Faccomcantidad"));
                    detalle.setFaccomprecio(rs.getDouble("Faccomprecio"));
                    detalle.setFaccomsubtotal(rs.getDouble("Faccomsubtotal"));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar detalle de factura", e);
        }

        return detalle;
    }

    // =========================
    // LISTAR DETALLES POR FACTURA
    // =========================
    /**
     * Devuelve todos los detalles pertenecientes a una factura específica.
     *
     * @param idFactura ID de la factura de compra
     * @return lista de detalles asociados
     */
    public List<clsFacturadetallecompras> listarPorFactura(int idFactura) {

        List<clsFacturadetallecompras> lista = new ArrayList<>();

        String sql =
                "SELECT * FROM facturadetallecompras WHERE Faccomid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFactura);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    clsFacturadetallecompras detalle = new clsFacturadetallecompras();

                    detalle.setFaccomdetid(rs.getInt("Faccomdetid"));
                    detalle.setFaccomid(rs.getInt("Faccomid"));
                    detalle.setProdid(rs.getInt("Prodid"));
                    detalle.setFaccomcantidad(rs.getDouble("Faccomcantidad"));
                    detalle.setFaccomprecio(rs.getDouble("Faccomprecio"));
                    detalle.setFaccomsubtotal(rs.getDouble("Faccomsubtotal"));

                    lista.add(detalle);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al listar detalles por factura", e);
        }

        return lista;
    }

    // =========================
    // ELIMINAR POR FACTURA (CASCADE MANUAL)
    // =========================
    /**
     * Elimina todos los detalles asociados a una factura.
     * Útil antes de eliminar la factura principal.
     */
    public void deleteByFactura(int idFactura) {

        String sql =
                "DELETE FROM facturadetallecompras WHERE Faccomid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFactura);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar detalles por factura", e);
        }
    }
    // =========================
// LISTAR TODOS LOS DETALLES
// =========================
public List<clsFacturadetallecompras> listar() {

    List<clsFacturadetallecompras> lista = new ArrayList<>();

    String sql =
            "SELECT Faccomdetid, Faccomid, Prodid, "
          + "Faccomcantidad, Faccomprecio, Faccomsubtotal "
          + "FROM facturadetallecompras";

    try (Connection conn = Conexion.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            clsFacturadetallecompras detalle =
                    new clsFacturadetallecompras();

            detalle.setFaccomdetid(rs.getInt("Faccomdetid"));
            detalle.setFaccomid(rs.getInt("Faccomid"));
            detalle.setProdid(rs.getInt("Prodid"));
            detalle.setFaccomcantidad(rs.getDouble("Faccomcantidad"));
            detalle.setFaccomprecio(rs.getDouble("Faccomprecio"));
            detalle.setFaccomsubtotal(rs.getDouble("Faccomsubtotal"));

            lista.add(detalle);
        }

    } catch (Exception e) {
        throw new RuntimeException("Error al listar detalles de compras", e);
    }

    return lista;
}
}