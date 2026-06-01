package Modelo.Bancos;  
//Angoly Camila Araujo Mayen 9959-24-17623
import Controlador.Bancos.clsCliente;
import Controlador.clsUsuarioConectado;
import Modelo.BitacoraDAO;
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientesDAO {

    public void deleteAll() throws Exception {
    try (Connection conn = Modelo.Conexion.getConnection()) {
        conn.createStatement().executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
        conn.createStatement().executeUpdate("DELETE FROM cliente");
        conn.createStatement().executeUpdate("ALTER TABLE cliente AUTO_INCREMENT = 1");
        conn.createStatement().executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
    }
}
    
    private static final int APL_CODIGO = 5400;

    // ── LISTAR TODOS ────────────────────────────────────────────
    public List<clsCliente> listar() {
        List<clsCliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clsCliente c = new clsCliente();
                c.setClid(rs.getInt("Clid"));
                c.setClinombre(rs.getString("Clinombre"));
                c.setClinit(rs.getString("Clinit"));
                c.setClitelefono(rs.getString("Clitelefono"));
                c.setCliestado(rs.getString("Cliestado"));
                c.setClidireccion(rs.getString("Clidireccion"));
                c.setClicorreo(rs.getString("Clicorreo"));
                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ── INSERTAR ────────────────────────────────────────────────
    public void insert(clsCliente cliente) {
        String sql = "INSERT INTO Cliente (Clinombre, Clinit, Clitelefono, "
                   + "Cliestado, Clidireccion, Clicorreo) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getClinombre());
            ps.setString(2, cliente.getClinit());
            ps.setString(3, cliente.getClitelefono());
            ps.setString(4, cliente.getCliestado());
            ps.setString(5, cliente.getClidireccion());
            ps.setString(6, cliente.getClicorreo());
            ps.executeUpdate();

            new BitacoraDAO().insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "INSERT Cliente");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al insertar cliente", e);
        }
    }

    // ── ACTUALIZAR ──────────────────────────────────────────────
    public void update(clsCliente cliente) {
        String sql = "UPDATE Cliente SET Clinombre=?, Clinit=?, Clitelefono=?, "
                   + "Cliestado=?, Clidireccion=?, Clicorreo=? "
                   + "WHERE Clid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getClinombre());
            ps.setString(2, cliente.getClinit());
            ps.setString(3, cliente.getClitelefono());
            ps.setString(4, cliente.getCliestado());
            ps.setString(5, cliente.getClidireccion());
            ps.setString(6, cliente.getClicorreo());
            ps.setInt(7, cliente.getClid());
            int rows = ps.executeUpdate();

            if (rows == 0) throw new RuntimeException("No se encontró el cliente para actualizar");

            new BitacoraDAO().insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "UPDATE Cliente");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar cliente", e);
        }
    }

    // ── ELIMINAR ────────────────────────────────────────────────
    public void delete(int idCliente) {
        String sql = "DELETE FROM Cliente WHERE Clid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            int rows = ps.executeUpdate();

            if (rows == 0) throw new RuntimeException("No se encontró el cliente para eliminar");

            new BitacoraDAO().insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "DELETE Cliente");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar cliente", e);
        }
    }

    // ── CONSULTAR POR ID ─────────────────────────────────────────
    public clsCliente query(int idCliente) {
        clsCliente cliente = null;
        String sql = "SELECT * FROM Cliente WHERE Clid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new clsCliente();
                    cliente.setClid(rs.getInt("Clid"));
                    cliente.setClinombre(rs.getString("Clinombre"));
                    cliente.setClinit(rs.getString("Clinit"));
                    cliente.setClitelefono(rs.getString("Clitelefono"));
                    cliente.setCliestado(rs.getString("Cliestado"));
                    cliente.setClidireccion(rs.getString("Clidireccion"));
                    cliente.setClicorreo(rs.getString("Clicorreo"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al consultar cliente", e);
        }
        return cliente;
    }

    // ── BUSCAR POR NIT ───────────────────────────────────────────
    public clsCliente queryPorNit(String nit) {
        clsCliente cliente = null;
        String sql = "SELECT * FROM Cliente WHERE Clinit=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nit);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new clsCliente();
                    cliente.setClid(rs.getInt("Clid"));
                    cliente.setClinombre(rs.getString("Clinombre"));
                    cliente.setClinit(rs.getString("Clinit"));
                    cliente.setClitelefono(rs.getString("Clitelefono"));
                    cliente.setCliestado(rs.getString("Cliestado"));
                    cliente.setClidireccion(rs.getString("Clidireccion"));
                    cliente.setClicorreo(rs.getString("Clicorreo"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar cliente por NIT", e);
        }
        return cliente;
    }
}

