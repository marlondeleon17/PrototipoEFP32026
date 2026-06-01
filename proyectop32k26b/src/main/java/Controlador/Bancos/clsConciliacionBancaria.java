//Karina Alejandra Arriaza Ortiz 9959-24-14190
package Controlador.Bancos;
import java.util.Date;
/**
 * Clase que representa una conciliación bancaria.
 * Permite comparar el saldo del sistema con el saldo del banco.
 */
public class clsConciliacionBancaria {
    // Identificador único de la conciliación
    private int Conbid;
    // Fecha en la que se realiza la conciliación
    private Date conbfecha;
    // Saldo registrado en el sistema
    private double Conbsaldosistema;
    // Saldo reportado por el banco
    private double Conbsaldobanco;
    // Diferencia entre ambos saldos
    private double Conbdiferencia;
    // Identificador de la cuenta bancaria
    private int CBANid;
    // Identificador del estado de conciliación
    private int Catesid;

    /**
     * Constructor con parámetros.
     * 
     * @param id         Identificador de la conciliación
     * @param fecha      Fecha de la conciliación
     * @param sistema    Saldo del sistema
     * @param banco      Saldo del banco
     * @param diferencia Diferencia entre saldos
     * @param cuenta     Cuenta bancaria asociada
     * @param estado     Estado de la conciliación
     */
    public clsConciliacionBancaria(int id, Date fecha, double sistema, double banco, double diferencia, int cuenta, int estado) {
    this.Conbid = id;
    this.conbfecha = fecha;
    this.Conbsaldosistema = sistema;
    this.Conbsaldobanco = banco;
    this.Conbdiferencia = diferencia;
    this.CBANid = cuenta;
    this.Catesid = estado;
}

    /**
     * @return Identificador único de la conciliación
     */
    public int getConbid() {
        return Conbid;
    }

    /**
     * @return Fecha en la que se realizó la conciliación
     */
    public Date getConbfecha() {
        return conbfecha;
    }

    /**
     * @return Saldo registrado en el sistema
     */
    public double getConbsaldosistema() {
        return Conbsaldosistema;
    }

    /**
     * @return Saldo reportado por el banco
     */
    public double getConbsaldobanco() {
        return Conbsaldobanco;
    }

    /**
     * @return Diferencia entre el saldo del sistema y el saldo del banco
     */
    public double getConbdiferencia() {
        return Conbdiferencia;
    }

    /**
     * @return Identificador de la cuenta bancaria asociada
     */
    public int getCBANid() {
        return CBANid;
    }

    /**
     * @return Identificador del estado de la conciliación
     */
    public int getCatesid() {
        return Catesid;
    }

    /**
     * @param conbid Identificador único de la conciliación
     */
    public void setConbid(int conbid) {
        this.Conbid = conbid;
    }

    /**
     * @param conbfecha Fecha en la que se realizó la conciliación
     */
    public void setConbfecha(Date conbfecha) {
        this.conbfecha = conbfecha;
    }

    /**
     * @param conbsaldosistema Saldo registrado en el sistema
     */
    public void setConbsaldosistema(double conbsaldosistema) {
        this.Conbsaldosistema = conbsaldosistema;
    }

    /**
     * @param conbsaldobanco Saldo reportado por el banco
     */
    public void setConbsaldobanco(double conbsaldobanco) {
        this.Conbsaldobanco = conbsaldobanco;
    }

    /**
     * @param conbdiferencia Diferencia entre el saldo del sistema y el saldo del banco
     */
    public void setConbdiferencia(double conbdiferencia) {
        this.Conbdiferencia = conbdiferencia;
    }

    /**
     * @param cbanId Identificador de la cuenta bancaria asociada
     */
    public void setCBANid(int cbanId) {
        this.CBANid = cbanId;
    }

    /**
     * @param catesid Identificador del estado de la conciliación
     */
    public void setCatesid(int catesid) {
        this.Catesid = catesid;
    }

    /**
     * Devuelve una representación en texto de la conciliación.
     * 
     * @return Información básica de la conciliación
     */
    @Override
    public String toString() {
        return "Conciliacion{ id=" + Conbid
                + ", saldoSistema=" + Conbsaldosistema
                + ", saldoBanco=" + Conbsaldobanco + " }";
    }
}