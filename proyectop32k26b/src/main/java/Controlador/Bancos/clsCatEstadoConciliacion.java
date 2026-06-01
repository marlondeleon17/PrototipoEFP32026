//Karina Alejandra Arriaza Ortiz 9959-24-14190
//Documentación
package Controlador.Bancos;

/**
 * Clase que representa un estado de conciliación bancaria.
 * Se utiliza para indicar el estado de una conciliación
 * (por ejemplo: Conciliado, Pendiente, Con diferencia).
 * 
 * Esta clase corresponde al catálogo CatEstadoConciliacion.
 * 
 * @author Proyecto Final
 */
public class clsCatEstadoConciliacion {

    // Identificador único del estado de conciliación
    private int Catesid;

    // Nombre del estado de conciliación
    private String Catesnombreestado;

    /**
     * Constructor con parámetros.
     * 
     * @param id Identificador del estado
     * @param nombre Nombre del estado de conciliación
     */
    public clsCatEstadoConciliacion(int id, String nombre) {
        this.Catesid = id;
        this.Catesnombreestado = nombre;
    }

    // Métodos getters y setters

    public int getCatesid() { return Catesid; }
    public void setCatesid(int Catesid) { this.Catesid = Catesid; }

    public String getCatesnombreestado() { return Catesnombreestado; }
    public void setCatesnombreestado(String Catesnombreestado) { 
        this.Catesnombreestado = Catesnombreestado; 
    }

    /**
     * Devuelve una representación en texto del estado de conciliación.
     * 
     * @return Información básica del estado
     */
    @Override
    public String toString() {
        return "EstadoConciliacion{ id=" + Catesid
                + ", estado=" + Catesnombreestado + " }";
    }
}