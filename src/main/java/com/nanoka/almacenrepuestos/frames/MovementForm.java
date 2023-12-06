/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.nanoka.almacenrepuestos.frames;

import javax.swing.JOptionPane;
import com.nanoka.almacenrepuestos.models.Product;
import com.nanoka.almacenrepuestos.models.Category;
import com.nanoka.almacenrepuestos.models.Supplier;
import java.math.BigDecimal;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

/**
 *
 * @author carlo
 */
public class MovementForm extends javax.swing.JDialog {
    private final ProductFrame productFrame;
    private final int id;
    /**
     * Creates new form ProductForm
     */
    public MovementForm(ProductFrame parent, boolean modal) {
        super(parent, modal);
        this.productFrame = parent;
        this.id = 0;
        setTitle("Agregar Producto");
        initComponents();
        loadCategories();
        loadSuppliers();
        ft_price.setValue(BigDecimal.ZERO);
        this.setFormatPrice();
    }
    
    public MovementForm(ProductFrame parent, boolean modal,int id, Product currentProduct) {
        super(parent, modal);
        this.productFrame = parent;
        this.id = id;
        setTitle("Editar Producto");
        initComponents();
        loadCategories();
        loadSuppliers();
        cb_category.setSelectedItem(this.productFrame.productService.getCategories().get(this.productFrame.productService.categoryService.binarySearch(this.productFrame.productService.getCategories(), currentProduct.getCategory().getId())));
        cb_supplier.setSelectedItem(this.productFrame.productService.getSuppliers().get(this.productFrame.productService.supplierService.binarySearch(this.productFrame.productService.getSuppliers(), currentProduct.getSupplier().getId())));
        txt_name.setText(currentProduct.getName());
        txt_measurement_unit.setText(currentProduct.getMeasurementUnit());
        sp_stock.setValue(currentProduct.getStock());
        sp_stock_min.setValue(currentProduct.getStockMin());
        ft_price.setValue(currentProduct.getPrice());
        this.setFormatPrice();
    }
    
    private void setFormatPrice() {
        NumberFormatter formatter = new NumberFormatter();
        formatter.setValueClass(BigDecimal.class);
        formatter.setMinimum(BigDecimal.ZERO);
        formatter.setFormat(NumberFormat.getInstance());
        formatter.setAllowsInvalid(false);
        DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter);
        ft_price.setFormatterFactory(factory);
    }
    
    private void save(Category category, Supplier supplier, String name, String measurementUnit, int stock, int stockMin, BigDecimal price) {
        if(!this.validedForm(name, measurementUnit, stock, stockMin, price)){
            return;
        }
        int state = productFrame.productService.save(category, supplier, name, measurementUnit, stock, stockMin, price);
        switch (state) {
            case 0 -> {
                JOptionPane.showMessageDialog(rootPane, "Agregado correctamente");
                txt_name.setText("");
                txt_measurement_unit.setText("");
                sp_stock.setValue(1);
                sp_stock_min.setValue(1);
                productFrame.refreshData();
            }
            case 2 -> JOptionPane.showMessageDialog(rootPane, "El producto ya existe");
            default -> JOptionPane.showMessageDialog(rootPane, "No se pudo guardar");
        }
    }
    
    public void update(Category category, Supplier supplier, String name, String measurementUnit, int stock, int stockMin, BigDecimal price){
        if(!this.validedForm(name, measurementUnit, stock, stockMin, price)){
            return;
        }
        int state = productFrame.productService.update(id,category, supplier, name, measurementUnit, stock, stockMin, price);
        switch (state) {
            case 0 -> {
                JOptionPane.showMessageDialog(rootPane, "Editado correctamente");
                productFrame.refreshData();
                dispose();
            }
            case 2 -> JOptionPane.showMessageDialog(rootPane, "El producto ya existe");
            case 3 -> JOptionPane.showMessageDialog(rootPane, "El proveedor no existe no existe");
            default -> JOptionPane.showMessageDialog(rootPane, "No se pudo guardar");
        }
    }
    
    private boolean validedForm(String name,String measurementUnit, int stock, int stockMin, BigDecimal price){
        if(name.isBlank()) {
            JOptionPane.showMessageDialog(rootPane, "El nombre es requerido");
            return false;
        }
        if(measurementUnit.isBlank()) {
            JOptionPane.showMessageDialog(rootPane, "La unidad de medida es requerida");
            return false;
        }
        if(stock < 0) {
            JOptionPane.showMessageDialog(rootPane, "El stock no puede ser negativo");
            return false;
        }
        if(stockMin < 1) {
            JOptionPane.showMessageDialog(rootPane, "El stock mínimo debe ser mayor a 0");
            return false;
        }
        
        if(price.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(rootPane, "El precio no puede ser negativo");
            return false;
        }
        return true;
    }
    
    private void loadCategories() {
        for(Category category : this.productFrame.productService.categoryService.categories) {
            cb_category.addItem(category);
        }
    }
    
    private void loadSuppliers() {
        for(Supplier supplier : this.productFrame.productService.supplierService.suppliers) {
            cb_supplier.addItem(supplier);
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

        btn_add = new javax.swing.JButton();
        btn_close = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_name = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_measurement_unit = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cb_category = new javax.swing.JComboBox<>();
        cb_supplier = new javax.swing.JComboBox<>();
        sp_stock = new javax.swing.JSpinner();
        sp_stock_min = new javax.swing.JSpinner();
        ft_price = new javax.swing.JFormattedTextField();

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

        jLabel1.setText("Nombre:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        jLabel2.setText("Unidad de medida:");

        jLabel3.setText("Cantidad");

        jLabel4.setText("Cantidad mínima:");

        jLabel5.setText("Valor:");

        jLabel6.setText("S/.");

        jLabel7.setText("Categoría:");

        jLabel8.setText("Proveedor:");

        ft_price.setColumns(10);
        ft_price.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        ft_price.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ft_price.setActionCommand("<Not Set>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_close, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ft_price))
                            .addComponent(sp_stock_min)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(71, 71, 71)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_name)
                            .addComponent(cb_category, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cb_supplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sp_stock)
                            .addComponent(txt_measurement_unit))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cb_category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cb_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_measurement_unit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(sp_stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(sp_stock_min, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(ft_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            this.save((Category) cb_category.getSelectedItem(),(Supplier) cb_supplier.getSelectedItem(),txt_name.getText(),txt_measurement_unit.getText(), (int)sp_stock.getValue(),(int) sp_stock_min.getValue(), (BigDecimal) ft_price.getValue());
        }else{
            this.update((Category) cb_category.getSelectedItem(),(Supplier) cb_supplier.getSelectedItem(),txt_name.getText(),txt_measurement_unit.getText(), (int)sp_stock.getValue(),(int) sp_stock_min.getValue(), (BigDecimal) ft_price.getValue());
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
            java.util.logging.Logger.getLogger(MovementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MovementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MovementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MovementForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MovementForm dialog = new MovementForm(new ProductFrame(), true);
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
    private javax.swing.JComboBox<Category> cb_category;
    private javax.swing.JComboBox<Supplier> cb_supplier;
    private javax.swing.JFormattedTextField ft_price;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSpinner sp_stock;
    private javax.swing.JSpinner sp_stock_min;
    private javax.swing.JTextField txt_measurement_unit;
    private javax.swing.JTextField txt_name;
    // End of variables declaration//GEN-END:variables
}
