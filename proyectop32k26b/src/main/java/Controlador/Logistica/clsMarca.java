//Ferdynand Monroy mayo 2026
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.Logistica;

import java.math.BigDecimal; //para private marcomision

/**
 *
 * @author ferito
 */
public class clsMarca {
    private int marcaid;
    private String marnombre;
    private int marestado;
    private BigDecimal marcomision;
    
    //constructor vacio
    public clsMarca(){
    
    }
    
    //constructor con parametros
    public clsMarca(int marcaid, String marnombre, int marestado, BigDecimal marcomision) {
        this.marcaid = marcaid;
        this.marnombre = marnombre;
        this.marestado = marestado;
        this.marcomision = marcomision;
    }

    //getters y setters
    public int getMarcaid() {
        return marcaid;
    }

    public void setMarcaid(int marcaid) {
        this.marcaid = marcaid;
    }

    public String getMarnombre() {
        return marnombre;
    }

    public void setMarnombre(String marnombre) {
        this.marnombre = marnombre;
    }

    public int getMarestado() {
        return marestado;
    }

    public void setMarestado(int marestado) {
        this.marestado = marestado;
    }
    
    public BigDecimal getMarcomision() {
        return marcomision;
    }

    public void setMarcomision(BigDecimal marcomision) {
        this.marcomision = marcomision;
    }

    //to string
    @Override
    public String toString() {
        return "clsMarca{" + "marcaid=" + marcaid + ", marnombre=" + marnombre + ", marestado=" + marestado + ", marcomision=" + marcomision + '}';
    }
    
}
