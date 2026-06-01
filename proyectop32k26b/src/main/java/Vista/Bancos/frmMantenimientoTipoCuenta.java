package Vista.Bancos;

import Controlador.Bancos.clsTipoCuenta;
import java.io.File;
import java.util.List;
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

public class frmMantenimientoTipoCuenta extends javax.swing.JInternalFrame {

    
public frmMantenimientoTipoCuenta() {
    initComponents();
    configurarTabla();
    cargarDatos();
    configurarBotones();
    configurarSeleccionTabla();
    this.setClosable(true);
    this.setIconifiable(true);
    this.setMaximizable(true);
    this.setResizable(true);
}

// Agrega esta variable al inicio de la clase, antes del constructor
private int idSeleccionado = -1;

// ── Configura columnas de la tabla ────────────────────────────────────
private void configurarTabla() {
    tblTipoCuenta.setModel(new DefaultTableModel(
        new Object[][]{},
        new String[]{"ID", "Nombre Tipo", "Descripción"}
    ));
}

// ── Carga todos los registros en la tabla ─────────────────────────────
private void cargarDatos() {
    DefaultTableModel modelo = (DefaultTableModel) tblTipoCuenta.getModel();
    modelo.setRowCount(0);

    List<clsTipoCuenta> lista = new clsTipoCuenta().getListado();
    for (clsTipoCuenta tc : lista) {
        modelo.addRow(new Object[]{
            tc.getTCidcuenta(),
            tc.getTCnombretipo(),
            tc.getTCdescripcion()
        });
    }
    // Ya no tocamos TCidcuenta aquí
}

private void configurarSeleccionTabla() {
    tblTipoCuenta.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting() && tblTipoCuenta.getSelectedRow() >= 0) {
            int fila = tblTipoCuenta.getSelectedRow();

            // Guardamos el ID en la variable de clase
            idSeleccionado = Integer.parseInt(
                tblTipoCuenta.getValueAt(fila, 0).toString()
            );

            Object valorNombre = tblTipoCuenta.getValueAt(fila, 1);
            Object valorDesc   = tblTipoCuenta.getValueAt(fila, 2);

            TCidcuenta.setText(String.valueOf(idSeleccionado));
            txtNombreTipo.setText(valorNombre != null ? valorNombre.toString() : "");
            txtDescripcion.setText(valorDesc != null ? valorDesc.toString() : "");
        }
    });
}

private void limpiarCampos() {
    TCidcuenta.setText("");
    txtNombreTipo.setText("");
    txtDescripcion.setText("");
    idSeleccionado = -1;
    tblTipoCuenta.clearSelection();
    
    // Limpia la tabla visualmente SIN recargar
    ((DefaultTableModel) tblTipoCuenta.getModel()).setRowCount(0);
}

// ── Conecta cada botón con su acción ─────────────────────────────────
private void configurarBotones() {

    btnInsertar.addActionListener(e -> {
    if (tblTipoCuenta.getSelectedRow() >= 0) {
        JOptionPane.showMessageDialog(this,
            "Hay un registro seleccionado.\n" +
            "Presione 'Limpiar' primero para insertar uno nuevo.");
        return;
    }
    String nombre = txtNombreTipo.getText().trim();
    String desc   = txtDescripcion.getText().trim();
    if (nombre.isEmpty()) {
        JOptionPane.showMessageDialog(this, "El nombre del tipo es obligatorio.");
        return;
    }
    clsTipoCuenta tc = new clsTipoCuenta(0, nombre, desc);
    int resultado = new clsTipoCuenta().setInsertar(tc);
    if (resultado > 0) {
        JOptionPane.showMessageDialog(this, "Tipo de cuenta insertado correctamente.");
        frmBitacoraBancaria.registrarBitacora(
    "INSERT", "CatTipoCuenta", null, null,
    "Nombre: " + txtNombreTipo.getText().trim() +
    " | Descripción: " + txtDescripcion.getText().trim(),
    "Tipo de cuenta insertado"
);
        limpiarCampos();
        cargarDatos();
    } else {
        JOptionPane.showMessageDialog(this, "Error al insertar. ¿El nombre ya existe?");
    }
});

btnActualizar.addActionListener(e -> {
    if (idSeleccionado == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un registro primero.");
        return;
    }
    String nombre = txtNombreTipo.getText().trim();
    String desc   = txtDescripcion.getText().trim();
    if (nombre.isEmpty()) {
        JOptionPane.showMessageDialog(this, "El nombre es obligatorio.");
        return;
    }
    // Usa idSeleccionado, no getText()
    clsTipoCuenta tc = new clsTipoCuenta(idSeleccionado, nombre, desc);
    int resultado = new clsTipoCuenta().setActualizar(tc);
    if (resultado > 0) {
        JOptionPane.showMessageDialog(this, "Actualizado correctamente.");
        idSeleccionado = -1;
        frmBitacoraBancaria.registrarBitacora(
    "UPDATE", "CatTipoCuenta", idSeleccionado, null,
    "Nombre: " + txtNombreTipo.getText().trim() +
    " | Descripción: " + txtDescripcion.getText().trim(),
    "Tipo de cuenta actualizado"
);
        limpiarCampos();
        cargarDatos();
    } else {
        JOptionPane.showMessageDialog(this, "Error al actualizar.");
    }
});

btnEliminar.addActionListener(e -> {
    if (tblTipoCuenta.getSelectedRow() < 0) {
        JOptionPane.showMessageDialog(this, "Seleccione un registro primero.");
        return;
    }
    // ↓ getText() en lugar de getSelectedItem()
    int id = Integer.parseInt(TCidcuenta.getText());
    int confirm = JOptionPane.showConfirmDialog(this,
        "¿Eliminar tipo de cuenta ID=" + id + "?",
        "Confirmar", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        int resultado = new clsTipoCuenta().setEliminar(id);
        if (resultado > 0) {
            JOptionPane.showMessageDialog(this, "Eliminado correctamente.");
            frmBitacoraBancaria.registrarBitacora(
    "DELETE", "CatTipoCuenta", id,
    "Nombre: " + txtNombreTipo.getText().trim(),
    null, "Tipo de cuenta eliminado"
);
            limpiarCampos();
            cargarDatos();
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
        new clsTipoCuenta().limpiarTabla();
        frmBitacoraBancaria.registrarBitacora(
            "DELETE ALL",
            "CatTipoCuenta",
            null,
            "Todos los registros de CatTipoCuenta fueron borrados",
            null,
            "Limpieza total de la tabla CatTipoCuenta"
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
            + "/src/main/java/Reportes/Bancos/RTipoCuenta.jrxml");
        print = JasperFillManager.fillReport(report, p, conn);
        JasperViewer view = new JasperViewer(print, false);
        view.setTitle("Reporte Tipo Cuenta");
        view.setVisible(true);
    } catch (Exception ex) {
        ex.printStackTrace();
    }
});

    btnAyuda.addActionListener(e ->
        JOptionPane.showMessageDialog(this,
            "CRUD de Tipos de Cuenta\n" +
            "- Insertar: llene nombre y descripción\n" +
            "- Actualizar/Eliminar: seleccione fila primero")
    );
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombreTipo = new javax.swing.JTextField();
        btnInsertar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        btnAyuda = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTipoCuenta = new javax.swing.JTable();
        TCidcuenta = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setText("Datos del Registro:");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("ID:");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setText("Descripcion:");

        txtDescripcion.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        txtDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setText("Nombre del Tipo de Cuenta:");

        txtNombreTipo.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

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

        tblTipoCuenta.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        tblTipoCuenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Tipo de Cuenta", "Descripción"
            }
        ));
        jScrollPane1.setViewportView(tblTipoCuenta);

        TCidcuenta.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel5.setText("TIPO CUENTA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TCidcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(btnInsertar)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar)
                        .addGap(18, 18, 18)
                        .addComponent(btnReporte)
                        .addGap(18, 18, 18)
                        .addComponent(btnAyuda))
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(txtDescripcion)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(txtNombreTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TCidcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsertar)
                    .addComponent(btnActualizar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnReporte)
                    .addComponent(btnAyuda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionActionPerformed

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
            java.util.logging.Logger.getLogger(frmMantenimientoTipoCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMantenimientoTipoCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMantenimientoTipoCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMantenimientoTipoCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMantenimientoTipoCuenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TCidcuenta;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAyuda;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTipoCuenta;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombreTipo;
    // End of variables declaration//GEN-END:variables
}
