//Britany Mishel Hernandez Davila 9959-24-4178
package Controlador.controladorCuentasCorrientes;

/**
 *
 * @author Mishel
 */
public class clsCobroEmision {
    int idCOBEM;
    int codigoCPC;
    int idMOVB;
    String fechaCOB;
    double montoCOB;
    String tipoCOB;

    public clsCobroEmision() {
    }

    public clsCobroEmision(int idCOBEM, int codigoCPC) {
        this.idCOBEM = idCOBEM;
        this.codigoCPC = codigoCPC;
    }

    public clsCobroEmision(int idCOBEM, int codigoCPC, int idMOVB, String fechaCOB, double montoCOB, String tipoCOB) {
        this.idCOBEM = idCOBEM;
        this.codigoCPC = codigoCPC;
        this.idMOVB = idMOVB;
        this.fechaCOB = fechaCOB;
        this.montoCOB = montoCOB;
        this.tipoCOB = tipoCOB;
    }

    public int getIdCOBEM() {
        return idCOBEM;    }

    public void setIdCOBEM(int idCOBEM) {
        this.idCOBEM = idCOBEM;    }

    public int getCodigoCPC() {
        return codigoCPC;    }

    public void setCodigoCPC(int codigoCPC) {
        this.codigoCPC = codigoCPC;    }

    public int getIdMOVB() {
        return idMOVB;    }

    public void setIdMOVB(int idMOVB) {
        this.idMOVB = idMOVB;    }

    public String getFechaCOB() {
        return fechaCOB;    }

    public void setFechaCOB(String fechaCOB) {
        this.fechaCOB = fechaCOB;    }

    public double getMontoCOB() {
        return montoCOB;    }

    public void setMontoCOB(double montoCOB) {
        this.montoCOB = montoCOB;    }

    public String getTipoCOB() {
        return tipoCOB;    }

    public void setTipoCOB(String tipoCOB) {
        this.tipoCOB = tipoCOB;    }

    @Override
    public String toString() {
        return "clsCobroEmision{" + "idCOBEM=" + idCOBEM + ", codigoCPC=" + codigoCPC + ", idMOVB=" + idMOVB + ", fechaCOB=" + fechaCOB + ", montoCOB=" + montoCOB + ", tipoCOB=" + tipoCOB + '}';
    }
    
    

}
