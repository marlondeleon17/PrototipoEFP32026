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
public class clsTransportistas {
    
    private int Tranid;
    private String Trantipovehiculo;
    private int Empcodigo;

    //constructor vacio
    public clsTransportistas(){
    }
    
    //constructor con parametros
    public clsTransportistas(int Tranid, String Trantipovehiculo, int Empcodigo) {
        this.Tranid = Tranid;
        this.Trantipovehiculo = Trantipovehiculo;
        this.Empcodigo = Empcodigo;
    }
    //getters y setters
    public int getTranid() {
        return Tranid;
    }

    public void setTranid(int Tranid) {
        this.Tranid = Tranid;
    }

    public String getTrantipovehiculo() {
        return Trantipovehiculo;
    }

    public void setTrantipovehiculo(String Trantipovehiculo) {
        this.Trantipovehiculo = Trantipovehiculo;
    }

    public int getEmpcodigo() {
        return Empcodigo;
    }

    public void setEmpcodigo(int Empcodigo) {
        this.Empcodigo = Empcodigo;
    }

    @Override
    public String toString() {
        return "clsTransportistas{" + "Tranid=" + Tranid + ", Trantipovehiculo=" + Trantipovehiculo + ", Empcodigo=" + Empcodigo + '}';
    }

}
