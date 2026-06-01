//Ferdynand Monroy abril 2026
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.Logistica;

import java.sql.Timestamp;
/**
 *
 * @author ferito
 */
public class clsArribosMercancia {

    private int Arriboid;
    private int Procodigo;
    private Timestamp Arrfechaarribo;
    private String Arrestadoverificacion;
    
     // Constructor vacío
    public clsArribosMercancia() {
    }
    
    //cosntructor con parametros
    public clsArribosMercancia(int Arriboid, int Procodigo, Timestamp Arrfechaarribo, String Arrestadoverificacion) {
        this.Arriboid = Arriboid;
        this.Procodigo = Procodigo;
        this.Arrfechaarribo = Arrfechaarribo;
        this.Arrestadoverificacion = Arrestadoverificacion;
    }

    //getters y setters
    public int getArriboid() {
        return Arriboid;
    }

    public void setArriboid(int Arriboid) {
        this.Arriboid = Arriboid;
    }

    public int getProcodigo() {
        return Procodigo;
    }

    public void setProcodigo(int Procodigo) {
        this.Procodigo = Procodigo;
    }

    public Timestamp getArrfechaarribo() {
        return Arrfechaarribo;
    }

    public void setArrfechaarribo(Timestamp Arrfechaarribo) {
        this.Arrfechaarribo = Arrfechaarribo;
    }

    public String getArrestadoverificacion() {
        return Arrestadoverificacion;
    }

    public void setArrestadoverificacion(String Arrestadoverificacion) {
        this.Arrestadoverificacion = Arrestadoverificacion;
    }

    @Override
    public String toString() {
        return "clsArribosMercancia{" + "Arriboid=" + Arriboid + ", Procodigo=" + Procodigo + ", Arrfechaarribo=" + Arrfechaarribo + ", Arrestadoverificacion=" + Arrestadoverificacion + '}';
    }
    
    
}
