/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.controladorPlanilla;

/**
 *
 * @author Meilyn Garcia 9959-23-17838
 */
public class clsDepartamento {
    private int Depcodigo;
    private String Depnombre;
    private int Depestado;

    public clsDepartamento() {
    }

    public clsDepartamento(int Depcodigo, String Depnombre, int Depestado) {
        this.Depcodigo = Depcodigo;
        this.Depnombre = Depnombre;
        this.Depestado = Depestado;
    }

    public int getDepcodigo() {
        return Depcodigo;
    }

    public void setDepcodigo(int Depcodigo) {
        this.Depcodigo = Depcodigo;
    }

    public String getDepnombre() {
        return Depnombre;
    }

    public void setDepnombre(String Depnombre) {
        this.Depnombre = Depnombre;
    }

    public int getDepestado() {
        return Depestado;
    }

    public void setDepestado(int Depestado) {
        this.Depestado = Depestado;
    }
}
