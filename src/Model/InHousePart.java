/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author rickychau
 */
public class InHousePart extends Part {
    
    private IntegerProperty machineId;

    public InHousePart(IntegerProperty id, StringProperty name, DoubleProperty price, IntegerProperty stock, IntegerProperty min, IntegerProperty max, IntegerProperty machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    public IntegerProperty getMachineId() {
        return machineId;
    }

    public void setMachineId(IntegerProperty machineId) {
        this.machineId = machineId;
    }
}
