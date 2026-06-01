package Modelo.Logistica;

import Modelo.Conexion;
import Controlador.Logistica.clsMarca;
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
 * DAO para la tabla marcas (CRUD completo).
 */
public class MarcaDAO {

    // INSERTAR
    public boolean insertar(clsMarca obj) {

        String sql = "INSERT INTO marcas (marnombre, marestado, marcomision) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, obj.getMarnombre());
            ps.setInt(2, obj.getMarestado());
            ps.setBigDecimal(3, obj.getMarcomision());

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Insertó una nueva marca: " + obj.getMarnombre());
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR
    public boolean actualizar(clsMarca obj) {

        String sql = "UPDATE marcas SET marnombre=?, marestado=?, marcomision=? WHERE marcaid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, obj.getMarnombre());
            ps.setInt(2, obj.getMarestado());
            ps.setBigDecimal(3, obj.getMarcomision());
            ps.setInt(4, obj.getMarcaid());

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Actualizó la marca ID: " + obj.getMarcaid());
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {

        String sql = "DELETE FROM marcas WHERE marcaid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Eliminó la marca ID: " + id);
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LISTAR
    public List<clsMarca> listar() {

        List<clsMarca> lista = new ArrayList<>();

        String sql = "SELECT * FROM marcas";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                clsMarca obj = new clsMarca();

                obj.setMarcaid(rs.getInt("marcaid"));
                obj.setMarnombre(rs.getString("marnombre"));
                obj.setMarestado(rs.getInt("marestado"));
                obj.setMarcomision(rs.getBigDecimal("marcomision"));

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID
    public clsMarca buscarPorId(int id) {

        String sql = "SELECT * FROM marcas WHERE marcaid=?";

        clsMarca obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                obj = new clsMarca();

                obj.setMarcaid(rs.getInt("marcaid"));
                obj.setMarnombre(rs.getString("marnombre"));
                obj.setMarestado(rs.getInt("marestado"));
                obj.setMarcomision(rs.getBigDecimal("marcomision"));
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

        // ID de aplicación para bitácora
        int aplCodigoBitacora = 2011;

        bitacora.insert(usuario, aplCodigoBitacora, accion);
    }
}