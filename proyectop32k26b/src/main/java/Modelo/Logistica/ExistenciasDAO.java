package Modelo.Logistica;

import Modelo.Conexion;
import Controlador.Logistica.clsExistencias;
import Controlador.clsUsuarioConectado;
import Modelo.BitacoraDAO;
import java.sql.*;
import java.util.*;

/**
 * Autor: Anthony Hetzael Suc Gomez
 * Carné: 9959-24-389
 * Fecha de creación: 2026
 *
 * DAO encargado de gestionar las operaciones CRUD de la tabla existencias.
 */
public class ExistenciasDAO {

    // INSERTAR
    public boolean insertar(clsExistencias obj) {

        String sql = "INSERT INTO existencias "
                + "(Prodid, Exnombreproducto, bodegaid, Existock, Exmarca, Exlinea) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getProdid());
            ps.setString(2, obj.getExnombreproducto());
            ps.setInt(3, obj.getBodegaid());
            ps.setInt(4, obj.getExistock());
            ps.setString(5, obj.getExmarca());
            ps.setString(6, obj.getExlinea());

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Insertó existencia ID: " + obj.getExistenciaid());
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR
    public boolean actualizar(clsExistencias obj) {

        String sql = "UPDATE existencias SET "
                + "Prodid=?, "
                + "Exnombreproducto=?, "
                + "bodegaid=?, "
                + "Existock=?, "
                + "Exmarca=?, "
                + "Exlinea=? "
                + "WHERE Existenciaid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getProdid());
            ps.setString(2, obj.getExnombreproducto());
            ps.setInt(3, obj.getBodegaid());
            ps.setInt(4, obj.getExistock());
            ps.setString(5, obj.getExmarca());
            ps.setString(6, obj.getExlinea());
            ps.setInt(7, obj.getExistenciaid());

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Actualizó existencia ID: " + obj.getExistenciaid());
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {

        String sql = "DELETE FROM existencias WHERE Existenciaid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Eliminó existencia ID: " + id);
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LISTAR
    public List<clsExistencias> listar() {

        List<clsExistencias> lista = new ArrayList<>();

        String sql = "SELECT * FROM existencias";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                clsExistencias obj = new clsExistencias();

                obj.setExistenciaid(rs.getInt("Existenciaid"));
                obj.setProdid(rs.getInt("Prodid"));
                obj.setExnombreproducto(rs.getString("Exnombreproducto"));
                obj.setBodegaid(rs.getInt("bodegaid"));
                obj.setExistock(rs.getInt("Existock"));
                obj.setExmarca(rs.getString("Exmarca"));
                obj.setExlinea(rs.getString("Exlinea"));

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID
    public clsExistencias buscarPorId(int id) {

        String sql = "SELECT * FROM existencias WHERE Existenciaid=?";

        clsExistencias obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                obj = new clsExistencias();

                obj.setExistenciaid(rs.getInt("Existenciaid"));
                obj.setProdid(rs.getInt("Prodid"));
                obj.setExnombreproducto(rs.getString("Exnombreproducto"));
                obj.setBodegaid(rs.getInt("bodegaid"));
                obj.setExistock(rs.getInt("Existock"));
                obj.setExmarca(rs.getString("Exmarca"));
                obj.setExlinea(rs.getString("Exlinea"));
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

        int aplCodigoBitacora = 2002;

        bitacora.insert(usuario, aplCodigoBitacora, accion);
    }
}