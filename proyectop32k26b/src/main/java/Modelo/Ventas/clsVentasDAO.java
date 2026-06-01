/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Ventas;

/**
 *
 * @author Marice
 */


import Controlador.Ventas.clsFacturasDetallesVentas;
import Controlador.Ventas.clsFacturasventas;
import Modelo.Conexion; 
import java.sql.*;
import java.util.List;

public class clsVentasDAO {

    private static final String SQL_INSERT_CABECERA = "INSERT INTO Facturasventas (Facvenumero, Cliid, Clinombre, Venid, Impid, Facvesubtotal, Facveiva, Facvetotal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_DETALLE = "INSERT INTO Facturadetalleventas (Facveid, Proid, Pronombre, Facvecantidad, Facveprecio, Facvesubtotal) VALUES (?, ?, ?, ?, ?, ?)";

    public boolean registrarVentaCompleta(clsFacturasventas factura, List<clsFacturasDetallesVentas> detalles) {
        Connection conn = null;
        PreparedStatement stmtCabecera = null;
        PreparedStatement stmtDetalle = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false); 

            stmtCabecera = conn.prepareStatement(SQL_INSERT_CABECERA, Statement.RETURN_GENERATED_KEYS);
            stmtCabecera.setString(1, factura.getFacvenumero());
            stmtCabecera.setInt(2, factura.getCliid());
            stmtCabecera.setString(3, factura.getClinombre());
            stmtCabecera.setInt(4, factura.getVenid());
            stmtCabecera.setInt(5, factura.getImpid());
            stmtCabecera.setDouble(6, factura.getFacvesubtotal());
            stmtCabecera.setDouble(7, factura.getFacveiva());
            stmtCabecera.setDouble(8, factura.getFacvetotal());
            stmtCabecera.executeUpdate();

            rs = stmtCabecera.getGeneratedKeys();
            int idFacturaGenerada = 0;
            if (rs.next()) {
                idFacturaGenerada = rs.getInt(1);
            }

            stmtDetalle = conn.prepareStatement(SQL_INSERT_DETALLE);
            for (clsFacturasDetallesVentas detalle : detalles) {
                stmtDetalle.setInt(1, idFacturaGenerada);
                stmtDetalle.setInt(2, detalle.getProid());
                stmtDetalle.setString(3, detalle.getPronombre());
                stmtDetalle.setDouble(4, detalle.getFacvecantidad());
                stmtDetalle.setDouble(5, detalle.getFacveprecio());
                stmtDetalle.setDouble(6, detalle.getFacvesubtotal());
                stmtDetalle.addBatch();
            }
            stmtDetalle.executeBatch(); 

            conn.commit(); 
            return true;

        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (SQLException ex1) {
                    ex1.printStackTrace();
                }
            }
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmtCabecera != null) stmtCabecera.close();
                if (stmtDetalle != null) stmtDetalle.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}