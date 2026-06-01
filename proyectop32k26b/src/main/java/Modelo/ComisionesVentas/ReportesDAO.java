/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.ComisionesVentas;
import Controlador.ComisionesVentas.clsReportes;
import Modelo.BitacoraDAO;
import Controlador.clsUsuarioConectado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.Conexion;
import javax.swing.JOptionPane;
/**
 *
 * @author xander reyes
 */
/**
 * ReportesDAO: Objeto de Acceso a Datos.
 * Esta clase centraliza todas las operaciones de lectura y escritura en la base de datos
 * específicamente para la tabla de reportes de comisiones.
 */
public class ReportesDAO {

    /**
     * Obtiene el nombre de un vendedor buscando por su ID único.
     * @param id Identificador del vendedor.
     * @return String con el nombre del vendedor o vacío si no se encuentra.
     */
    
    public String obtenerNombreVendedor(int id) {
        String nombre = ""; 
        // Consulta SQL parametrizada para evitar ataques de Inyección SQL
        String sql = "SELECT Vennombre FROM vendedores WHERE Venid = ?"; 
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) { 
            
            ps.setInt(1, id); // Asigna el ID al parámetro de la consulta
            
            try (ResultSet rs = ps.executeQuery()) { 
                if (rs.next()) { 
                    // Extrae el nombre directamente de la columna de la base de datos
                    nombre = rs.getString("Vennombre"); 
                }
            }
        } catch (SQLException e) { 
            System.err.println("Error al obtener nombre del vendedor: " + e.getMessage());
        }
        return nombre;
    }

    /**
     * Recupera una lista de objetos clsReportes filtrados por vendedor y rango de fechas.
     * Utiliza la tabla 'reportescomisionventa' que ya contiene la información consolidada.
     */
    public List<clsReportes> listarPorFiltro(int idVen, String fInicial, String fFinal) {
        List<clsReportes> lista = new ArrayList<>(); 
        // Seleccionamos todos los campos requeridos, incluyendo los nuevos: Repid y Rephora
        String sql = "SELECT Repid, Repfecha, Rephora, Venid, Vennombre, Comcomision " +
                     "FROM reportescomisionventa " +
                     "WHERE Venid = ? AND Repfecha BETWEEN ? AND ?";

        try (Connection conn = Conexion.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) { 
            
            // Configuración de parámetros para el filtro
            ps.setInt(1, idVen); 
            ps.setString(2, fInicial); 
            ps.setString(3, fFinal); 
            
            try (ResultSet rs = ps.executeQuery()) { 
                while (rs.next()) { 
                    // Se crea una nueva instancia de la entidad por cada fila encontrada
                    clsReportes rep = new clsReportes(); 
                    
                    // Llenado de los atributos del objeto con los datos de la base de datos
                    rep.setRepid(rs.getInt("Repid"));           // Nuevo: ID autoincremental del reporte
                    rep.setRephora(rs.getString("Rephora"));     // Nuevo: Hora del registro
                    rep.setVenid(rs.getInt("Venid"));            // ID del vendedor
                    rep.setVen_nombre(rs.getString("Vennombre"));// Nombre del vendedor
                    rep.setComcomision(rs.getDouble("Comcomision")); // Monto de comisión
                    rep.setRepfecha(rs.getString("Repfecha"));   // Fecha del registro
                    
                    lista.add(rep); // Se añade el objeto a la colección
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta de filtrado: " + e.getMessage());
        }
        return lista; 
    }

    /**
     * Inserta un nuevo registro de reporte en la base de datos.
     * @param reporte Objeto de tipo clsReportes con la información a guardar.
     * @return boolean True si la inserción fue exitosa, False en caso contrario.
     */
    public boolean insertar(clsReportes reporte) {
        // SQL para insertar en reportescomisionventa. 
        // Se usa CURDATE() y CURTIME() para registrar el momento exacto de la operación.
        String sql = "INSERT INTO reportescomisionventa (Repfecha, Rephora, Venid, Vennombre, Comcomision) " +
                     "VALUES (CURDATE(), CURTIME(), ?, ?, ?)";
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Mapeo de los atributos del objeto a los parámetros del SQL
            ps.setInt(1, reporte.getVenid()); 
            ps.setString(2, reporte.getVen_nombre());
            ps.setDouble(3, reporte.getComcomision()); 
            
            // Retorna true si se insertó al menos un registro (executeUpdate devuelve filas afectadas)
            return ps.executeUpdate() > 0; 
        } catch (SQLException e) {
            // Notifica al usuario en caso de un error crítico de persistencia
            JOptionPane.showMessageDialog(null, "Error al guardar el reporte: " + e.getMessage());
            return false;
        }
    }
}