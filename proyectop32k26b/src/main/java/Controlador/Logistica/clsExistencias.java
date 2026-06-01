 //Ferdynand Monroy mayo 2026
package Controlador.Logistica;

/**
 *
 * @author ferito
 */
public class clsExistencias {

    private int Existenciaid;
    private int Prodid;
    private String Exnombreproducto;
    private int bodegaid;
    private int Existock;
    private String Exmarca;
    private String Exlinea;

    // Constructor vacío
    public clsExistencias() {
    }

    // Constructor lleno
    public clsExistencias(int Existenciaid, int Prodid, String Exnombreproducto,
                          int bodegaid, int Existock,
                          String Exmarca, String Exlinea) {

        this.Existenciaid = Existenciaid;
        this.Prodid = Prodid;
        this.Exnombreproducto = Exnombreproducto;
        this.bodegaid = bodegaid;
        this.Existock = Existock;
        this.Exmarca = Exmarca;
        this.Exlinea = Exlinea;
    }

    // GETTERS Y SETTERS

    public int getExistenciaid() {
        return Existenciaid;
    }

    public void setExistenciaid(int Existenciaid) {
        this.Existenciaid = Existenciaid;
    }

    public int getProdid() {
        return Prodid;
    }

    public void setProdid(int Prodid) {
        this.Prodid = Prodid;
    }

    public String getExnombreproducto() {
        return Exnombreproducto;
    }

    public void setExnombreproducto(String Exnombreproducto) {
        this.Exnombreproducto = Exnombreproducto;
    }

    public int getBodegaid() {
        return bodegaid;
    }

    public void setBodegaid(int bodegaid) {
        this.bodegaid = bodegaid;
    }

    public int getExistock() {
        return Existock;
    }

    public void setExistock(int Existock) {
        this.Existock = Existock;
    }

    public String getExmarca() {
        return Exmarca;
    }

    public void setExmarca(String Exmarca) {
        this.Exmarca = Exmarca;
    }

    public String getExlinea() {
        return Exlinea;
    }

    public void setExlinea(String Exlinea) {
        this.Exlinea = Exlinea;
    }

    @Override
    public String toString() {
        return "clsExistencias{" +
                "Existenciaid=" + Existenciaid +
                ", Prodid=" + Prodid +
                ", Exnombreproducto=" + Exnombreproducto +
                ", bodegaid=" + bodegaid +
                ", Existock=" + Existock +
                ", Exmarca=" + Exmarca +
                ", Exlinea=" + Exlinea +
                '}';
    }
}