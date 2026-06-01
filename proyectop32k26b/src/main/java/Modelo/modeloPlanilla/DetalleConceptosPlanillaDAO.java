/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.modeloPlanilla;

import Controlador.controladorPlanilla.clsDetalleConceptosPlanilla;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

/**
 *
 * @author Meilyn Garcia
 */
public class DetalleConceptosPlanillaDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // INSERTAR
    public boolean insertar(clsDetalleConceptosPlanilla d) {

        String sql = "INSERT INTO detalleconceptosplanilla (Detcodigo, Concodigo, Monto) VALUES (?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, d.getDetcodigo());
            ps.setInt(2, d.getConcodigo());
            ps.setDouble(3, d.getMonto());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error INSERT detalle conceptos: " + e.getMessage());
            return false;
        }
    }

    // LISTAR
    public List<clsDetalleConceptosPlanilla> listar() {

        List<clsDetalleConceptosPlanilla> lista = new ArrayList<>();

        String sql = "SELECT * FROM detalleconceptosplanilla";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                clsDetalleConceptosPlanilla d = new clsDetalleConceptosPlanilla();

                d.setDetconcodigo(rs.getInt("Detconcodigo"));
                d.setDetcodigo(rs.getInt("Detcodigo"));
                d.setConcodigo(rs.getInt("Concodigo"));
                d.setMonto(rs.getDouble("Monto"));

                lista.add(d);
            }

        } catch (SQLException e) {
            System.out.println("Error LISTAR detalle conceptos: " + e.getMessage());
        }

        return lista;
    }

    // BUSCAR
    public clsDetalleConceptosPlanilla buscar(int id) {

        String sql = "SELECT * FROM detalleconceptosplanilla WHERE Detconcodigo=?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {

                clsDetalleConceptosPlanilla d = new clsDetalleConceptosPlanilla();

                d.setDetconcodigo(rs.getInt("Detconcodigo"));
                d.setDetcodigo(rs.getInt("Detcodigo"));
                d.setConcodigo(rs.getInt("Concodigo"));
                d.setMonto(rs.getDouble("Monto"));

                return d;
            }

        } catch (SQLException e) {
            System.out.println("Error BUSCAR detalle conceptos: " + e.getMessage());
        }

        return null;
    }

    // ACTUALIZAR
    public boolean actualizar(clsDetalleConceptosPlanilla d) {

        String sql = "UPDATE detalleconceptosplanilla SET Detcodigo=?, Concodigo=?, Monto=? WHERE Detconcodigo=?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, d.getDetcodigo());
            ps.setInt(2, d.getConcodigo());
            ps.setDouble(3, d.getMonto());
            ps.setInt(4, d.getDetconcodigo());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error UPDATE detalle conceptos: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {

        String sql = "DELETE FROM detalleconceptosplanilla WHERE Detconcodigo=?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error DELETE detalle conceptos: " + e.getMessage());
            return false;
        }
    }
}
