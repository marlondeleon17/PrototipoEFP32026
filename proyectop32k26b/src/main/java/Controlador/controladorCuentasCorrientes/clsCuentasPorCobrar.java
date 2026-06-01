//Britany Mishel Hernandez Davila 9959-24-4178
package Controlador.controladorCuentasCorrientes;

public class clsCuentasPorCobrar {
    int codigoCPC;
    int idCLI;
    String fechaCPC;
    double montoCPC;
    double saldoCPC;
    String estadoCPC;

    public clsCuentasPorCobrar() {
    }

    public clsCuentasPorCobrar(int codigoCPC, int idCLI) {
        this.codigoCPC = codigoCPC;
        this.idCLI = idCLI;
    }

    public clsCuentasPorCobrar(int codigoCPC, int idCLI, String fechaCPC, double montoCPC, double saldoCPC, String estadoCPC) {
        this.codigoCPC = codigoCPC;
        this.idCLI = idCLI;
        this.fechaCPC = fechaCPC;
        this.montoCPC = montoCPC;
        this.saldoCPC = saldoCPC;
        this.estadoCPC = estadoCPC;
    }

    public int getCodigoCPC() {
        return codigoCPC;    }

    public void setCodigoCPC(int codigoCPC) {
        this.codigoCPC = codigoCPC;    }

    public int getIdCLI() {
        return idCLI;    }

    public void setIdCLI(int idCLI) {
        this.idCLI = idCLI;    }

    public String getFechaCPC() {
        return fechaCPC;    }

    public void setFechaCPC(String fechaCPC) {
        this.fechaCPC = fechaCPC;    }

    public double getMontoCPC() {
        return montoCPC;    }

    public void setMontoCPC(double montoCPC) {
        this.montoCPC = montoCPC;    }

    public double getSaldoCPC() {
        return saldoCPC;    }

    public void setSaldoCPC(double saldoCPC) {
        this.saldoCPC = saldoCPC;    }

    public String getEstadoCPC() {
        return estadoCPC;    }

    public void setEstadoCPC(String estadoCPC) {
        this.estadoCPC = estadoCPC;    }
    
        @Override
    public String toString() {
        return "cuentasPorCobrar{" + "codigoCPC=" + codigoCPC + ", idCLI=" + idCLI + ", fechaCPC=" + fechaCPC + ", montoCPC=" + montoCPC + ", saldoCPC=" + saldoCPC + ", estadoCPC=" + estadoCPC + '}';
    }
}
