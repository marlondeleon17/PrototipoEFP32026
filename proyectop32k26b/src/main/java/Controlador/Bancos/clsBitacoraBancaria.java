package Controlador.Bancos;

import java.util.Date;
// Creado Por Karina Alejandra Arriaza Ortiz 9959-24-14190
// Modificado por Angoly Camila Araujo Mayen 9959-24-17623
public class clsBitacoraBancaria {

    private int     BBid;
    private String     BBusuarioaccion;
    private String  BBaccion;
    private String  BBtabla;
    private Integer BBregistroid;      // Nullable → DEFAULT NULL en BD
    private String  BBvaloranterior;
    private String  BBvalornuevo;
    private Date    BBfechaaccion;
    private String  BBdescripcion;

    // Constructor vacío — necesario para instanciar y usar setters
    public clsBitacoraBancaria() { }

    // Constructor completo
    public clsBitacoraBancaria(int BBid, String BBusuarioaccion, String BBaccion,
                                String BBtabla, Integer BBregistroid,
                                String BBvaloranterior, String BBvalornuevo,
                                Date BBfechaaccion, String BBdescripcion) {
        this.BBid            = BBid;
        this.BBusuarioaccion = BBusuarioaccion;
        this.BBaccion        = BBaccion;
        this.BBtabla         = BBtabla;
        this.BBregistroid    = BBregistroid;
        this.BBvaloranterior = BBvaloranterior;
        this.BBvalornuevo    = BBvalornuevo;
        this.BBfechaaccion   = BBfechaaccion;
        this.BBdescripcion   = BBdescripcion;
    }

    // Constructor sin ID — para INSERT (AUTO_INCREMENT genera el ID)
    public clsBitacoraBancaria(String BBusuarioaccion, String BBaccion,
                                String BBtabla, Integer BBregistroid,
                                String BBvaloranterior, String BBvalornuevo,
                                String BBdescripcion) {
        this.BBusuarioaccion = BBusuarioaccion;
        this.BBaccion        = BBaccion;
        this.BBtabla         = BBtabla;
        this.BBregistroid    = BBregistroid;
        this.BBvaloranterior = BBvaloranterior;
        this.BBvalornuevo    = BBvalornuevo;
        this.BBfechaaccion   = new Date();    // Equivalente a CURRENT_TIMESTAMP
        this.BBdescripcion   = BBdescripcion;
    }

    // ── Getters & Setters ──────────────────────────────────────
    public int     getBBid()                          { return BBid; }
    public void    setBBid(int BBid)                  { this.BBid = BBid; }

    public String  getBBusuarioaccion()                             { return BBusuarioaccion; }
    public void    setBBusuarioaccion(String BBusuarioaccion)          { this.BBusuarioaccion = BBusuarioaccion; }

    public String  getBBaccion()                      { return BBaccion; }
    public void    setBBaccion(String BBaccion)        { this.BBaccion = BBaccion; }

    public String  getBBtabla()                       { return BBtabla; }
    public void    setBBtabla(String BBtabla)          { this.BBtabla = BBtabla; }

    public Integer getBBregistroid()                          { return BBregistroid; }
    public void    setBBregistroid(Integer BBregistroid)      { this.BBregistroid = BBregistroid; }

    public String  getBBvaloranterior()                             { return BBvaloranterior; }
    public void    setBBvaloranterior(String BBvaloranterior)       { this.BBvaloranterior = BBvaloranterior; }

    public String  getBBvalornuevo()                          { return BBvalornuevo; }
    public void    setBBvalornuevo(String BBvalornuevo)        { this.BBvalornuevo = BBvalornuevo; }

    public Date    getBBfechaaccion()                         { return BBfechaaccion; }
    public void    setBBfechaaccion(Date BBfechaaccion)        { this.BBfechaaccion = BBfechaaccion; }

    public String  getBBdescripcion()                         { return BBdescripcion; }
    public void    setBBdescripcion(String BBdescripcion)      { this.BBdescripcion = BBdescripcion; }

    // ── toString ───────────────────────────────────────────────
    @Override
    public String toString() {
        return "BitacoraBancaria{ id=" + BBid + ", accion=" + BBaccion + ", tabla=" + BBtabla + " }";
    }
}
