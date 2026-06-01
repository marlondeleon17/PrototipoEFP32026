/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.controladorCuentasCorrientes;

import java.time.LocalDateTime;
/**
 *
 * @author WINDOWS
 */
public class clsMovimientoTransacciones {
    private int mccid;
    private String mccfecha;
    private double mccmonto;
    private String mcctipo;
    private String mccconcepto;
    private String mccestado;
    private double mccsaldo;
    private int cliid;
    private int procodigo;
    private int acrecodigo;
    private int venid;
    private int TTid;
    private String mccmodulo;
    private int mccorigenid;

    public clsMovimientoTransacciones() {
    }

    public clsMovimientoTransacciones(int mccid, String mccfecha, double mccmonto, String mcctipo, String mccconcepto, String mccestado, double mccsaldo, int cliid, int procodigo, int acrecodigo, int venid, int TTid, String mccmodulo, int mccorigenid) {
        this.mccid = mccid;
        this.mccfecha = mccfecha;
        this.mccmonto = mccmonto;
        this.mcctipo = mcctipo;
        this.mccconcepto = mccconcepto;
        this.mccestado = mccestado;
        this.mccsaldo = mccsaldo;
        this.cliid = cliid;
        this.procodigo = procodigo;
        this.acrecodigo = acrecodigo;
        this.venid = venid;
        this.TTid = TTid;
        this.mccmodulo = mccmodulo;
        this.mccorigenid = mccorigenid;
    }

    public int getMccid() {
        return mccid;
    }

    public void setMccid(int mccid) {
        this.mccid = mccid;
    }

    public String getMccfecha() {
        return mccfecha;
    }

    public void setMccfecha(String mccfecha) {
        this.mccfecha = mccfecha;
    }

    public double getMccmonto() {
        return mccmonto;
    }

    public void setMccmonto(double mccmonto) {
        this.mccmonto = mccmonto;
    }

    public String getMcctipo() {
        return mcctipo;
    }

    public void setMcctipo(String mcctipo) {
        this.mcctipo = mcctipo;
    }

    public String getMccconcepto() {
        return mccconcepto;
    }

    public void setMccconcepto(String mccconcepto) {
        this.mccconcepto = mccconcepto;
    }

    public String getMccestado() {
        return mccestado;
    }

    public void setMccestado(String mccestado) {
        this.mccestado = mccestado;
    }

    public double getMccsaldo() {
        return mccsaldo;
    }

    public void setMccsaldo(double mccsaldo) {
        this.mccsaldo = mccsaldo;
    }

    public int getCliid() {
        return cliid;
    }

    public void setCliid(int cliid) {
        this.cliid = cliid;
    }

    public int getProcodigo() {
        return procodigo;
    }

    public void setProcodigo(int procodigo) {
        this.procodigo = procodigo;
    }

    public int getAcrecodigo() {
        return acrecodigo;
    }

    public void setAcrecodigo(int acrecodigo) {
        this.acrecodigo = acrecodigo;
    }

    public int getVenid() {
        return venid;
    }

    public void setVenid(int venid) {
        this.venid = venid;
    }

    public int getTTid() {
        return TTid;
    }

    public void setTTid(int TTid) {
        this.TTid = TTid;
    }

    public String getMccmodulo() {
        return mccmodulo;
    }

    public void setMccmodulo(String mccmodulo) {
        this.mccmodulo = mccmodulo;
    }

    public int getMccorigenid() {
        return mccorigenid;
    }

    public void setMccorigenid(int mccorigenid) {
        this.mccorigenid = mccorigenid;
    }

    @Override
    public String toString() {
        return "clsMovimientoTransacciones{" + "mccid=" + mccid + ", mccfecha=" + mccfecha + ", mccmonto=" + mccmonto + ", mcctipo=" + mcctipo + ", mccconcepto=" + mccconcepto + ", mccestado=" + mccestado + ", mccsaldo=" + mccsaldo + ", cliid=" + cliid + ", procodigo=" + procodigo + ", acrecodigo=" + acrecodigo + ", venid=" + venid + ", TTid=" + TTid + ", mccmodulo=" + mccmodulo + ", mccorigenid=" + mccorigenid + '}';
    }

}



