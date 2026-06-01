/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.controladorPlanilla;
import java.sql.Date;
/**
 *
 * @author meilyn garcia
 */
public class clsPlanillaEncabezado {
    
    private int placodigo;
    private Date plafecha;
    private double platotal;
    private int plaestado;
    private int movbid;

    public clsPlanillaEncabezado() {
    }

    public clsPlanillaEncabezado(int placodigo, Date plafecha, double platotal, int plaestado, int movbid) {
        this.placodigo = placodigo;
        this.plafecha = plafecha;
        this.platotal = platotal;
        this.plaestado = plaestado;
        this.movbid = movbid;
    }


    public int getPlacodigo() {
        return placodigo;
    }

    public void setPlacodigo(int placodigo) {
        this.placodigo = placodigo;
    }

    public Date getPlafecha() {
        return plafecha;
    }

    public void setPlafecha(Date plafecha) {
        this.plafecha = plafecha;
    }

    public double getPlatotal() {
        return platotal;
    }

    public void setPlatotal(double platotal) {
        this.platotal = platotal;
    }

    public int getPlaestado() {
        return plaestado;
    }

    public void setPlaestado(int plaestado) {
        this.plaestado = plaestado;
    }

    public int getMovbid() {
        return movbid;
    }

    public void setMovbid(int movbid) {
        this.movbid = movbid;
    }
}
