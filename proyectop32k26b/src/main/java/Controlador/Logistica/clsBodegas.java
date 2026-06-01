//Ferdynand Monroy abril 2026
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.Logistica;

/**
 *
 * @author ferito
 */
public class clsBodegas {

    private int bodegaid;
    private String Bodnombre;
    private String Bodubicacion;
    
    //constructor vacio
    public clsBodegas() {
    }
    
    //constructor con parametros
    public clsBodegas(int bodegaid, String Bodnombre, String Bodubicacion) {
        this.bodegaid = bodegaid;
        this.Bodnombre = Bodnombre;
        this.Bodubicacion = Bodubicacion;
    }
    //getters y setters
    public int getBodegaid() {
        return bodegaid;
    }

    public void setBodegaid(int bodegaid) {
        this.bodegaid = bodegaid;
    }

    public String getBodnombre() {
        return Bodnombre;
    }

    public void setBodnombre(String Bodnombre) {
        this.Bodnombre = Bodnombre;
    }

    public String getBodubicacion() {
        return Bodubicacion;
    }

    public void setBodubicacion(String Bodubicacion) {
        this.Bodubicacion = Bodubicacion;
    }

    @Override
    public String toString() {
        return "clsBodegas{" + "bodegaid=" + bodegaid + ", Bodnombre=" + Bodnombre + ", Bodubicacion=" + Bodubicacion + '}';
    }
    
     
}
