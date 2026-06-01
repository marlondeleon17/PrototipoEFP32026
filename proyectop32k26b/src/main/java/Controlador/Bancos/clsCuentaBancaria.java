package Controlador.Bancos;

/**
 * Clase Controlador para CuentaBancaria
 * Módulo Transaccional - Código de Aplicación: 5500
 *
 * Representa una cuenta bancaria del sistema.
 * Delega operaciones CRUD al CuentaBancariaDAO.
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

import Modelo.Bancos.CuentaBancariaDAO;
import java.util.List;

public class clsCuentaBancaria {

    // ── Atributos ─────────────────────────────────────────────────────────
    private int    CBANid;
    private String CBANnumerocuenta;
    private double CBANsaldoactual;
    private String CBANfechaapertura;   // Se maneja como String para facilidad de UI
    private int    Banid;
    private int    Cliid;
    private int    TCidcuenta;

    // Campos auxiliares para mostrar en tabla (JOINs)
    private String nombreBanco;
    private String nombreCliente;
    private String nombreTipoCuenta;

    // ── Constructores ─────────────────────────────────────────────────────

    public clsCuentaBancaria() {}
    public List<Object[]> getBancos() { return new CuentaBancariaDAO().seleccionarBancos(); }
    public List<Object[]> getClientes() { return new CuentaBancariaDAO().seleccionarClientes(); }
    public List<Object[]> getTiposCuenta() { return new CuentaBancariaDAO().seleccionarTiposCuenta(); }
    public void limpiarTabla() { new CuentaBancariaDAO().limpiarTabla(); }

    /**
     * Constructor completo con claves foráneas
     */
    public clsCuentaBancaria(int CBANid, String CBANnumerocuenta,
            double CBANsaldoactual, String CBANfechaapertura,
            int Banid, int Cliid, int TCidcuenta) {
        this.CBANid            = CBANid;
        this.CBANnumerocuenta  = CBANnumerocuenta;
        this.CBANsaldoactual   = CBANsaldoactual;
        this.CBANfechaapertura = CBANfechaapertura;
        this.Banid             = Banid;
        this.Cliid             = Cliid;
        this.TCidcuenta        = TCidcuenta;
    }

    // ── Getters y Setters ─────────────────────────────────────────────────

    public int getCBANid() { return CBANid; }
    public void setCBANid(int CBANid) { this.CBANid = CBANid; }

    public String getCBANnumerocuenta() { return CBANnumerocuenta; }
    public void setCBANnumerocuenta(String CBANnumerocuenta) { this.CBANnumerocuenta = CBANnumerocuenta; }

    public double getCBANsaldoactual() { return CBANsaldoactual; }
    public void setCBANsaldoactual(double CBANsaldoactual) { this.CBANsaldoactual = CBANsaldoactual; }

    public String getCBANfechaapertura() { return CBANfechaapertura; }
    public void setCBANfechaapertura(String CBANfechaapertura) { this.CBANfechaapertura = CBANfechaapertura; }

    public int getBanid() { return Banid; }
    public void setBanid(int Banid) { this.Banid = Banid; }

    public int getCliid() { return Cliid; }
    public void setCliid(int Cliid) { this.Cliid = Cliid; }

    public int getTCidcuenta() { return TCidcuenta; }
    public void setTCidcuenta(int TCidcuenta) { this.TCidcuenta = TCidcuenta; }

    // Auxiliares para reportes/tablas
    public String getNombreBanco() { return nombreBanco; }
    public void setNombreBanco(String nombreBanco) { this.nombreBanco = nombreBanco; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getNombreTipoCuenta() { return nombreTipoCuenta; }
    public void setNombreTipoCuenta(String nombreTipoCuenta) { this.nombreTipoCuenta = nombreTipoCuenta; }

    // ── Métodos de Negocio ────────────────────────────────────────────────

    public int setInsertar(clsCuentaBancaria cb) {
        return new CuentaBancariaDAO().insertar(cb);
    }

    public List<clsCuentaBancaria> getListado() {
        return new CuentaBancariaDAO().seleccionar();
    }

    public clsCuentaBancaria getBuscarPorId(int id) {
        return new CuentaBancariaDAO().buscarPorId(id);
    }

    public int setActualizar(clsCuentaBancaria cb) {
        return new CuentaBancariaDAO().actualizar(cb);
    }

    public int setEliminar(int id) {
        return new CuentaBancariaDAO().eliminar(id);
    }

    @Override
    public String toString() {
        return "clsCuentaBancaria{CBANid=" + CBANid
                + ", CBANnumerocuenta=" + CBANnumerocuenta + "}";
    }
}