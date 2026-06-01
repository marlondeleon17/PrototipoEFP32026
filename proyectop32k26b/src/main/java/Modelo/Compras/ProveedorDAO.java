/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author JENNIFER BARRIOS 
 */

package Modelo.Compras;

import Controlador.Compras.clsProveedor;
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {
    private static final String SQL_SELECT = "SELECT Procodigo, Pronombre, Pronit, Procuentabancaria, Proestado, Procontacto, Prodepartamento FROM proveedores";
    private static final String SQL_INSERT = "INSERT INTO proveedores(Pronombre, Pronit, Procuentabancaria, Proestado, Procontacto, Prodepartamento) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE proveedores SET Pronombre=?, Pronit=?, Procuentabancaria=?, Proestado=?, Procontacto=?, Prodepartamento=? WHERE Procodigo=?";
    private static final String SQL_DELETE = "DELETE FROM proveedores WHERE Procodigo=?";
    private static final String SQL_QUERY = "SELECT Procodigo, Pronombre, Pronit, Procuentabancaria, Proestado, Procontacto, Prodepartamento FROM proveedores WHERE Procodigo=?";

    public List<clsProveedor> consultaProveedores() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<clsProveedor> proveedores = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                clsProveedor proveedor = new clsProveedor();
                proveedor.setProcodigo(rs.getInt("Procodigo"));
                proveedor.setPronombre(rs.getString("Pronombre"));
                proveedor.setPronit(rs.getString("Pronit"));
                proveedor.setProcuentabancaria(rs.getString("Procuentabancaria"));
                proveedor.setProestado(rs.getString("Proestado"));
                proveedor.setProcontacto(rs.getString("Procontacto"));
                proveedor.setProdepartamento(rs.getString("Prodepartamento"));
                proveedores.add(proveedor);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return proveedores;
    }
    
    public clsProveedor consultaProveedorPorId(clsProveedor proveedor) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
        conn = Conexion.getConnection();
        // SQL_QUERY es la que tiene el WHERE Procodigo = ?
        stmt = conn.prepareStatement(SQL_QUERY); 
        stmt.setInt(1, proveedor.getProcodigo()); // Aquí pasas el ID que quieres buscar
        rs = stmt.executeQuery();
        
        if (rs.next()) { // Solo usamos IF porque esperamos un solo resultado
            proveedor.setPronombre(rs.getString("Pronombre"));
            proveedor.setPronit(rs.getString("Pronit"));
            proveedor.setProcuentabancaria(rs.getString("Procuentabancaria"));
            proveedor.setProestado(rs.getString("Proestado"));
            proveedor.setProcontacto(rs.getString("Procontacto"));
            proveedor.setProdepartamento(rs.getString("Prodepartamento"));
        }
    } catch (SQLException ex) {
        ex.printStackTrace(System.out);
    } finally {
        Conexion.close(rs);
        Conexion.close(stmt);
        Conexion.close(conn);
    }
    return proveedor; 
}

    public int ingresarProveedor(clsProveedor proveedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, proveedor.getPronombre());
            stmt.setString(2, proveedor.getPronit());
            stmt.setString(3, proveedor.getProcuentabancaria());
            stmt.setString(4, proveedor.getProestado());
            stmt.setString(5, proveedor.getProcontacto());
            stmt.setString(6, proveedor.getProdepartamento());
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    public int actualizaProveedor(clsProveedor proveedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, proveedor.getPronombre());
            stmt.setString(2, proveedor.getPronit());
            stmt.setString(3, proveedor.getProcuentabancaria());
            stmt.setString(4, proveedor.getProestado());
            stmt.setString(5, proveedor.getProcontacto());
            stmt.setString(6, proveedor.getProdepartamento());
            stmt.setInt(7, proveedor.getProcodigo());
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    public int borrarProveedor(clsProveedor proveedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, proveedor.getProcodigo());
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }
}
