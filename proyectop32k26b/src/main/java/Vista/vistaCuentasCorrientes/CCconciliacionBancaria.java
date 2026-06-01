/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista.vistaCuentasCorrientes;
import Controlador.Bancos.clsBanco;
import Controlador.controladorCuentasCorrientes.clsMovimientoTransacciones;
import Modelo.Bancos.BancoDAO;
import Modelo.Conexion;
import Modelo.modeloCuentasCorrientes.MovimientoTransaccionesDAO;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Connection;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author WINDOWS
 */
public class CCconciliacionBancaria extends javax.swing.JFrame {

    /**
     * Creates new form CCconciliacionBancaria
     */
    //Tabla simulación del DAO (Borrar futuramente)
    private List<clsMovimientoTransacciones> generarDatosSimulados(int id, String tipoSujeto) {
    List<clsMovimientoTransacciones> listaSimulada = new java.util.ArrayList<>();
    double saldoAcumulado = 0;

    // Creamos 3 movimientos de prueba
    String[] conceptos = {"Factura de Venta #001", "Pago recibido - Cheque", "Nota de Crédito"};
    String[] tipos = {"CARGO", "ABONO", "CARGO"};
    double[] montos = {1500.00, 1000.00, 250.00};

    for (int i = 0; i < conceptos.length; i++) {
        clsMovimientoTransacciones mov = new clsMovimientoTransacciones();
        mov.setMccfecha("2026-05-" + (10 + i)); // Fechas simuladas
        mov.setMccconcepto(conceptos[i] + " (" + tipoSujeto + " " + id + ")");
        mov.setMcctipo(tipos[i]);
        mov.setMccmonto(montos[i]);
        
        if (tipos[i].equals("CARGO")) saldoAcumulado += montos[i];
        else saldoAcumulado -= montos[i];
        
        mov.setMccsaldo(saldoAcumulado);
        listaSimulada.add(mov);
    }
    return listaSimulada;
}
    //Se borra hasta acá
    
    //Comienzo de Programa Estado de Cuenta
    private MovimientoTransaccionesDAO movDAO = new MovimientoTransaccionesDAO();
    private DefaultTableModel modeloTabla;
    private DefaultTableModel modeloMovimientos;
    private DefaultTableModel modeloSistema;
    private DefaultTableModel modeloBanco;
    private DefaultTableModel modeloEstadoCuenta;

    // Método para inicializar la JTable de Estado de Cuenta
    private void inicializarEstadoCuenta() {
        // Tabla
        String[] cols = {"Fecha", "Concepto", "Cargo", "Abono", "Saldo"};
        modeloEstadoCuenta = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        jTableEstadoCuenta.setModel(modeloEstadoCuenta);

        // Radio buttons
        javax.swing.ButtonGroup bg = new javax.swing.ButtonGroup();
        bg.add(jrbCliente);
        bg.add(jrbProveedor);
        jrbCliente.setSelected(true);
        jcbProveedor.setEnabled(false);

        // Clientes simulados hasta que exista ClienteDAO
        jcbCliente.removeAllItems();
        jcbCliente.addItem("1 - Comercial El Sol");
        jcbCliente.addItem("2 - Ferreteria Morales");
        jcbCliente.addItem("3 - Tienda La Esquina");
        jcbCliente.addItem("4 - Supermercado Rico");

        // Proveedores simulados hasta que exista ProveedorDAO
        jcbProveedor.removeAllItems();
        jcbProveedor.addItem("1 - Distribuidora XYZ");
        jcbProveedor.addItem("2 - Importaciones ABC");
        jcbProveedor.addItem("3 - Suministros El Norte");
    }

    // Método para cargar datos por cliente
    private void cargarEstadoCuentaCliente(int cliid) {
        modeloTabla.setRowCount(0);
    
    // Intentar obtener de la DAO real
    List<clsMovimientoTransacciones> movimientos = movDAO.queryPorCliente(cliid);
    
    // --- PLAN DE CONTINGENCIA ---
    if (movimientos == null || movimientos.isEmpty()) {
        movimientos = generarDatosSimulados(cliid, "Cliente");
    }
    // ----------------------------

    for (clsMovimientoTransacciones mov : movimientos) {
        double cargo = 0, abono = 0;
        if (mov.getMcctipo().equals("CARGO")) {
            cargo = mov.getMccmonto();
        } else {
            abono = Math.abs(mov.getMccmonto());
        }

        Object[] fila = {
            mov.getMccfecha(),
            mov.getMccconcepto(),
            String.format("Q %.2f", cargo),
            String.format("Q %.2f", abono),
            String.format("Q %.2f", mov.getMccsaldo())
        };
        modeloTabla.addRow(fila);
    }

    // Calcular saldo final de la lista simulada
    double saldoFinal = (movimientos.isEmpty()) ? 0 : movimientos.get(movimientos.size()-1).getMccsaldo();
    lblSaldoActual.setText(String.format("Saldo actual: Q %.2f", saldoFinal));
    lblSaldoActual.setForeground(saldoFinal > 0 ? Color.RED : new Color(0, 128, 0));
    }

    private void cargarEstadoCuentaProveedor(int procodigo) {
        modeloTabla.setRowCount(0);

        List<clsMovimientoTransacciones> movimientos = movDAO.queryPorProveedor(procodigo);

        for (clsMovimientoTransacciones mov : movimientos) {
            double cargo = 0, abono = 0;

            if (mov.getMcctipo().equals("CARGO")) {
                cargo = mov.getMccmonto();
            } else {
                abono = Math.abs(mov.getMccmonto());
            }

            Object[] fila = {
                mov.getMccfecha(),
                mov.getMccconcepto(),
                String.format("Q %.2f", cargo),
                String.format("Q %.2f", abono),
                String.format("Q %.2f", mov.getMccsaldo())
            };
            modeloTabla.addRow(fila);
        }

        double saldo = movDAO.saldoProveedor(procodigo);
        lblSaldoActual.setText(String.format("Saldo actual: Q %.2f", saldo));

        if (saldo > 0) {
            lblSaldoActual.setForeground(Color.RED);
        } else {
            lblSaldoActual.setForeground(new Color(0, 128, 0));
        }
    }
    
    private void cargarComboClientes() {
        jcbCliente.removeAllItems();
        jcbCliente.addItem("0 - Seleccione Cliente (MODO PRUEBA)");

        // Datos quemados para simular que vienen de la DB
        jcbCliente.addItem("101 - Cliente de Prueba A");
        jcbCliente.addItem("102 - Cliente de Prueba B");
        jcbCliente.addItem("103 - Distribuidora Norte");
    }
    
    public CCconciliacionBancaria() {
        initComponents(); 
        setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setTitle("SIG - Conciliación Bancaria");

        // 1. Inicializar Modelos de Tabla (Esto evita el NullPointerException)
        this.inicializarEstadoCuenta();
        this.inicializarMovimientos(); // <--- TE FALTABA ESTA
        this.inicializarTablasConciliacion(); 

        // 2. Cargar Datos de la Base de Datos
        this.cargarComboClientes();
        this.cargarComboBancos(); // <--- ASEGÚRATE DE QUE SE LLAME UNA VEZ

        // 3. Configuración de Radio Buttons
        bgTipo.add(jrbCliente);
        bgTipo.add(jrbProveedor);
        jrbCliente.setSelected(true);
        jcbProveedor.setEnabled(false);
    }

    
    
    //Inicio de Movimientos
    private void inicializarMovimientos() {
        // Llenar JComboBox Tipo
        jcbTipo.removeAllItems();
        jcbTipo.addItem("CARGO");
        jcbTipo.addItem("ABONO");

        // Módulo
        jcbModulo.removeAllItems();
        jcbModulo.addItem("VENTAS");
        jcbModulo.addItem("COMPRAS");
        jcbModulo.addItem("PLANILLA");
        jcbModulo.addItem("BANCOS");
        jcbModulo.addItem("CC");
        jcbModulo.addItem("LOGISTICA");

        // Clientes simulados
        jcbClientes.removeAllItems();
        jcbClientes.addItem("1 - Comercial El Sol");
        jcbClientes.addItem("2 - Ferreteria Morales");
        jcbClientes.addItem("3 - Tienda La Esquina");
        jcbClientes.addItem("4 - Supermercado Rico");

        // Proveedores simulados
        jcbProveedores.removeAllItems();
        jcbProveedores.addItem("1 - Distribuidora XYZ");
        jcbProveedores.addItem("2 - Importaciones ABC");
        jcbProveedores.addItem("3 - Suministros El Norte");
        jcbProveedores.setEnabled(false); // deshabilitado por defecto

        // Tabla
        String[] cols = {"Fecha", "Concepto", "Cargo", "Abono", "Saldo"};
        modeloMovimientos = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        jTableMovimientos.setModel(modeloMovimientos);
        cargarTodosMovimientos();
    }
    
    //Consultas TTid

    private int obtenerTTid(String modulo, String tipo) {
        // Basado en los datos insertados en Cattipotransaccion
        switch (modulo) {
            case "VENTAS":   return 10;
            case "COMPRAS":  return 11;
            case "PLANILLA": return 6;
            case "BANCOS":   return tipo.equals("CARGO") ? 1 : 2;
            case "CC":       return 9;
            default:         return 4;
        }
    }

    //Limpiar

    private void limpiarMovimientos() {
        jcbTipo.setSelectedIndex(0);
        txtConcepto.setText("");
        txtMonto.setText("");
        jcbModulo.setSelectedIndex(0);
        jcbClientes.setSelectedIndex(0);
        jcbProveedores.setSelectedIndex(0);
        jcbProveedores.setEnabled(false);
    }
    
    //Movimientos en Tabla
    private void cargarTodosMovimientos() {
        modeloMovimientos.setRowCount(0);
        List<clsMovimientoTransacciones> lista = movDAO.select();
        for (clsMovimientoTransacciones mov : lista) {
            double cargo = 0, abono = 0;
            if (mov.getMcctipo().equals("CARGO")) {
                cargo = mov.getMccmonto();
            } else {
                abono = Math.abs(mov.getMccmonto());
            }
            modeloMovimientos.addRow(new Object[]{
                mov.getMccfecha(),
                mov.getMccconcepto(),
                String.format("Q %.2f", cargo),
                String.format("Q %.2f", abono),
                String.format("Q %.2f", mov.getMccsaldo())
            });
        }
    }
    
    //Funcionmiento Ventana Combobox
    private void jcbModuloActionPerformed(ActionEvent evt) {
        if (jcbModulo.getSelectedItem() == null) return;
        String modulo = jcbModulo.getSelectedItem().toString();
        switch (modulo) {
            case "VENTAS":
                jcbClientes.setEnabled(true);
                jcbProveedores.setEnabled(false);
                jcbProveedores.setSelectedIndex(0);
                break;
            case "COMPRAS": case "PLANILLA":
                jcbProveedores.setEnabled(true);
                jcbClientes.setEnabled(false);
                jcbClientes.setSelectedIndex(0);
                break;
            default:
                jcbClientes.setEnabled(true);
                jcbProveedores.setEnabled(true);
        }
    }
    
    //Comienzo de Menu Conciliación
    private void inicializarTablasConciliacion() {
        // Configuración Tabla Sistema
        String[] colSistema = {"Fecha", "Concepto", "Monto", "Estado"};
        modeloSistema = new DefaultTableModel(colSistema, 0);
        jtMovimientosSistema.setModel(modeloSistema);

        // Configuración Tabla Banco
        String[] colBanco = {"Referencia", "Monto", "Conciliado", "Tipo"};
        modeloBanco = new DefaultTableModel(colBanco, 0);
        jtMovimientosBanco.setModel(modeloBanco);
    }
    
    private void cargarComboBancos() {
        jcbBancoConciliacion.removeAllItems();
        jcbBancoConciliacion.addItem("Seleccione un Banco...");

        BancoDAO dao = new BancoDAO();
        // Utilizamos el método listar() de tu DAO actual
        List<clsBanco> listaBancos = dao.listar(); 

        for (clsBanco b : listaBancos) {
            // Mostramos el nombre y guardamos el ID de forma implícita
            jcbBancoConciliacion.addItem(b.getBanid() + " - " + b.getBannombre());
        }
    }
    
    private void calcularDiferenciaTotal() {
        double totalSistema = 0;
        double totalBanco = 0;

        // Sumar columna Monto de Sistema (Índice 2)
        for (int i = 0; i < jtMovimientosSistema.getRowCount(); i++) {
            String valor = jtMovimientosSistema.getValueAt(i, 2).toString().replace("Q", "").trim();
            totalSistema += Double.parseDouble(valor);
        }

        // Sumar columna Monto de Banco (Índice 2)
        for (int i = 0; i < jtMovimientosBanco.getRowCount(); i++) {
            totalBanco += Double.parseDouble(jtMovimientosBanco.getValueAt(i, 2).toString());
        }

        double dif = totalSistema - totalBanco;
        txtDiferenciaMonto.setText(String.format("%.2f", Math.abs(dif)));

        if(dif == 0) {
            txtDiferenciaMonto.setForeground(java.awt.Color.GREEN);
        } else {
            txtDiferenciaMonto.setForeground(java.awt.Color.RED);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgTipo = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jrbCliente = new javax.swing.JRadioButton();
        jrbProveedor = new javax.swing.JRadioButton();
        btnConsultar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableEstadoCuenta = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        lblSaldoActual = new javax.swing.JTextField();
        jcbCliente = new javax.swing.JComboBox<>();
        jcbProveedor = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jcbTipo = new javax.swing.JComboBox<>();
        txtConcepto = new javax.swing.JTextField();
        txtMonto = new javax.swing.JTextField();
        jcbModulo = new javax.swing.JComboBox<>();
        jcbClientes = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMovimientos = new javax.swing.JTable();
        jcbProveedores = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jcbBancoConciliacion = new javax.swing.JComboBox<>();
        jdcFechaInicio = new com.toedter.calendar.JDateChooser();
        jdcFechaFinal = new com.toedter.calendar.JDateChooser();
        btnBuscarConciliacion = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtMovimientosSistema = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtMovimientosBanco = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtDiferenciaMonto = new javax.swing.JTextField();
        txtDiferenciaEstado = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnEjecutarConciliacion = new javax.swing.JButton();
        btnVerReporte = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel10.setText("Ver Cuenta De:");

        bgTipo.add(jrbCliente);
        jrbCliente.setText("Cliente");
        jrbCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbClienteActionPerformed(evt);
            }
        });

        bgTipo.add(jrbProveedor);
        jrbProveedor.setText("Proveedor");

        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        jButton5.setText("Generar PDF");

        jTableEstadoCuenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Fecha ", "Concepto", "Cargo", "Abono", "Saldo"
            }
        ));
        jScrollPane4.setViewportView(jTableEstadoCuenta);

        jLabel11.setText("Saldo Actual");

        jcbCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jcbProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jrbCliente)
                                    .addComponent(jrbProveedor))
                                .addGap(59, 59, 59)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcbProveedor, 0, 308, Short.MAX_VALUE)
                                    .addComponent(jcbCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap(424, Short.MAX_VALUE))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSaldoActual, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbCliente)
                    .addComponent(jcbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbProveedor)
                    .addComponent(jcbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConsultar)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblSaldoActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(167, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Estado de Cuenta", jPanel3);

        jcbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "CARGO", "ABONO" }));

        jcbModulo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "VENTAS", "CC" }));

        jcbClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "CLIENTES" }));
        jcbClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesActionPerformed(evt);
            }
        });

        jLabel1.setText("Tipo:");

        jLabel2.setText("Concepto:");

        jLabel3.setText("Monto:");

        jLabel4.setText("Modulo:");

        jLabel5.setText("Clientes:");

        jLabel6.setText("Proveedores:");

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jTableMovimientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Fecha", "Concepto", "Cargo", "Abono", "Saldo"
            }
        ));
        jScrollPane1.setViewportView(jTableMovimientos);

        jcbProveedores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "CLIENTES" }));
        jcbProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProveedoresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                        .addGap(194, 194, 194)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jcbClientes, 0, 378, Short.MAX_VALUE)
                            .addComponent(jcbModulo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMonto, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtConcepto, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbTipo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbProveedores, javax.swing.GroupLayout.Alignment.LEADING, 0, 378, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(239, 239, 239)
                        .addComponent(btnRegistrar)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(209, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbModulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jcbProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnLimpiar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Movimientos", jPanel1);

        jcbBancoConciliacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbBancoConciliacionActionPerformed(evt);
            }
        });

        btnBuscarConciliacion.setText("Buscar");
        btnBuscarConciliacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarConciliacionActionPerformed(evt);
            }
        });

        jLabel7.setText("Banco:");

        jLabel8.setText("Período:");

        jtMovimientosSistema.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Fecha", "Concepto", "Monto", "Estado"
            }
        ));
        jScrollPane2.setViewportView(jtMovimientosSistema);

        jtMovimientosBanco.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Referencia", "Monto", "Cancelado", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jtMovimientosBanco);

        jLabel9.setText("Diferencia");

        txtDiferenciaMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiferenciaMontoActionPerformed(evt);
            }
        });

        jLabel12.setText("Fecha Inicio");

        jLabel13.setText("Fecha Final");

        jLabel14.setText("Movimiento Cuenta Bancaria");

        jLabel15.setText("Movimiento Sistema");

        btnEjecutarConciliacion.setText("Ejecutar Conciliación");
        btnEjecutarConciliacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarConciliacionActionPerformed(evt);
            }
        });

        btnVerReporte.setText("Generar Reporte");
        btnVerReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerReporteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(56, 56, 56)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jcbBancoConciliacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jdcFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jdcFechaFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(299, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnBuscarConciliacion)
                                .addGap(51, 51, 51)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnVerReporte)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 6, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnEjecutarConciliacion)
                        .addGap(96, 96, 96))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtDiferenciaEstado)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(27, 27, 27)
                            .addComponent(txtDiferenciaMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(287, 287, 287))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbBancoConciliacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdcFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarConciliacion)
                    .addComponent(btnVerReporte))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(49, 49, 49)
                .addComponent(btnEjecutarConciliacion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtDiferenciaMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtDiferenciaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(240, 240, 240))
        );

        jTabbedPane1.addTab("Conciliación Bancaria", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDiferenciaMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiferenciaMontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiferenciaMontoActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        // TODO add your handling code here:
        modeloEstadoCuenta.setRowCount(0);
        List<clsMovimientoTransacciones> lista;
        double saldo = 0;

        if (jrbCliente.isSelected()) {
            String sel = jcbCliente.getSelectedItem().toString();
            int cliid = Integer.parseInt(sel.split(" - ")[0]);
            lista = movDAO.queryPorCliente(cliid);
            saldo = movDAO.saldoCliente(cliid);
        } else {
            String sel = jcbProveedor.getSelectedItem().toString();
            int procodigo = Integer.parseInt(sel.split(" - ")[0]);
            lista = movDAO.queryPorProveedor(procodigo);
            saldo = movDAO.saldoProveedor(procodigo);
        }

        for (clsMovimientoTransacciones mov : lista) {
            double cargo = 0, abono = 0;
            if (mov.getMcctipo().equals("CARGO")) {
                cargo = mov.getMccmonto();
            } else {
                abono = Math.abs(mov.getMccmonto());
            }
            modeloEstadoCuenta.addRow(new Object[]{
                mov.getMccfecha(),
                mov.getMccconcepto(),
                String.format("Q %.2f", cargo),
                String.format("Q %.2f", abono),
                String.format("Q %.2f", mov.getMccsaldo())
            });
        }

        // lblSaldoActual es JTextField en tu form
        lblSaldoActual.setText(String.format("Q %.2f", saldo));
        lblSaldoActual.setForeground(saldo > 0 ? Color.RED : new Color(0, 128, 0));
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void jrbClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbClienteActionPerformed
        jcbCliente.setEnabled(true);
        jcbProveedor.setEnabled(false);
        jcbProveedor.setSelectedIndex(0);
    }//GEN-LAST:event_jrbClienteActionPerformed

    private void jcbClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesActionPerformed
        // TODO add your handling code here:
        jcbProveedor.setEnabled(true);
        jcbCliente.setEnabled(false);
        jcbCliente.setSelectedIndex(0);
    }//GEN-LAST:event_jcbClientesActionPerformed

    private void jcbProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProveedoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbProveedoresActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
         // Validaciones
        if (txtConcepto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un concepto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtMonto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un monto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String tipo     = jcbTipo.getSelectedItem().toString();
            String concepto = txtConcepto.getText().trim();
            double monto    = Double.parseDouble(txtMonto.getText().trim());
            String modulo   = jcbModulo.getSelectedItem().toString();

            if (tipo.equals("ABONO")) monto = monto * -1;

            int cliid = 0, procodigo = 0;

            if (jcbClientes.isEnabled() && jcbClientes.getSelectedItem() != null) {
                cliid = Integer.parseInt(
                    jcbClientes.getSelectedItem().toString().split(" - ")[0]);
            }
            if (jcbProveedores.isEnabled() && jcbProveedores.getSelectedItem() != null) {
                procodigo = Integer.parseInt(
                    jcbProveedores.getSelectedItem().toString().split(" - ")[0]);
            }

            double saldo = 0;
            if (cliid > 0)     saldo = movDAO.saldoCliente(cliid) + monto;
            else if (procodigo > 0) saldo = movDAO.saldoProveedor(procodigo) + monto;

            int TTid = obtenerTTid(modulo, tipo);

            int resultado = movDAO.insert(
                monto, tipo, concepto,
                saldo, cliid, procodigo,
                0, 0, TTid, modulo, 0
            );

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this,
                    "Movimiento registrado.", "Exito",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarMovimientos();
                cargarTodosMovimientos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "No se pudo registrar.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Monto invalido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiarMovimientos();
        modeloMovimientos.setRowCount(0);
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnBuscarConciliacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarConciliacionActionPerformed
        
        String seleccion = jcbBancoConciliacion.getSelectedItem().toString();
        if(seleccion.contains("Seleccione")) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un banco.");
            return;
        }

        // 2. VALIDACIÓN CRÍTICA: Verificar que las fechas no sean nulas
        if (jdcFechaInicio.getDate() == null || jdcFechaFinal.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Debe elegir una Fecha de Inicio y una Fecha Final.");
            return; // Detiene la ejecución para que no salga el error que te salió
        }

        try {
            int idBanco = Integer.parseInt(seleccion.split(" - ")[0]);

            // 3. Ahora sí es seguro formatear
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String fIn = sdf.format(jdcFechaInicio.getDate());
            String fFin = sdf.format(jdcFechaFinal.getDate());

            // 4. Limpiar y Cargar
            modeloSistema.setRowCount(0);
            List<clsMovimientoTransacciones> lista = movDAO.queryParaConciliacion(idBanco, fIn, fFin);

            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay movimientos para este rango.");
            } else {
                for (clsMovimientoTransacciones m : lista) {
                    modeloSistema.addRow(new Object[]{
                        m.getMccfecha(),
                        m.getMccconcepto(),
                        String.format("Q %.2f", m.getMccmonto()),
                        m.getMccestado().equals("A") ? "Pendiente" : "Conciliado"
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage());
        }
        
    }//GEN-LAST:event_btnBuscarConciliacionActionPerformed

    private void btnEjecutarConciliacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarConciliacionActionPerformed
        // TODO add your handling code here:
        int filaSistema = jtMovimientosSistema.getSelectedRow();
        int filaBanco = jtMovimientosBanco.getSelectedRow();

        if (filaSistema != -1 && filaBanco != -1) {
            // Obtenemos los montos de ambas tablas para comparar
            String montoStrSistema = jtMovimientosSistema.getValueAt(filaSistema, 2).toString();
            String montoStrBanco = jtMovimientosBanco.getValueAt(filaBanco, 1).toString();

            double montoSistema = Double.parseDouble(montoStrSistema);
            double montoBanco = Double.parseDouble(montoStrBanco);

            if (montoSistema == montoBanco) {
                // AQUÍ OCURRE EL "UPDATE" DEL CRUD
                // movDAO.actualizarEstadoConciliado(idMovimiento);

                JOptionPane.showMessageDialog(this, "¡Movimiento Conciliado con éxito!");

                // Actualizamos la vista (Visualmente cambiamos el estado)
                jtMovimientosSistema.setValueAt("CONCILIADO", filaSistema, 3);
                jtMovimientosBanco.setValueAt("SÍ", filaBanco, 2);
            } else {
                JOptionPane.showMessageDialog(this, "Los montos no coinciden. No se puede conciliar.", "Error de Cuadre", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un movimiento en AMBAS tablas.");
        }
    }//GEN-LAST:event_btnEjecutarConciliacionActionPerformed

    private void jcbBancoConciliacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbBancoConciliacionActionPerformed
    
    }//GEN-LAST:event_jcbBancoConciliacionActionPerformed

    private void btnVerReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerReporteActionPerformed
        // TODO add your handling code here:
    Connection conn = null;
    Map p = new HashMap();
    JasperReport report;
    JasperPrint print;
        try {
            conn = Conexion.getConnection();
            report = JasperCompileManager.compileReport(new File("").getAbsolutePath()
                + "/src/main/java/Reportes/CuentasCorrientes/MovimientosTransaccionesReportes.jrxml");
                print = JasperFillManager.fillReport(report, p, conn);
            JasperViewer view = new JasperViewer(print, false);
                view.setTitle("Reporte Prueba");
            view.setVisible(true);
        } catch (Exception e) {
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnVerReporteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CCconciliacionBancaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CCconciliacionBancaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CCconciliacionBancaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CCconciliacionBancaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CCconciliacionBancaria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgTipo;
    private javax.swing.JButton btnBuscarConciliacion;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnEjecutarConciliacion;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnVerReporte;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableEstadoCuenta;
    private javax.swing.JTable jTableMovimientos;
    private javax.swing.JComboBox<String> jcbBancoConciliacion;
    private javax.swing.JComboBox<String> jcbCliente;
    private javax.swing.JComboBox<String> jcbClientes;
    private javax.swing.JComboBox<String> jcbModulo;
    private javax.swing.JComboBox<String> jcbProveedor;
    private javax.swing.JComboBox<String> jcbProveedores;
    private javax.swing.JComboBox<String> jcbTipo;
    private com.toedter.calendar.JDateChooser jdcFechaFinal;
    private com.toedter.calendar.JDateChooser jdcFechaInicio;
    private javax.swing.JRadioButton jrbCliente;
    private javax.swing.JRadioButton jrbProveedor;
    private javax.swing.JTable jtMovimientosBanco;
    private javax.swing.JTable jtMovimientosSistema;
    private javax.swing.JTextField lblSaldoActual;
    private javax.swing.JTextField txtConcepto;
    private javax.swing.JTextField txtDiferenciaEstado;
    private javax.swing.JTextField txtDiferenciaMonto;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}
