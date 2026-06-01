package Modelo.Logistica;

import Modelo.Conexion;
import Controlador.Logistica.clsEnvios;
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
 * DAO para la tabla envios (CRUD completo).
 */
public class EnviosDAO {

    // INSERTAR
    public boolean insertar(clsEnvios obj) {
        String sql = "INSERT INTO envios (Pedid, Tranid, Envfechasalida, Envnumeroguia) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getPedid());
            ps.setInt(2, obj.getTranid());
            ps.setTimestamp(3, obj.getEnvfechasalida());
            ps.setString(4, obj.getEnvnumeroguia());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR
    public boolean actualizar(clsEnvios obj) {
        String sql = "UPDATE envios SET Pedid=?, Tranid=?, Envfechasalida=?, Envnumeroguia=? WHERE Envid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getPedid());
            ps.setInt(2, obj.getTranid());
            ps.setTimestamp(3, obj.getEnvfechasalida());
            ps.setString(4, obj.getEnvnumeroguia());
            ps.setInt(5, obj.getEnvid());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {
        String sql = "DELETE FROM envios WHERE Envid=?";

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
    public List<clsEnvios> listar() {
        List<clsEnvios> lista = new ArrayList<>();
        String sql = "SELECT * FROM envios";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clsEnvios obj = new clsEnvios();

                obj.setEnvid(rs.getInt("Envid"));
                obj.setPedid(rs.getInt("Pedid"));
                obj.setTranid(rs.getInt("Tranid"));
                obj.setEnvfechasalida(rs.getTimestamp("Envfechasalida"));
                obj.setEnvnumeroguia(rs.getString("Envnumeroguia"));

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID
    public clsEnvios buscarPorId(int id) {
        String sql = "SELECT * FROM envios WHERE Envid=?";
        clsEnvios obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new clsEnvios();

                obj.setEnvid(rs.getInt("Envid"));
                obj.setPedid(rs.getInt("Pedid"));
                obj.setTranid(rs.getInt("Tranid"));
                obj.setEnvfechasalida(rs.getTimestamp("Envfechasalida"));
                obj.setEnvnumeroguia(rs.getString("Envnumeroguia"));
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