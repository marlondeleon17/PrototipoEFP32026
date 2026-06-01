/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.modeloPlanilla;

import Controlador.controladorPlanilla.clsEmpleados;
import Controlador.clsBitacora;
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marlo
 */
public class EmpleadosDAO {
    
    
    
    private static final String SQL_SELECT =
            "SELECT empcodigo, empnombre, empdpi, puecodigo, empfecha_ingreso, empestado FROM empleados";

    private static final String SQL_INSERT =
            "INSERT INTO empleados (empnombre, empdpi, puecodigo, empfecha_ingreso, empestado) VALUES(?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE empleados SET empnombre=?, empdpi=?, puecodigo=?, empfecha_ingreso=?, empestado=? WHERE empcodigo=?";

    private static final String SQL_DELETE =
            "DELETE FROM empleados WHERE empcodigo=?";

    private static final String SQL_SELECT_ID =
            "SELECT empcodigo, empnombre, empdpi, puecodigo, empfecha_ingreso, empestado FROM empleados WHERE empcodigo=?";


    private static final String SQL_INSERT_BITACORA =
            "INSERT INTO bitacora(usuid, aplcodigo, bitfecha, bitip, bitequipo, bitaccion) VALUES(?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_BITACORA =
            "SELECT bitcodigo, usuid, aplcodigo, bitfecha, bitip, bitequipo, bitaccion FROM bitacora";

    private static final String SQL_UPDATE_BITACORA =
            "UPDATE bitacora SET usuid=?, aplcodigo=?, bitfecha=?, bitip=?, bitequipo=?, bitaccion=? WHERE bitcodigo=?";

    private static final String SQL_DELETE_BITACORA =
            "DELETE FROM bitacora WHERE bitcodigo=?";


    public List<clsEmpleados> obtenerEmpleados(clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<clsEmpleados> lista = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                clsEmpleados e = new clsEmpleados();

                e.setEmpcodigo(rs.getInt("empcodigo"));
                e.setEmpnombre(rs.getString("empnombre"));
                e.setEmpdpi(rs.getString("empdpi"));
                e.setPuecodigo(rs.getInt("puecodigo"));
                e.setEmpfecha_ingreso(rs.getDate("empfecha_ingreso"));
                e.setEmpestado(rs.getInt("empestado"));

                lista.add(e);
            }

            bitacora.setBitaccion("SELECT empleados");
            insertarBitacora(bitacora);

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return lista;
    }

    public int insertarEmpleado(clsEmpleados empleado, clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);

            stmt.setString(1, empleado.getEmpnombre());
            stmt.setString(2, empleado.getEmpdpi());
            stmt.setInt(3, empleado.getPuecodigo());
            stmt.setDate(4, empleado.getEmpfecha_ingreso());
            stmt.setInt(5, empleado.getEmpestado());

            rows = stmt.executeUpdate();

            bitacora.setBitaccion("INSERT empleado " + empleado.getEmpnombre());
            insertarBitacora(bitacora);

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return rows;
    }

    public int actualizarEmpleado(clsEmpleados empleado, clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            stmt.setString(1, empleado.getEmpnombre());
            stmt.setString(2, empleado.getEmpdpi());
            stmt.setInt(3, empleado.getPuecodigo());
            stmt.setDate(4, empleado.getEmpfecha_ingreso());
            stmt.setInt(5, empleado.getEmpestado());
            stmt.setInt(6, empleado.getEmpcodigo());

            rows = stmt.executeUpdate();

            bitacora.setBitaccion("UPDATE empleado " + empleado.getEmpcodigo());
            insertarBitacora(bitacora);

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return rows;
    }

    public int eliminarEmpleado(clsEmpleados empleado, clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);

            stmt.setInt(1, empleado.getEmpcodigo());

            rows = stmt.executeUpdate();

            bitacora.setBitaccion("DELETE empleado " + empleado.getEmpcodigo());
            insertarBitacora(bitacora);

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return rows;
    }

    public clsEmpleados obtenerEmpleadoPorId(int id, clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        clsEmpleados empleado = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ID);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                empleado = new clsEmpleados();

                empleado.setEmpcodigo(rs.getInt("empcodigo"));
                empleado.setEmpnombre(rs.getString("empnombre"));
                empleado.setEmpdpi(rs.getString("empdpi"));
                empleado.setPuecodigo(rs.getInt("puecodigo"));
                empleado.setEmpfecha_ingreso(rs.getDate("empfecha_ingreso"));
                empleado.setEmpestado(rs.getInt("empestado"));
            }

            bitacora.setBitaccion("SELECT empleado ID " + id);
            insertarBitacora(bitacora);

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return empleado;
    }


    public int insertarBitacora(clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_BITACORA);

            stmt.setInt(1, bitacora.getUsucodigo());
            stmt.setInt(2, bitacora.getAplcodigo());
            stmt.setString(3, bitacora.getBitfecha());
            stmt.setString(4, bitacora.getBitip());
            stmt.setString(5, bitacora.getBitequipo());
            stmt.setString(6, bitacora.getBitaccion());

            rows = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return rows;
    }

    public List<clsBitacora> obtenerBitacora() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<clsBitacora> lista = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BITACORA);
            rs = stmt.executeQuery();

            while (rs.next()) {
                clsBitacora b = new clsBitacora();

                b.setBitcodigo(rs.getInt("bitcodigo"));
                b.setUsucodigo(rs.getInt("usuid"));
                b.setAplcodigo(rs.getInt("aplcodigo"));
                b.setBitfecha(rs.getString("bitfecha"));
                b.setBitip(rs.getString("bitip"));
                b.setBitequipo(rs.getString("bitequipo"));
                b.setBitaccion(rs.getString("bitaccion"));

                lista.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return lista;
    }

    public int actualizarBitacora(clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_BITACORA);

            stmt.setInt(1, bitacora.getUsucodigo());
            stmt.setInt(2, bitacora.getAplcodigo());
            stmt.setString(3, bitacora.getBitfecha());
            stmt.setString(4, bitacora.getBitip());
            stmt.setString(5, bitacora.getBitequipo());
            stmt.setString(6, bitacora.getBitaccion());
            stmt.setInt(7, bitacora.getBitcodigo());

            rows = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return rows;
    }

    public int eliminarBitacora(clsBitacora bitacora) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_BITACORA);

            stmt.setInt(1, bitacora.getBitcodigo());

            rows = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return rows;
    }
    
    
}
