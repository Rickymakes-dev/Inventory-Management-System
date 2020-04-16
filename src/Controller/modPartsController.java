/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rickychau
 */
public class modPartsController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    private boolean isInHouse = true;
    
    Part partIndexed = mainMenuController.selectedPart;
    int partIndexedId = partIndexed.getId().getValue();

    @FXML
    private RadioButton mPar_inHouse_RBtn;

    @FXML
    private ToggleGroup modPartRBtn;

    @FXML
    private RadioButton mPar_outSourced_RBtn;

    @FXML
    private TextField mPar_ID_txtF;

    @FXML
    private TextField mPar_PartName_txtF;

    @FXML
    private TextField mPar_Inv_txtF;

    @FXML
    private TextField mPar_Price_txtF;

    @FXML
    private TextField mPar_Max_txtF;

    @FXML
    private TextField mPar_Min_txtF;

    @FXML
    private Label mPar_mID_Comp_pTxt;

    @FXML
    private TextField mPar_mID_Com_txtF;

    @FXML
    void onActionSavemodPart(ActionEvent event) 
    {
        try
        {
            int modPartTempStock = Integer.parseInt(mPar_Inv_txtF.getText());
            int modPartTempMin = Integer.parseInt(mPar_Min_txtF.getText());
            int modPartTempMax = Integer.parseInt(mPar_Max_txtF.getText());
            
            if(modPartTempMax < modPartTempMin)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Incorrect Input");
                alert.setHeaderText("Minimum must have a value less than Maximum");
                alert.setContentText("Please make sure Minimum does not exceed "+modPartTempMax+"!");

                alert.showAndWait();
            }
            else if(modPartTempStock < modPartTempMin || modPartTempMax < modPartTempStock)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Incorrect Input");
                alert.setHeaderText("Inventory must be within Maximum and Minimum value");
                alert.setContentText("Please make sure inventory does not exceed "+modPartTempMax+" and below "+modPartTempMin+"!");

                alert.showAndWait();
            }
            else
            {
                IntegerProperty id      = new SimpleIntegerProperty(Integer.parseInt(mPar_ID_txtF.getText()));
                StringProperty name     = new SimpleStringProperty(mPar_PartName_txtF.getText());
                DoubleProperty price    = new SimpleDoubleProperty(Double.parseDouble(mPar_Price_txtF.getText()));
                IntegerProperty stock   = new SimpleIntegerProperty(Integer.parseInt(mPar_Inv_txtF.getText()));
                IntegerProperty min     = new SimpleIntegerProperty(Integer.parseInt(mPar_Min_txtF.getText()));
                IntegerProperty max     = new SimpleIntegerProperty(Integer.parseInt(mPar_Max_txtF.getText()));
                
                if(isInHouse)
                {
                    IntegerProperty machineID = new SimpleIntegerProperty(Integer.parseInt(mPar_mID_Com_txtF.getText()));
                    InHousePart part = new InHousePart(id, name, price, stock, min, max, machineID);
                    Inventory.updatePart(partIndexedId, part);
                }
                else
                {
                    StringProperty companyName = new SimpleStringProperty(mPar_mID_Com_txtF.getText());
                    OutsourcedPart part = new OutsourcedPart(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(partIndexedId, part);
                }
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/mainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(IOException | NumberFormatException e)
        {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Input Error");
                alert.setHeaderText("Input Error: "+e);
                alert.setContentText("Please make sure data type match with field");

                alert.showAndWait();
        }
    }

    @FXML
    void onActionmainMenumodPart(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will clear all input, continue?");
        Optional<ButtonType> answer = alert.showAndWait();
        
        if(answer.isPresent() && answer.get() == ButtonType.OK)
        {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/mainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionInHouseRBtn(ActionEvent event) {
        addPartRBtnCheck();
        //mPar_mID_Com_txtF.setText(null);
    }

    @FXML
    void onActionOutsourcedRBtn(ActionEvent event) {
        addPartRBtnCheck();
        //mPar_mID_Com_txtF.setText(null);
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addPartRBtnCheck();
        
        mPar_ID_txtF.setText("000"+Integer.toString(partIndexedId));
        mPar_PartName_txtF.setText(partIndexed.getName().getValue());
        mPar_Inv_txtF.setText(Integer.toString(partIndexed.getStock().getValue()));
        mPar_Price_txtF.setText(Double.toString(partIndexed.getPrice().getValue()));
        mPar_Min_txtF.setText(Integer.toString(partIndexed.getMin().getValue()));
        mPar_Max_txtF.setText(Integer.toString(partIndexed.getMax().getValue()));
        
        if (partIndexed instanceof InHousePart)
        {
            mPar_inHouse_RBtn.setSelected(true);
            addPartRBtnCheck();
            mPar_mID_Com_txtF.setText(Integer.toString(((InHousePart)Inventory.getAllParts().get(Inventory.getAllParts().indexOf(partIndexed))).getMachineId().getValue()));
        }
        else
        {
            mPar_outSourced_RBtn.setSelected(true);
            addPartRBtnCheck();
            mPar_mID_Com_txtF.setText((((OutsourcedPart)Inventory.getAllParts().get(Inventory.getAllParts().indexOf(partIndexed))).getCompanyName().getValue()));
        }
    }    
    
    private void addPartRBtnCheck()
    {
        if(mPar_inHouse_RBtn.isSelected())
        {
            mPar_mID_Comp_pTxt.setText("Machine ID");
            mPar_mID_Com_txtF.setPromptText("Machine ID");
            
            isInHouse = true;
        }
        else
        {
            mPar_mID_Comp_pTxt.setText("Company Name");
            mPar_mID_Com_txtF.setPromptText("Company Name");
            
            isInHouse = false; 
        }
        //System.out.println(isInHouse);
    } 
}
