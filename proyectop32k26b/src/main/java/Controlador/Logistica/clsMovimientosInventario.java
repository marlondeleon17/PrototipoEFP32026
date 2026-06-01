 //Ferdynand Monroy mayo 2026
package Controlador.Logistica;

import java.sql.Timestamp;

/**
 *
 * @author ferito
 */
public class clsMovimientosInventario {

    private int Movimientoid;
    private int Prodid;
    private int bodegaid;
    private String Movtipomovimiento;
    private String Movmotivo;
    private int Movcantidad;
    private String Movmarca;
    private String Movlinea;
    private Timestamp Movfecha;

    // Constructor vacío
    public clsMovimientosInventario() {
    }

    // Constructor lleno
    public clsMovimientosInventario(int Movimientoid, int Prodid, int bodegaid,
            String Movtipomovimiento, String Movmotivo,
            int Movcantidad, String Movmarca,
            String Movlinea, Timestamp Movfecha) {

        this.Movimientoid = Movimientoid;
        this.Prodid = Prodid;
        this.bodegaid = bodegaid;
        this.Movtipomovimiento = Movtipomovimiento;
        this.Movmotivo = Movmotivo;
        this.Movcantidad = Movcantidad;
        this.Movmarca = Movmarca;
        this.Movlinea = Movlinea;
        this.Movfecha = Movfecha;
    }

    // GETTERS Y SETTERS

    public int getMovimientoid() {
        return Movimientoid;
    }

    public void setMovimientoid(int Movimientoid) {
        this.Movimientoid = Movimientoid;
    }

    public int getProdid() {
        return Prodid;
    }

    public void setProdid(int Prodid) {
        this.Prodid = Prodid;
    }

    public int getBodegaid() {
        return bodegaid;
    }

    public void setBodegaid(int bodegaid) {
        this.bodegaid = bodegaid;
    }

    public String getMovtipomovimiento() {
        return Movtipomovimiento;
    }

    public void setMovtipomovimiento(String Movtipomovimiento) {
        this.Movtipomovimiento = Movtipomovimiento;
    }

    public String getMovmotivo() {
        return Movmotivo;
    }

    public void setMovmotivo(String Movmotivo) {
        this.Movmotivo = Movmotivo;
    }

    public int getMovcantidad() {
        return Movcantidad;
    }

    public void setMovcantidad(int Movcantidad) {
        this.Movcantidad = Movcantidad;
    }

    public String getMovmarca() {
        return Movmarca;
    }

    public void setMovmarca(String Movmarca) {
        this.Movmarca = Movmarca;
    }

    public String getMovlinea() {
        return Movlinea;
    }

    public void setMovlinea(String Movlinea) {
        this.Movlinea = Movlinea;
    }

    public Timestamp getMovfecha() {
        return Movfecha;
    }

    public void setMovfecha(Timestamp Movfecha) {
        this.Movfecha = Movfecha;
    }

    @Override
    public String toString() {
        return "clsMovimientosInventario{" +
                "Movimientoid=" + Movimientoid +
                ", Prodid=" + Prodid +
                ", bodegaid=" + bodegaid +
                ", Movtipomovimiento=" + Movtipomovimiento +
                ", Movmotivo=" + Movmotivo +
                ", Movcantidad=" + Movcantidad +
                ", Movmarca=" + Movmarca +
                ", Movlinea=" + Movlinea +
                ", Movfecha=" + Movfecha +
                '}';
    }
}