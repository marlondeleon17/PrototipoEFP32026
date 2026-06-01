package Modelo.Logistica;

import Modelo.Conexion;
import Controlador.Logistica.clsDetalleArribo;
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
 * DAO para la tabla detallearribo (CRUD completo).
 */
public class DetalleArriboDAO {

    // INSERTAR
    public boolean insertar(clsDetalleArribo obj) {
        String sql = "INSERT INTO detallearribo (Arriboid, Prodid, Detarribocantidad, Detarribopreciounitariocompra) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getArriboid());
            ps.setInt(2, obj.getProdid());
            ps.setInt(3, obj.getDetarribocantidad());
            ps.setDouble(4, obj.getDetarribopreciounitariocompra());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR
    public boolean actualizar(clsDetalleArribo obj) {
        String sql = "UPDATE detallearribo SET Arriboid=?, Prodid=?, Detarribocantidad=?, Detarribopreciounitariocompra=? WHERE Detallearriboid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getArriboid());
            ps.setInt(2, obj.getProdid());
            ps.setInt(3, obj.getDetarribocantidad());
            ps.setDouble(4, obj.getDetarribopreciounitariocompra());
            ps.setInt(5, obj.getDetallearriboid());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {
        String sql = "DELETE FROM detallearribo WHERE Detallearriboid=?";

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
    public List<clsDetalleArribo> listar() {
        List<clsDetalleArribo> lista = new ArrayList<>();
        String sql = "SELECT * FROM detallearribo";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clsDetalleArribo obj = new clsDetalleArribo();

                obj.setDetallearriboid(rs.getInt("Detallearriboid"));
                obj.setArriboid(rs.getInt("Arriboid"));
                obj.setProdid(rs.getInt("Prodid"));
                obj.setDetarribocantidad(rs.getInt("Detarribocantidad"));
                obj.setDetarribopreciounitariocompra(rs.getDouble("Detarribopreciounitariocompra"));

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID
    public clsDetalleArribo buscarPorId(int id) {
        String sql = "SELECT * FROM detallearribo WHERE Detallearriboid=?";
        clsDetalleArribo obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new clsDetalleArribo();

                obj.setDetallearriboid(rs.getInt("Detallearriboid"));
                obj.setArriboid(rs.getInt("Arriboid"));
                obj.setProdid(rs.getInt("Prodid"));
                obj.setDetarribocantidad(rs.getInt("Detarribocantidad"));
                obj.setDetarribopreciounitariocompra(rs.getDouble("Detarribopreciounitariocompra"));
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