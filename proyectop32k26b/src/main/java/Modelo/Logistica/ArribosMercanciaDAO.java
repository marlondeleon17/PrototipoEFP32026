package Modelo.Logistica;

import Modelo.Conexion;
import Controlador.Logistica.clsArribosMercancia;
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
 * DAO para la tabla arribosmercancia (CRUD completo).
 */
public class ArribosMercanciaDAO {

    // INSERTAR
    public boolean insertar(clsArribosMercancia obj) {
        String sql = "INSERT INTO arribosmercancia (Procodigo, Arrfechaarribo, Arrestadoverificacion) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getProcodigo());
            ps.setTimestamp(2, obj.getArrfechaarribo());
            ps.setString(3, obj.getArrestadoverificacion());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR
    public boolean actualizar(clsArribosMercancia obj) {
        String sql = "UPDATE arribosmercancia SET Procodigo=?, Arrfechaarribo=?, Arrestadoverificacion=? WHERE Arriboid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getProcodigo());
            ps.setTimestamp(2, obj.getArrfechaarribo());
            ps.setString(3, obj.getArrestadoverificacion());
            ps.setInt(4, obj.getArriboid());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {
        String sql = "DELETE FROM arribosmercancia WHERE Arriboid=?";

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
    public List<clsArribosMercancia> listar() {
        List<clsArribosMercancia> lista = new ArrayList<>();
        String sql = "SELECT * FROM arribosmercancia";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clsArribosMercancia obj = new clsArribosMercancia();

                obj.setArriboid(rs.getInt("Arriboid"));
                obj.setProcodigo(rs.getInt("Procodigo"));
                obj.setArrfechaarribo(rs.getTimestamp("Arrfechaarribo"));
                obj.setArrestadoverificacion(rs.getString("Arrestadoverificacion"));

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID
    public clsArribosMercancia buscarPorId(int id) {
        String sql = "SELECT * FROM arribosmercancia WHERE Arriboid=?";
        clsArribosMercancia obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new clsArribosMercancia();

                obj.setArriboid(rs.getInt("Arriboid"));
                obj.setProcodigo(rs.getInt("Procodigo"));
                obj.setArrfechaarribo(rs.getTimestamp("Arrfechaarribo"));
                obj.setArrestadoverificacion(rs.getString("Arrestadoverificacion"));
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