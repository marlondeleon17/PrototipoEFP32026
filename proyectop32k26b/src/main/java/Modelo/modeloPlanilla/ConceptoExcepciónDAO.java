/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.modeloPlanilla;

import Controlador.controladorPlanilla.clsConceptoExcepción;
import Controlador.clsBitacora;
import Modelo.Conexion;
import java.sql.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author marlo
 */
public class ConceptoExcepciónDAO {
    
    private static final String SQL_SELECT = "SELECT Exccodigo, Concodigo, Empcodigo FROM conceptosexcepcion";
    private static final String SQL_INSERT = "INSERT INTO conceptosexcepcion (Concodigo, Empcodigo) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE conceptosexcepcion SET Concodigo=?, Empcodigo=? WHERE Exccodigo=?";
    private static final String SQL_DELETE = "DELETE FROM conceptosexcepcion WHERE Exccodigo=?";
    private static final String SQL_SELECT_ID = "SELECT Exccodigo, Concodigo, Empcodigo FROM conceptosexcepcion WHERE Exccodigo=?";

    private int idUsuarioBitacora = 1; 

    public int insertarExcepcion(clsConceptoExcepción excepcion, clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, excepcion.getConCodigo());
            stmt.setInt(2, excepcion.getEmpCodigo()); 
            rows = stmt.executeUpdate();
            
            if (rows > 0 && bitacora != null) {
                // NOTA: Ajusta los parámetros de este método según la firma real de tu clsBitacora
                // bitacora.ingresarBitacora(idUsuarioBitacora, "123", "Insertar", "conceptosexcepcion");
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
            cerrarConexion(null, stmt, conn); 
        }
        return rows;
    }

    public int actualizarExcepcion(clsConceptoExcepción excepcion, clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setInt(1, excepcion.getConCodigo());
            stmt.setInt(2, excepcion.getEmpCodigo());
            stmt.setInt(3, excepcion.getExcCodigo());
            rows = stmt.executeUpdate();
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
            cerrarConexion(null, stmt, conn); 
        }
        return rows;
    }

    public int eliminarExcepcion(clsConceptoExcepción excepcion, clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, excepcion.getExcCodigo());
            rows = stmt.executeUpdate();
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
            cerrarConexion(null, stmt, conn); 
        }
        return rows;
    }

    public clsConceptoExcepción obtenerExcepcionPorId(int id, clsBitacora bitacora) {
        clsConceptoExcepción excepcion = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                excepcion = new clsConceptoExcepción();
                excepcion.setExcCodigo(rs.getInt("Exccodigo"));
                excepcion.setConCodigo(rs.getInt("Concodigo"));
                excepcion.setEmpCodigo(rs.getInt("Empcodigo"));
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
            cerrarConexion(rs, stmt, conn); 
        }
        return excepcion;
    }

    public DefaultTableModel listarExcepcionesEnTabla() {
         DefaultTableModel modelo = new DefaultTableModel();

    modelo.addColumn("Código Excepción");
    modelo.addColumn("Código Concepto");
    modelo.addColumn("Código Empleado");

    String SQL_SELECT =
            "SELECT Exccodigo, Concodigo, Empcodigo "
            + "FROM conceptosexcepcion";

    try (
        Connection conn = Conexion.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(SQL_SELECT)
    ) {

        while (rs.next()) {

            modelo.addRow(new Object[]{

                rs.getInt("Exccodigo"),
                rs.getInt("Concodigo"),
                rs.getInt("Empcodigo")
            });
        }

    } catch (SQLException e) {

        e.printStackTrace();
    }

    return modelo;
    }

    private void cerrarConexion(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try { 
            if (rs != null) rs.close(); 
            if (stmt != null) stmt.close(); 
            if (conn != null) conn.close(); 
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }

    public List<clsConceptoExcepción> listar(clsBitacora bitacora) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
