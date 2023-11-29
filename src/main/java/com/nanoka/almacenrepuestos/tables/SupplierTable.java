/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nanoka.almacenrepuestos.tables;

import javax.swing.table.AbstractTableModel;
import com.nanoka.almacenrepuestos.models.Supplier;
import java.util.List;

public class SupplierTable extends AbstractTableModel {
    private List<Supplier> suppliers;
    private String[] columnNames = {"RUC","Nombre","Teléfono","Email"};

    public SupplierTable(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    @Override
    public int getRowCount() {
        return suppliers.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }
    
    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Supplier supplier = suppliers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return supplier.getRuc();
            case 1:
                return supplier.getName();
            case 2:
                return supplier.getTel();
            case 3:
                return supplier.getEmail();
            default:
                return null;
        }
    }
}
