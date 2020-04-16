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
public abstract class Part 
{
    private IntegerProperty id;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty stock;
    private IntegerProperty min;
    private IntegerProperty max;

    public Part(IntegerProperty id, StringProperty name, DoubleProperty price, IntegerProperty stock, IntegerProperty min, IntegerProperty max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public IntegerProperty getId() {
        return id;
    }

    public void setId(IntegerProperty id) {
        this.id = id;
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(StringProperty name) {
        this.name = name;
    }

    public DoubleProperty getPrice() {
        return price;
    }

    public void setPrice(DoubleProperty price) {
        this.price = price;
    }

    public IntegerProperty getStock() {
        return stock;
    }

    public void setStock(IntegerProperty stock) {
        this.stock = stock;
    }

    public IntegerProperty getMin() {
        return min;
    }

    public void setMin(IntegerProperty min) {
        this.min = min;
    }

    public IntegerProperty getMax() {
        return max;
    }

    public void setMax(IntegerProperty max) {
        this.max = max;
    }
    
}
