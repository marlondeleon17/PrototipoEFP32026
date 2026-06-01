package Modelo.modeloPlanilla;

import Controlador.controladorPlanilla.clsPlanillaDetalle;
import Controlador.clsBitacora; // Mantengo la bitácora como en tu base
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * DAO para planilladetalle basado en la estructura de PuestoDAO
 */
public class PlanillaDetalleDAO {

    private static final String SQL_SELECT = "SELECT detcodigo, placodigo, empcodigo, detsalario, dettotalpercepciones, dettotaldeducciones, detliquido FROM planilladetalle";
    private static final String SQL_INSERT = "INSERT INTO planilladetalle (placodigo, empcodigo, detsalario, dettotalpercepciones, dettotaldeducciones, detliquido) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE planilladetalle SET placodigo=?, empcodigo=?, detsalario=?, dettotalpercepciones=?, dettotaldeducciones=?, detliquido=? WHERE detcodigo=?";
    private static final String SQL_DELETE = "DELETE FROM planilladetalle WHERE detcodigo=?";
    private static final String SQL_SELECT_ID = "SELECT detcodigo, placodigo, empcodigo, detsalario, dettotalpercepciones, dettotaldeducciones, detliquido FROM planilladetalle WHERE detcodigo=?";

    public int insertarPlanillaDetalle(clsPlanillaDetalle detalle, clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, detalle.getPlacodigo());
            stmt.setInt(2, detalle.getEmpcodigo());
            stmt.setDouble(3, detalle.getDetsalario());
            stmt.setDouble(4, detalle.getDettotalpercepciones());
            stmt.setDouble(5, detalle.getDettotaldeducciones());
            stmt.setDouble(6, detalle.getDetliquido());
            rows = stmt.executeUpdate();
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
            cerrarConexion(null, stmt, conn); 
        }
        return rows;
    }

    public int actualizarPlanillaDetalle(clsPlanillaDetalle detalle, clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setInt(1, detalle.getPlacodigo());
            stmt.setInt(2, detalle.getEmpcodigo());
            stmt.setDouble(3, detalle.getDetsalario());
            stmt.setDouble(4, detalle.getDettotalpercepciones());
            stmt.setDouble(5, detalle.getDettotaldeducciones());
            stmt.setDouble(6, detalle.getDetliquido());
            stmt.setInt(7, detalle.getDetcodigo());
            rows = stmt.executeUpdate();
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
            cerrarConexion(null, stmt, conn); 
        }
        return rows;
    }

    public int eliminarPlanillaDetalle(clsPlanillaDetalle detalle, clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, detalle.getDetcodigo());
            rows = stmt.executeUpdate();
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
            cerrarConexion(null, stmt, conn); 
        }
        return rows;
    }

    public clsPlanillaDetalle obtenerPlanillaDetallePorId(int id, clsBitacora bitacora) {
        clsPlanillaDetalle detalle = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                detalle = new clsPlanillaDetalle();
                detalle.setDetcodigo(rs.getInt("detcodigo"));
                detalle.setPlacodigo(rs.getInt("placodigo"));
                detalle.setEmpcodigo(rs.getInt("empcodigo"));
                detalle.setDetsalario(rs.getDouble("detsalario"));
                detalle.setDettotalpercepciones(rs.getDouble("dettotalpercepciones"));
                detalle.setDettotaldeducciones(rs.getDouble("dettotaldeducciones"));
                detalle.setDetliquido(rs.getDouble("detliquido"));
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
            cerrarConexion(rs, stmt, conn); 
        }
        return detalle;
    }

    public DefaultTableModel listarPlanillaDetalleEnTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Cód. Detalle");
        modelo.addColumn("Cód. Planilla");
        modelo.addColumn("Cód. Empleado");
        modelo.addColumn("Salario");
        modelo.addColumn("Líquido");
        
        try (Connection conn = Conexion.getConnection(); 
             Statement st = conn.createStatement(); 
             ResultSet rs = st.executeQuery(SQL_SELECT)) {
            
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("detcodigo"), 
                    rs.getInt("placodigo"), 
                    rs.getInt("empcodigo"),
                    rs.getDouble("detsalario"),
                    rs.getDouble("detliquido")
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
}