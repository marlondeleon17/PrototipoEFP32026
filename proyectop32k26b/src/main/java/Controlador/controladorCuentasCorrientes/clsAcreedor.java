/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.controladorCuentasCorrientes;

import Modelo.modeloCuentasCorrientes.AcreedorDAO;
import java.util.List;

/**
 *
 * @author astri
 */
public class clsAcreedor {

    // =========================================================
    // ATRIBUTOS
    // =========================================================
    private int    AcreCodigo;
    private String AcreNombre;
    private String AcreNit;
    private String AcreCuentaBancaria;
    private String AcreEstado;

    // =========================================================
    // CONSTRUCTOR VACÍO
    // =========================================================
    public clsAcreedor() {
    }

    
    public clsAcreedor(int AcreCodigo, String AcreNombre, String AcreNit,
                       String AcreCuentaBancaria, String AcreEstado) {
        this.AcreCodigo         = AcreCodigo;
        this.AcreNombre         = AcreNombre;
        this.AcreNit            = AcreNit;
        this.AcreCuentaBancaria = AcreCuentaBancaria;
        this.AcreEstado         = AcreEstado;
    }

    
    public int getAcreCodigo() { return AcreCodigo; }
    public void setAcreCodigo(int AcreCodigo) { this.AcreCodigo = AcreCodigo; }

    public String getAcreNombre() { return AcreNombre; }
    public void setAcreNombre(String AcreNombre) { this.AcreNombre = AcreNombre; }

    public String getAcreNit() { return AcreNit; }
    public void setAcreNit(String AcreNit) { this.AcreNit = AcreNit; }

    public String getAcreCuentaBancaria() { return AcreCuentaBancaria; }
    public void setAcreCuentaBancaria(String AcreCuentaBancaria) { this.AcreCuentaBancaria = AcreCuentaBancaria; }

    public String getAcreEstado() { return AcreEstado; }
    public void setAcreEstado(String AcreEstado) { this.AcreEstado = AcreEstado; }

  
    @Override
    public String toString() {
        return "Acreedor{"
                + "AcreCodigo="         + AcreCodigo
                + ", AcreNombre='"      + AcreNombre
                + ", AcreNit='"         + AcreNit
                + ", AcreCuentaBancaria='" + AcreCuentaBancaria
                + ", AcreEstado='"      + AcreEstado
                + '}';
    }
    // MÉTODOS DE ACCESO AL DAO (CRUD)
    

    // INSERT
    public int setIngresarAcreedor(clsAcreedor acreedor) {
        AcreedorDAO daoAcreedor = new AcreedorDAO();
        return daoAcreedor.ingresaAcreedor(acreedor);
    }

    // SELECT todos
    public List<clsAcreedor> getListadoAcreedores() {
        AcreedorDAO daoAcreedor = new AcreedorDAO();
        return daoAcreedor.consultaAcreedores();
    }

    // SELECT por Codigo
    public clsAcreedor getBuscarAcreedorPorId(clsAcreedor acreedor) {
        AcreedorDAO daoAcreedor = new AcreedorDAO();
        return daoAcreedor.consultaAcreedorPorId(acreedor);
    }

    // SELECT por Nombre
    public List<clsAcreedor> getBuscarAcreedoresPorNombre(clsAcreedor acreedor) {
        AcreedorDAO daoAcreedor = new AcreedorDAO();
        return daoAcreedor.consultaAcreedoresPorNombre(acreedor);
    }

    // SELECT por NIT
    public clsAcreedor getBuscarAcreedorPorNit(clsAcreedor acreedor) {
        AcreedorDAO daoAcreedor = new AcreedorDAO();
        return daoAcreedor.consultaAcreedorPorNit(acreedor);
    }

    // SELECT por Estado
    public List<clsAcreedor> getBuscarAcreedoresPorEstado(clsAcreedor acreedor) {
        AcreedorDAO daoAcreedor = new AcreedorDAO();
        return daoAcreedor.consultaAcreedoresPorEstado(acreedor);
    }

    // UPDATE
    public int setModificarAcreedor(clsAcreedor acreedor) {
        AcreedorDAO daoAcreedor = new AcreedorDAO();
        return daoAcreedor.actualizaAcreedor(acreedor);
    }

    // DELETE
    public int setBorrarAcreedor(clsAcreedor acreedor) {
        AcreedorDAO daoAcreedor = new AcreedorDAO();
        return daoAcreedor.borraAcreedor(acreedor);
    }
}