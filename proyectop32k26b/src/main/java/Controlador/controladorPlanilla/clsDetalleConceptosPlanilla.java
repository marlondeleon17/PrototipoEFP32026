/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.controladorPlanilla;

/**
 *
 * @author Meilyn Garcia
 */
public class clsDetalleConceptosPlanilla {
    private int detconcodigo;
    private int detcodigo;   // planilladetalle
    private int concodigo;   // conceptosplanilla
    private double monto;

    public clsDetalleConceptosPlanilla() {
    }

    public clsDetalleConceptosPlanilla(int detconcodigo, int detcodigo, int concodigo, double monto) {
        this.detconcodigo = detconcodigo;
        this.detcodigo = detcodigo;
        this.concodigo = concodigo;
        this.monto = monto;
    }

    public int getDetconcodigo() {
        return detconcodigo;
    }

    public void setDetconcodigo(int detconcodigo) {
        this.detconcodigo = detconcodigo;
    }

    public int getDetcodigo() {
        return detcodigo;
    }

    public void setDetcodigo(int detcodigo) {
        this.detcodigo = detcodigo;
    }

    public int getConcodigo() {
        return concodigo;
    }

    public void setConcodigo(int concodigo) {
        this.concodigo = concodigo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
