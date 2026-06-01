/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.Ventas;


/**
 * @author Marice
 */
public class clsVendedores {
    private int venid;
    private int emcodigo;
    private String vennombre;
    private String ventelefono;
    private String vendireccion;
    private String vencorreo;
    private double vencomisiones;

    public clsVendedores() {
    }

    // Getters y Setters
    public int getVenid() {
        return venid;
    }

    public void setVenid(int venid) {
        this.venid = venid;
    }

    public int getEmcodigo() {
        return emcodigo;
    }

    public void setEmcodigo(int emcodigo) {
        this.emcodigo = emcodigo;
    }

    public String getVennombre() {
        return vennombre;
    }

    public void setVennombre(String vennombre) {
        this.vennombre = vennombre;
    }

    public String getVentelefono() {
        return ventelefono;
    }

    public void setVentelefono(String ventelefono) {
        this.ventelefono = ventelefono;
    }

    public String getVendireccion() {
        return vendireccion;
    }

    public void setVendireccion(String vendireccion) {
        this.vendireccion = vendireccion;
    }

    public String getVencorreo() {
        return vencorreo;
    }

    public void setVencorreo(String vencorreo) {
        this.vencorreo = vencorreo;
    }

    public double getVencomisiones() {
        return vencomisiones;
    }

    public void setVencomisiones(double vencomisiones) {
        this.vencomisiones = vencomisiones;
    }
}