package Modelo.Logistica;

import Modelo.Conexion;
import Controlador.Logistica.clsProductos;
import Controlador.clsUsuarioConectado;
import Modelo.BitacoraDAO;
import java.sql.*;
import java.util.*;

/**
 * Autor: Anthony Hetzael Suc Gomez
 * Carné: 9959-24-389
 * Fecha de creación: 2026
 * 
 * Descripción:
 * DAO encargado de gestionar las operaciones CRUD de la tabla productos.
 * Permite insertar, actualizar, eliminar, listar y buscar registros en la base de datos.
 */
public class ProductosDAO {

    // INSERTAR
    public boolean insertar(clsProductos obj) {

        String sql = "INSERT INTO productos "
                + "(Prodnombre, Prodstockactual, Prodpuntoreorden, "
                + "Prodprecioventa, lineaid, marcaid, Prodcomision) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, obj.getProdNombre());
            ps.setInt(2, obj.getProdStockActual());
            ps.setInt(3, obj.getProdPuntoReorden());
            ps.setBigDecimal(4, obj.getProdPrecioVenta());
            ps.setInt(5, obj.getLineaId());
            ps.setInt(6, obj.getMarcaId());
            ps.setBigDecimal(7, obj.getProdcomision());

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Insertó un nuevo producto: " + obj.getProdNombre());
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR
    public boolean actualizar(clsProductos obj) {

        String sql = "UPDATE productos SET "
                + "Prodnombre=?, "
                + "Prodstockactual=?, "
                + "Prodpuntoreorden=?, "
                + "Prodprecioventa=?, "
                + "lineaid=?, "
                + "marcaid=?, "
                + "Prodcomision=? "
                + "WHERE Prodid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, obj.getProdNombre());
            ps.setInt(2, obj.getProdStockActual());
            ps.setInt(3, obj.getProdPuntoReorden());
            ps.setBigDecimal(4, obj.getProdPrecioVenta());
            ps.setInt(5, obj.getLineaId());
            ps.setInt(6, obj.getMarcaId());
            ps.setBigDecimal(7, obj.getProdcomision());
            ps.setInt(8, obj.getProdId());

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Actualizó el producto ID: " + obj.getProdId());
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {

        String sql = "DELETE FROM productos WHERE Prodid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Eliminó el producto ID: " + id);
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LISTAR
    public List<clsProductos> listar() {

        List<clsProductos> lista = new ArrayList<>();

        String sql = "SELECT * FROM productos";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                clsProductos obj = new clsProductos();

                obj.setProdId(rs.getInt("Prodid"));
                obj.setProdNombre(rs.getString("Prodnombre"));
                obj.setProdStockActual(rs.getInt("Prodstockactual"));
                obj.setProdPuntoReorden(rs.getInt("Prodpuntoreorden"));
                obj.setProdPrecioVenta(rs.getBigDecimal("Prodprecioventa"));
                obj.setLineaId(rs.getInt("lineaid"));
                obj.setMarcaId(rs.getInt("marcaid"));
                obj.setProdcomision(rs.getBigDecimal("Prodcomision"));

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID
    public clsProductos buscarPorId(int id) {

        String sql = "SELECT * FROM productos WHERE Prodid=?";

        clsProductos obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                obj = new clsProductos();

                obj.setProdId(rs.getInt("Prodid"));
                obj.setProdNombre(rs.getString("Prodnombre"));
                obj.setProdStockActual(rs.getInt("Prodstockactual"));
                obj.setProdPuntoReorden(rs.getInt("Prodpuntoreorden"));
                obj.setProdPrecioVenta(rs.getBigDecimal("Prodprecioventa"));
                obj.setLineaId(rs.getInt("lineaid"));
                obj.setMarcaId(rs.getInt("marcaid"));
                obj.setProdcomision(rs.getBigDecimal("Prodcomision"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    // BITACORA
    private void registrarBitacora(String accion) {

        int usuario = clsUsuarioConectado.getUsuId();

        if (usuario == 0) {
            throw new RuntimeException("No hay usuario autenticado");
        }

        BitacoraDAO bitacora = new BitacoraDAO();

        int aplCodigoBitacora = 2010;

        bitacora.insert(usuario, aplCodigoBitacora, accion);
    }
}