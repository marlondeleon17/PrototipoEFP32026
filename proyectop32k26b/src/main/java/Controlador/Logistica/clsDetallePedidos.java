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
public class clsDetallePedidos {

    private int Detallepedidoid;
    private int Pedid;
    private int Prodid;
    private int Detallepedidocantidad;
    
    //constructor vacio
    public clsDetallePedidos() {
    }
    //constructor con parametros
    public clsDetallePedidos(int Detallepedidoid, int Pedid, int Prodid, int Detallepedidocantidad) {
        this.Detallepedidoid = Detallepedidoid;
        this.Pedid = Pedid;
        this.Prodid = Prodid;
        this.Detallepedidocantidad = Detallepedidocantidad;
    }
    //getters y setters
    public int getDetallepedidoid() {
        return Detallepedidoid;
    }

    public void setDetallepedidoid(int Detallepedidoid) {
        this.Detallepedidoid = Detallepedidoid;
    }

    public int getPedid() {
        return Pedid;
    }

    public void setPedid(int Pedid) {
        this.Pedid = Pedid;
    }

    public int getProdid() {
        return Prodid;
    }

    public void setProdid(int Prodid) {
        this.Prodid = Prodid;
    }

    public int getDetallepedidocantidad() {
        return Detallepedidocantidad;
    }

    public void setDetallepedidocantidad(int Detallepedidocantidad) {
        this.Detallepedidocantidad = Detallepedidocantidad;
    }

    @Override
    public String toString() {
        return "clsDetallePedidos{" + "Detallepedidoid=" + Detallepedidoid + ", Pedid=" + Pedid + ", Prodid=" + Prodid + ", Detallepedidocantidad=" + Detallepedidocantidad + '}';
    }
    
}
