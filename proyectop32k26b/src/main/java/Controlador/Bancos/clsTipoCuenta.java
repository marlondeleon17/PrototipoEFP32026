package Controlador.Bancos;

/**
 * Clase Controlador para CatTipoCuenta
 * Módulo Maestro - Código de Aplicación: 5000
 *
 * Representa un tipo de cuenta bancaria del catálogo.
 * Contiene los atributos de la tabla CatTipoCuenta y
 * los métodos de negocio que delegan al DAO correspondiente.
 *
 * Estructura de datos (tabla CatTipoCuenta):
 *   TCidcuenta   INT          PK, AUTO_INCREMENT
 *   TCnombretipo VARCHAR(50)  NOT NULL, UNIQUE
 *   TCdescripcion VARCHAR(150)
 *
 * @author Angel Méndez
 * @carnet 9959-24-6845
 * @since 2026-05-10
 */

import Modelo.Bancos.TipoCuentaDAO;
import java.util.List;

public class clsTipoCuenta {

    // ── Atributos (espejo de la tabla CatTipoCuenta) ──────────────────────
    private int    TCidcuenta;
    private String TCnombretipo;
    private String TCdescripcion;

    // ── Constructores ─────────────────────────────────────────────────────

    /** Constructor vacío requerido para instancias sin datos iniciales */
    public clsTipoCuenta() {}

    /**
     * Constructor completo
     * @param TCidcuenta    ID único del tipo de cuenta
     * @param TCnombretipo  Nombre del tipo (ej. "Monetaria", "Ahorro")
     * @param TCdescripcion Descripción opcional del tipo
     */
    public clsTipoCuenta(int TCidcuenta, String TCnombretipo, String TCdescripcion) {
        this.TCidcuenta    = TCidcuenta;
        this.TCnombretipo  = TCnombretipo;
        this.TCdescripcion = TCdescripcion;
    }

    // ── Getters y Setters ─────────────────────────────────────────────────

    public int getTCidcuenta() { return TCidcuenta; }
    public void setTCidcuenta(int TCidcuenta) { this.TCidcuenta = TCidcuenta; }

    public String getTCnombretipo() { return TCnombretipo; }
    public void setTCnombretipo(String TCnombretipo) { this.TCnombretipo = TCnombretipo; }

    public String getTCdescripcion() { return TCdescripcion; }
    public void setTCdescripcion(String TCdescripcion) { this.TCdescripcion = TCdescripcion; }

    // ── Métodos de Negocio (delegan al DAO) ───────────────────────────────

    /**
     * Inserta un nuevo tipo de cuenta en la BD
     * @param tc objeto con los datos a insertar
     * @return filas afectadas (1 = éxito, 0 = fallo)
     */
    public int setInsertar(clsTipoCuenta tc) {
        return new TipoCuentaDAO().insertar(tc);
    }

    /**
     * Obtiene todos los tipos de cuenta registrados
     * @return lista de clsTipoCuenta
     */
    public List<clsTipoCuenta> getListado() {
        return new TipoCuentaDAO().seleccionar();
    }

    /**
     * Busca un tipo de cuenta por su ID
     * @param id TCidcuenta a buscar
     * @return objeto clsTipoCuenta o null si no existe
     */
    public clsTipoCuenta getBuscarPorId(int id) {
        return new TipoCuentaDAO().buscarPorId(id);
    }

    /**
     * Actualiza un tipo de cuenta existente
     * @param tc objeto con los datos actualizados
     * @return filas afectadas (1 = éxito, 0 = fallo)
     */
    public int setActualizar(clsTipoCuenta tc) {
        return new TipoCuentaDAO().actualizar(tc);
    }

    /**
     * Elimina un tipo de cuenta por su ID
     * @param id TCidcuenta a eliminar
     * @return filas afectadas (1 = éxito, 0 = fallo)
     */
    public int setEliminar(int id) {
        return new TipoCuentaDAO().eliminar(id);
    }
    
    public void resetearAutoIncrement() {
    new TipoCuentaDAO().resetearAutoIncrement();
    }
    
    public void limpiarTabla() {
    new TipoCuentaDAO().limpiarTabla();
    }

    @Override
    public String toString() {
        return "clsTipoCuenta{TCidcuenta=" + TCidcuenta
                + ", TCnombretipo=" + TCnombretipo + "}";
    }
}