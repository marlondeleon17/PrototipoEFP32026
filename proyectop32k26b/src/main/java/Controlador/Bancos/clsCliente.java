package Controlador.Bancos;  
// Creado Por Karina Alejandra Arriaza Ortiz 9959-24-14190
//Modificado por Angoly Camila Araujo Mayen 9959-24-17623

public class clsCliente {

    private int    Clid;
    private String Clinombre;
    private String Clinit;
    private String Clitelefono;
    private String Cliestado;
    private String Clidireccion;
    private String Clicorreo;

    // Constructor vacío — necesario para instanciar y usar setters
    public clsCliente() { }

    // Constructor completo
    public clsCliente(int id, String nombre, String nit, String telefono,
                      String estado, String direccion, String correo) {
        this.Clid        = id;
        this.Clinombre   = nombre;
        this.Clinit      = nit;
        this.Clitelefono = telefono;
        this.Cliestado   = estado;
        this.Clidireccion = direccion;
        this.Clicorreo   = correo;
    }

    // ── Getters & Setters ──────────────────────────────────────
    public int    getClid()              { return Clid; }
    public void   setClid(int Clid)      { this.Clid = Clid; }

    public String getClinombre()                   { return Clinombre; }
    public void   setClinombre(String Clinombre)   { this.Clinombre = Clinombre; }

    public String getClinit()                  { return Clinit; }
    public void   setClinit(String Clinit)     { this.Clinit = Clinit; }

    public String getClitelefono()                     { return Clitelefono; }
    public void   setClitelefono(String Clitelefono)   { this.Clitelefono = Clitelefono; }

    public String getCliestado()                   { return Cliestado; }
    public void   setCliestado(String Cliestado)   { this.Cliestado = Cliestado; }

    public String getClidireccion()                      { return Clidireccion; }
    public void   setClidireccion(String Clidireccion)   { this.Clidireccion = Clidireccion; }

    public String getClicorreo()                   { return Clicorreo; }
    public void   setClicorreo(String Clicorreo)   { this.Clicorreo = Clicorreo; }

    // ── toString ───────────────────────────────────────────────
    @Override
    public String toString() {
        return "Cliente{ id=" + Clid + ", nombre=" + Clinombre + ", NIT=" + Clinit + " }";
    }
}
