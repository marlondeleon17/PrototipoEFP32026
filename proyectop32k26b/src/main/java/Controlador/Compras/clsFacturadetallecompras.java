/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.Compras;

/**
 *
 * @author Isaias Cedillo 9959-24-1672
 */
public class clsFacturadetallecompras  {
     // ATRIBUTOS
    private int Faccomdetid;
    private int Faccomid;
    private int Prodid;

    // Campo auxiliar para mostrar el nombre del producto
    // en interfaces o tablas
    private String Prodnombre;

    private double Faccomcantidad;
    private double Faccomprecio;
    private double Faccomsubtotal;

    // =========================
    // GETTERS
    // =========================

    public int getFaccomdetid() {
        return Faccomdetid;
    }

    public int getFaccomid() {
        return Faccomid;
    }

    public int getProdid() {
        return Prodid;
    }

    public String getProdnombre() {
        return Prodnombre;
    }

    public double getFaccomcantidad() {
        return Faccomcantidad;
    }

    public double getFaccomprecio() {
        return Faccomprecio;
    }

    public double getFaccomsubtotal() {
        return Faccomsubtotal;
    }

    // =========================
    // SETTERS
    // =========================

    public void setFaccomdetid(int Faccomdetid) {
        this.Faccomdetid = Faccomdetid;
    }

    public void setFaccomid(int Faccomid) {
        this.Faccomid = Faccomid;
    }

    public void setProdid(int Prodid) {
        this.Prodid = Prodid;
    }

    public void setProdnombre(String Prodnombre) {
        this.Prodnombre = Prodnombre;
    }

    public void setFaccomcantidad(double Faccomcantidad) {
        this.Faccomcantidad = Faccomcantidad;
    }

    public void setFaccomprecio(double Faccomprecio) {
        this.Faccomprecio = Faccomprecio;
    }

    public void setFaccomsubtotal(double Faccomsubtotal) {
        this.Faccomsubtotal = Faccomsubtotal;
    }

    // =========================
    // CONSTRUCTOR VACÍO
    // =========================

    public clsFacturadetallecompras() {
    }

    // =========================
    // CONSTRUCTOR SIN ID
    // Para INSERT
    // =========================

    public clsFacturadetallecompras(
            int Faccomid,
            int Prodid,
            String Prodnombre,
            double Faccomcantidad,
            double Faccomprecio,
            double Faccomsubtotal) {

        this.Faccomid = Faccomid;
        this.Prodid = Prodid;
        this.Prodnombre = Prodnombre;
        this.Faccomcantidad = Faccomcantidad;
        this.Faccomprecio = Faccomprecio;
        this.Faccomsubtotal = Faccomsubtotal;
    }

    // =========================
    // CONSTRUCTOR COMPLETO
    // Para SELECT
    // =========================

    public clsFacturadetallecompras(
            int Faccomdetid,
            int Faccomid,
            int Prodid,
            String Prodnombre,
            double Faccomcantidad,
            double Faccomprecio,
            double Faccomsubtotal) {

        this.Faccomdetid = Faccomdetid;
        this.Faccomid = Faccomid;
        this.Prodid = Prodid;
        this.Prodnombre = Prodnombre;
        this.Faccomcantidad = Faccomcantidad;
        this.Faccomprecio = Faccomprecio;
        this.Faccomsubtotal = Faccomsubtotal;
    }

    // =========================
    // TOSTRING
    // =========================

    @Override
    public String toString() {

        return "clsFacturadetallecompras{"
                + "Faccomdetid=" + Faccomdetid
                + ", Faccomid=" + Faccomid
                + ", Prodid=" + Prodid
                + ", Prodnombre=" + Prodnombre
                + ", Faccomcantidad=" + Faccomcantidad
                + ", Faccomprecio=" + Faccomprecio
                + ", Faccomsubtotal=" + Faccomsubtotal
                + '}';
    }
}
