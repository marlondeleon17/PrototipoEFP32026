/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.modeloPlanilla;

import Controlador.controladorPlanilla.clsBitacoraPlanilla;
import Modelo.Conexion;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
/**
 *
 * @author Meilyn Garcia 9959-23-17838
 */
public class BitacoraPlanillaDAO {


        public void insertarBitacora(
            clsBitacoraPlanilla bitacora) {

        Connection conexion = null;
        PreparedStatement ps = null;

        try {

            conexion = Conexion.getConnection();

            String sql =
                    "INSERT INTO BitacoraPlanilla "
                    + "(codigo, accion, tablaAfectada, "
                    + "descripcion, usuario, fecha) "
                    + "VALUES (?, ?, ?, ?, ?, NOW())";

            ps = conexion.prepareStatement(sql);

            ps.setInt(1,
        bitacora.getCodigo());

        ps.setString(2,
        bitacora.getAccion());

        ps.setString(3,
        bitacora.getTablaAfectada());

        ps.setString(4,
        bitacora.getDescripcion());

        ps.setString(5,
        bitacora.getUsuario());

            ps.executeUpdate();

            System.out.println(
                    "Bitácora registrada correctamente"
            );

        } catch (Exception e) {

            System.out.println(
                    "Error bitacora: "
                    + e.getMessage()
            );

        } finally {

            try {

                if(ps != null){
                    ps.close();
                }

                if(conexion != null){
                    conexion.close();
                }

            } catch (Exception e) {

                System.out.println(
                        "Error cerrando conexión: "
                        + e.getMessage()
                );
            }
        }
    }
}
