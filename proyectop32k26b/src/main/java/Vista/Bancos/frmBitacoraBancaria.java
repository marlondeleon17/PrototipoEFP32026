/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista.Bancos;
// Angoly Camila Araujo Mayen 9959 - 24 - 17
import Controlador.Bancos.clsBitacoraBancaria;
import Modelo.Bancos.BitacoraBancariaDAO;
import java.util.Date;
import java.util.List;
import java.io.File;

public class frmBitacoraBancaria extends javax.swing.JInternalFrame {

    private final BitacoraBancariaDAO bitacoraDAO = new BitacoraBancariaDAO();

    // ── Tabla de códigos por tabla ─────────────────────────────
    private static final java.util.Map<String, Integer> CODIGOS_TABLA;
    static {
        CODIGOS_TABLA = new java.util.LinkedHashMap<>();
        // Maestras
        CODIGOS_TABLA.put("CatTipoCuenta",          5000);
        CODIGOS_TABLA.put("CatTipoTransaccion",      5100);
        CODIGOS_TABLA.put("CatEstadoConciliacion",   5200);
        CODIGOS_TABLA.put("Banco",                   5300);
        CODIGOS_TABLA.put("Cliente",                 5400);
        // Transaccionales
        CODIGOS_TABLA.put("CuentaBancaria",          5500);
        CODIGOS_TABLA.put("MovimientoBancario",      5600);
        CODIGOS_TABLA.put("ConciliacionBancaria",    5700);
        CODIGOS_TABLA.put("BitacoraBancaria",        5800);
    }

    // ── Constructor ────────────────────────────────────────────
    public frmBitacoraBancaria() {
        initComponents();
        cargarTodos();
        jLabel1.setVisible(false);
        jLabel2.setVisible(false);
        fechaInicio.setVisible(false);
        fechaFin.setVisible(false);
        this.setClosable(true);
        this.setIconifiable(true);
        this.setMaximizable(true);
        this.setResizable(true);
    }

    // ── Cargar toda la bitácora en la tabla ────────────────────
    private void cargarTodos() {
        List<clsBitacoraBancaria> lista = bitacoraDAO.listar();
        llenarTabla(lista);
    }

    // ── Obtener código numérico de una tabla ───────────────────
    private int obtenerCodigo(String nombreTabla) {
        if (nombreTabla == null) return 0;
        return CODIGOS_TABLA.getOrDefault(nombreTabla.trim(), 0);
    }

    // ── Llenar la JTable con una lista ─────────────────────────
    private void llenarTabla(List<clsBitacoraBancaria> lista) {
        javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel) tablaBitacora.getModel();
        modelo.setRowCount(0);

        for (clsBitacoraBancaria b : lista) {
            int codigo = obtenerCodigo(b.getBBtabla());
            String codigoStr = (codigo > 0) ? String.valueOf(codigo) : "-";

            modelo.addRow(new Object[]{
                b.getBBid(),
                b.getBBusuarioaccion(),
                b.getBBaccion(),
                b.getBBtabla(),
                codigoStr, // Para código
                b.getBBregistroid(),
                b.getBBvaloranterior(),
                b.getBBvalornuevo(),
                b.getBBfechaaccion(),
                b.getBBdescripcion()
            });
        }
    }

  public static void registrarBitacora(String accion, String tabla,
                               Integer registroId,
                               String valorAnterior,
                               String valorNuevo,
                               String descripcion) {
    try {

        BitacoraBancariaDAO dao = new BitacoraBancariaDAO();
        int codigo = CODIGOS_TABLA.getOrDefault(tabla, 0);

        // Obtener IP
        String ip = "IP desconocida";

        try {
            ip = java.net.InetAddress.getLocalHost().getHostAddress();
        } catch (Exception ignored) {}

        // Obtener nombre de PC
        String nombrePC = "PC desconocida";

        try {
            nombrePC = java.net.InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Descripción final
       String descFinal = "[" + codigo + "] "
                 + "| IP: " + ip
                 + " | " + descripcion;

        clsBitacoraBancaria b = new clsBitacoraBancaria(
            nombrePC,
            accion,
            tabla,
            registroId,
            valorAnterior,
            valorNuevo,
            descFinal
        );

        dao.insert(b);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fechaInicio = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        cboxTipoBusqueda = new javax.swing.JComboBox<>();
        ayudas = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaBitacora = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fechaFin = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 153));
        jLabel3.setText("Bitácora Bancaria ");

        cboxTipoBusqueda.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboxTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Usuario", "Acción", "Tabla", "Código", "Registro", "Valor Anterior", "Valor nuevo", "Fechas", "Descripción" }));
        cboxTipoBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxTipoBusquedaActionPerformed(evt);
            }
        });

        ayudas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ayudas.setText("Ayuda");
        ayudas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ayudasActionPerformed(evt);
            }
        });

        txtBuscar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(255, 227, 250));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        tablaBitacora.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Usuario", "Acción", "Tabla", "Código", "Registro", "Valor Anterior", "Valor Nuevo", "Fecha", "Descripción"
            }
        ));
        jScrollPane2.setViewportView(tablaBitacora);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Fecha Inicio:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Fecha Fin:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuscar)
                        .addGap(47, 47, 47)
                        .addComponent(btnLimpiar)
                        .addGap(30, 30, 30)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(124, 124, 124))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(ayudas)
                        .addGap(14, 14, 14))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboxTipoBusqueda, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtBuscar))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(507, 507, 507)
                .addComponent(jLabel3)
                .addContainerGap(531, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(cboxTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(fechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpiar)
                    .addComponent(ayudas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboxTipoBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxTipoBusquedaActionPerformed
   boolean esFecha = cboxTipoBusqueda.getSelectedItem().equals("Fechas");
        txtBuscar.setVisible(!esFecha);
        jLabel1.setVisible(esFecha);
        jLabel2.setVisible(esFecha);
        fechaInicio.setVisible(esFecha);
        fechaFin.setVisible(esFecha);
    }//GEN-LAST:event_cboxTipoBusquedaActionPerformed

    private void ayudasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ayudasActionPerformed
      //Corregí errores del boton de Ayuda (Angel Méndez)
        try {
            String ruta = "src\\main\\java\\Ayudas\\Bancos\\Ayuda Bancos.chm";

            File archivo = new File(ruta);

            if (archivo.exists()) {
                Runtime.getRuntime().exec("hh.exe \"" + ruta + "\"");
            } else {
                System.out.println("La ayuda no fue encontrada");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }   
    }//GEN-LAST:event_ayudasActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
         btnBuscarActionPerformed(evt);
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
    String tipo  = (String) cboxTipoBusqueda.getSelectedItem();
    String texto = txtBuscar.getText().trim();
    List<clsBitacoraBancaria> lista     = bitacoraDAO.listar();
    List<clsBitacoraBancaria> resultado = new java.util.ArrayList<>();

    try {
        switch (tipo) {
            case "ID":
                if (!texto.isEmpty()) {
                    clsBitacoraBancaria b = bitacoraDAO.query(Integer.parseInt(texto));
                    if (b != null) resultado.add(b);
                } else {
                    resultado = lista;
                }
                break;

            case "Usuario":
                for (clsBitacoraBancaria b : lista)
                    if (String.valueOf(b.getBBusuarioaccion()).toLowerCase().contains(texto.toLowerCase()))
                        resultado.add(b);
                break;

            case "Acción":  // ← con tilde, igual que el combo
                for (clsBitacoraBancaria b : lista)
                    if (b.getBBaccion() != null &&
                        b.getBBaccion().toLowerCase().contains(texto.toLowerCase()))
                        resultado.add(b);
                break;

            case "Tabla":
                for (clsBitacoraBancaria b : lista)
                    if (b.getBBtabla() != null &&
                        b.getBBtabla().toLowerCase().contains(texto.toLowerCase()))
                        resultado.add(b);
                break;

            case "Código":  // ← busca por código numérico (5000, 5300, etc.)
                for (clsBitacoraBancaria b : lista) {
                    int cod = obtenerCodigo(b.getBBtabla());
                    if (String.valueOf(cod).contains(texto))
                        resultado.add(b);
                }
                break;

            case "Registro":
                for (clsBitacoraBancaria b : lista)
                    if (b.getBBregistroid() != null &&
                        String.valueOf(b.getBBregistroid()).contains(texto))
                        resultado.add(b);
                break;

            case "Valor Anterior":
                for (clsBitacoraBancaria b : lista)
                    if (b.getBBvaloranterior() != null &&
                        b.getBBvaloranterior().toLowerCase().contains(texto.toLowerCase()))
                        resultado.add(b);
                break;

            case "Valor nuevo":
                for (clsBitacoraBancaria b : lista)
                    if (b.getBBvalornuevo() != null &&
                        b.getBBvalornuevo().toLowerCase().contains(texto.toLowerCase()))
                        resultado.add(b);
                break;

            case "Fechas":
                Date inicio = fechaInicio.getDate();
                Date fin    = fechaFin.getDate();
                if (inicio == null || fin == null) {
                    javax.swing.JOptionPane.showMessageDialog(this,
                        "Seleccione ambas fechas.", "Aviso",
                        javax.swing.JOptionPane.WARNING_MESSAGE);
                    return;
                }
                for (clsBitacoraBancaria b : lista) {
                    Date fa = b.getBBfechaaccion();
                    if (fa != null && !fa.before(inicio) && !fa.after(fin))
                        resultado.add(b);
                }
                break;

            case "Descripción":
                for (clsBitacoraBancaria b : lista)
                    if (b.getBBdescripcion() != null &&
                        b.getBBdescripcion().toLowerCase().contains(texto.toLowerCase()))
                        resultado.add(b);
                break;

            default:
                resultado = lista;
        }

        llenarTabla(resultado);

        if (resultado.isEmpty())
            javax.swing.JOptionPane.showMessageDialog(this,
                "No se encontraron registros.", "Información",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);

    } catch (NumberFormatException ex) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Ingrese un número válido para buscar por ID o Registro.", "Error",
            javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
         int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
        this,
        "¿Está seguro que desea eliminar TODOS los registros de la bitácora?\nEsta acción no se puede deshacer.",
        "Confirmar limpieza", //Para confirmar la eliminación de datos
        javax.swing.JOptionPane.YES_NO_OPTION,
        javax.swing.JOptionPane.WARNING_MESSAGE
    );

    if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
        boolean exito = bitacoraDAO.deleteAll();

        if (exito) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Bitácora limpiada correctamente.",
                "Éxito",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Ocurrió un error al limpiar la bitácora.",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    // Limpiar filtros y recargar tabla (vacía)
    txtBuscar.setText("");
    fechaInicio.setDate(null);
    fechaFin.setDate(null);
    cboxTipoBusqueda.setSelectedIndex(0);
    txtBuscar.setVisible(true);
    jLabel1.setVisible(false);
    jLabel2.setVisible(false);
    fechaInicio.setVisible(false);
    fechaFin.setVisible(false);
    cargarTodos();
    }//GEN-LAST:event_btnLimpiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ayudas;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cboxTipoBusqueda;
    private com.toedter.calendar.JDateChooser fechaFin;
    private com.toedter.calendar.JDateChooser fechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaBitacora;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
 }    
