//Karina Alejandra Arriaza Ortiz 9959-24-14190
//Documentado
package Controlador.Bancos;

/**
 * Clase que representa un tipo de transacción bancaria.
 * Define las operaciones que se pueden realizar (depósito, retiro, etc.).
 * 
 * @author Proyecto Final - Sistema Bancario
 */
public class clsCatTipoTransaccion {

    // Identificador único del tipo de transacción
    private int TTid;

    // Nombre del tipo de transacción
    private String TTnombretipo;

    // Descripción del tipo de transacción
    private String TTdescripcion;

    /**
     * Constructor con parámetros.
     * 
     * @param id Identificador del tipo de transacción
     * @param nombre Nombre del tipo de transacción
     * @param descripcion Descripción del tipo de transacción
     */
    public clsCatTipoTransaccion(int id, String nombre, String descripcion) {
        this.TTid = id;
        this.TTnombretipo = nombre;
        this.TTdescripcion = descripcion;
    }

    // Métodos getters y setters

    public int getTTid() { return TTid; }
    public void setTTid(int TTid) { this.TTid = TTid; }

    public String getTTnombretipo() { return TTnombretipo; }
    public void setTTnombretipo(String TTnombretipo) { this.TTnombretipo = TTnombretipo; }

    public String getTTdescripcion() { return TTdescripcion; }
    public void setTTdescripcion(String TTdescripcion) { this.TTdescripcion = TTdescripcion; }

    /**
     * Devuelve una representación en texto del tipo de transacción.
     * 
     * @return Información básica del tipo de transacción
     */
    @Override
    public String toString() {
        return "TipoTransaccion{ id=" + TTid
                + ", nombre=" + TTnombretipo + " }";
    }
}