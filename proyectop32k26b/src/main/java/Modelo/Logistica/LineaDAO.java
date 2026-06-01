package Modelo.Logistica;

import Modelo.Conexion;
import Controlador.Logistica.clsLinea;
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
 * DAO para la tabla lineas (CRUD completo).
 */
public class LineaDAO {

    // INSERTAR
    public boolean insertar(clsLinea obj) {

        String sql = "INSERT INTO lineas (linnombre, linestado, lincomision) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, obj.getLinNombre());
            ps.setBoolean(2, obj.isLinEstado());
            ps.setBigDecimal(3, obj.getLinComision());

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Insertó una nueva línea: " + obj.getLinNombre());
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR
    public boolean actualizar(clsLinea obj) {

        String sql = "UPDATE lineas SET linnombre=?, linestado=?, lincomision=? WHERE lineaid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, obj.getLinNombre());
            ps.setBoolean(2, obj.isLinEstado());
            ps.setBigDecimal(3, obj.getLinComision());
            ps.setInt(4, obj.getLineaId());

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Actualizó la línea ID: " + obj.getLineaId());
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {

        String sql = "DELETE FROM lineas WHERE lineaid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Eliminó la línea ID: " + id);
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LISTAR
    public List<clsLinea> listar() {

        List<clsLinea> lista = new ArrayList<>();

        String sql = "SELECT * FROM lineas";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                clsLinea obj = new clsLinea();

                obj.setLineaId(rs.getInt("lineaid"));
                obj.setLinNombre(rs.getString("linnombre"));
                obj.setLinEstado(rs.getBoolean("linestado"));
                obj.setLinComision(rs.getBigDecimal("lincomision"));

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID
    public clsLinea buscarPorId(int id) {

        String sql = "SELECT * FROM lineas WHERE lineaid=?";

        clsLinea obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                obj = new clsLinea();

                obj.setLineaId(rs.getInt("lineaid"));
                obj.setLinNombre(rs.getString("linnombre"));
                obj.setLinEstado(rs.getBoolean("linestado"));
                obj.setLinComision(rs.getBigDecimal("lincomision"));
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
        int aplCodigoBitacora = 2012;

        bitacora.insert(usuario, aplCodigoBitacora, accion);
    }
}