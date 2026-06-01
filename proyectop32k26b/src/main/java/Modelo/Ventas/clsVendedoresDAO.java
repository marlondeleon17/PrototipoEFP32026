/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Ventas;

import Controlador.Ventas.clsVendedores;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.Conexion; // Asegúrate de tener tu clase Conexion en el paquete Modelo

/**
 * @author Marice
 */
public class clsVendedoresDAO {

    // 1. Definición de las consultas SQL
    private static final String SQL_SELECT = "SELECT Venid, Emcodigo, Vennombre, Ventelefono, Vendireccion, Vencorreo, Vencomisiones FROM Vendedores";
    private static final String SQL_INSERT = "INSERT INTO Vendedores(Venid, Emcodigo, Vennombre, Ventelefono, Vendireccion, Vencorreo, Vencomisiones) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE Vendedores SET Emcodigo=?, Vennombre=?, Ventelefono=?, Vendireccion=?, Vencorreo=?, Vencomisiones=? WHERE Venid=?";
    private static final String SQL_DELETE = "DELETE FROM Vendedores WHERE Venid=?";
    private static final String SQL_QUERY = "SELECT Venid, Emcodigo, Vennombre, Ventelefono, Vendireccion, Vencorreo, Vencomisiones FROM Vendedores WHERE Venid=?";

    // 2. Método para listar todos los vendedores (para la tabla visual)
    public List<clsVendedores> consulta() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<clsVendedores> vendedores = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                clsVendedores vendedor = new clsVendedores();
                vendedor.setVenid(rs.getInt("Venid"));
                vendedor.setEmcodigo(rs.getInt("Emcodigo"));
                vendedor.setVennombre(rs.getString("Vennombre"));
                vendedor.setVentelefono(rs.getString("Ventelefono"));
                vendedor.setVendireccion(rs.getString("Vendireccion"));
                vendedor.setVencorreo(rs.getString("Vencorreo"));
                vendedor.setVencomisiones(rs.getDouble("Vencomisiones"));
                vendedores.add(vendedor);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return vendedores;
    }

    // 3. Método para insertar un nuevo vendedor
    public int insertar(clsVendedores vendedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, vendedor.getVenid());
            stmt.setInt(2, vendedor.getEmcodigo());
            stmt.setString(3, vendedor.getVennombre());
            stmt.setString(4, vendedor.getVentelefono());
            stmt.setString(5, vendedor.getVendireccion());
            stmt.setString(6, vendedor.getVencorreo());
            stmt.setDouble(7, vendedor.getVencomisiones());
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    // 4. Método para actualizar datos de un vendedor existente
    public int actualizar(clsVendedores vendedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setInt(1, vendedor.getEmcodigo());
            stmt.setString(2, vendedor.getVennombre());
            stmt.setString(3, vendedor.getVentelefono());
            stmt.setString(4, vendedor.getVendireccion());
            stmt.setString(5, vendedor.getVencorreo());
            stmt.setDouble(6, vendedor.getVencomisiones());
            stmt.setInt(7, vendedor.getVenid());
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    // 5. Método para eliminar un vendedor
    public int borrar(clsVendedores vendedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, vendedor.getVenid());
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    // 6. Método para buscar un solo vendedor por ID
    public clsVendedores buscar(clsVendedores vendedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_QUERY);
            stmt.setInt(1, vendedor.getVenid());
            rs = stmt.executeQuery();
            while (rs.next()) {
                vendedor.setEmcodigo(rs.getInt("Emcodigo"));
                vendedor.setVennombre(rs.getString("Vennombre"));
                vendedor.setVentelefono(rs.getString("Ventelefono"));
                vendedor.setVendireccion(rs.getString("Vendireccion"));
                vendedor.setVencorreo(rs.getString("Vencorreo"));
                vendedor.setVencomisiones(rs.getDouble("Vencomisiones"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return vendedor;
    }
}