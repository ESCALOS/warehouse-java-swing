/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nanoka.almacenrepuestos.tables;

import javax.swing.table.AbstractTableModel;
import com.nanoka.almacenrepuestos.models.Category;
import java.util.List;

/**
 *
 * @author carlo
 */
public class CategoryTable extends AbstractTableModel {
    private  List<Category> categories;
    private String[] columnNames = {"Nombre"};

    public CategoryTable(List<Category> categories) {
        this.categories = categories;
    }
    
    @Override
    public int getRowCount() {
        return categories.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0){
            return categories.get(rowIndex).getName();
        }
        return null;
    }
    
    public int getId(int rowIndex, int columnIndex) {
        return categories.get(rowIndex).getId();
    }
}
