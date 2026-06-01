/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Prototipo;

import Controlador.Prototipo.clsCarreras;
import Controlador.clsBitacora;
import java.util.List;
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author marlo
 */
public class CarrerasDAO {

    private static final String SQL_SELECT =
        "SELECT codigo_carrera, nombre_carrera, codigo_facultad, estatus_carrera FROM carreras";

private static final String SQL_INSERT =
        "INSERT INTO carreras(codigo_carrera, nombre_carrera, codigo_facultad, estatus_carrera) VALUES(?,?,?,?)";

private static final String SQL_UPDATE =
        "UPDATE carreras SET nombre_carrera=?, codigo_facultad=?, estatus_carrera=? WHERE codigo_carrera=?";

private static final String SQL_DELETE =
        "DELETE FROM carreras WHERE codigo_carrera=?";

private static final String SQL_SELECT_ID =
        "SELECT codigo_carrera, nombre_carrera, codigo_facultad, estatus_carrera FROM carreras WHERE codigo_carrera=?";


    public List<clsCarreras> obtenerCarreras(clsBitacora bitacora) {

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    List<clsCarreras> lista = new ArrayList<>();

    try {

        conn = Conexion.getConnection();
        stmt = conn.prepareStatement(SQL_SELECT);
        rs = stmt.executeQuery();

        while (rs.next()) {

            clsCarreras carrera = new clsCarreras();

            carrera.setCodigoCarrera(rs.getString("codigo_carrera"));
            carrera.setNombreCarrera(rs.getString("nombre_carrera"));
            carrera.setCodigoFacultad(rs.getString("codigo_facultad"));
            carrera.setEstatusCarrera(rs.getString("estatus_carrera"));

            lista.add(carrera);
        }

        bitacora.setBitaccion("SELECT carreras");
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

    public int actualizarCarrera(clsCarreras carrera, clsBitacora bitacora) {

    Connection conn = null;
    PreparedStatement stmt = null;
    int rows = 0;

    try {

        conn = Conexion.getConnection();
        stmt = conn.prepareStatement(SQL_UPDATE);

        stmt.setString(1, carrera.getNombreCarrera());
        stmt.setString(2, carrera.getCodigoFacultad());
        stmt.setString(3, carrera.getEstatusCarrera());
        stmt.setString(4, carrera.getCodigoCarrera());

        rows = stmt.executeUpdate();

        bitacora.setBitaccion("UPDATE carrera " + carrera.getCodigoCarrera());
        insertarBitacora(bitacora);

    } catch (SQLException e) {
        e.printStackTrace(System.out);
    } finally {
        Conexion.close(stmt);
        Conexion.close(conn);
    }

    return rows;
}

   public int eliminarCarrera(clsCarreras carrera, clsBitacora bitacora) {

    Connection conn = null;
    PreparedStatement stmt = null;
    int rows = 0;

    try {

        conn = Conexion.getConnection();
        stmt = conn.prepareStatement(SQL_DELETE);

        stmt.setString(1, carrera.getCodigoCarrera());

        rows = stmt.executeUpdate();

        bitacora.setBitaccion("DELETE carrera " + carrera.getCodigoCarrera());
        insertarBitacora(bitacora);

    } catch (SQLException e) {
        e.printStackTrace(System.out);
    } finally {
        Conexion.close(stmt);
        Conexion.close(conn);
    }

    return rows;
}

    public clsCarreras obtenerCarreraPorId(String codigo, clsBitacora bitacora) {

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    clsCarreras carrera = null;

    try {

        conn = Conexion.getConnection();
        stmt = conn.prepareStatement(SQL_SELECT_ID);

        stmt.setString(1, codigo);

        rs = stmt.executeQuery();

        if (rs.next()) {

            carrera = new clsCarreras();

            carrera.setCodigoCarrera(rs.getString("codigo_carrera"));
            carrera.setNombreCarrera(rs.getString("nombre_carrera"));
            carrera.setCodigoFacultad(rs.getString("codigo_facultad"));
            carrera.setEstatusCarrera(rs.getString("estatus_carrera"));
        }

        bitacora.setBitaccion("SELECT carrera " + codigo);
        insertarBitacora(bitacora);

    } catch (SQLException e) {
        e.printStackTrace(System.out);
    } finally {
        Conexion.close(rs);
        Conexion.close(stmt);
        Conexion.close(conn);
    }

    return carrera;
}


    public int insertarCarrera(clsCarreras carrera, clsBitacora bitacora) {

    Connection conn = null;
    PreparedStatement stmt = null;
    int rows = 0;

    try {

        conn = Conexion.getConnection();
        stmt = conn.prepareStatement(SQL_INSERT);

        stmt.setString(1, carrera.getCodigoCarrera());
        stmt.setString(2, carrera.getNombreCarrera());
        stmt.setString(3, carrera.getCodigoFacultad());
        stmt.setString(4, carrera.getEstatusCarrera());

        rows = stmt.executeUpdate();

        bitacora.setBitaccion("INSERT carrera " + carrera.getCodigoCarrera());
        insertarBitacora(bitacora);

    } catch (SQLException e) {
        e.printStackTrace(System.out);
    } finally {
        Conexion.close(stmt);
        Conexion.close(conn);
    }

    return rows;
}

    

    private void insertarBitacora(clsBitacora bitacora) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public clsCarreras obtenerCarreraId(int id, clsBitacora bitacora) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
