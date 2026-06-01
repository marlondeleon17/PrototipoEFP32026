package Controlador.Bancos;
// Creado Por Karina Alejandra Arriaza Ortiz 9959-24-14190
//Modificado por Angoly Camila Araujo Mayen 9959-24-17623 y realizado por Karina Arriaza

import java.util.Date;

public class clsBanco {

    private int    Banid;
    private String Bannombre;
    private String Bandireccion;
    private String Bantelefono;
    private String Bancorreo;
    private Date   Banfecharegistro;

    // Constructor vacío — necesario para instanciar y luego usar setters
    public clsBanco() { }

    // Constructor completo
    public clsBanco(int id, String nombre, String direccion,
                    String telefono, String correo, Date fecha) {
        this.Banid             = id;
        this.Bannombre         = nombre;
        this.Bandireccion      = direccion;
        this.Bantelefono       = telefono;
        this.Bancorreo         = correo;
        this.Banfecharegistro  = fecha;
    }

    // ── Getters & Setters ──────────────────────────────────────
    public int    getBanid()           { return Banid; }
    public void   setBanid(int Banid)  { this.Banid = Banid; }

    public String getBannombre()                  { return Bannombre; }
    public void   setBannombre(String Bannombre)  { this.Bannombre = Bannombre; }

    public String getBandireccion()                     { return Bandireccion; }
    public void   setBandireccion(String Bandireccion)  { this.Bandireccion = Bandireccion; }

    public String getBantelefono()                    { return Bantelefono; }
    public void   setBantelefono(String Bantelefono)  { this.Bantelefono = Bantelefono; }

    public String getBancorreo()                  { return Bancorreo; }
    public void   setBancorreo(String Bancorreo)  { this.Bancorreo = Bancorreo; }

    public Date getBanfecharegistro()                     { return Banfecharegistro; }
    public void setBanfecharegistro(Date Banfecharegistro){ this.Banfecharegistro = Banfecharegistro; }

    // ── toString ───────────────────────────────────────────────
    @Override
    public String toString() {
        return "Banco{ idBanco=" + Banid + ", nombreBanco=" + Bannombre + " }";
    }
}
