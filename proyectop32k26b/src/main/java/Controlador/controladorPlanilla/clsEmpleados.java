/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.controladorPlanilla;

import java.sql.Date;

/**
 *
 * @author marlo
 */
public class clsEmpleados {
    
    private int empcodigo;
    private String empnombre;
    private String empdpi;
    private int puecodigo;
    private Date empfecha_ingreso;
    private int empestado;
    
     public clsEmpleados() {
    }

    public clsEmpleados(int empcodigo) {
        this.empcodigo = empcodigo;
    }

    public clsEmpleados(String empnombre, String empdpi, int puecodigo, Date empfecha_ingreso, int empestado) {
        this.empnombre = empnombre;
        this.empdpi = empdpi;
        this.puecodigo = puecodigo;
        this.empfecha_ingreso = empfecha_ingreso;
        this.empestado = empestado;
    }

    public clsEmpleados(int empcodigo, String empnombre, String empdpi, int puecodigo, Date empfecha_ingreso, int empestado) {
        this.empcodigo = empcodigo;
        this.empnombre = empnombre;
        this.empdpi = empdpi;
        this.puecodigo = puecodigo;
        this.empfecha_ingreso = empfecha_ingreso;
        this.empestado = empestado;
    }

    public int getEmpcodigo() {
        return empcodigo;
    }

    public void setEmpcodigo(int empcodigo) {
        this.empcodigo = empcodigo;
    }

    public String getEmpnombre() {
        return empnombre;
    }

    public void setEmpnombre(String empnombre) {
        this.empnombre = empnombre;
    }

    public String getEmpdpi() {
        return empdpi;
    }

    public void setEmpdpi(String empdpi) {
        this.empdpi = empdpi;
    }

    public int getPuecodigo() {
        return puecodigo;
    }

    public void setPuecodigo(int puecodigo) {
        this.puecodigo = puecodigo;
    }

    public Date getEmpfecha_ingreso() {
        return empfecha_ingreso;
    }

    public void setEmpfecha_ingreso(Date empfecha_ingreso) {
        this.empfecha_ingreso = empfecha_ingreso;
    }

    public int getEmpestado() {
        return empestado;
    }

    public void setEmpestado(int empestado) {
        this.empestado = empestado;
    }

    @Override
    public String toString() {
        return "Empleados{" +
                "empcodigo=" + empcodigo +
                ", empnombre='" + empnombre + '\'' +
                ", empdpi='" + empdpi + '\'' +
                ", puecodigo=" + puecodigo +
                ", empfecha_ingreso=" + empfecha_ingreso +
                ", empestado=" + empestado +
                '}';
    }
    
    
}
