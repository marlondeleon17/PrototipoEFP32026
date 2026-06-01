/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.controladorPlanilla;

/**
 *
 * @author marlo
 */
public class clsConceptoExcepción {
    
    private int Exccodigo;
    private int Concodigo;
    private int Empcodigo;

    public clsConceptoExcepción() {
    }

    public clsConceptoExcepción(int excCodigo, int conCodigo, int empCodigo) {
        this.Exccodigo = excCodigo;
        this.Concodigo = conCodigo;
        this.Empcodigo = empCodigo;
    }

    public int getExcCodigo() {
        return Exccodigo;
    }

    public void setExcCodigo(int excCodigo) {
        this.Exccodigo = excCodigo;
    }

    public int getConCodigo() {
        return Concodigo;
    }

    public void setConCodigo(int conCodigo) {
        this.Concodigo = conCodigo;
    }

    public int getEmpCodigo() {
        return Empcodigo;
    }

    public void setEmpCodigo(int empCodigo) {
        this.Empcodigo = empCodigo;
    }

    @Override
    public String toString() {
        return "ConceptoExcepcion{" +
                "excCodigo=" + Exccodigo +
                ", conCodigo=" + Concodigo +
                ", empCodigo=" + Empcodigo +
                '}';
    }
    
}
