//Ferdynand Monroy mayo 2026
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.Logistica;

import java.math.BigDecimal; //import para prodPrecioVenta

/**
 *
 * @author ferito
 */
public class clsProductos {

    private int prodId;                    
    private String prodNombre;             
    private int prodStockActual;           
    private int prodPuntoReorden;          
    private BigDecimal prodPrecioVenta;    
    private int lineaId;                   
    private int marcaId; 
    private BigDecimal Prodcomision;
    
    //constructor vacio
    public clsProductos(){
    }

    //constructor con parametros
    public clsProductos(int prodId, String prodNombre, int prodStockActual, int prodPuntoReorden, BigDecimal prodPrecioVenta, int lineaId, int marcaId, BigDecimal Prodcomision) {
        this.prodId = prodId;
        this.prodNombre = prodNombre;
        this.prodStockActual = prodStockActual;
        this.prodPuntoReorden = prodPuntoReorden;
        this.prodPrecioVenta = prodPrecioVenta;
        this.lineaId = lineaId;
        this.marcaId = marcaId;
        this.Prodcomision = Prodcomision;
    }

    //getters y setters
    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }

    public int getProdStockActual() {
        return prodStockActual;
    }

    public void setProdStockActual(int prodStockActual) {
        this.prodStockActual = prodStockActual;
    }

    public int getProdPuntoReorden() {
        return prodPuntoReorden;
    }

    public void setProdPuntoReorden(int prodPuntoReorden) {
        this.prodPuntoReorden = prodPuntoReorden;
    }

    public BigDecimal getProdPrecioVenta() {
        return prodPrecioVenta;
    }

    public void setProdPrecioVenta(BigDecimal prodPrecioVenta) {
        this.prodPrecioVenta = prodPrecioVenta;
    }

    public int getLineaId() {
        return lineaId;
    }

    public void setLineaId(int lineaId) {
        this.lineaId = lineaId;
    }

    public int getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(int marcaId) {
        this.marcaId = marcaId;
    }
    
    public BigDecimal getProdcomision() {
        return Prodcomision;
    }

    public void setProdcomision(BigDecimal Prodcomision) {
        this.Prodcomision = Prodcomision;
    }

    //to string
     @Override
    public String toString() {
        return "clsProductos{" + "prodId=" + prodId + ", prodNombre=" + prodNombre + ", prodStockActual=" + prodStockActual + ", prodPuntoReorden=" + prodPuntoReorden + ", prodPrecioVenta=" + prodPrecioVenta + ", lineaId=" + lineaId + ", marcaId=" + marcaId + ", Prodcomision=" + Prodcomision + '}';
    }

}
