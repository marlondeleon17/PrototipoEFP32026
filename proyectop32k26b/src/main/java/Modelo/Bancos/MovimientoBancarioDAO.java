package Modelo.Bancos;

/**
 * Data Access Object para MovimientoBancario
 * Módulo Transaccional - Código de Aplicación: 5600
 *
 * Proporciona operaciones CRUD sobre la tabla MovimientoBancario
 * y registra automáticamente cada acción en BitacoraBancaria.
 * Actualiza el saldo de CuentaBancaria en cada operación.
 *
 * Estructura de tabla MovimientoBancario:
 *   Movbid               INT           PK AUTO_INCREMENT
 *   Movbfechamovimiento  DATETIME      DEFAULT CURRENT_TIMESTAMP
 *   Movibmonto           DECIMAL(12,2) NOT NULL
 *   Movdescripcion       VARCHAR(255)
 *   CBANid               INT           FK -> CuentaBancaria
 *   TTid                 INT           FK -> CatTipoTransaccion
 *   Movbtipomov          VARCHAR(20)   NOT NULL  (Credito / Debito)
 *   Movbreferencia       VARCHAR(50)
 *   Movbconciliado       CHAR(1)       DEFAULT 'N'
 *
 * @author Angel Méndez
 * @carnet 9959-24-6845
 * @since  2026-05-10
 * @update 2026-05-11
 */

import Controlador.Bancos.clsMovimientoBancario;
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientoBancarioDAO {

    private static final String TABLA = "MovimientoBancario";

    // ─────────────────────────────────────────────────────────────────────
    // HELPERS PRIVADOS DE SALDO
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Verifica si la cuenta tiene fondos suficientes para un débito.
     *
     * @param cbanId ID de la cuenta bancaria
     * @param monto  monto a debitar
     * @return true si saldo >= monto
     */
    private boolean tieneFondos(int cbanId, double monto) {
        String sql = "SELECT CBANsaldoactual FROM CuentaBancaria WHERE CBANid = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cbanId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double saldo = rs.getDouble("CBANsaldoactual");
                return saldo >= monto;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return false;
    }

    /**
     * Suma o resta el saldo de una cuenta bancaria.
     *
     * @param cbanId ID de la cuenta
     * @param monto  monto a aplicar
     * @param sumar  true = suma, false = resta
     */
    private void actualizarSaldo(int cbanId, double monto, boolean sumar) {
        String sql = sumar
            ? "UPDATE CuentaBancaria SET CBANsaldoactual = CBANsaldoactual + ? WHERE CBANid = ?"
            : "UPDATE CuentaBancaria SET CBANsaldoactual = CBANsaldoactual - ? WHERE CBANid = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, monto);
            stmt.setInt(2, cbanId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Obtiene el saldo actual de una cuenta bancaria.
     *
     * @param cbanId ID de la cuenta
     * @return saldo actual o 0 si no existe
     */
    public double getSaldoCuenta(int cbanId) {
        String sql = "SELECT CBANsaldoactual FROM CuentaBancaria WHERE CBANid = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cbanId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("CBANsaldoactual");
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return 0;
    }

    // ─────────────────────────────────────────────────────────────────────
    // INSERT
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Inserta un nuevo movimiento bancario y actualiza el saldo.
     * Si es Débito, valida fondos antes de insertar.
     *
     * @param mov   datos del movimiento
     * @param usuId usuario conectado para bitácora
     * @return filas afectadas, -1 si fondos insuficientes
     */
    public int insertar(clsMovimientoBancario mov, int usuId) {
        int resultado = 0;

        // Validar fondos ANTES de insertar si es Debito
        if ("Debito".equals(mov.getMovbtipomov())) {
            if (!tieneFondos(mov.getCBANid(), mov.getMovibmonto())) {
                return -1;
            }
        }

        String sql = "INSERT INTO MovimientoBancario "
                + "(Movibmonto, Movdescripcion, CBANid, TTid, Movbtipomov, Movbreferencia, Movbconciliado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, mov.getMovibmonto());
            stmt.setString(2, mov.getMovdescripcion());
            stmt.setInt(3, mov.getCBANid());
            stmt.setInt(4, mov.getTTid());
            stmt.setString(5, mov.getMovbtipomov());
            stmt.setString(6, mov.getMovbreferencia());
            stmt.setString(7, mov.getMovbconciliado() != null ? mov.getMovbconciliado() : "N");
            resultado = stmt.executeUpdate();

            if (resultado > 0) {
                int nuevoId = 0;
                ResultSet gk = stmt.getGeneratedKeys();
                if (gk.next()) nuevoId = gk.getInt(1);

                // Actualizar saldo: Credito suma, Debito resta
                boolean sumar = "Credito".equals(mov.getMovbtipomov());
                actualizarSaldo(mov.getCBANid(), mov.getMovibmonto(), sumar);

                String valorNuevo = "Monto=" + mov.getMovibmonto()
                        + ", Cuenta=" + mov.getCBANid()
                        + ", TipoTrans=" + mov.getTTid()
                        + ", TipoMov=" + mov.getMovbtipomov()
                        + ", Referencia=" + mov.getMovbreferencia()
                        + ", Conciliado=" + mov.getMovbconciliado();

                registrarBitacora(usuId, "INSERT", TABLA, nuevoId, null, valorNuevo,
                        "Nuevo movimiento: " + mov.getMovbtipomov() + " Q" + mov.getMovibmonto());
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return resultado;
    }

    /** Sobrecarga sin usuId */
    public int insertar(clsMovimientoBancario mov) { return insertar(mov, 0); }

    // ─────────────────────────────────────────────────────────────────────
    // SELECT (con JOINs)
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Obtiene todos los movimientos con número de cuenta y tipo de transacción.
     * @return lista de clsMovimientoBancario con campos auxiliares rellenos
     */
    public List<clsMovimientoBancario> seleccionar() {
        List<clsMovimientoBancario> lista = new ArrayList<>();
        String sql =
            "SELECT m.Movbid, m.Movbfechamovimiento, m.Movibmonto, m.Movdescripcion, "
            + "m.CBANid, m.TTid, m.Movbtipomov, m.Movbreferencia, m.Movbconciliado, "
            + "cb.CBANnumerocuenta, tt.TTnombretipo "
            + "FROM MovimientoBancario m "
            + "INNER JOIN CuentaBancaria cb ON m.CBANid = cb.CBANid "
            + "INNER JOIN CatTipoTransaccion tt ON m.TTid = tt.TTid "
            + "ORDER BY m.Movbid DESC";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clsMovimientoBancario mov = new clsMovimientoBancario(
                        rs.getInt("Movbid"),
                        rs.getString("Movbfechamovimiento"),
                        rs.getDouble("Movibmonto"),
                        rs.getString("Movdescripcion"),
                        rs.getInt("CBANid"),
                        rs.getInt("TTid"),
                        rs.getString("Movbtipomov"),
                        rs.getString("Movbreferencia"),
                        rs.getString("Movbconciliado")
                );
                mov.setNumeroCuenta(rs.getString("CBANnumerocuenta"));
                mov.setNombreTipoTransaccion(rs.getString("TTnombretipo"));
                lista.add(mov);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return lista;
    }

    // ─────────────────────────────────────────────────────────────────────
    // SELECT por ID
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Busca un movimiento por su PK incluyendo datos de JOIN.
     * @param id Movbid
     * @return clsMovimientoBancario o null
     */
    public clsMovimientoBancario buscarPorId(int id) {
        String sql =
            "SELECT m.Movbid, m.Movbfechamovimiento, m.Movibmonto, m.Movdescripcion, "
            + "m.CBANid, m.TTid, m.Movbtipomov, m.Movbreferencia, m.Movbconciliado, "
            + "cb.CBANnumerocuenta, tt.TTnombretipo "
            + "FROM MovimientoBancario m "
            + "INNER JOIN CuentaBancaria cb ON m.CBANid = cb.CBANid "
            + "INNER JOIN CatTipoTransaccion tt ON m.TTid = tt.TTid "
            + "WHERE m.Movbid = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                clsMovimientoBancario mov = new clsMovimientoBancario(
                        rs.getInt("Movbid"),
                        rs.getString("Movbfechamovimiento"),
                        rs.getDouble("Movibmonto"),
                        rs.getString("Movdescripcion"),
                        rs.getInt("CBANid"),
                        rs.getInt("TTid"),
                        rs.getString("Movbtipomov"),
                        rs.getString("Movbreferencia"),
                        rs.getString("Movbconciliado")
                );
                mov.setNumeroCuenta(rs.getString("CBANnumerocuenta"));
                mov.setNombreTipoTransaccion(rs.getString("TTnombretipo"));
                return mov;
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
     * Actualiza un movimiento bancario.
     * Revierte el saldo anterior y aplica el nuevo.
     * Valida fondos si el nuevo movimiento es Débito.
     *
     * @param mov   datos actualizados
     * @param usuId usuario conectado para bitácora
     * @return filas afectadas, -1 si fondos insuficientes
     */
    public int actualizar(clsMovimientoBancario mov, int usuId) {
        int resultado = 0;

        clsMovimientoBancario anterior = buscarPorId(mov.getMovbid());
        String valorAnterior = anterior != null
                ? "Monto=" + anterior.getMovibmonto()
                  + ", Cuenta=" + anterior.getCBANid()
                  + ", TipoMov=" + anterior.getMovbtipomov()
                  + ", Conciliado=" + anterior.getMovbconciliado()
                : null;

        // Validar fondos si el nuevo movimiento es Debito
        if ("Debito".equals(mov.getMovbtipomov()) && anterior != null) {
            double saldoReal = getSaldoCuenta(mov.getCBANid());
            double saldoConReversion = "Debito".equals(anterior.getMovbtipomov())
                ? saldoReal + anterior.getMovibmonto()
                : saldoReal - anterior.getMovibmonto();

            if (saldoConReversion < mov.getMovibmonto()) {
                return -1;
            }
        }

        String sql = "UPDATE MovimientoBancario SET Movibmonto=?, Movdescripcion=?, "
                + "CBANid=?, TTid=?, Movbtipomov=?, Movbreferencia=?, Movbconciliado=? "
                + "WHERE Movbid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, mov.getMovibmonto());
            stmt.setString(2, mov.getMovdescripcion());
            stmt.setInt(3, mov.getCBANid());
            stmt.setInt(4, mov.getTTid());
            stmt.setString(5, mov.getMovbtipomov());
            stmt.setString(6, mov.getMovbreferencia());
            stmt.setString(7, mov.getMovbconciliado());
            stmt.setInt(8, mov.getMovbid());
            resultado = stmt.executeUpdate();

            if (resultado > 0 && anterior != null) {
                // 1. Revertir saldo anterior
                boolean sumarReversion = "Debito".equals(anterior.getMovbtipomov());
                actualizarSaldo(anterior.getCBANid(), anterior.getMovibmonto(), sumarReversion);

                // 2. Aplicar nuevo saldo
                boolean sumarNuevo = "Credito".equals(mov.getMovbtipomov());
                actualizarSaldo(mov.getCBANid(), mov.getMovibmonto(), sumarNuevo);

                String valorNuevo = "Monto=" + mov.getMovibmonto()
                        + ", Cuenta=" + mov.getCBANid()
                        + ", TipoMov=" + mov.getMovbtipomov()
                        + ", Conciliado=" + mov.getMovbconciliado();
                registrarBitacora(usuId, "UPDATE", TABLA, mov.getMovbid(),
                        valorAnterior, valorNuevo,
                        "Actualización movimiento ID=" + mov.getMovbid());
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return resultado;
    }

    /** Sobrecarga sin usuId */
    public int actualizar(clsMovimientoBancario mov) { return actualizar(mov, 0); }

    // ─────────────────────────────────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Elimina un movimiento bancario y revierte el saldo en la cuenta.
     *
     * @param id    Movbid a eliminar
     * @param usuId usuario conectado para bitácora
     * @return filas afectadas
     */
    public int eliminar(int id, int usuId) {
        int resultado = 0;

        clsMovimientoBancario anterior = buscarPorId(id);
        String valorAnterior = anterior != null
                ? "Monto=" + anterior.getMovibmonto()
                  + ", TipoMov=" + anterior.getMovbtipomov()
                  + ", Cuenta=" + anterior.getCBANid()
                : null;

        String sql = "DELETE FROM MovimientoBancario WHERE Movbid=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            resultado = stmt.executeUpdate();
            if (resultado > 0 && anterior != null) {
                // Revertir saldo: si era Debito se suma, si era Credito se resta
                boolean sumar = "Debito".equals(anterior.getMovbtipomov());
                actualizarSaldo(anterior.getCBANid(), anterior.getMovibmonto(), sumar);

                registrarBitacora(usuId, "DELETE", TABLA, id,
                        valorAnterior, null,
                        "Eliminación movimiento ID=" + id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return resultado;
    }

    /** Sobrecarga sin usuId */
    public int eliminar(int id) { return eliminar(id, 0); }

    // ─────────────────────────────────────────────────────────────────────
    // SELECT BITÁCORA
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Obtiene los registros de BitacoraBancaria de MovimientoBancario.
     * @return lista de Object[] con columnas de la bitácora
     */
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
    // COMBOS
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Obtiene cuentas bancarias para cargar en ComboBox.
     * @return lista de [CBANid, CBANnumerocuenta]
     */
    public List<Object[]> seleccionarCuentas() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT CBANid, CBANnumerocuenta FROM CuentaBancaria ORDER BY CBANid ASC";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next())
                lista.add(new Object[]{rs.getInt("CBANid"), rs.getString("CBANnumerocuenta")});
        } catch (SQLException ex) { ex.printStackTrace(System.out); }
        return lista;
    }

    /**
     * Obtiene tipos de transacción para cargar en ComboBox.
     * @return lista de [TTid, TTnombretipo]
     */
    public List<Object[]> seleccionarTiposTransaccion() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT TTid, TTnombretipo FROM CatTipoTransaccion ORDER BY TTid ASC";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next())
                lista.add(new Object[]{rs.getInt("TTid"), rs.getString("TTnombretipo")});
        } catch (SQLException ex) { ex.printStackTrace(System.out); }
        return lista;
    }

    // ─────────────────────────────────────────────────────────────────────
    // LIMPIAR
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Trunca la tabla MovimientoBancario y resetea el AUTO_INCREMENT.
     * Desactiva temporalmente las llaves foráneas.
     */
    public void limpiarTabla() {
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stmt.executeUpdate("TRUNCATE TABLE MovimientoBancario");
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException ex) { ex.printStackTrace(System.out); }
    }

    // ─────────────────────────────────────────────────────────────────────
    // HELPER: registrar en BitacoraBancaria
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Inserta un registro en BitacoraBancaria.
     *
     * @param usuId         usuario que ejecuta la acción
     * @param accion        INSERT, UPDATE o DELETE
     * @param tabla         nombre de la tabla afectada
     * @param registroId    PK del registro
     * @param valorAnterior estado anterior (null en INSERT)
     * @param valorNuevo    estado nuevo (null en DELETE)
     * @param descripcion   texto descriptivo
     */
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
}