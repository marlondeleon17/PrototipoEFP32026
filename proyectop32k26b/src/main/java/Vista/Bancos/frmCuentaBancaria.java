package Vista.Bancos;
// Imports necesarios arriba del todo
import Controlador.Bancos.clsCuentaBancaria;
import java.io.File;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.sql.Connection;
import Modelo.Conexion;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.table.DefaultTableModel;

public class frmCuentaBancaria extends javax.swing.JInternalFrame {

    
    // Variable de clase
private int idSeleccionado = -1;

public frmCuentaBancaria() {
    initComponents();
    CBANid.setEditable(false);
    configurarTabla();
    cargarCombos();
    cargarDatos();
    configurarBotones();
    configurarSeleccionTabla();
    this.setClosable(true);
    this.setIconifiable(true);
    this.setMaximizable(true);
    this.setResizable(true);
    this.setSize(900,650);
}

private void configurarTabla() {
    tblTipoCuenta.setModel(new DefaultTableModel(
        new Object[][]{},
        new String[]{"ID", "Número Cuenta", "Saldo", "Fecha Apertura", "Banco", "Cliente", "Tipo"}
    ));
}

private void cargarCombos() {
    idbanco.removeAllItems();
    for (Object[] b : new clsCuentaBancaria().getBancos())
        idbanco.addItem(b[0] + " - " + b[1]);

    idcliente.removeAllItems();
    for (Object[] c : new clsCuentaBancaria().getClientes())
        idcliente.addItem(c[0] + " - " + c[1]);

    cmbTipoCuenta.removeAllItems();
    for (Object[] t : new clsCuentaBancaria().getTiposCuenta())
        cmbTipoCuenta.addItem(t[0] + " - " + t[1]);
    
    if (idbanco.getItemCount() > 0)      idbanco.setSelectedIndex(0);
    if (idcliente.getItemCount() > 0)    idcliente.setSelectedIndex(0);
    if (cmbTipoCuenta.getItemCount() > 0) cmbTipoCuenta.setSelectedIndex(0);
}

private void cargarDatos() {
    DefaultTableModel modelo = (DefaultTableModel) tblTipoCuenta.getModel();
    modelo.setRowCount(0);
    // Usamos tu getListado() que devuelve List<clsCuentaBancaria>
    for (clsCuentaBancaria cb : new clsCuentaBancaria().getListado()) {
        modelo.addRow(new Object[]{
            cb.getCBANid(),
            cb.getCBANnumerocuenta(),
            cb.getCBANsaldoactual(),
            cb.getCBANfechaapertura(),
            cb.getNombreBanco(),
            cb.getNombreCliente(),
            cb.getNombreTipoCuenta()
        });
    }
}

private void configurarSeleccionTabla() {
    tblTipoCuenta.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting() && tblTipoCuenta.getSelectedRow() >= 0) {
            int fila = tblTipoCuenta.getSelectedRow();
            idSeleccionado = Integer.parseInt(tblTipoCuenta.getValueAt(fila, 0).toString());
            CBANid.setText(String.valueOf(idSeleccionado));
            txtNumeroCuenta.setText(tblTipoCuenta.getValueAt(fila, 1).toString());
            txtSaldoActual.setText(tblTipoCuenta.getValueAt(fila, 2).toString());

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                txtFechaApertura.setDate(sdf.parse(
                    tblTipoCuenta.getValueAt(fila, 3).toString()));
            } catch (Exception ex) {}

            seleccionarCombo(idbanco,       tblTipoCuenta.getValueAt(fila, 4).toString());
            seleccionarCombo(idcliente,     tblTipoCuenta.getValueAt(fila, 5).toString());
            seleccionarCombo(cmbTipoCuenta, tblTipoCuenta.getValueAt(fila, 6).toString());
        }
    });
}

private void seleccionarCombo(javax.swing.JComboBox cb, String texto) {
    for (int i = 0; i < cb.getItemCount(); i++) {
        if (cb.getItemAt(i).toString().contains(texto)) {
            cb.setSelectedIndex(i);
            return;
        }
    }
}

private int getIdCombo(javax.swing.JComboBox cb) {
    if (cb.getSelectedItem() == null) return -1;
    return Integer.parseInt(cb.getSelectedItem().toString().split(" - ")[0].trim());
}

private void configurarBotones() {

    btnInsertar.addActionListener(e -> {
        if (tblTipoCuenta.getSelectedRow() >= 0) {
            JOptionPane.showMessageDialog(this, "Presione 'Limpiar' primero para insertar.");
            return;
        }
        String numero = txtNumeroCuenta.getText().trim();
        String saldoStr = txtSaldoActual.getText().trim();
        if (numero.isEmpty() || saldoStr.isEmpty() || txtFechaApertura.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }
        try {
            double saldo = Double.parseDouble(saldoStr);
            String fecha = new SimpleDateFormat("yyyy-MM-dd").format(txtFechaApertura.getDate());
            int banId = getIdCombo(idbanco);
            int cliId = getIdCombo(idcliente);
            int tcId  = getIdCombo(cmbTipoCuenta);

            clsCuentaBancaria cb = new clsCuentaBancaria(0, numero, saldo, fecha, banId, cliId, tcId);
            int r = new clsCuentaBancaria().setInsertar(cb);
            if (r > 0) {
                JOptionPane.showMessageDialog(this, "Cuenta insertada correctamente.");
                frmBitacoraBancaria.registrarBitacora(
    "INSERT", "CuentaBancaria", null, null,
    "Número: " + txtNumeroCuenta.getText().trim() +
    " | Saldo: " + txtSaldoActual.getText().trim(),
    "Cuenta bancaria insertada"
);
                limpiarCampos(); cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al insertar. ¿Número de cuenta duplicado?");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El saldo debe ser un número válido.");
        }
    });

    btnActualizar.addActionListener(e -> {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro primero.");
            return;
        }
        try {
            String numero = txtNumeroCuenta.getText().trim();
            double saldo  = Double.parseDouble(txtSaldoActual.getText().trim());
            String fecha  = new SimpleDateFormat("yyyy-MM-dd").format(txtFechaApertura.getDate());
            int banId = getIdCombo(idbanco);
            int cliId = getIdCombo(idcliente);
            int tcId  = getIdCombo(cmbTipoCuenta);

            clsCuentaBancaria cb = new clsCuentaBancaria(idSeleccionado, numero, saldo, fecha, banId, cliId, tcId);
            int r = new clsCuentaBancaria().setActualizar(cb);
            if (r > 0) {
                JOptionPane.showMessageDialog(this, "Actualizado correctamente.");
                frmBitacoraBancaria.registrarBitacora(
    "UPDATE", "CuentaBancaria", idSeleccionado, null,
    "Número: " + txtNumeroCuenta.getText().trim() +
    " | Saldo: " + txtSaldoActual.getText().trim(),
    "Cuenta bancaria actualizada"
);
                idSeleccionado = -1; limpiarCampos(); cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Verifique los datos ingresados.");
        }
    });

    btnEliminar.addActionListener(e -> {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro primero.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar cuenta ID=" + idSeleccionado + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int r = new clsCuentaBancaria().setEliminar(idSeleccionado);
            if (r > 0) {
                JOptionPane.showMessageDialog(this, "Eliminado correctamente.");
                frmBitacoraBancaria.registrarBitacora(
    "DELETE", "CuentaBancaria", idSeleccionado,
    "Número: " + txtNumeroCuenta.getText().trim(),
    null, "Cuenta bancaria eliminada"
);
                idSeleccionado = -1; limpiarCampos(); cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar.");
            }
        }
    });

    btnLimpiar.addActionListener(e -> {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Borrar TODOS los registros y resetear a ID=1?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new clsCuentaBancaria().limpiarTabla();
            frmBitacoraBancaria.registrarBitacora(
            "DELETE ALL",
            "CuentaBancaria",
            null,
            "Todos los registros de CuentaBancaria fueron borrados",
            null,
            "Limpieza total de la tabla CuentaBancaria"
        );
            limpiarCampos();
            cargarDatos();
            JOptionPane.showMessageDialog(this, "Tabla limpiada correctamente.");
        }
    });

    btnReporte.addActionListener(e -> {
    Connection conn = null;
    Map p = new HashMap();
    JasperReport report;
    JasperPrint print;
    try {
        conn = Conexion.getConnection();
        report = JasperCompileManager.compileReport(
            new File("").getAbsolutePath()
            + "/src/main/java/Reportes/Bancos/RCuentaBancaria.jrxml");
        print = JasperFillManager.fillReport(report, p, conn);
        JasperViewer view = new JasperViewer(print, false);
        view.setTitle("Reporte Cuenta Bancaria");
        view.setVisible(true);
    } catch (Exception ex) {
        ex.printStackTrace();
    }
});

    btnAyuda.addActionListener(e ->
        JOptionPane.showMessageDialog(this,
            "CRUD Cuenta Bancaria\n" +
            "- Insertar: llene todos los campos\n" +
            "- Actualizar/Eliminar: seleccione fila primero\n" +
            "- Limpiar: borra todos los registros")
    );
}

private void limpiarCampos() {
    CBANid.setText("");
    txtNumeroCuenta.setText("");
    txtSaldoActual.setText("");
    txtFechaApertura.setDate(null);
    idSeleccionado = -1;
    tblTipoCuenta.clearSelection();
    ((DefaultTableModel) tblTipoCuenta.getModel()).setRowCount(0);
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNumeroCuenta = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSaldoActual = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbTipoCuenta = new javax.swing.JComboBox<>();
        btnInsertar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        btnAyuda = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTipoCuenta = new javax.swing.JTable();
        CBANid = new javax.swing.JTextField();
        idbanco = new javax.swing.JComboBox<>();
        idcliente = new javax.swing.JComboBox<>();
        txtFechaApertura = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setText("Datos del Registro:");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Número de Cuenta:");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setText("ID");

        txtNumeroCuenta.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setText("Saldo Actual:");

        txtSaldoActual.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setText("Fecha Apertura:");

        cmbTipoCuenta.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        cmbTipoCuenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnInsertar.setBackground(new java.awt.Color(204, 204, 204));
        btnInsertar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInsertar.setText("Insertar");

        btnActualizar.setBackground(new java.awt.Color(204, 204, 204));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizar.setText("Actualizar");

        btnEliminar.setBackground(new java.awt.Color(204, 204, 204));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminar.setText("Eliminar");

        btnLimpiar.setBackground(new java.awt.Color(204, 204, 204));
        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimpiar.setText("Limpiar");

        btnReporte.setBackground(new java.awt.Color(204, 204, 204));
        btnReporte.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReporte.setText("Reporte");

        btnAyuda.setBackground(new java.awt.Color(204, 204, 204));
        btnAyuda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAyuda.setText("Ayuda");
        btnAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAyudaActionPerformed(evt);
            }
        });

        tblTipoCuenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Número Cuenta", "Saldo", "Fecha", "Banco", "Cliente", "Tipo de Cuenta"
            }
        ));
        jScrollPane1.setViewportView(tblTipoCuenta);

        CBANid.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        idbanco.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        idbanco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        idcliente.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        idcliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel9.setText("CUENTA BANCARIA");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel10.setText("ID Banco:");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel11.setText("Tipo de Cuenta:");

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel12.setText("ID Cliente:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaApertura, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(idbanco, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNumeroCuenta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel10))
                        .addGap(68, 68, 68)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSaldoActual, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(idcliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(cmbTipoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(CBANid, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnInsertar)
                                .addGap(18, 18, 18)
                                .addComponent(btnActualizar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(191, 191, 191)
                                .addComponent(btnEliminar)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimpiar)
                                .addGap(18, 18, 18)
                                .addComponent(btnReporte)
                                .addGap(18, 18, 18)
                                .addComponent(btnAyuda))))
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(468, Short.MAX_VALUE)
                    .addComponent(jLabel12)
                    .addGap(270, 270, 270)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(CBANid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSaldoActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idbanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(idcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbTipoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnInsertar)
                            .addComponent(btnActualizar)
                            .addComponent(btnEliminar)
                            .addComponent(btnLimpiar)
                            .addComponent(btnReporte)
                            .addComponent(btnAyuda)))
                    .addComponent(txtFechaApertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(240, 240, 240)
                    .addComponent(jLabel12)
                    .addContainerGap(585, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAyudaActionPerformed
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
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnAyudaActionPerformed

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
            java.util.logging.Logger.getLogger(frmCuentaBancaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCuentaBancaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCuentaBancaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCuentaBancaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCuentaBancaria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CBANid;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAyuda;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JComboBox<String> cmbTipoCuenta;
    private javax.swing.JComboBox<String> idbanco;
    private javax.swing.JComboBox<String> idcliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTipoCuenta;
    private com.toedter.calendar.JDateChooser txtFechaApertura;
    private javax.swing.JTextField txtNumeroCuenta;
    private javax.swing.JTextField txtSaldoActual;
    // End of variables declaration//GEN-END:variables
}
