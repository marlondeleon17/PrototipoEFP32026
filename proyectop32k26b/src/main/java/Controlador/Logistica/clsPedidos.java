 //Ferdynan Monroy abril 2026
package Controlador.Logistica;

/**
 *
 * @author ferito
 */
public class clsPedidos {

    private int Pedid;
    private int Cliid;
    private int Prodid;
    private int Pedcantidad;
    private String Pedmarca;
    private String Pedlinea;
    private String Pedestado;

    // Constructor vacío
    public clsPedidos() {
    }

    // Constructor lleno
    public clsPedidos(int Pedid, int Cliid, int Prodid, int Pedcantidad,
                      String Pedmarca, String Pedlinea, String Pedestado) {
        this.Pedid = Pedid;
        this.Cliid = Cliid;
        this.Prodid = Prodid;
        this.Pedcantidad = Pedcantidad;
        this.Pedmarca = Pedmarca;
        this.Pedlinea = Pedlinea;
        this.Pedestado = Pedestado;
    }

    // GETTERS Y SETTERS

    public int getPedid() {
        return Pedid;
    }

    public void setPedid(int Pedid) {
        this.Pedid = Pedid;
    }

    public int getCliid() {
        return Cliid;
    }

    public void setCliid(int Cliid) {
        this.Cliid = Cliid;
    }

    public int getProdid() {
        return Prodid;
    }

    public void setProdid(int Prodid) {
        this.Prodid = Prodid;
    }

    public int getPedcantidad() {
        return Pedcantidad;
    }

    public void setPedcantidad(int Pedcantidad) {
        this.Pedcantidad = Pedcantidad;
    }

    public String getPedmarca() {
        return Pedmarca;
    }

    public void setPedmarca(String Pedmarca) {
        this.Pedmarca = Pedmarca;
    }

    public String getPedlinea() {
        return Pedlinea;
    }

    public void setPedlinea(String Pedlinea) {
        this.Pedlinea = Pedlinea;
    }

    public String getPedestado() {
        return Pedestado;
    }

    public void setPedestado(String Pedestado) {
        this.Pedestado = Pedestado;
    }

    @Override
    public String toString() {
        return "clsPedidos{" +
                "Pedid=" + Pedid +
                ", Cliid=" + Cliid +
                ", Prodid=" + Prodid +
                ", Pedcantidad=" + Pedcantidad +
                ", Pedmarca=" + Pedmarca +
                ", Pedlinea=" + Pedlinea +
                ", Pedestado=" + Pedestado +
                '}';
    }
}