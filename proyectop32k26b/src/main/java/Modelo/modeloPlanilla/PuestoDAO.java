package Modelo.modeloPlanilla;

import Controlador.controladorPlanilla.clsPuesto;
import Controlador.clsBitacora;
import Modelo.Conexion;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class PuestoDAO {

    // Consultas SQL - Asegúrate de que los nombres de las columnas coincidan con tu DB
    private static final String SQL_SELECT = "SELECT Puecodigo, Puenombre, Puesalario_base, Depcodigo FROM puestos";
    private static final String SQL_INSERT = "INSERT INTO puestos (Puenombre, Puesalario_base, Depcodigo) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE puestos SET Puenombre=?, Puesalario_base=?, Depcodigo=? WHERE Puecodigo=?";
    private static final String SQL_DELETE = "DELETE FROM puestos WHERE Puecodigo=?";
    private static final String SQL_SELECT_ID = "SELECT Puecodigo, Puenombre, Puesalario_base, Depcodigo FROM puestos WHERE Puecodigo=?";

    // Insertar un nuevo puesto
    public int insertarPuesto(clsPuesto puesto, clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, puesto.getPuenombre());
            stmt.setBigDecimal(2, puesto.getPuesalarioBase());
            stmt.setInt(3, puesto.getDepcodigo()); 
            rows = stmt.executeUpdate();
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
            cerrarConexion(null, stmt, conn); 
        }
        return rows;
    }

    // Actualizar un puesto existente
    public int actualizarPuesto(clsPuesto puesto, clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, puesto.getPuenombre());
            stmt.setBigDecimal(2, puesto.getPuesalarioBase());
            stmt.setInt(3, puesto.getDepcodigo());
            stmt.setInt(4, puesto.getPuecodigo());
            rows = stmt.executeUpdate();
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
            cerrarConexion(null, stmt, conn); 
        }
        return rows;
    }

    // Eliminar un puesto
    public int eliminarPuesto(clsPuesto puesto, clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, puesto.getPuecodigo());
            rows = stmt.executeUpdate();
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
            cerrarConexion(null, stmt, conn); 
        }
        return rows;
    }

    // Buscar un puesto por su ID (para el botón OK/Buscar)
    public clsPuesto obtenerPuestoPorId(int id, clsBitacora bitacora) {
        clsPuesto puesto = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                puesto = new clsPuesto();
                puesto.setPuecodigo(rs.getInt("Puecodigo"));
                puesto.setPuenombre(rs.getString("Puenombre"));
                puesto.setPuesalarioBase(rs.getBigDecimal("Puesalario_base"));
                puesto.setDepcodigo(rs.getInt("Depcodigo"));
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
            cerrarConexion(rs, stmt, conn); 
        }
        return puesto;
    }

    // Listar todos los puestos para llenar el JTable
    public DefaultTableModel listarPuestosEnTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Salario");
        
        try (Connection conn = Conexion.getConnection(); 
             Statement st = conn.createStatement(); 
             ResultSet rs = st.executeQuery(SQL_SELECT)) {
            
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("Puecodigo"), 
                    rs.getString("Puenombre"), 
                    rs.getBigDecimal("Puesalario_base")
                });
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return modelo;
    }

    // Método privado para cerrar recursos y evitar fugas de memoria
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