package Modelo.Logistica;

import Modelo.Conexion;
import Controlador.Logistica.clsPedidos;
import Controlador.clsUsuarioConectado;
import Modelo.BitacoraDAO;
import java.sql.*;
import java.util.*;

/**
 * Autor: Anthony Hetzael Suc Gomez
 * Carné: 9959-24-389
 * Fecha de creación: 2026
 *
 * DAO de pedidos
 */
public class PedidosDAO {

    // INSERTAR
    public boolean insertar(clsPedidos obj) {

        String sql = "INSERT INTO pedidos "
                + "(Cliid, Prodid, Pedcantidad, Pedmarca, Pedlinea, Pedestado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getCliid());
            ps.setInt(2, obj.getProdid());
            ps.setInt(3, obj.getPedcantidad());
            ps.setString(4, obj.getPedmarca());
            ps.setString(5, obj.getPedlinea());
            ps.setString(6, obj.getPedestado());

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Insertó un pedido ID: " + obj.getPedid());
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR
    public boolean actualizar(clsPedidos obj) {

        String sql = "UPDATE pedidos SET "
                + "Cliid=?, "
                + "Prodid=?, "
                + "Pedcantidad=?, "
                + "Pedmarca=?, "
                + "Pedlinea=?, "
                + "Pedestado=? "
                + "WHERE Pedid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getCliid());
            ps.setInt(2, obj.getProdid());
            ps.setInt(3, obj.getPedcantidad());
            ps.setString(4, obj.getPedmarca());
            ps.setString(5, obj.getPedlinea());
            ps.setString(6, obj.getPedestado());
            ps.setInt(7, obj.getPedid());

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Actualizó pedido ID: " + obj.getPedid());
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {

        String sql = "DELETE FROM pedidos WHERE Pedid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            boolean resultado = ps.executeUpdate() > 0;

            if (resultado) {
                registrarBitacora("Eliminó pedido ID: " + id);
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LISTAR
    public List<clsPedidos> listar() {

        List<clsPedidos> lista = new ArrayList<>();

        String sql = "SELECT * FROM pedidos";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                clsPedidos obj = new clsPedidos();

                obj.setPedid(rs.getInt("Pedid"));
                obj.setCliid(rs.getInt("Cliid"));
                obj.setProdid(rs.getInt("Prodid"));
                obj.setPedcantidad(rs.getInt("Pedcantidad"));
                obj.setPedmarca(rs.getString("Pedmarca"));
                obj.setPedlinea(rs.getString("Pedlinea"));
                obj.setPedestado(rs.getString("Pedestado"));

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID
    public clsPedidos buscarPorId(int id) {

        String sql = "SELECT * FROM pedidos WHERE Pedid=?";

        clsPedidos obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                obj = new clsPedidos();

                obj.setPedid(rs.getInt("Pedid"));
                obj.setCliid(rs.getInt("Cliid"));
                obj.setProdid(rs.getInt("Prodid"));
                obj.setPedcantidad(rs.getInt("Pedcantidad"));
                obj.setPedmarca(rs.getString("Pedmarca"));
                obj.setPedlinea(rs.getString("Pedlinea"));
                obj.setPedestado(rs.getString("Pedestado"));
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

        int aplCodigoBitacora = 2003;

        bitacora.insert(usuario, aplCodigoBitacora, accion);
    }
}