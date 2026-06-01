/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.controladorPlanilla;

/**
 *
 * @author Meilyn Garcia 9959-23-17838
 */
public class clsBitacoraPlanilla {
    private int idBitacora;
    private int codigo;
    private String accion;
    private String tablaAfectada;
    private String descripcion;
    private String usuario;
    private String fecha;
    
    public clsBitacoraPlanilla() {
    }

    public clsBitacoraPlanilla(String accion, String tablaAfectada,
            String descripcion, String usuario) {

        this.accion = accion;
        this.tablaAfectada = tablaAfectada;
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    public int getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(int idBitacora) {
        this.idBitacora = idBitacora;
    }
    
    public int getCodigo() {
    return codigo;
    }

    public void setCodigo(int codigo) {
    this.codigo = codigo;
    }

    public String getAccion() {
        return accion;
    }
    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getTablaAfectada() {
        return tablaAfectada;
    }

    public void setTablaAfectada(String tablaAfectada) {
        this.tablaAfectada = tablaAfectada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
