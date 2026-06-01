package Controlador.Bancos;

import Modelo.Bancos.MovimientoBancarioDAO;
import java.util.List;

 /* @author Angel Méndez
 * @carnet 9959-24-6845
  @since 2026-05-10*/

public class clsMovimientoBancario {

    // ── Atributos ─────────────────────────────────────────────────────────
    private int    Movbid;
    private String Movbfechamovimiento;
    private double Movibmonto;
    private String Movdescripcion;
    private int    CBANid;
    private int    TTid;
    private String Movbtipomov;
    private String Movbreferencia;
    private String Movbconciliado;

    // Campos auxiliares para mostrar en tabla
    private String numeroCuenta;
    private String nombreTipoTransaccion;

    // ── Constructores ─────────────────────────────────────────────────────

    public clsMovimientoBancario() {}

    public clsMovimientoBancario(int Movbid, String Movbfechamovimiento,
            double Movibmonto, String Movdescripcion,
            int CBANid, int TTid, String Movbtipomov,
            String Movbreferencia, String Movbconciliado) {
        this.Movbid              = Movbid;
        this.Movbfechamovimiento = Movbfechamovimiento;
        this.Movibmonto          = Movibmonto;
        this.Movdescripcion      = Movdescripcion;
        this.CBANid              = CBANid;
        this.TTid                = TTid;
        this.Movbtipomov         = Movbtipomov;
        this.Movbreferencia      = Movbreferencia;
        this.Movbconciliado      = Movbconciliado;
    }

    // ── Getters y Setters ─────────────────────────────────────────────────

    public int getMovbid() { return Movbid; }
    public void setMovbid(int Movbid) { this.Movbid = Movbid; }

    public String getMovbfechamovimiento() { return Movbfechamovimiento; }
    public void setMovbfechamovimiento(String v) { this.Movbfechamovimiento = v; }

    public double getMovibmonto() { return Movibmonto; }
    public void setMovibmonto(double v) { this.Movibmonto = v; }

    public String getMovdescripcion() { return Movdescripcion; }
    public void setMovdescripcion(String v) { this.Movdescripcion = v; }

    public int getCBANid() { return CBANid; }
    public void setCBANid(int v) { this.CBANid = v; }

    public int getTTid() { return TTid; }
    public void setTTid(int v) { this.TTid = v; }

    public String getMovbtipomov() { return Movbtipomov; }
    public void setMovbtipomov(String v) { this.Movbtipomov = v; }

    public String getMovbreferencia() { return Movbreferencia; }
    public void setMovbreferencia(String v) { this.Movbreferencia = v; }

    public String getMovbconciliado() { return Movbconciliado; }
    public void setMovbconciliado(String v) { this.Movbconciliado = v; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String v) { this.numeroCuenta = v; }

    public String getNombreTipoTransaccion() { return nombreTipoTransaccion; }
    public void setNombreTipoTransaccion(String v) { this.nombreTipoTransaccion = v; }

    // ── Métodos de Negocio ────────────────────────────────────────────────

    public int setInsertar(clsMovimientoBancario mov) {
        return new MovimientoBancarioDAO().insertar(mov);
    }

    public List<clsMovimientoBancario> getListado() {
        return new MovimientoBancarioDAO().seleccionar();
    }

    public clsMovimientoBancario getBuscarPorId(int id) {
        return new MovimientoBancarioDAO().buscarPorId(id);
    }

    public int setActualizar(clsMovimientoBancario mov) {
        return new MovimientoBancarioDAO().actualizar(mov);
    }

    public int setEliminar(int id) {
        return new MovimientoBancarioDAO().eliminar(id);
    }

    public List<Object[]> getCuentasBancarias() {
        return new MovimientoBancarioDAO().seleccionarCuentas();
    }

    public List<Object[]> getTiposTransaccion() {
        return new MovimientoBancarioDAO().seleccionarTiposTransaccion();
    }

    public void limpiarTabla() {
        new MovimientoBancarioDAO().limpiarTabla();
    }

    public double getSaldoCuenta(int cbanId) {
        return new MovimientoBancarioDAO().getSaldoCuenta(cbanId);
    }

    @Override
    public String toString() {
        return "clsMovimientoBancario{Movbid=" + Movbid
                + ", Movibmonto=" + Movibmonto + "}";
    }
}