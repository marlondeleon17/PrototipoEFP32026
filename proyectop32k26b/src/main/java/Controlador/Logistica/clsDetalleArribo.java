//Ferdynand Monroy mayo 2026
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.Logistica;

/**
 *
 * @author ferito
 */
public class clsDetalleArribo {

    private int Detallearriboid;
    private int Arriboid;
    private int Prodid;
    private int Detarribocantidad;
    private double Detarribopreciounitariocompra;
    
    //constructor vacio
    public clsDetalleArribo() {
    }
    
    //constructor con parametros
    public clsDetalleArribo(int Detallearriboid, int Arriboid, int Prodid, int Detarribocantidad, double Detarribopreciounitariocompra) {
        this.Detallearriboid = Detallearriboid;
        this.Arriboid = Arriboid;
        this.Prodid = Prodid;
        this.Detarribocantidad = Detarribocantidad;
        this.Detarribopreciounitariocompra = Detarribopreciounitariocompra;
    }
    //getters y setters
    public int getDetallearriboid() {
        return Detallearriboid;
    }

    public void setDetallearriboid(int Detallearriboid) {
        this.Detallearriboid = Detallearriboid;
    }

    public int getArriboid() {
        return Arriboid;
    }

    public void setArriboid(int Arriboid) {
        this.Arriboid = Arriboid;
    }

    public int getProdid() {
        return Prodid;
    }

    public void setProdid(int Prodid) {
        this.Prodid = Prodid;
    }

    public int getDetarribocantidad() {
        return Detarribocantidad;
    }

    public void setDetarribocantidad(int Detarribocantidad) {
        this.Detarribocantidad = Detarribocantidad;
    }

    public double getDetarribopreciounitariocompra() {
        return Detarribopreciounitariocompra;
    }

    public void setDetarribopreciounitariocompra(double Detarribopreciounitariocompra) {
        this.Detarribopreciounitariocompra = Detarribopreciounitariocompra;
    }

    @Override
    public String toString() {
        return "clsDetalleArribo{" + "Detallearriboid=" + Detallearriboid + ", Arriboid=" + Arriboid + ", Prodid=" + Prodid + ", Detarribocantidad=" + Detarribocantidad + ", Detarribopreciounitariocompra=" + Detarribopreciounitariocompra + '}';
    }
    
}
