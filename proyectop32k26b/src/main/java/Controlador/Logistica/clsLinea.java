//Ferdynand Monroy mayo 2026
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.Logistica;

import java.math.BigDecimal; //import para lincomison

/**
 *
 * @author ferito
 */
public class clsLinea {
    private int lineaId;              
    private String linNombre;         
    private boolean linEstado;        
    private BigDecimal linComision;  // lincomision
    
    //constructor vacio
   public clsLinea(){
   
   }

    //constructor con parametros
    public clsLinea(int lineaId, String linNombre, boolean linEstado, BigDecimal linComision) {
        this.lineaId = lineaId;
        this.linNombre = linNombre;
        this.linEstado = linEstado;
        this.linComision = linComision;
    }

    //getters y setters
    public int getLineaId() {
        return lineaId;
    }

    public void setLineaId(int lineaId) {
        this.lineaId = lineaId;
    }

    public String getLinNombre() {
        return linNombre;
    }

    public void setLinNombre(String linNombre) {
        this.linNombre = linNombre;
    }

    public boolean isLinEstado() {
        return linEstado;
    }

    public void setLinEstado(boolean linEstado) {
        this.linEstado = linEstado;
    }

    public BigDecimal getLinComision() {
        return linComision;
    }

    public void setLinComision(BigDecimal linComision) {
        this.linComision = linComision;
    }

    //to string
    @Override
    public String toString() {
        return "clsLinea{" + "lineaId=" + lineaId + ", linNombre=" + linNombre + ", linEstado=" + linEstado + ", linComision=" + linComision + '}';
    }
     
   
}
