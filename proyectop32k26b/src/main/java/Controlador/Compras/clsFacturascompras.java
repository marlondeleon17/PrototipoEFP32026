/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.Compras;
//Librerias
import java.sql.Timestamp;
/**
 *
 * @author Isaias Cedillo 9959-24-1672
 */
public class clsFacturascompras  {
   private int Faccomid;
   private String Faccomnumero;
   private Timestamp Faccomfecha;
   private int Procodigo;
   private double Faccomsubtotal;
   private double Faccomiva;
   private double Faccomtotal;
   private String Faccomestado;
   //GETTERS

    public int getFaccomid() {
        return Faccomid;
    }

    public String getFaccomnumero() {
        return Faccomnumero;
    }

    public Timestamp getFaccomfecha() {
        return Faccomfecha;
    }

    public int getProcodigo() {
        return Procodigo;
    }

    public double getFaccomsubtotal() {
        return Faccomsubtotal;
    }

    public double getFaccomiva() {
        return Faccomiva;
    }

    public double getFaccomtotal() {
        return Faccomtotal;
    }

    public String getFaccomestado() {
        return Faccomestado;
    }
   //SETTERS

    public void setFaccomid(int Faccomid) {
        this.Faccomid = Faccomid;
    }

    public void setFaccomnumero(String Faccomnumero) {
        this.Faccomnumero = Faccomnumero;
    }

    public void setFaccomfecha(Timestamp Faccomfecha) {
        this.Faccomfecha = Faccomfecha;
    }

    public void setProcodigo(int Procodigo) {
        this.Procodigo = Procodigo;
    }

    public void setFaccomsubtotal(double Faccomsubtotal) {
        this.Faccomsubtotal = Faccomsubtotal;
    }

    public void setFaccomiva(double Faccomiva) {
        this.Faccomiva = Faccomiva;
    }

    public void setFaccomtotal(double Faccomtotal) {
        this.Faccomtotal = Faccomtotal;
    }

    public void setFaccomestado(String Faccomestado) {
        this.Faccomestado = Faccomestado;
    }
    //Constructores
    public clsFacturascompras() {}
    public clsFacturascompras(int Faccomid) 
    {   
    this.Faccomid = Faccomid;
    }
    public clsFacturascompras(int Faccomid, String Faccomnumero) 
    {   
    this.Faccomid = Faccomid;
    this.Faccomnumero = Faccomnumero;
    }
    public clsFacturascompras(int Faccomid, String Faccomnumero,Timestamp Faccomfecha) 
    {   
    this.Faccomid = Faccomid;
    this.Faccomnumero = Faccomnumero;
    this.Faccomfecha = Faccomfecha;
    }
    public clsFacturascompras(int Faccomid, String Faccomnumero,Timestamp Faccomfecha,int Procodigo) 
    {   
    this.Faccomid = Faccomid;
    this.Faccomnumero = Faccomnumero;
    this.Faccomfecha = Faccomfecha;
    this.Procodigo = Procodigo;
    
    }
    public clsFacturascompras(int Faccomid, String Faccomnumero,Timestamp Faccomfecha,int Procodigo,double Faccomsubtotal) 
    {   
    this.Faccomid = Faccomid;
    this.Faccomnumero = Faccomnumero;
    this.Faccomfecha = Faccomfecha;
    this.Procodigo = Procodigo;
    this.Faccomsubtotal = Faccomsubtotal;
    
    }
    public clsFacturascompras(int Faccomid, String Faccomnumero,Timestamp Faccomfecha,int Procodigo,double Faccomsubtotal,double Faccomiva) 
    {   
    this.Faccomid = Faccomid;
    this.Faccomnumero = Faccomnumero;
    this.Faccomfecha = Faccomfecha;
    this.Procodigo = Procodigo;
    this.Faccomsubtotal = Faccomsubtotal;
    this.Faccomiva = Faccomiva;
    }  
    public clsFacturascompras(int Faccomid, String Faccomnumero,Timestamp Faccomfecha,int Procodigo,double Faccomsubtotal,double Faccomiva,double Faccomtotal) 
    {   
    this.Faccomid = Faccomid;
    this.Faccomnumero = Faccomnumero;
    this.Faccomfecha = Faccomfecha;
    this.Procodigo = Procodigo;
    this.Faccomsubtotal = Faccomsubtotal;
    this.Faccomiva = Faccomiva;
    this.Faccomtotal=Faccomtotal;
    }  
    public clsFacturascompras(int Faccomid, String Faccomnumero,Timestamp Faccomfecha,int Procodigo,double Faccomsubtotal,double Faccomiva,double Faccomtotal, String Faccomestado) 
    {   
    this.Faccomid = Faccomid;
    this.Faccomnumero = Faccomnumero;
    this.Faccomfecha = Faccomfecha;
    this.Procodigo = Procodigo;
    this.Faccomsubtotal = Faccomsubtotal;
    this.Faccomiva = Faccomiva;
    this.Faccomtotal=Faccomtotal;
    this.Faccomestado = Faccomestado;
    }  
    //ToString

    @Override
    public String toString() {
        return "clsFacturascompras{" + "Faccomid=" + Faccomid + ", Faccomnumero=" + Faccomnumero + ", Faccomfecha=" + Faccomfecha + ", Procodigo=" + Procodigo + ", Faccomsubtotal=" + Faccomsubtotal + ", Faccomiva=" + Faccomiva + ", Faccomtotal=" + Faccomtotal + ", Faccomestado=" + Faccomestado + '}';
    }
    
}
