/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.modeloPlanilla;

import Controlador.controladorPlanilla.clsDepartamento;
import Controlador.clsBitacora;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Meilyn Garcia 9959-23-17838
 */
public class DepartamentoDAO {
    private static final String SQL_SELECT
            = "SELECT Depcodigo, Depnombre, Depestado FROM departamentos";

    private static final String SQL_INSERT
            = "INSERT INTO departamentos (Depnombre, Depestado) VALUES(?, ?)";

    private static final String SQL_UPDATE
            = "UPDATE departamentos SET Depnombre=?, Depestado=? WHERE Depcodigo=?";

    private static final String SQL_DELETE
            = "DELETE FROM departamentos WHERE Depcodigo=?";

    private static final String SQL_SELECT_ID
            = "SELECT Depcodigo, Depnombre, Depestado FROM departamentos WHERE Depcodigo=?";

    // ==========================
    // OBTENER TODOS
    // ==========================
    public List<clsDepartamento> obtenerDepartamentos(clsBitacora bitacora) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<clsDepartamento> lista = new ArrayList<>();

        try {

            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {

                clsDepartamento d = new clsDepartamento();

                d.setDepcodigo(rs.getInt("Depcodigo"));
                d.setDepnombre(rs.getString("Depnombre"));
                d.setDepestado(rs.getInt("Depestado"));

                lista.add(d);
            }

            bitacora.setBitaccion("SELECT departamentos");

            bitacora.setBitaccion("SELECT departamentos");

        } catch (SQLException e) {

            e.printStackTrace(System.out);

        } finally {

            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return lista;
    }

    // ==========================
    // INSERTAR
    // ==========================
    public int insertarDepartamento(clsDepartamento departamento, clsBitacora bitacora) {


        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {

            conn = Conexion.getConnection();

            stmt = conn.prepareStatement(
                    "INSERT INTO departamentos(Depnombre, Depestado) VALUES(?, ?)"
            );

            stmt.setString(1, departamento.getDepnombre());
            stmt.setInt(2, departamento.getDepestado());

            rows = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return rows;
    
    }

    // ==========================
    // ACTUALIZAR
    // ==========================
    public int actualizarDepartamento(clsDepartamento departamento, clsBitacora bitacora) {

        Connection conn = null;
        PreparedStatement stmt = null;

        int rows = 0;

        try {

            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            stmt.setString(1, departamento.getDepnombre());
            stmt.setInt(2, departamento.getDepestado());
            stmt.setInt(3, departamento.getDepcodigo());

            rows = stmt.executeUpdate();

            bitacora.setBitaccion("UPDATE departamento " + departamento.getDepcodigo());

            bitacora.setBitaccion("SELECT departamentos");
        } catch (SQLException e) {

            e.printStackTrace(System.out);

        } finally {

            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return rows;
    }

    // ==========================
    // ELIMINAR
    // ==========================
    public int eliminarDepartamento(clsDepartamento departamento, clsBitacora bitacora) {

        Connection conn = null;
        PreparedStatement stmt = null;

        int rows = 0;

        try {

            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);

            stmt.setInt(1, departamento.getDepcodigo());

            rows = stmt.executeUpdate();

            bitacora.setBitaccion("DELETE departamento " + departamento.getDepcodigo());

            bitacora.setBitaccion("SELECT departamentos");

        } catch (SQLException e) {

            e.printStackTrace(System.out);

        } finally {

            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return rows;
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================
    public clsDepartamento obtenerDepartamentoPorId(int id, clsBitacora bitacora) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        clsDepartamento departamento = null;

        try {

            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ID);

            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {

                departamento = new clsDepartamento();

                departamento.setDepcodigo(rs.getInt("Depcodigo"));
                departamento.setDepnombre(rs.getString("Depnombre"));
                departamento.setDepestado(rs.getInt("Depestado"));
            }

            bitacora.setBitaccion("SELECT departamento ID " + id);

            bitacora.setBitaccion("SELECT departamentos");

        } catch (SQLException e) {

            e.printStackTrace(System.out);

        } finally {

            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return departamento;
    }
    public DefaultTableModel listarDepartamentos() {
        
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Código");
        modelo.addColumn("Nombre del Departamento");
        modelo.addColumn("Estado");

        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;

        try {

            cn = Conexion.getConnection();

            st = cn.createStatement();

            rs = st.executeQuery(
                    "SELECT Depcodigo, Depnombre, Depestado FROM departamentos");

            while (rs.next()) {

                String codigo = rs.getString("Depcodigo");
                String nombre = rs.getString("Depnombre");

                String estado;

                if (rs.getInt("Depestado") == 1) {
                    estado = "Activo";
                } else {
                    estado = "Inactivo";
                }

                modelo.addRow(new Object[]{
                    codigo,
                    nombre,
                    estado
                });
            }

        } catch (Exception e) {

            System.out.println("Error al listar departamentos");
            System.out.println(e);

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }

                if (st != null) {
                    st.close();
                }

                if (cn != null) {
                    cn.close();
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return modelo;
    }
}