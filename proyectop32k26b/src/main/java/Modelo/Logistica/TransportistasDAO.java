package Modelo.Logistica;

import Modelo.Conexion;
import Controlador.Logistica.clsTransportistas;
import Controlador.clsUsuarioConectado;
import Modelo.BitacoraDAO;
import java.sql.*;
import java.util.*;

/**
 * Autor: Anthony Hetzael Suc Gomez
 * Carné: 9959-24-389
 * Fecha de creación: 2026
 *
 * Descripción:
 * DAO encargado de gestionar las operaciones CRUD
 * de la tabla transportistas.
 */
public class TransportistasDAO {

    // ========================= INSERTAR =========================
    public boolean insertar(clsTransportistas obj) {

        String sql = "INSERT INTO transportistas "
                   + "(Trantipovehiculo, Empcodigo) "
                   + "VALUES (?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, obj.getTrantipovehiculo());
            ps.setInt(2, obj.getEmpcodigo());

            boolean exito = ps.executeUpdate() > 0;

            // REGISTRO EN BITACORA
            if (exito) {

                try {

                    registrarBitacora(
    "Insertó transportista ID "
    + obj.getEmpcodigo()
);

                } catch (Exception ex) {

                    System.out.println(
                        "Error bitácora INSERT: "
                        + ex.getMessage()
                    );
                }
            }

            return exito;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // ========================= ACTUALIZAR =========================
    public boolean actualizar(clsTransportistas obj) {

        String sql = "UPDATE transportistas "
                   + "SET Trantipovehiculo=?, Empcodigo=? "
                   + "WHERE Tranid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, obj.getTrantipovehiculo());
            ps.setInt(2, obj.getEmpcodigo());
            ps.setInt(3, obj.getTranid());

            boolean exito = ps.executeUpdate() > 0;

            // REGISTRO EN BITACORA
            if (exito) {

                try {

                    registrarBitacora(
    "Actualizó transportista ID "
    + obj.getTranid()
);

                } catch (Exception ex) {

                    System.out.println(
                        "Error bitácora UPDATE: "
                        + ex.getMessage()
                    );
                }
            }

            return exito;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // ========================= ELIMINAR =========================
    public boolean eliminar(int id) {

        String sql = "DELETE FROM transportistas WHERE Tranid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            boolean exito = ps.executeUpdate() > 0;

            // REGISTRO EN BITACORA
            if (exito) {

                try {

                    registrarBitacora(
    "Eliminó transportista ID "
    + id
);

                } catch (Exception ex) {

                    System.out.println(
                        "Error bitácora DELETE: "
                        + ex.getMessage()
                    );
                }
            }

            return exito;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // ========================= LISTAR =========================
    public List<clsTransportistas> listar() {

        List<clsTransportistas> lista = new ArrayList<>();

        String sql = "SELECT * FROM transportistas";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                clsTransportistas obj =
                        new clsTransportistas();

                obj.setTranid(rs.getInt("Tranid"));
                obj.setTrantipovehiculo(
                        rs.getString("Trantipovehiculo"));
                obj.setEmpcodigo(
                        rs.getInt("Empcodigo"));

                lista.add(obj);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    // ========================= BUSCAR POR ID =========================
    public clsTransportistas buscarPorId(int id) {

        String sql =
                "SELECT * FROM transportistas WHERE Tranid=?";

        clsTransportistas obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                obj = new clsTransportistas();

                obj.setTranid(rs.getInt("Tranid"));
                obj.setTrantipovehiculo(
                        rs.getString("Trantipovehiculo"));
                obj.setEmpcodigo(
                        rs.getInt("Empcodigo"));
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return obj;
    }

    // ========================= BITACORA =========================
    private void registrarBitacora(String accion) {

        int usuario = clsUsuarioConectado.getUsuId();

        if (usuario == 0) {

            throw new RuntimeException(
                "No hay usuario autenticado"
            );
        }

        BitacoraDAO bitacora = new BitacoraDAO();

        int aplCodigoBitacora = 2000;

        bitacora.insert(
                usuario,
                aplCodigoBitacora,
                accion
        );
    }
}