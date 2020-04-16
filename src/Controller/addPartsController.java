/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
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
public class addPartsController implements Initializable {
    
    Stage stage;
    Parent scene;
    private boolean isInHouse = true;
    private int autoGenPartId;

    @FXML
    private RadioButton aPar_inHouse_RBtn;

    @FXML
    private ToggleGroup addPartsRBtn;

    @FXML
    private RadioButton aPar_outSourced_RBtn;

    @FXML
    private TextField aPar_ID_txtF;

    @FXML
    private TextField aPar_name_txtF;

    @FXML
    private TextField aPar_Inv_txtF;

    @FXML
    private TextField aPar_Price_txtF;

    @FXML
    private TextField aPar_Max_txtF;

    @FXML
    private TextField aPar_Min_txtF;

    @FXML
    private Label aPar_mID_Com_pTxt;

    @FXML
    private TextField aPar_mID_Com_txtF;

    @FXML
    private Button aPar_Save_btn;

    @FXML
    private Button aPar_Cancel_btn;

    @FXML
    void onActionMainMenuAddPart(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will clear all input, continue?");
        Optional<ButtonType> answer = alert.showAndWait();
        
        if(answer.isPresent() && answer.get() == ButtonType.OK)
        {
            if(Inventory.onQueuePart != 0)
            {
                Inventory.onQueuePart = Integer.parseInt(aPar_ID_txtF.getText());
            }
            else
            {
            Inventory.partIdCount--;
            }
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/mainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionSaveAddPart(ActionEvent event) {
        try
        {
            int addPartTempStock = Integer.parseInt(aPar_Inv_txtF.getText());
            int addPartTempMin = Integer.parseInt(aPar_Min_txtF.getText());
            int addPartTempMax = Integer.parseInt(aPar_Max_txtF.getText());
            
            if(addPartTempMax < addPartTempMin)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Incorrect Input");
                alert.setHeaderText("Minimum must have a value less than Maximum");
                alert.setContentText("Please make sure Minimum does not exceed "+addPartTempMax+"!");

                alert.showAndWait();
            }
            else if(addPartTempStock < addPartTempMin || addPartTempMax < addPartTempStock)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Incorrect Input");
                alert.setHeaderText("Inventory must be within Maximum and Minimum value");
                alert.setContentText("Please make sure inventory does not exceed "+addPartTempMax+" and below "+addPartTempMin+"!");

                alert.showAndWait();
            }
            else
            {
                IntegerProperty id      = new SimpleIntegerProperty(this.autoGenPartId);
                StringProperty name     = new SimpleStringProperty(aPar_name_txtF.getText());
                DoubleProperty price    = new SimpleDoubleProperty(Double.parseDouble(aPar_Price_txtF.getText()));
                IntegerProperty stock   = new SimpleIntegerProperty(Integer.parseInt(aPar_Inv_txtF.getText()));
                IntegerProperty min     = new SimpleIntegerProperty(Integer.parseInt(aPar_Min_txtF.getText()));
                IntegerProperty max     = new SimpleIntegerProperty(Integer.parseInt(aPar_Max_txtF.getText()));
                
                if(isInHouse)
                {
                    IntegerProperty machineID = new SimpleIntegerProperty(Integer.parseInt(aPar_mID_Com_txtF.getText()));
                    InHousePart part = new InHousePart(id, name, price, stock, min, max, machineID);
                    Inventory.addPart(part);
                }
                else
                {
                    StringProperty companyName = new SimpleStringProperty(aPar_mID_Com_txtF.getText());
                    OutsourcedPart part = new OutsourcedPart(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(part);
                }
                Inventory.onQueuePart = 0;
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
    void onActionInHouseRBtn(ActionEvent event) {
        addPartRBtnCheck();
    }

    @FXML
    void onActionOutsourcedRBtn(ActionEvent event) {
        addPartRBtnCheck();
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
        autoGenPartId = Inventory.getAutoGenPartId();
        aPar_ID_txtF.setText("000"+autoGenPartId);

    }
    
    private void addPartRBtnCheck()
    {
        if(aPar_inHouse_RBtn.isSelected())
        {
            aPar_mID_Com_pTxt.setText("Machine ID");
            aPar_mID_Com_txtF.setPromptText("Machine ID");
            isInHouse = true;
        }
        else
        {
            aPar_mID_Com_pTxt.setText("Company Name");
            aPar_mID_Com_txtF.setPromptText("Company Name");
            isInHouse = false; 
        }
        //System.out.println(isInHouse);
    }
}
