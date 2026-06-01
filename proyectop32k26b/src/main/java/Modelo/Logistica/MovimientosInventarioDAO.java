package Modelo.Logistica;

import Modelo.Conexion;
import Controlador.Logistica.clsMovimientosInventario;
import Controlador.clsUsuarioConectado;
import Modelo.BitacoraDAO;
import java.sql.*;
import java.util.*;

/**
 * Autor: Anthony Hetzael Suc Gomez
 * Carné: 9959-24-389
 * Fecha de creación: 2026
 *
 * DAO encargado de gestionar las operaciones CRUD
 * de la tabla movimientosinventario.
 */
public class MovimientosInventarioDAO {

    // INSERTAR
    public boolean insertar(clsMovimientosInventario obj) {

        String sql = "INSERT INTO movimientosinventario "
                + "(Prodid, bodegaid, Movtipomovimiento, "
                + "Movmotivo, Movcantidad, Movmarca, "
                + "Movlinea, Movfecha) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getProdid());
            ps.setInt(2, obj.getBodegaid());
            ps.setString(3, obj.getMovtipomovimiento());
            ps.setString(4, obj.getMovmotivo());
            ps.setInt(5, obj.getMovcantidad());
            ps.setString(6, obj.getMovmarca());
            ps.setString(7, obj.getMovlinea());
            ps.setTimestamp(8, obj.getMovfecha());

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Insertó movimiento ID: " + obj.getMovimientoid());
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR
    public boolean actualizar(clsMovimientosInventario obj) {

        String sql = "UPDATE movimientosinventario SET "
                + "Prodid=?, "
                + "bodegaid=?, "
                + "Movtipomovimiento=?, "
                + "Movmotivo=?, "
                + "Movcantidad=?, "
                + "Movmarca=?, "
                + "Movlinea=?, "
                + "Movfecha=? "
                + "WHERE Movimientoid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getProdid());
            ps.setInt(2, obj.getBodegaid());
            ps.setString(3, obj.getMovtipomovimiento());
            ps.setString(4, obj.getMovmotivo());
            ps.setInt(5, obj.getMovcantidad());
            ps.setString(6, obj.getMovmarca());
            ps.setString(7, obj.getMovlinea());
            ps.setTimestamp(8, obj.getMovfecha());
            ps.setInt(9, obj.getMovimientoid());

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Actualizó movimiento ID: " + obj.getMovimientoid());
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {

        String sql = "DELETE FROM movimientosinventario WHERE Movimientoid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Eliminó movimiento ID: " + id);
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LISTAR
    public List<clsMovimientosInventario> listar() {

        List<clsMovimientosInventario> lista = new ArrayList<>();

        String sql = "SELECT * FROM movimientosinventario";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                clsMovimientosInventario obj = new clsMovimientosInventario();

                obj.setMovimientoid(rs.getInt("Movimientoid"));
                obj.setProdid(rs.getInt("Prodid"));
                obj.setBodegaid(rs.getInt("bodegaid"));
                obj.setMovtipomovimiento(rs.getString("Movtipomovimiento"));
                obj.setMovmotivo(rs.getString("Movmotivo"));
                obj.setMovcantidad(rs.getInt("Movcantidad"));
                obj.setMovmarca(rs.getString("Movmarca"));
                obj.setMovlinea(rs.getString("Movlinea"));
                obj.setMovfecha(rs.getTimestamp("Movfecha"));

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID
    public clsMovimientosInventario buscarPorId(int id) {

        String sql = "SELECT * FROM movimientosinventario WHERE Movimientoid=?";

        clsMovimientosInventario obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                obj = new clsMovimientosInventario();

                obj.setMovimientoid(rs.getInt("Movimientoid"));
                obj.setProdid(rs.getInt("Prodid"));
                obj.setBodegaid(rs.getInt("bodegaid"));
                obj.setMovtipomovimiento(rs.getString("Movtipomovimiento"));
                obj.setMovmotivo(rs.getString("Movmotivo"));
                obj.setMovcantidad(rs.getInt("Movcantidad"));
                obj.setMovmarca(rs.getString("Movmarca"));
                obj.setMovlinea(rs.getString("Movlinea"));
                obj.setMovfecha(rs.getTimestamp("Movfecha"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    /**
     * Registrar acción en bitácora
     */
    private void registrarBitacora(String accion) {

        int usuario = clsUsuarioConectado.getUsuId();

        if (usuario == 0) {
            throw new RuntimeException("No hay usuario autenticado");
        }

        BitacoraDAO bitacora = new BitacoraDAO();

        int aplCodigoBitacora = 2001;

        bitacora.insert(usuario, aplCodigoBitacora, accion);
    }
}