//Britany Mishel Hernandez Davila 9959-24-4178
//
package Modelo.modeloCuentasCorrientes;

import Controlador.controladorCuentasCorrientes.clsCobroEmision;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CobroEmisionDAO {
    private static final String SQL_SELECT = "SELECT Cobemid, Cpccodigo, Movbid, Cobfecha, Cobmonto, Cobtipo FROM cobrosemision";
    private static final String SQL_INSERT ="INSERT INTO cobrosemision(Cpccodigo, Movbid, Cobfecha, Cobmonto, Cobtipo) VALUES(?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE cobrosemision SET Cpccodigo=?, Movbid=?, Cobfecha=?, Cobmonto=?, Cobtipo=? WHERE Cobemid = ?";
    private static final String SQL_DELETE = "DELETE FROM cobrosemision WHERE Cobemid=?";
    private static final String SQL_QUERY = "SELECT Cobemid, Cpccodigo, Movbid, Cobfecha, Cobmonto, Cobtipo FROM cobrosemision WHERE Cobemid = ?";
    
    public List<clsCobroEmision> select() {
        //Declaracion de variables
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        clsCobroEmision cobro = null;
        //Definicion de matriz
        List<clsCobroEmision> cobros = new ArrayList<clsCobroEmision>();

        try {
            //Conexion con la base de datos
            conn = Modelo.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {

                int idCOBEM = rs.getInt("Cobemid");
                int codigoCPC = rs.getInt("Cpccodigo");
                int idMOVB = rs.getInt("Movbid");
                String fechaCOB = rs.getString("Cobfecha");
                double montoCOB = rs.getDouble("Cobmonto");
                String tipoCOB = rs.getString("Cobtipo");

                cobro = new clsCobroEmision();//Objeto

                // Asignacion de valores
                cobro.setIdCOBEM(idCOBEM);
                cobro.setCodigoCPC(codigoCPC);
                cobro.setIdMOVB(idMOVB);
                cobro.setFechaCOB(fechaCOB);
                cobro.setMontoCOB(montoCOB);
                cobro.setTipoCOB(tipoCOB);

                cobros.add(cobro);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Modelo.Conexion.close(rs);
            Modelo.Conexion.close(stmt);
            Modelo.Conexion.close(conn);
        }
        return cobros;
    }
//Metodo para insertar valores en la base de datos en la tabla cobrosemision
    public int insert(clsCobroEmision cobro) {
        //Declaracion de variables
        Connection conn = null;
        PreparedStatement stmt = null;
        
        int rows = 0;
  
        try {  
            //Conexion con la base de datos
            conn = Modelo.Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);

            //Asignacion de parametros
            stmt.setInt(1, cobro.getCodigoCPC());
            stmt.setInt(2, cobro.getIdMOVB());
            stmt.setString(3, cobro.getFechaCOB());
            stmt.setDouble(4, cobro.getMontoCOB());
            stmt.setString(5, cobro.getTipoCOB());
            
            //Mensaque de la accion realizada en consola
            System.out.println("Ejecutando query: " + SQL_INSERT);
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados: " + rows);
        } catch (SQLException ex) {  
            ex.printStackTrace(System.out);  
        } finally {  
            Modelo.Conexion.close(stmt);
            Modelo.Conexion.close(conn); 
        }
        return rows; //Retorna el numero de registros afectados
    }
    
//Metodo para actualizar los valores en la base de datos en la tabla cuentasporcobrar    
    public int update(clsCobroEmision cobro) {
        //Declaracion de variables
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            //Conexion con la base de datos
            conn = Modelo.Conexion.getConnection();
            //Mensaque de la accion realizada en consola
            System.out.println("Ejecutando query: " + SQL_UPDATE);           
            stmt = conn.prepareStatement(SQL_UPDATE);
            
            //Asignacion de valores en los parametros
            stmt.setInt(1, cobro.getCodigoCPC());
            stmt.setInt(2, cobro.getIdMOVB());
            stmt.setString(3, cobro.getFechaCOB());
            stmt.setDouble(4, cobro.getMontoCOB());
            stmt.setString(5, cobro.getTipoCOB());
            //Codigo de la cobro que se actualizara
            stmt.setInt(6, cobro.getIdCOBEM());

            rows = stmt.executeUpdate();
            System.out.println("Registros actualizados: " + rows);    
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Modelo.Conexion.close(stmt);
            Modelo.Conexion.close(conn);
        }
        return rows; //Retorna el numero de registros afectados
    }   
 //Metodo para eliminar valores en la base de datos en la tabla cuentasporcobrar    
    public int delete(clsCobroEmision cobro){
        //Declaracion de variables
        Connection conn = null;
        PreparedStatement stmt = null;
        
        int rows = 0;
        
        try{
            //Conexion con la base de datos
            conn = Modelo.Conexion.getConnection();
            //Mensaque de la accion realizada en consola
            System.out.println("Ejecutando query: "+ SQL_DELETE);
            stmt = conn.prepareStatement(SQL_DELETE);
            
            //Codigo de la cuenta por cobrar a eliminar
            stmt.setInt(1, cobro.getIdCOBEM());
            
            rows = stmt.executeUpdate();
            // Mostrar registros eliminados
            System.out.println("Registros eliminados: " + rows);
            
        } catch (SQLException ex){
            ex.printStackTrace(System.out);
        } finally {
            Modelo.Conexion.close(stmt);
            Modelo.Conexion.close(conn);
        }
        return rows; //Retorna el numero de registros afectados
    }
 //Metodo query para los valores en la base de datos en la tabla cuentasporcobrar   
    public clsCobroEmision query(clsCobroEmision cobro) {
        //Definicion de variables
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
             
        try {
            conn = Modelo.Conexion.getConnection(); //Conexion con la base de datos
            //Mensaque de la accion realizada en consola
            System.out.println("Ejecutando query:" + SQL_QUERY);
            stmt = conn.prepareStatement(SQL_QUERY);
            //Parametro de busqueda
            stmt.setInt(1, cobro.getIdCOBEM());
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                //Valores desde la base de datos
                int idCOBEM = rs.getInt("Cobemid");
                int codigoCPC = rs.getInt("Cpccodigo");
                int idMOVB = rs.getInt("Movbid");
                String fechaCOB = rs.getString("Cobfecha");
                double montoCOB = rs.getDouble("Cobmonto");
                String tipoCOB = rs.getString("Cobtipo");

                cobro = new clsCobroEmision(); //Creacion del objeto cobro
                
                //Asignacion de valores en el objeto 
                cobro.setIdCOBEM(idCOBEM);
                cobro.setCodigoCPC(codigoCPC);
                cobro.setIdMOVB(idMOVB);
                cobro.setFechaCOB(fechaCOB);
                cobro.setMontoCOB(montoCOB);
                cobro.setTipoCOB(tipoCOB);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return cobro;//Retorns el objeto encontrado
    }
        
    
}
