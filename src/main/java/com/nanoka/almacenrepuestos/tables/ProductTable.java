 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nanoka.almacenrepuestos.tables;

import com.nanoka.almacenrepuestos.models.Product;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author carlo
 */
public class ProductTable extends AbstractTableModel{
    private List<Product> products;
    private String[] columnNames = {"Nombre","Unidad de Medida","Stock","Stock Mínimo","Valor"};

    public ProductTable(List<Product> products) {
        this.products = products;
    }
    
    @Override
    public int getRowCount() {
        return this.products.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return product.getName();
            case 1:
                return product.getMeasurementUnit();
            case 2:
                return product.getStock();
            case 3:
                return product.getStockMin();
            case 4:
                return product.getPrice();
            default:
                return null;
        }
    }
    
}
