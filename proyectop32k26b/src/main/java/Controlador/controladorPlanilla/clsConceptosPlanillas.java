/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.controladorPlanilla;

/**
 *
 * @author meilyn garcia
 */
public class clsConceptosPlanillas {
    private int codigo;
    private String nombre;
    private String tipo;
    private double porcentaje;
    private double monto;
    private String aplica;
    private int estado;

    public clsConceptosPlanillas() {
    }

    public clsConceptosPlanillas(int codigo, String nombre, String tipo,
            double porcentaje, double monto,
            String aplica, int estado) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.porcentaje = porcentaje;
        this.monto = monto;
        this.aplica = aplica;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getAplica() {
        return aplica;
    }

    public void setAplica(String aplica) {
        this.aplica = aplica;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
