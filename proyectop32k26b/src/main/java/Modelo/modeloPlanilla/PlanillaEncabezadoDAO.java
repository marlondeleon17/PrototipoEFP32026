/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.modeloPlanilla;

import Controlador.controladorPlanilla.clsPlanillaEncabezado;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class PlanillaEncabezadoDAO {

    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // INSERTAR
    public boolean insertar(clsPlanillaEncabezado p) {

        String sql = "INSERT INTO planillaencabezado "
                + "(Plafecha, Platotal, Plaestado, Movbid) "
                + "VALUES (?, ?, ?, ?)";

        try {

            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);

            ps.setDate(1, p.getPlafecha());
            ps.setDouble(2, p.getPlatotal());
            ps.setInt(3, p.getPlaestado());

            if (p.getMovbid() == 0) {

                ps.setNull(4, java.sql.Types.INTEGER);

            } else {

                ps.setInt(4, p.getMovbid());
            }

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Error INSERTAR planilla: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // LISTAR
    public List<clsPlanillaEncabezado> listar() {

        List<clsPlanillaEncabezado> lista
                = new ArrayList<>();

        String sql = "SELECT * FROM planillaencabezado";

        try {

            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                clsPlanillaEncabezado p
                        = new clsPlanillaEncabezado();

                p.setPlacodigo(
                        rs.getInt("Placodigo")
                );

                p.setPlafecha(
                        rs.getDate("Plafecha")
                );

                p.setPlatotal(
                        rs.getDouble("Platotal")
                );

                p.setPlaestado(
                        rs.getInt("Plaestado")
                );

                int mov = rs.getInt("Movbid");

                if (rs.wasNull()) {

                    p.setMovbid(0);

                } else {

                    p.setMovbid(mov);
                }

                lista.add(p);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error LISTAR planilla: "
                    + e.getMessage()
            );
        }

        return lista;
    }

    // BUSCAR
    public clsPlanillaEncabezado buscar(int codigo) {

        String sql = "SELECT * FROM planillaencabezado "
                + "WHERE Placodigo=?";

        try {

            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, codigo);

            rs = ps.executeQuery();

            if (rs.next()) {

                clsPlanillaEncabezado p
                        = new clsPlanillaEncabezado();

                p.setPlacodigo(
                        rs.getInt("Placodigo")
                );

                p.setPlafecha(
                        rs.getDate("Plafecha")
                );

                p.setPlatotal(
                        rs.getDouble("Platotal")
                );

                p.setPlaestado(
                        rs.getInt("Plaestado")
                );

                int mov = rs.getInt("Movbid");

                if (rs.wasNull()) {

                    p.setMovbid(0);

                } else {

                    p.setMovbid(mov);
                }

                return p;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error BUSCAR planilla: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // MODIFICAR
    public boolean modificar(clsPlanillaEncabezado p) {

        String sql = "UPDATE planillaencabezado "
                + "SET Plafecha=?, "
                + "Platotal=?, "
                + "Plaestado=?, "
                + "Movbid=? "
                + "WHERE Placodigo=?";

        try {

            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);

            ps.setDate(1, p.getPlafecha());
            ps.setDouble(2, p.getPlatotal());
            ps.setInt(3, p.getPlaestado());

            if (p.getMovbid() == 0) {

                ps.setNull(4, java.sql.Types.INTEGER);

            } else {

                ps.setInt(4, p.getMovbid());
            }

            ps.setInt(5, p.getPlacodigo());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Error MODIFICAR planilla: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int codigo) {

        String sql = "DELETE FROM planillaencabezado "
                + "WHERE Placodigo=?";

        try {

            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, codigo);

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Error ELIMINAR planilla: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // CARGAR DATOS EN JTABLE
    public DefaultTableModel cargarDatos() {

        DefaultTableModel modelo
                = new DefaultTableModel();

        modelo.addColumn("Codigo");
        modelo.addColumn("Fecha");
        modelo.addColumn("Total");
        modelo.addColumn("Estado");
        modelo.addColumn("Movimiento Banco");

        String[] datos = new String[5];

        String sql = "SELECT * FROM planillaencabezado";

        try {

            con = Conexion.getConnection();

            Statement st = con.createStatement();

            rs = st.executeQuery(sql);

            while (rs.next()) {

                datos[0] = rs.getString("Placodigo");

                datos[1] = rs.getString("Plafecha");

                datos[2] = rs.getString("Platotal");

                if (rs.getInt("Plaestado") == 1) {

                    datos[3] = "ACTIVO";

                } else {

                    datos[3] = "INACTIVO";
                }

                datos[4] = rs.getString("Movbid");

                modelo.addRow(datos);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error cargarDatos: "
                    + e.getMessage()
            );
        }

        return modelo;
    }
}
