package Modelo.Logistica;

import Modelo.Conexion;
import Controlador.Logistica.clsDetallePedidos;
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
 * DAO para la tabla detallepedidos (CRUD completo).
 */
public class DetallePedidosDAO {

    // INSERTAR
    public boolean insertar(clsDetallePedidos obj) {
        String sql = "INSERT INTO detallepedidos (Pedid, Prodid, Detallepedidocantidad) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getPedid());
            ps.setInt(2, obj.getProdid());
            ps.setInt(3, obj.getDetallepedidocantidad());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR
    public boolean actualizar(clsDetallePedidos obj) {
        String sql = "UPDATE detallepedidos SET Pedid=?, Prodid=?, Detallepedidocantidad=? WHERE Detallepedidoid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getPedid());
            ps.setInt(2, obj.getProdid());
            ps.setInt(3, obj.getDetallepedidocantidad());
            ps.setInt(4, obj.getDetallepedidoid());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {
        String sql = "DELETE FROM detallepedidos WHERE Detallepedidoid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LISTAR
    public List<clsDetallePedidos> listar() {
        List<clsDetallePedidos> lista = new ArrayList<>();
        String sql = "SELECT * FROM detallepedidos";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clsDetallePedidos obj = new clsDetallePedidos();

                obj.setDetallepedidoid(rs.getInt("Detallepedidoid"));
                obj.setPedid(rs.getInt("Pedid"));
                obj.setProdid(rs.getInt("Prodid"));
                obj.setDetallepedidocantidad(rs.getInt("Detallepedidocantidad"));

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID
    public clsDetallePedidos buscarPorId(int id) {
        String sql = "SELECT * FROM detallepedidos WHERE Detallepedidoid=?";
        clsDetallePedidos obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new clsDetallePedidos();

                obj.setDetallepedidoid(rs.getInt("Detallepedidoid"));
                obj.setPedid(rs.getInt("Pedid"));
                obj.setProdid(rs.getInt("Prodid"));
                obj.setDetallepedidocantidad(rs.getInt("Detallepedidocantidad"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }
    /**
     * Registra una acción en la bitácora del sistema.
     * 
     * @param accion Descripción de la acción realizada
     */
    private void registrarBitacora(String accion) {

        int usuario = clsUsuarioConectado.getUsuId();

        // Validación de usuario autenticado
        if (usuario == 0) {
            throw new RuntimeException("No hay usuario autenticado");
        }

        BitacoraDAO bitacora = new BitacoraDAO();

        // ID de aplicación para bitácora (debe existir en la BD)
        int aplCodigoBitacora = 2000;

        bitacora.insert(usuario, aplCodigoBitacora, accion);
    }
}