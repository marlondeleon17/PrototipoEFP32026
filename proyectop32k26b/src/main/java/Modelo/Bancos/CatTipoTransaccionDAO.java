//Karina Alejandra Arriaza Ortiz 9959-24-14190
//Modificado y documentado
package Modelo.Bancos;

import Controlador.Bancos.clsCatTipoTransaccion;
import Controlador.clsUsuarioConectado;
import Modelo.BitacoraDAO;
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para la gestión de la tabla CatTipoTransaccion.
 * Permite realizar operaciones CRUD sobre los tipos de transacción.

 * Además, registra cada acción en la bitácora del sistema.
 * 
 * @author Proyecto Final
 */
public class CatTipoTransaccionDAO {

    // Código de la aplicación para registrar en bitácora
    private static final int APL_CODIGO = 5100;

    /**
     * Obtiene todos los tipos de transacción.
     * 
     * @return Lista de objetos clsCatTipoTransaccion
     */
    public List<clsCatTipoTransaccion> listar() {
        List<clsCatTipoTransaccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM CatTipoTransaccion";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clsCatTipoTransaccion tt = new clsCatTipoTransaccion(
                    rs.getInt("TTid"),
                    rs.getString("TTnombretipo"),
                    rs.getString("TTdescripcion")
                );
                lista.add(tt);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Inserta un nuevo tipo de transacción.
     * 
     * @param tt Objeto a insertar
     */
    public void insert(clsCatTipoTransaccion tt) {
        String sql = "INSERT INTO CatTipoTransaccion (TTnombretipo, TTdescripcion) VALUES (?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tt.getTTnombretipo());
            ps.setString(2, tt.getTTdescripcion());
            ps.executeUpdate();

            // Registro en bitácora
            new BitacoraDAO().insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "INSERT");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al insertar TipoTransaccion", e);
        }
    }

    /**
     * Actualiza un tipo de transacción existente.
     * 
     * @param tt Objeto con los nuevos datos
     */
    public void update(clsCatTipoTransaccion tt) {
        String sql = "UPDATE CatTipoTransaccion SET TTnombretipo=?, TTdescripcion=? WHERE TTid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tt.getTTnombretipo());
            ps.setString(2, tt.getTTdescripcion());
            ps.setInt(3, tt.getTTid());

            int rows = ps.executeUpdate();

            if (rows == 0)
                throw new RuntimeException("No se encontró el registro para actualizar");

            // Registro en bitácora
            new BitacoraDAO().insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "UPDATE");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar TipoTransaccion", e);
        }
    }

    /**
     * Elimina un tipo de transacción por su ID.
     * 
     * @param idTT Identificador del registro
     */
    public void delete(int idTT) {
        String sql = "DELETE FROM CatTipoTransaccion WHERE TTid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTT);

            int rows = ps.executeUpdate();

            if (rows == 0)
                throw new RuntimeException("No se encontró el registro para eliminar");

            // Registro en bitácora
            new BitacoraDAO().insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "DELETE");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar TipoTransaccion", e);
        }
    }

    /**
     * Consulta un tipo de transacción por su ID.
     * 
     * @param idTT Identificador del tipo de transacción
     * @return Objeto encontrado o null si no existe
     */
    public clsCatTipoTransaccion query(int idTT) {
        clsCatTipoTransaccion tt = null;
        String sql = "SELECT * FROM CatTipoTransaccion WHERE TTid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTT);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tt = new clsCatTipoTransaccion(
                        rs.getInt("TTid"),
                        rs.getString("TTnombretipo"),
                        rs.getString("TTdescripcion")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al consultar TipoTransaccion", e);
        }
        return tt;
    }
}