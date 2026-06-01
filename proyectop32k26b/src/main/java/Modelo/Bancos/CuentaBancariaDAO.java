package Modelo.Bancos;

/**
 * Data Access Object para CuentaBancaria
 * Módulo Transaccional - Código de Aplicación: 5500
 *
 * Proporciona operaciones CRUD sobre la tabla CuentaBancaria
 * y registra automáticamente cada acción en BitacoraBancaria.
 *
 * Estructura de tabla CuentaBancaria:
 *   CBANid             INT           PK AUTO_INCREMENT
 *   CBANnumerocuenta   VARCHAR(50)   NOT NULL UNIQUE
 *   CBANsaldoactual    DECIMAL(12,2) DEFAULT 0.00
 *   CBANfechaapertura  DATE          NOT NULL
 *   Banid              INT           FK -> Banco
 *   Cliid              INT           FK -> Cliente
 *   TCidcuenta         INT           FK -> CatTipoCuenta
 *
 * @author Angel Méndez
 * @carnet 9959-24-6845
 * @since 2026-05-10
 */

import Controlador.Bancos.clsCuentaBancaria;
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaBancariaDAO {

    private static final String TABLA = "CuentaBancaria";

    // ─────────────────────────────────────────────────────────────────────
    // INSERT
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Inserta una nueva cuenta bancaria.
     * @param cb    datos de la cuenta
     * @param usuId usuario conectado para bitácora
     * @return filas afectadas
     */
    public int insertar(clsCuentaBancaria cb, int usuId) {
        int resultado = 0;
        String sql = "INSERT INTO CuentaBancaria "
                + "(CBANnumerocuenta, CBANsaldoactual, CBANfechaapertura, Banid, Cliid, TCidcuenta) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cb.getCBANnumerocuenta());
            stmt.setDouble(2, cb.getCBANsaldoactual());
            stmt.setString(3, cb.getCBANfechaapertura());
            stmt.setInt(4, cb.getBanid());
            stmt.setInt(5, cb.getCliid());
            stmt.setInt(6, cb.getTCidcuenta());
            resultado = stmt.executeUpdate();

            if (resultado > 0) {
                int nuevoId = 0;
                ResultSet gk = stmt.getGeneratedKeys();
                if (gk.next()) nuevoId = gk.getInt(1);

                String valorNuevo = "NumCuenta=" + cb.getCBANnumerocuenta()
                        + ", Saldo=" + cb.getCBANsaldoactual()
                        + ", FechaAp=" + cb.getCBANfechaapertura()
                        + ", Banco=" + cb.getBanid()
                        + ", Cliente=" + cb.getCliid()
                        + ", TipoCuenta=" + cb.getTCidcuenta();

                registrarBitacora(usuId, "INSERT", TABLA, nuevoId, null, valorNuevo,
                        "Nueva cuenta bancaria: " + cb.getCBANnumerocuenta());
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return resultado;
    }

    /** Sobrecarga sin usuId */
    public int insertar(clsCuentaBancaria cb) { return insertar(cb, 0); }

    // ─────────────────────────────────────────────────────────────────────
    // SELECT (con JOIN para mostrar nombres)
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Obtiene todas las cuentas bancarias con nombres de banco, cliente y tipo.
     * @return lista de clsCuentaBancaria con campos auxiliares rellenos
     */
    public List<clsCuentaBancaria> seleccionar() {
        List<clsCuentaBancaria> lista = new ArrayList<>();
        String sql =
            "SELECT cb.CBANid, cb.CBANnumerocuenta, cb.CBANsaldoactual, cb.CBANfechaapertura, "
            + "cb.Banid, cb.Cliid, cb.TCidcuenta, "
            + "b.Bannombre, c.Clinombre, tc.TCnombretipo "
            + "FROM CuentaBancaria cb "
            + "INNER JOIN Banco b ON cb.Banid = b.Banid "
            + "INNER JOIN Cliente c ON cb.Cliid = c.Clid "
            + "INNER JOIN CatTipoCuenta tc ON cb.TCidcuenta = tc.TCidcuenta "
            + "ORDER BY cb.CBANid";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clsCuentaBancaria cb = new clsCuentaBancaria(
                        rs.getInt("CBANid"),
                        rs.getString("CBANnumerocuenta"),
                        rs.getDouble("CBANsaldoactual"),
                        rs.getString("CBANfechaapertura"),
                        rs.getInt("Banid"),
                        rs.getInt("Cliid"),
                        rs.getInt("TCidcuenta")
                );
                cb.setNombreBanco(rs.getString("Bannombre"));
                cb.setNombreCliente(rs.getString("Clinombre"));
                cb.setNombreTipoCuenta(rs.getString("TCnombretipo"));
                lista.add(cb);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return lista;
    }

    // ─────────────────────────────────────────────────────────────────────
    // SELECT por ID
    // ─────────────────────────────────────────────────────────────────────

    public clsCuentaBancaria buscarPorId(int id) {
        String sql = "SELECT cb.CBANid, cb.CBANnumerocuenta, cb.CBANsaldoactual, cb.CBANfechaapertura, "
                + "cb.Banid, cb.Cliid, cb.TCidcuenta, "
                + "b.Bannombre, c.Clinombre, tc.TCnombretipo "
                + "FROM CuentaBancaria cb "
                + "INNER JOIN Banco b ON cb.Banid = b.Banid "
                + "INNER JOIN Cliente c ON cb.Cliid = c.Clid "
                + "INNER JOIN CatTipoCuenta tc ON cb.TCidcuenta = tc.TCidcuenta "
                + "WHERE cb.CBANid = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                clsCuentaBancaria cb = new clsCuentaBancaria(
                        rs.getInt("CBANid"),
                        rs.getString("CBANnumerocuenta"),
                        rs.getDouble("CBANsaldoactual"),
                        rs.getString("CBANfechaapertura"),
                        rs.getInt("Banid"),
                        rs.getInt("Cliid"),
                        rs.getInt("TCidcuenta")
                );
                cb.setNombreBanco(rs.getString("Bannombre"));
                cb.setNombreCliente(rs.getString("Clinombre"));
                cb.setNombreTipoCuenta(rs.getString("TCnombretipo"));
                return cb;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return null;
    }

    // ─────────────────────────────────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Actualiza una cuenta bancaria.
     * Registra valor anterior y nuevo en BitacoraBancaria.
     */
    public int actualizar(clsCuentaBancaria cb, int usuId) {
        int resultado = 0;

        clsCuentaBancaria anterior = buscarPorId(cb.getCBANid());
        String valorAnterior = anterior != null
                ? "NumCuenta=" + anterior.getCBANnumerocuenta()
                  + ", Saldo=" + anterior.getCBANsaldoactual()
                  + ", Banco=" + anterior.getBanid()
                  + ", Cliente=" + anterior.getCliid()
                  + ", TipoCuenta=" + anterior.getTCidcuenta()
                : null;

        String sql = "UPDATE CuentaBancaria SET CBANnumerocuenta=?, CBANsaldoactual=?, "
                + "CBANfechaapertura=?, Banid=?, Cliid=?, TCidcuenta=? WHERE CBANid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cb.getCBANnumerocuenta());
            stmt.setDouble(2, cb.getCBANsaldoactual());
            stmt.setString(3, cb.getCBANfechaapertura());
            stmt.setInt(4, cb.getBanid());
            stmt.setInt(5, cb.getCliid());
            stmt.setInt(6, cb.getTCidcuenta());
            stmt.setInt(7, cb.getCBANid());
            resultado = stmt.executeUpdate();

            if (resultado > 0) {
                String valorNuevo = "NumCuenta=" + cb.getCBANnumerocuenta()
                        + ", Saldo=" + cb.getCBANsaldoactual()
                        + ", Banco=" + cb.getBanid()
                        + ", Cliente=" + cb.getCliid()
                        + ", TipoCuenta=" + cb.getTCidcuenta();
                registrarBitacora(usuId, "UPDATE", TABLA, cb.getCBANid(),
                        valorAnterior, valorNuevo,
                        "Actualización cuenta bancaria ID=" + cb.getCBANid());
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return resultado;
    }

    public int actualizar(clsCuentaBancaria cb) { return actualizar(cb, 0); }

    // ─────────────────────────────────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────────────────────────────────

    public int eliminar(int id, int usuId) {
        int resultado = 0;

        clsCuentaBancaria anterior = buscarPorId(id);
        String valorAnterior = anterior != null
                ? "NumCuenta=" + anterior.getCBANnumerocuenta()
                  + ", Saldo=" + anterior.getCBANsaldoactual()
                : null;

        String sql = "DELETE FROM CuentaBancaria WHERE CBANid=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            resultado = stmt.executeUpdate();
            if (resultado > 0) {
                registrarBitacora(usuId, "DELETE", TABLA, id,
                        valorAnterior, null,
                        "Eliminación cuenta bancaria ID=" + id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return resultado;
    }

    public int eliminar(int id) { return eliminar(id, 0); }

    // ─────────────────────────────────────────────────────────────────────
    // SELECT BITÁCORA
    // ─────────────────────────────────────────────────────────────────────

    public List<Object[]> seleccionarBitacora() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT BBid, BBusuarioaccion, BBaccion, BBtabla, BBregistroid, "
                + "BBvaloranterior, BBvalornuevo, BBfechaaccion, BBdescripcion "
                + "FROM BitacoraBancaria WHERE BBtabla = ? ORDER BY BBfechaaccion DESC";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, TABLA);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("BBid"),
                    rs.getInt("BBusuarioaccion"),
                    rs.getString("BBaccion"),
                    rs.getString("BBtabla"),
                    rs.getObject("BBregistroid"),
                    rs.getString("BBvaloranterior"),
                    rs.getString("BBvalornuevo"),
                    rs.getString("BBfechaaccion"),
                    rs.getString("BBdescripcion")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return lista;
    }

    // ─────────────────────────────────────────────────────────────────────
    // HELPER: registrar en BitacoraBancaria
    // ─────────────────────────────────────────────────────────────────────

    private void registrarBitacora(int usuId, String accion, String tabla,
            int registroId, String valorAnterior, String valorNuevo, String descripcion) {

        String sql = "INSERT INTO BitacoraBancaria "
                + "(BBusuarioaccion, BBaccion, BBtabla, BBregistroid, "
                + " BBvaloranterior, BBvalornuevo, BBdescripcion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuId);
            stmt.setString(2, accion);
            stmt.setString(3, tabla);
            stmt.setInt(4, registroId);
            stmt.setString(5, valorAnterior);
            stmt.setString(6, valorNuevo);
            stmt.setString(7, descripcion);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    public List<Object[]> seleccionarBancos() {
    List<Object[]> lista = new ArrayList<>();
    String sql = "SELECT Banid, Bannombre FROM Banco ORDER BY Bannombre";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next())
            lista.add(new Object[]{rs.getInt("Banid"), rs.getString("Bannombre")});
    } catch (SQLException ex) { ex.printStackTrace(System.out); }
    return lista;
}

public List<Object[]> seleccionarClientes() {
    List<Object[]> lista = new ArrayList<>();
    String sql = "SELECT Clid, Clinombre FROM Cliente ORDER BY Clinombre";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next())
            lista.add(new Object[]{rs.getInt("Clid"), rs.getString("Clinombre")});
    } catch (SQLException ex) { ex.printStackTrace(System.out); }
    return lista;
}

public List<Object[]> seleccionarTiposCuenta() {
    List<Object[]> lista = new ArrayList<>();
    String sql = "SELECT TCidcuenta, TCnombretipo FROM CatTipoCuenta ORDER BY TCnombretipo";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next())
            lista.add(new Object[]{rs.getInt("TCidcuenta"), rs.getString("TCnombretipo")});
    } catch (SQLException ex) { ex.printStackTrace(System.out); }
    return lista;
}

public void limpiarTabla() {
    try (Connection conn = Conexion.getConnection();
         Statement stmt = conn.createStatement()) {
        stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
        stmt.executeUpdate("TRUNCATE TABLE CuentaBancaria");
        stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
    } catch (SQLException ex) { ex.printStackTrace(System.out); }
}
}
