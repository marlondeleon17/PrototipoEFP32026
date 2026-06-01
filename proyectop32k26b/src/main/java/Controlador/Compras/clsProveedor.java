package Controlador.Compras;

// JENNIFER BARRIOS, ENCARGADA DE PROVEEDORES.

public class clsProveedor {
    private int Procodigo;
    private String Pronombre;
    private String Pronit;
    private String Procuentabancaria;
    private String Proestado;
    private String Procontacto;
    private String Prodepartamento;

    public clsProveedor() {
    }

    public clsProveedor(int Procodigo) {
        this.Procodigo = Procodigo;
    }

    // Getters y Setters
    public int getProcodigo() { return Procodigo; }
    public void setProcodigo(int Procodigo) { this.Procodigo = Procodigo; }

    public String getPronombre() { return Pronombre; }
    public void setPronombre(String Pronombre) { this.Pronombre = Pronombre; }

    public String getPronit() { return Pronit; }
    public void setPronit(String Pronit) { this.Pronit = Pronit; }

    public String getProcuentabancaria() { return Procuentabancaria; }
    public void setProcuentabancaria(String Procuentabancaria) { this.Procuentabancaria = Procuentabancaria; }

    public String getProestado() { return Proestado; }
    public void setProestado(String Proestado) { this.Proestado = Proestado; }

    public String getProcontacto() { return Procontacto; }
    public void setProcontacto(String Procontacto) { this.Procontacto = Procontacto; }

    public String getProdepartamento() { return Prodepartamento; }
    public void setProdepartamento(String Prodepartamento) { this.Prodepartamento = Prodepartamento; }

    @Override
    public String toString() {
        return "clsProveedor{" + "Procodigo=" + Procodigo + ", Pronombre=" + Pronombre + ", Proestado=" + Proestado + '}';
    }
}