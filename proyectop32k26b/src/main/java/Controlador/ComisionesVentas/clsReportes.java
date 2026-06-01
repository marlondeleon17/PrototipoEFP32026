/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.ComisionesVentas;

/**
 *
 * @author Xander Reyes
 */
/**
 * Clase de entidad que representa un registro de reporte de comisiones.
 * Se utiliza para transportar datos entre la base de datos y la interfaz gráfica.
 */
public class clsReportes {
    
    // Atributos privados que coinciden con las columnas de la tabla 'reportescomisionventa'
    private int Repid;          // Identificador único y correlativo del reporte
    private String Rephora;     // Almacena la hora en que se generó el registro (formato TIME)
    private int Venid;          // Almacena el número de identificación del vendedor
    private String ven_nombre;  // Nombre completo del vendedor (obtenido mediante JOIN o tabla reporte)
    private double Comcomision; // Monto monetario de la comisión calculada
    private String Repfecha;    // Almacena la fecha del registro (formato AAAA-MM-DD)

    // Constructor vacío
    // Permite instanciar la clase sin asignar valores inmediatos, útil para frameworks y llenado manual
    public clsReportes() {
    }

    // Constructor con parámetros
    // Inicializa el objeto con todos sus datos en una sola línea, ideal para usar dentro del ResultSet del DAO
    public clsReportes(int Repid, String Rephora, int Venid, String ven_nombre, double Comcomision, String Repfecha) {
        this.Repid = Repid;
        this.Rephora = Rephora;
        this.Venid = Venid;
        this.ven_nombre = ven_nombre;
        this.Comcomision = Comcomision;
        this.Repfecha = Repfecha;
    }

    // GETTERS Y SETTERS
    // Métodos de acceso para leer y modificar los atributos privados (Encapsulamiento)

    public int getRepid() {
        return Repid;
    }

    public void setRepid(int Repid) {
        this.Repid = Repid;
    }

    public String getRephora() {
        return Rephora;
    }

    public void setRephora(String Rephora) {
        this.Rephora = Rephora;
    }

    public int getVenid() {
        return Venid;
    }

    public void setVenid(int Venid) {
        this.Venid = Venid;
    }

    public String getVen_nombre() {
        return ven_nombre;
    }

    public void setVen_nombre(String ven_nombre) {
        this.ven_nombre = ven_nombre;
    }

    public double getComcomision() {
        return Comcomision;
    }

    public void setComcomision(double Comcomision) {
        this.Comcomision = Comcomision;
    }
    
    public String getRepfecha() {
        return Repfecha;
    }
    
    public void setRepfecha (String Repfecha) {
        this.Repfecha = Repfecha;
    }

    // Método para representar el objeto como una cadena de texto
    // Útil para depuración (debugging) y para imprimir los datos en consola
    @Override
    public String toString() {
        return "clsReportes{" +
                "Repid=" + Repid +
                ", Rephora='" + Rephora + '\'' +
                ", Venid=" + Venid +
                ", ven_nombre='" + ven_nombre + '\'' +
                ", Comcomision=" + Comcomision +
                ", Repfecha=" + Repfecha + '\'' +
                '}';
    }
}