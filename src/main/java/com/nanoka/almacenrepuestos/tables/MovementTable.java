package com.nanoka.almacenrepuestos.tables;

import com.nanoka.almacenrepuestos.models.Movement;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author carlo
 */
public class MovementTable extends AbstractTableModel{
    private List<Movement> movements;
    private String[] columnNames = {"Movemento","Tipo de Movimiento","Cantidad","Precio","Fecha"};

    public MovementTable(List<Movement> movements) {
        this.movements = movements;
    }
    
    @Override
    public int getRowCount() {
        return this.movements.size();
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
        Movement movement = movements.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return movement.getProduct().getName();
            case 1:
                return movement.getMovementType();
            case 2:
                return movement.getQuantity();
            case 3:
                return "S/. "+movement.getPrice();
            case 4:
                return movement.getDatetime();
            default:
                return null;
        }
    }
    
}
