//Karina Alejandra Arriaza Ortiz 9959-24-14190
//Documentación
package Modelo.Bancos;

import Controlador.Bancos.clsCatEstadoConciliacion;
import Controlador.clsUsuarioConectado;
import Modelo.BitacoraDAO;
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la gestión del catálogo
 * de Estados de Conciliación (CatEstadoConciliacion).
 * 
 * Permite realizar operaciones CRUD sobre la base de datos:
 * - Listar todos los registros
 * - Insertar nuevos estados
 * - Actualizar estados existentes
 * - Eliminar registros
 * - Consultar por ID
 * 
 * Además, cada operación es registrada en la bitácora del sistema.
 * 
 * @author Proyecto Final
 */
public class CatEstadoConciliacionDAO {

    // Código de la aplicación para registro en bitácora
    private static final int APL_CODIGO = 5200;

    /**
     * Obtiene todos los estados de conciliación.
     * 
     * @return Lista de objetos clsCatEstadoConciliacion
     */
    public List<clsCatEstadoConciliacion> listar() {
        List<clsCatEstadoConciliacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM CatEstadoConciliacion";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new clsCatEstadoConciliacion(
                    rs.getInt("Catesid"),
                    rs.getString("Catesnombreestado")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Inserta un nuevo estado de conciliación.
     * 
     * @param cates Objeto a insertar
     */
    public void insert(clsCatEstadoConciliacion cates) {
        String sql = "INSERT INTO CatEstadoConciliacion (Catesnombreestado) VALUES (?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cates.getCatesnombreestado());
            ps.executeUpdate();

            // Registro en bitácora
            new BitacoraDAO().insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "INSERT");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al insertar CatEstadoConciliacion", e);
        }
    }

    /**
     * Actualiza un estado de conciliación existente.
     * 
     * @param cates Objeto con datos actualizados
     */
    public void update(clsCatEstadoConciliacion cates) {
        String sql = "UPDATE CatEstadoConciliacion SET Catesnombreestado=? WHERE Catesid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cates.getCatesnombreestado());
            ps.setInt(2, cates.getCatesid());

            int rows = ps.executeUpdate();

            if (rows == 0)
                throw new RuntimeException("No se encontró el EstadoConciliacion para actualizar");

            // Registro en bitácora
            new BitacoraDAO().insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "UPDATE");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar CatEstadoConciliacion", e);
        }
    }

    /**
     * Elimina un estado de conciliación por su ID.
     * 
     * @param id Identificador del estado
     */
    public void delete(int id) {
        String sql = "DELETE FROM CatEstadoConciliacion WHERE Catesid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows == 0)
                throw new RuntimeException("No se encontró el EstadoConciliacion para eliminar");

            // Registro en bitácora
            new BitacoraDAO().insert(clsUsuarioConectado.getUsuId(), APL_CODIGO, "DELETE");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar CatEstadoConciliacion", e);
        }
    }

    /**
     * Consulta un estado de conciliación por su ID.
     * 
     * @param id Identificador del estado
     * @return Objeto encontrado o null si no existe
     */
    public clsCatEstadoConciliacion query(int id) {
        clsCatEstadoConciliacion cates = null;
        String sql = "SELECT * FROM CatEstadoConciliacion WHERE Catesid=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cates = new clsCatEstadoConciliacion(
                        rs.getInt("Catesid"),
                        rs.getString("Catesnombreestado")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al consultar CatEstadoConciliacion", e);
        }
        return cates;
    }
}