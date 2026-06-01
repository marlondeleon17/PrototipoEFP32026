package Vista.Bancos;

import Controlador.Bancos.clsMovimientoBancario;
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

public class frmMovimientoBancario extends javax.swing.JInternalFrame {
    
    private int idSeleccionado = -1;
    
    public frmMovimientoBancario() {
        initComponents();
        Movbid.setEditable(false);
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
    // busca la tabla dentro del jScrollPane2
    javax.swing.JTable tbl = (javax.swing.JTable) jScrollPane2.getViewport().getView();
    tbl.setModel(new DefaultTableModel(
        new Object[][]{},
        new String[]{"ID", "Fecha", "Monto", "Descripción", "Cuenta", "Tipo Transacción", "Tipo Mov", "Referencia", "Conciliado"}
    ));
}

private javax.swing.JTable getTabla() {
    return (javax.swing.JTable) jScrollPane2.getViewport().getView();
}

private void cargarCombos() {
    cmbCuentaBancaria.removeAllItems();
    for (Object[] c : new clsMovimientoBancario().getCuentasBancarias())
        cmbCuentaBancaria.addItem(c[0] + " - " + c[1]);

    cmbTipoTransaccion.removeAllItems();
    for (Object[] t : new clsMovimientoBancario().getTiposTransaccion())
        cmbTipoTransaccion.addItem(t[0] + " - " + t[1]);

    cmbTipoMovimiento.removeAllItems();
    cmbTipoMovimiento.addItem("Credito");
    cmbTipoMovimiento.addItem("Debito");

    if (cmbCuentaBancaria.getItemCount() > 0) cmbCuentaBancaria.setSelectedIndex(0);
    if (cmbTipoTransaccion.getItemCount() > 0) cmbTipoTransaccion.setSelectedIndex(0);
    if (cmbTipoMovimiento.getItemCount() > 0)  cmbTipoMovimiento.setSelectedIndex(0);

    // Actualiza saldo al cambiar cuenta
    cmbCuentaBancaria.addActionListener(e -> actualizarLblSaldo());
    
    // Actualiza tipo movimiento automático según transacción
    cmbTipoTransaccion.addActionListener(e -> {
        if (cmbTipoTransaccion.getSelectedItem() != null) {
            String tipo = cmbTipoTransaccion.getSelectedItem().toString();
            if (tipo.contains("Deposito") || tipo.contains("Cobro")) {
                cmbTipoMovimiento.setSelectedItem("Credito");
            } else {
                cmbTipoMovimiento.setSelectedItem("Debito");
            }
        }
    });

    // Muestra saldo inicial
    actualizarLblSaldo();
}

private void actualizarLblSaldo() {
    if (cmbCuentaBancaria.getSelectedItem() != null) {
        int id = getIdCombo(cmbCuentaBancaria);
        double saldo = new clsMovimientoBancario().getSaldoCuenta(id);
        lblSaldo.setText("Saldo: Q" + String.format("%.2f", saldo));
    } else {
        lblSaldo.setText("Saldo: Q0.00");
    }
}

private void cargarDatos() {
    DefaultTableModel modelo = (DefaultTableModel) getTabla().getModel();
    modelo.setRowCount(0);
    for (clsMovimientoBancario mov : new clsMovimientoBancario().getListado()) {
        modelo.addRow(new Object[]{
            mov.getMovbid(),
            mov.getMovbfechamovimiento(),
            mov.getMovibmonto(),
            mov.getMovdescripcion(),
            mov.getNumeroCuenta(),
            mov.getNombreTipoTransaccion(),
            mov.getMovbtipomov(),
            mov.getMovbreferencia(),
            mov.getMovbconciliado()
        });
    }
    actualizarLblSaldo();
}
        
private void configurarSeleccionTabla() {
    getTabla().getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting() && getTabla().getSelectedRow() >= 0) {
            int fila = getTabla().getSelectedRow();
            idSeleccionado = Integer.parseInt(getTabla().getValueAt(fila, 0).toString());
            Movbid.setText(String.valueOf(idSeleccionado));
            
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                txtFechaApertura.setDate(sdf.parse(getTabla().getValueAt(fila, 1).toString()));
            } catch (Exception ex) {}

            txtMonto.setText(getTabla().getValueAt(fila, 2).toString());
            txtDescripcion.setText(getTabla().getValueAt(fila, 3) != null ? getTabla().getValueAt(fila, 3).toString() : "");
            txtReferencia.setText(getTabla().getValueAt(fila, 7) != null ? getTabla().getValueAt(fila, 7).toString() : "");
            chkConciliado.setSelected("S".equals(getTabla().getValueAt(fila, 8)));

            seleccionarCombo(cmbCuentaBancaria,  getTabla().getValueAt(fila, 4).toString());
            seleccionarCombo(cmbTipoTransaccion, getTabla().getValueAt(fila, 5).toString());
            seleccionarCombo(cmbTipoMovimiento,  getTabla().getValueAt(fila, 6).toString());
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
        if (getTabla().getSelectedRow() >= 0) {
            JOptionPane.showMessageDialog(this, "Presione 'Limpiar' primero para insertar.");
            return;
        }
        String montoStr = txtMonto.getText().trim();
        if (montoStr.isEmpty() || txtFechaApertura.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Monto y fecha son obligatorios.");
            return;
        }
        try {
            double monto  = Double.parseDouble(montoStr);
            String fecha  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(txtFechaApertura.getDate());
            int cbanId    = getIdCombo(cmbCuentaBancaria);
            int ttId      = getIdCombo(cmbTipoTransaccion);
            String tipomov = cmbTipoMovimiento.getSelectedItem().toString();
            String ref    = txtReferencia.getText().trim();
            String conc   = chkConciliado.isSelected() ? "S" : "N";
            String desc   = txtDescripcion.getText().trim();

            clsMovimientoBancario mov = new clsMovimientoBancario(
                0, fecha, monto, desc, cbanId, ttId, tipomov, ref, conc);
            int r = new clsMovimientoBancario().setInsertar(mov);
            if (r > 0) {
            JOptionPane.showMessageDialog(this, "Movimiento insertado correctamente.");
            frmBitacoraBancaria.registrarBitacora(
    "INSERT", "MovimientoBancario", null, null,
    "Monto: " + txtMonto.getText().trim() +
    " | Tipo: " + cmbTipoMovimiento.getSelectedItem().toString() +
    " | Descripción: " + txtDescripcion.getText().trim(),
    "Movimiento bancario insertado"
);
            limpiarCampos(); 
            cargarDatos();
            } else if (r == -1) {
            JOptionPane.showMessageDialog(this,
            "Fondos insuficientes.\nNo se realizó el movimiento.",
            "Fondos insuficientes", JOptionPane.WARNING_MESSAGE);
            } else {
            JOptionPane.showMessageDialog(this, "Error al insertar.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El monto debe ser un número válido.");
        }
    });

    btnActualizar.addActionListener(e -> {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro primero.");
            return;
        }
        try {
            double monto   = Double.parseDouble(txtMonto.getText().trim());
            String fecha   = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(txtFechaApertura.getDate());
            int cbanId     = getIdCombo(cmbCuentaBancaria);
            int ttId       = getIdCombo(cmbTipoTransaccion);
            String tipomov = cmbTipoMovimiento.getSelectedItem().toString();
            String ref     = txtReferencia.getText().trim();
            String conc    = chkConciliado.isSelected() ? "S" : "N";
            String desc    = txtDescripcion.getText().trim();

            clsMovimientoBancario mov = new clsMovimientoBancario(
                idSeleccionado, fecha, monto, desc, cbanId, ttId, tipomov, ref, conc);
            int r = new clsMovimientoBancario().setActualizar(mov);
            if (r > 0) {
                JOptionPane.showMessageDialog(this, "Actualizado correctamente.");
                frmBitacoraBancaria.registrarBitacora(
    "UPDATE", "MovimientoBancario", idSeleccionado, null,
    "Monto: " + txtMonto.getText().trim() +
    " | Tipo: " + cmbTipoMovimiento.getSelectedItem().toString() +
    " | Descripción: " + txtDescripcion.getText().trim(),
    "Movimiento bancario actualizado"
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
            "¿Eliminar movimiento ID=" + idSeleccionado + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int r = new clsMovimientoBancario().setEliminar(idSeleccionado);
            if (r > 0) {
                JOptionPane.showMessageDialog(this, "Eliminado correctamente.");
                frmBitacoraBancaria.registrarBitacora(
    "DELETE", "MovimientoBancario", idSeleccionado,
    "Monto: " + txtMonto.getText().trim(),
    null, "Movimiento bancario eliminado"
);
                idSeleccionado = -1; limpiarCampos(); cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar.");
            }
        }
    });

    btnLimpiar.addActionListener(e -> {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Borrar TODOS los movimientos y resetear a ID=1?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new clsMovimientoBancario().limpiarTabla();
            frmBitacoraBancaria.registrarBitacora(
            "DELETE ALL",
            "MovimientoBancario",
            null,
            "Todos los registros de MovimientoBancario fueron borrados",
            null,
            "Limpieza total de la tabla MovimientoBancario"
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
            + "/src/main/java/Reportes/Bancos/RMovimientoBancario.jrxml");
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
            "CRUD Movimiento Bancario\n" +
            "- Insertar: llene monto, fecha y seleccione cuenta\n" +
            "- Actualizar/Eliminar: seleccione fila primero\n" +
            "- Limpiar: borra todos los registros")
    );
}

private void limpiarCampos() {
    Movbid.setText("");
    txtMonto.setText("");
    txtDescripcion.setText("");
    txtReferencia.setText("");
    txtFechaApertura.setDate(null);
    chkConciliado.setSelected(false);
    idSeleccionado = -1;
    getTabla().clearSelection();
    ((DefaultTableModel) getTabla().getModel()).setRowCount(0);
}
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblTipoCuenta = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtReferencia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbCuentaBancaria = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbTipoMovimiento = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        chkConciliado = new javax.swing.JCheckBox();
        btnInsertar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        btnAyuda = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMovimientoBancario = new javax.swing.JTable();
        Movbid = new javax.swing.JTextField();
        txtFechaApertura = new com.toedter.calendar.JDateChooser();
        cmbTipoTransaccion = new javax.swing.JComboBox<>();
        lblSaldo = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        tblTipoCuenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblTipoCuenta);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setText("ID");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setText("Datos del Registro:");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Fecha Moviento:");

        txtReferencia.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setText("Monto:");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setText("Descipción:");

        txtDescripcion.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setText("ID Cuenta Bancaria:");

        cmbCuentaBancaria.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        cmbCuentaBancaria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setText("ID Tipo Transacción:");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel8.setText("Tipo Movimiento:");

        cmbTipoMovimiento.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        cmbTipoMovimiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel9.setText("Referencia:");

        chkConciliado.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        chkConciliado.setText("Conciliado");

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
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

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

        tblMovimientoBancario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Fecha", "Monto", "Descripción", "Cuenta", "Tipo Transacción", "Tipo Movimiento", "Referencia", "Conciliado"
            }
        ));
        jScrollPane2.setViewportView(tblMovimientoBancario);

        Movbid.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        cmbTipoTransaccion.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        cmbTipoTransaccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblSaldo.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblSaldo.setText("Saldo: Q0.00");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel10.setText("Movimiento Bancario");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(cmbCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblSaldo))
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7)
                                    .addComponent(txtReferencia, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                    .addComponent(jLabel9)
                                    .addComponent(cmbTipoTransaccion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(61, 61, 61)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(chkConciliado, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(Movbid, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(93, 93, 93)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtFechaApertura, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtDescripcion)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
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
                                .addComponent(btnAyuda)
                                .addGap(82, 82, 82)))
                        .addContainerGap(129, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(Movbid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cmbCuentaBancaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addComponent(txtFechaApertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipoTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSaldo))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkConciliado))
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsertar)
                    .addComponent(btnActualizar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnReporte)
                    .addComponent(btnAyuda))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139))
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

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarActionPerformed

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
            java.util.logging.Logger.getLogger(frmMovimientoBancario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMovimientoBancario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMovimientoBancario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMovimientoBancario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMovimientoBancario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Movbid;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAyuda;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JCheckBox chkConciliado;
    private javax.swing.JComboBox<String> cmbCuentaBancaria;
    private javax.swing.JComboBox<String> cmbTipoMovimiento;
    private javax.swing.JComboBox<String> cmbTipoTransaccion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JTable tblMovimientoBancario;
    private javax.swing.JTable tblTipoCuenta;
    private javax.swing.JTextField txtDescripcion;
    private com.toedter.calendar.JDateChooser txtFechaApertura;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtReferencia;
    // End of variables declaration//GEN-END:variables
}
