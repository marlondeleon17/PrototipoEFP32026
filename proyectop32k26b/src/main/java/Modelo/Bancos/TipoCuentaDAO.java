package Modelo.Bancos;

/**
 * Data Access Object para CatTipoCuenta
 * Módulo Maestro - Código de Aplicación: 5000
 *
 * Proporciona operaciones CRUD sobre la tabla CatTipoCuenta
 * y registra automáticamente cada acción en BitacoraBancaria.
 *
 * Estructura de tabla CatTipoCuenta:
 *   TCidcuenta   INT          PK AUTO_INCREMENT
 *   TCnombretipo VARCHAR(50)  NOT NULL UNIQUE
 *   TCdescripcion VARCHAR(150)
 *
 * Estructura de tabla BitacoraBancaria:
 *   BBid, BBusuarioaccion, BBaccion, BBtabla,
 *   BBregistroid, BBvaloranterior, BBvalornuevo,
 *   BBfechaaccion, BBdescripcion
 *
 * @author Angel Méndez
 * @carnet 9959-24-6845
 * @since 2026-05-10
 */

import Controlador.Bancos.clsTipoCuenta;
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoCuentaDAO {

    // ── Nombre de tabla para bitácora ─────────────────────────────────────
    private static final String TABLA = "CatTipoCuenta";

    // ─────────────────────────────────────────────────────────────────────
    // INSERT
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Inserta un nuevo tipo de cuenta.
     * Registra la acción en BitacoraBancaria con BBvaloranterior = NULL
     * y BBvalornuevo = datos insertados.
     *
     * @param tc objeto con TCnombretipo y TCdescripcion
     * @param usuId ID del usuario conectado (para bitácora)
     * @return filas afectadas
     */
    public int insertar(clsTipoCuenta tc, int usuId) {
        int resultado = 0;
        String sql = "INSERT INTO CatTipoCuenta (TCnombretipo, TCdescripcion) VALUES (?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tc.getTCnombretipo());
            stmt.setString(2, tc.getTCdescripcion());
            resultado = stmt.executeUpdate();

            if (resultado > 0) {
                // Obtener ID generado para la bitácora
                int nuevoId = 0;
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) nuevoId = generatedKeys.getInt(1);

                String valorNuevo = "Nombre=" + tc.getTCnombretipo()
                        + ", Descripcion=" + tc.getTCdescripcion();

                registrarBitacora(usuId, "INSERT", TABLA, nuevoId, null, valorNuevo,
                        "Inserción de tipo de cuenta: " + tc.getTCnombretipo());
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return resultado;
    }

    /** Sobrecarga para compatibilidad con clsTipoCuenta.setInsertar() sin usuId */
    public int insertar(clsTipoCuenta tc) {
        return insertar(tc, 0);
    }

    // ─────────────────────────────────────────────────────────────────────
    // SELECT (listado completo)
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Obtiene todos los tipos de cuenta.
     * @return lista de clsTipoCuenta
     */
    public List<clsTipoCuenta> seleccionar() {
        List<clsTipoCuenta> lista = new ArrayList<>();
        String sql = "SELECT TCidcuenta, TCnombretipo, TCdescripcion FROM CatTipoCuenta ORDER BY TCidcuenta";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new clsTipoCuenta(
                        rs.getInt("TCidcuenta"),
                        rs.getString("TCnombretipo"),
                        rs.getString("TCdescripcion")
                ));
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
     * Busca un tipo de cuenta por su PK.
     * @param id TCidcuenta
     * @return objeto clsTipoCuenta o null
     */
    public clsTipoCuenta buscarPorId(int id) {
        String sql = "SELECT TCidcuenta, TCnombretipo, TCdescripcion FROM CatTipoCuenta WHERE TCidcuenta = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new clsTipoCuenta(
                        rs.getInt("TCidcuenta"),
                        rs.getString("TCnombretipo"),
                        rs.getString("TCdescripcion")
                );
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
     * Actualiza un tipo de cuenta existente.
     * Registra valor anterior y nuevo en BitacoraBancaria.
     *
     * @param tc    objeto con los datos nuevos (debe tener TCidcuenta)
     * @param usuId ID del usuario conectado
     * @return filas afectadas
     */
    public int actualizar(clsTipoCuenta tc, int usuId) {
        int resultado = 0;

        // Guardar valor anterior para bitácora
        clsTipoCuenta anterior = buscarPorId(tc.getTCidcuenta());
        String valorAnterior = anterior != null
                ? "Nombre=" + anterior.getTCnombretipo() + ", Descripcion=" + anterior.getTCdescripcion()
                : null;

        String sql = "UPDATE CatTipoCuenta SET TCnombretipo=?, TCdescripcion=? WHERE TCidcuenta=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tc.getTCnombretipo());
            stmt.setString(2, tc.getTCdescripcion());
            stmt.setInt(3, tc.getTCidcuenta());
            resultado = stmt.executeUpdate();

            if (resultado > 0) {
                String valorNuevo = "Nombre=" + tc.getTCnombretipo()
                        + ", Descripcion=" + tc.getTCdescripcion();
                registrarBitacora(usuId, "UPDATE", TABLA, tc.getTCidcuenta(),
                        valorAnterior, valorNuevo,
                        "Actualización tipo de cuenta ID=" + tc.getTCidcuenta());
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return resultado;
    }

    /** Sobrecarga sin usuId */
    public int actualizar(clsTipoCuenta tc) {
        return actualizar(tc, 0);
    }

    // ─────────────────────────────────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Elimina un tipo de cuenta por su ID.
     * Registra en BitacoraBancaria el valor eliminado.
     *
     * @param id    TCidcuenta a eliminar
     * @param usuId ID del usuario conectado
     * @return filas afectadas
     */
    public int eliminar(int id, int usuId) {
        int resultado = 0;

        // Guardar valor anterior para bitácora
        clsTipoCuenta anterior = buscarPorId(id);
        String valorAnterior = anterior != null
                ? "Nombre=" + anterior.getTCnombretipo() + ", Descripcion=" + anterior.getTCdescripcion()
                : null;

        String sql = "DELETE FROM CatTipoCuenta WHERE TCidcuenta=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            resultado = stmt.executeUpdate();

            if (resultado > 0) {
                registrarBitacora(usuId, "DELETE", TABLA, id,
                        valorAnterior, null,
                        "Eliminación tipo de cuenta ID=" + id);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return resultado;
    }

    /** Sobrecarga sin usuId */
    public int eliminar(int id) {
        return eliminar(id, 0);
    }

    // ─────────────────────────────────────────────────────────────────────
    // SELECT BITÁCORA (solo de esta tabla)
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Obtiene los registros de BitacoraBancaria filtrados por tabla CatTipoCuenta.
     * Se usa para mostrar en el panel de bitácora del CRUD.
     *
     * @return lista de Object[] con columnas:
     *         [BBid, BBusuarioaccion, BBaccion, BBtabla, BBregistroid,
     *          BBvaloranterior, BBvalornuevo, BBfechaaccion, BBdescripcion]
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
    // HELPER PRIVADO: registrar en BitacoraBancaria
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Inserta un registro en BitacoraBancaria.
     *
     * @param usuId          ID del usuario que ejecuta la acción
     * @param accion         "INSERT", "UPDATE" o "DELETE"
     * @param tabla          nombre de la tabla afectada
     * @param registroId     PK del registro afectado
     * @param valorAnterior  estado anterior (null en INSERT)
     * @param valorNuevo     estado nuevo (null en DELETE)
     * @param descripcion    texto libre descriptivo
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
    
    public void resetearAutoIncrement() {
    String sql = "ALTER TABLE CatTipoCuenta AUTO_INCREMENT = 1";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace(System.out);
    }
    }
    
    public void limpiarTabla() {
    try (Connection conn = Conexion.getConnection();
         Statement stmt = conn.createStatement()) {
        
        stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
        stmt.executeUpdate("TRUNCATE TABLE CatTipoCuenta");
        stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        
    } catch (SQLException ex) {
        ex.printStackTrace(System.out);
    }
}
}