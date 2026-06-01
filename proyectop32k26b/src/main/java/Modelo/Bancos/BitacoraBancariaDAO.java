package Modelo.Bancos;
// Angoly Camila Araujo Mayen 9959-24-17623 2026
import Controlador.Bancos.clsBitacoraBancaria;
import Controlador.clsUsuarioConectado;
import Modelo.BitacoraDAO;
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BitacoraBancariaDAO {

    private static final int APL_CODIGO = 5800;
    
public boolean deleteAll() {
    String sql = "DELETE FROM BitacoraBancaria";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.executeUpdate();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    

    // ── LISTAR TODOS ────────────────────────────────────────────
    public List<clsBitacoraBancaria> listar() {
        List<clsBitacoraBancaria> lista = new ArrayList<>();
        String sql = "SELECT * FROM BitacoraBancaria";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clsBitacoraBancaria b = new clsBitacoraBancaria();
                b.setBBid(rs.getInt("BBid"));
                b.setBBusuarioaccion(rs.getString("BBusuarioaccion"));
                b.setBBaccion(rs.getString("BBaccion"));
                b.setBBtabla(rs.getString("BBtabla"));
                b.setBBregistroid(rs.getObject("BBregistroid") != null ? rs.getInt("BBregistroid") : null);
                b.setBBvaloranterior(rs.getString("BBvaloranterior"));
                b.setBBvalornuevo(rs.getString("BBvalornuevo"));
                b.setBBfechaaccion(rs.getTimestamp("BBfechaaccion"));
                b.setBBdescripcion(rs.getString("BBdescripcion"));
                lista.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ── INSERTAR ────────────────────────────────────────────────
    public void insert(clsBitacoraBancaria bitacora) {
        String sql = "INSERT INTO BitacoraBancaria (BBusuarioaccion, BBaccion, BBtabla, "
                   + "BBregistroid, BBvaloranterior, BBvalornuevo, BBdescripcion) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bitacora.getBBusuarioaccion());
            ps.setString(2, bitacora.getBBaccion());
            ps.setString(3, bitacora.getBBtabla());
            if (bitacora.getBBregistroid() != null)
                ps.setInt(4, bitacora.getBBregistroid());
            else
                ps.setNull(4, Types.INTEGER);
            ps.setString(5, bitacora.getBBvaloranterior());
            ps.setString(6, bitacora.getBBvalornuevo());
            ps.setString(7, bitacora.getBBdescripcion());
            ps.executeUpdate();

            new BitacoraDAO().insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "INSERT BitacoraBancaria");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al insertar en bitácora bancaria", e);
        }
    }

    // ── ACTUALIZAR ──────────────────────────────────────────────
    public void update(clsBitacoraBancaria bitacora) {
        String sql = "UPDATE BitacoraBancaria SET BBusuarioaccion=?, BBaccion=?, BBtabla=?, "
                   + "BBregistroid=?, BBvaloranterior=?, BBvalornuevo=?, BBdescripcion=? "
                   + "WHERE BBid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bitacora.getBBusuarioaccion());
            ps.setString(2, bitacora.getBBaccion());
            ps.setString(3, bitacora.getBBtabla());
            if (bitacora.getBBregistroid() != null)
                ps.setInt(4, bitacora.getBBregistroid());
            else
                ps.setNull(4, Types.INTEGER);
            ps.setString(5, bitacora.getBBvaloranterior());
            ps.setString(6, bitacora.getBBvalornuevo());
            ps.setString(7, bitacora.getBBdescripcion());
            ps.setInt(8, bitacora.getBBid());
            int rows = ps.executeUpdate();

            if (rows == 0) throw new RuntimeException("No se encontró el registro para actualizar");

            new BitacoraDAO().insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "UPDATE BitacoraBancaria");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar bitácora bancaria", e);
        }
    }

    // ── ELIMINAR ────────────────────────────────────────────────
    public void delete(int idBitacora) {
        String sql = "DELETE FROM BitacoraBancaria WHERE BBid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBitacora);
            int rows = ps.executeUpdate();

            if (rows == 0) throw new RuntimeException("No se encontró el registro para eliminar");

            new BitacoraDAO().insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "DELETE BitacoraBancaria");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar bitácora bancaria", e);
        }
    }

    // ── CONSULTAR POR ID ─────────────────────────────────────────
    public clsBitacoraBancaria query(int idBitacora) {
        clsBitacoraBancaria bitacora = null;
        String sql = "SELECT * FROM BitacoraBancaria WHERE BBid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBitacora);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bitacora = new clsBitacoraBancaria();
                    bitacora.setBBid(rs.getInt("BBid"));
                    bitacora.setBBusuarioaccion(rs.getString("BBusuarioaccion"));
                    bitacora.setBBaccion(rs.getString("BBaccion"));
                    bitacora.setBBtabla(rs.getString("BBtabla"));
                    bitacora.setBBregistroid(rs.getObject("BBregistroid") != null ? rs.getInt("BBregistroid") : null);
                    bitacora.setBBvaloranterior(rs.getString("BBvaloranterior"));
                    bitacora.setBBvalornuevo(rs.getString("BBvalornuevo"));
                    bitacora.setBBfechaaccion(rs.getTimestamp("BBfechaaccion"));
                    bitacora.setBBdescripcion(rs.getString("BBdescripcion"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al consultar bitácora bancaria", e);
        }
        return bitacora;
    }

    // ── BUSCAR POR TABLA ─────────────────────────────────────────
    public List<clsBitacoraBancaria> queryPorTabla(String tabla) {
        List<clsBitacoraBancaria> lista = new ArrayList<>();
        String sql = "SELECT * FROM BitacoraBancaria WHERE BBtabla=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tabla);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clsBitacoraBancaria b = new clsBitacoraBancaria();
                    b.setBBid(rs.getInt("BBid"));
                    b.setBBusuarioaccion(rs.getString("BBusuarioaccion"));
                    b.setBBaccion(rs.getString("BBaccion"));
                    b.setBBtabla(rs.getString("BBtabla"));
                    b.setBBregistroid(rs.getObject("BBregistroid") != null ? rs.getInt("BBregistroid") : null);
                    b.setBBvaloranterior(rs.getString("BBvaloranterior"));
                    b.setBBvalornuevo(rs.getString("BBvalornuevo"));
                    b.setBBfechaaccion(rs.getTimestamp("BBfechaaccion"));
                    b.setBBdescripcion(rs.getString("BBdescripcion"));
                    lista.add(b);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar bitácora por tabla", e);
        }
        return lista;
    }

    private Connection getConexion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

