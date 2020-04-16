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
public class OutsourcedPart extends Part
{

    private StringProperty companyName;

    public OutsourcedPart(IntegerProperty id, StringProperty name, DoubleProperty price, IntegerProperty stock, IntegerProperty min, IntegerProperty max, StringProperty companyName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    public StringProperty getCompanyName() {
        return companyName;
    }

    public void setCompanyName(StringProperty companyName) {
        this.companyName = companyName;
    }
  
}