package Controlador.controladorPlanilla;

import java.math.BigDecimal;

public class clsPuesto {
    private int puecodigo;
    private String puenombre;
    private BigDecimal puesalarioBase;
    private int depcodigo; // Campo obligatorio en BD

    public clsPuesto() {
        this.puecodigo = 0;
        this.puenombre = "";
        this.puesalarioBase = BigDecimal.ZERO;
        this.depcodigo = 0;
    }

    public clsPuesto(int puecodigo, String puenombre, BigDecimal puesalarioBase, int depcodigo) {
        this.puecodigo = puecodigo;
        this.puenombre = puenombre;
        this.puesalarioBase = puesalarioBase;
        this.depcodigo = depcodigo;
    }

    // Getters y Setters
    public int getPuecodigo() { return puecodigo; }
    public void setPuecodigo(int puecodigo) { this.puecodigo = puecodigo; }

    public String getPuenombre() { return puenombre; }
    public void setPuenombre(String puenombre) { this.puenombre = puenombre; }

    public BigDecimal getPuesalarioBase() { return puesalarioBase; }
    public void setPuesalarioBase(BigDecimal puesalarioBase) {
        this.puesalarioBase = (puesalarioBase == null) ? BigDecimal.ZERO : puesalarioBase;
    }

    public int getDepcodigo() { return depcodigo; }
    public void setDepcodigo(int depcodigo) { this.depcodigo = depcodigo; }

    @Override
    public String toString() {
        return "clsPuesto{" + "puecodigo=" + puecodigo + ", puenombre=" + puenombre + ", depcodigo=" + depcodigo + '}';
    }
}