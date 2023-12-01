/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.nanoka.almacenrepuestos.frames;

import javax.swing.JOptionPane;
import com.nanoka.almacenrepuestos.models.Supplier;

/**
 *
 * @author carlo
 */
public class SupplierForm extends javax.swing.JDialog {
    private final SupplierFrame supplierFrame;
    private final int id;
    private final int supplierIndex;
    /**
     * Creates new form SupplierForm
     */
    public SupplierForm(SupplierFrame parent, boolean modal) {
        super(parent, modal);
        this.supplierFrame = parent;
        this.id = 0;
        this.supplierIndex = -1;
        setTitle("Agregar Proveedor");
        initComponents();
    }
    
    public SupplierForm(SupplierFrame parent, boolean modal,int id, int supplierIndex, Supplier currentSupplier) {
        super(parent, modal);
        this.supplierFrame = parent;
        this.id = id;
        this.supplierIndex = supplierIndex;
        setTitle("Editar Proveedor");
        initComponents();
        txt_ruc.setText(currentSupplier.getRuc());
        txt_name.setText(currentSupplier.getName());
        txt_tel.setText(currentSupplier.getTel());
        txt_email.setText(currentSupplier.getEmail());
    }
    
    private void save(String ruc, String name, String tel, String email) {
        if(!this.validedForm(ruc, name, tel, email)){
            return;
        }
        int state = supplierFrame.supplierService.save(ruc, name, tel, email);
        switch (state) {
            case 0 -> {
                JOptionPane.showMessageDialog(rootPane, "Agregado correctamente");
                txt_ruc.setText("");
                txt_name.setText("");
                txt_tel.setText("");
                txt_email.setText("");
                supplierFrame.refreshData();
            }
            case 21 -> JOptionPane.showMessageDialog(rootPane, "El ruc ya existe");
            case 22 -> JOptionPane.showMessageDialog(rootPane, "La nombre ya existe");
            case 23 -> JOptionPane.showMessageDialog(rootPane, "El teléfono ya existe");
            case 24 -> JOptionPane.showMessageDialog(rootPane, "El correo ya existe");
            default -> JOptionPane.showMessageDialog(rootPane, "No se pudo guardar");
        }
    }
    
    public void update(String ruc, String name, String tel, String email){
        if(!this.validedForm(ruc, name, tel, email)){
            return;
        }
        int state = supplierFrame.supplierService.update(id,this.supplierIndex,ruc, name, tel, email);
        switch (state) {
            case 0 -> {
                JOptionPane.showMessageDialog(rootPane, "Editado correctamente");
                supplierFrame.refreshData();
                dispose();
            }
            case 21 -> JOptionPane.showMessageDialog(rootPane, "El ruc ya existe");
            case 22 -> JOptionPane.showMessageDialog(rootPane, "La nombre ya existe");
            case 23 -> JOptionPane.showMessageDialog(rootPane, "El teléfono ya existe");
            case 24 -> JOptionPane.showMessageDialog(rootPane, "El correo ya existe");
            case 3 -> JOptionPane.showMessageDialog(rootPane, "El proveedor no existe no existe");
            default -> JOptionPane.showMessageDialog(rootPane, "No se pudo guardar");
        }
    }
    
    private boolean validedForm(String ruc,String name, String tel, String email){
        if(ruc.isBlank()) {
            JOptionPane.showMessageDialog(rootPane, "El ruc es requerido");
            return false;
        }
        if(name.isBlank()) {
            JOptionPane.showMessageDialog(rootPane, "El nombre es requerido");
            return false;
        }
        if(tel.isBlank()) {
            JOptionPane.showMessageDialog(rootPane, "El telefono es requerido");
            return false;
        }
        if(email.isBlank()) {
            JOptionPane.showMessageDialog(rootPane, "El correo es requerido");
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_add = new javax.swing.JButton();
        btn_close = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_ruc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_name = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_tel = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        btn_add.setBackground(new java.awt.Color(0, 204, 51));
        btn_add.setForeground(new java.awt.Color(255, 255, 255));
        btn_add.setText("Guardar");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_close.setBackground(new java.awt.Color(255, 0, 0));
        btn_close.setForeground(new java.awt.Color(255, 255, 255));
        btn_close.setText("Cerrar");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });

        jLabel1.setText("RUC:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        jLabel2.setText("Nombre:");

        jLabel3.setText("Telefono:");

        jLabel4.setText("Correo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_close, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_ruc, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(txt_name)
                            .addComponent(txt_tel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_email))
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_tel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_close)
                    .addComponent(btn_add))
                .addGap(15, 15, 15))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        dispose();
    }//GEN-LAST:event_btn_closeActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        if(this.id == 0) {
            this.save(txt_ruc.getText(),txt_name.getText(),txt_tel.getText(),txt_email.getText());
        }else{
            this.update(txt_ruc.getText(),txt_name.getText(),txt_tel.getText(),txt_email.getText());
        }
    }//GEN-LAST:event_btn_addActionPerformed

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
            java.util.logging.Logger.getLogger(SupplierForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SupplierForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SupplierForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SupplierForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SupplierForm dialog = new SupplierForm(new SupplierFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_close;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_ruc;
    private javax.swing.JTextField txt_tel;
    // End of variables declaration//GEN-END:variables
}
