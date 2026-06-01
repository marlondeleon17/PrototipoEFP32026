package Controlador.controladorPlanilla;
public class clsPlanillaDetalle {
    // Atributos privados según el diagrama ER
    private int detcodigo;
    private int placodigo;
    private int empcodigo;
    private double detsalario;
    private double dettotalpercepciones;
    private double dettotaldeducciones;
    private double detliquido;

    // Constructor vacío (necesario para frameworks)
    public clsPlanillaDetalle() {
    }

    // Constructor completo para facilitar la creación de objetos
    public clsPlanillaDetalle(int detcodigo, int placodigo, int empcodigo, double detsalario, 
                             double dettotalpercepciones, double dettotaldeducciones, double detliquido) {
        this.detcodigo = detcodigo;
        this.placodigo = placodigo;
        this.empcodigo = empcodigo;
        this.detsalario = detsalario;
        this.dettotalpercepciones = dettotalpercepciones;
        this.dettotaldeducciones = dettotaldeducciones;
        this.detliquido = detliquido;
    }

    // Getters y Setters
    public int getDetcodigo() { return detcodigo; }
    public void setDetcodigo(int detcodigo) { this.detcodigo = detcodigo; }

    public int getPlacodigo() { return placodigo; }
    public void setPlacodigo(int placodigo) { this.placodigo = placodigo; }

    public int getEmpcodigo() { return empcodigo; }
    public void setEmpcodigo(int empcodigo) { this.empcodigo = empcodigo; }

    public double getDetsalario() { return detsalario; }
    public void setDetsalario(double detsalario) { this.detsalario = detsalario; }

    public double getDettotalpercepciones() { return dettotalpercepciones; }
    public void setDettotalpercepciones(double dettotalpercepciones) { this.dettotalpercepciones = dettotalpercepciones; }

    public double getDettotaldeducciones() { return dettotaldeducciones; }
    public void setDettotaldeducciones(double dettotaldeducciones) { this.dettotaldeducciones = dettotaldeducciones; }

    public double getDetliquido() { return detliquido; }
    public void setDetliquido(double detliquido) { this.detliquido = detliquido; }

    @Override
    public String toString() {
        return "clsPlanillaDetalle{" + "detcodigo=" + detcodigo + ", empcodigo=" + empcodigo + ", liquido=" + detliquido + '}';
    }
}