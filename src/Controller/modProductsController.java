/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;
import static Model.Inventory.getAllParts;
import Model.Part;
import Model.Product;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rickychau
 */
public class modProductsController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    private Product product;
    
    Product productIndexed = mainMenuController.selectedProduct;
    int productIndexedId = productIndexed.getId().getValue();
    private int modIndex;
    
    private ObservableList<Part> addtoProList = FXCollections.observableArrayList();
    private ObservableList<Part> partForProduct = FXCollections.observableArrayList();


    @FXML
    private TextField mPro_ID_txtF;

    @FXML
    private TextField mPro_Name_txtF;

    @FXML
    private TextField mPro_Inv_txtF;

    @FXML
    private TextField mPro_Price_txtF;
   
    @FXML
    private TextField mPro_Max_txtF;

    @FXML
    private TextField mPro_Min_txtF;

    @FXML
    private Button mPro_Search_btn;

    @FXML
    private TextField mPro_SearchF_txtF;
    
    @FXML
    private TableView<Part> mPro_A_Par_Table;

    @FXML
    private TableColumn<Part, Integer> mPro_A_PartID_Col;

    @FXML
    private TableColumn<Part, String> mPro_A_PartName_Col;

    @FXML
    private TableColumn<Part, Integer> mPro_A_Inv_Col;

    @FXML
    private TableColumn<Part, String> mPro_A_Price_Col;

    @FXML
    private Button mPro_Add_btn;

    @FXML
    private TableView<Part> mPro_D_Par_Table;
    
    @FXML
    private TableColumn<Part, Integer> mPro_D_PartID_Col;

    @FXML
    private TableColumn<Part, String> mPro_D_PartName_Col;

    @FXML
    private TableColumn<Part, Integer> mPro_D_Inv_Col;

    @FXML
    private TableColumn<Part, String> mPro_D_Price_Col;

    @FXML
    void onActionAddModPro(ActionEvent event) 
    {
        Part selected = mPro_A_Par_Table.getSelectionModel().getSelectedItem();
        if(selected != null)
        {
            addtoProList.add(selected);
            mPro_D_Par_Table.setItems(addtoProList);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selection Error");
            alert.setHeaderText("No item have been selected for product.");
            alert.setContentText("Please click on item to select for product!");

            alert.showAndWait();
        }
    }

    @FXML
    void onActionDeleteModPro(ActionEvent event) 
     {
        try
        {
            Part selected = mPro_D_Par_Table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete?");
            alert.setHeaderText("Do you wish to delete "+ selected.getName().get()+"?");
            alert.setContentText("This action is permanent!");
            Optional<ButtonType> answer = alert.showAndWait();

            if(answer.isPresent() && answer.get() == ButtonType.OK)
            {
                addtoProList.remove(selected);
                mPro_D_Par_Table.setItems(addtoProList);
            }
        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selection Error");
            alert.setHeaderText("No item have been selected for edit.");
            alert.setContentText("Please click on item to select for modification!");

            alert.showAndWait();
        }

    }

    @FXML
    void onActionSaveModPro(ActionEvent event) 
     {
        try
        {
            int addPartTempStock = Integer.parseInt(mPro_Inv_txtF.getText());
            int addPartTempMin = Integer.parseInt(mPro_Min_txtF.getText());
            int addPartTempMax = Integer.parseInt(mPro_Max_txtF.getText());
            
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
                IntegerProperty id      = new SimpleIntegerProperty(Integer.parseInt(mPro_ID_txtF.getText()));
                StringProperty name     = new SimpleStringProperty(mPro_Name_txtF.getText());
                DoubleProperty price    = new SimpleDoubleProperty(Double.parseDouble(mPro_Price_txtF.getText()));
                IntegerProperty stock   = new SimpleIntegerProperty(Integer.parseInt(mPro_Inv_txtF.getText()));
                IntegerProperty min     = new SimpleIntegerProperty(Integer.parseInt(mPro_Min_txtF.getText()));
                IntegerProperty max     = new SimpleIntegerProperty(Integer.parseInt(mPro_Max_txtF.getText()));
                Product productList = new Product(id, name, price, stock, min, max);
                for(Part string: addtoProList)
                {
                    productList.addAssociatedPart(string);
                }
                //Inventory.updateProduct(productIndexedId, productList);
                Inventory.updateProduct(modIndex, productList);
                Inventory.onQueueProduct = 0;
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
    void onActionSearchModPro(ActionEvent event) 
      {
        if(mPro_SearchF_txtF.getText().isEmpty())
        {
            mPro_A_Par_Table.setItems(getAllParts());
        }
        else
        {
            if(Inventory.isNumeric(mPro_SearchF_txtF.getText()))
            {
                if(Inventory.lookupPart(Integer.parseInt(mPro_SearchF_txtF.getText())) != null)
                {
                    ObservableList<Part> tempList = FXCollections.observableArrayList();
                    tempList.add(Inventory.lookupPart(Integer.parseInt(mPro_SearchF_txtF.getText())));
                    mPro_A_Par_Table.setItems(tempList);
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Selection Error");
                    alert.setHeaderText("No item have been selected for edit.");
                    alert.setContentText("Please click on item to select for modification!");

                    alert.showAndWait();
                }
            }
            else
            {
                if(Inventory.lookupPart(mPro_SearchF_txtF.getText()) != null)
                {
                    ObservableList<Part> tempList = FXCollections.observableArrayList();
                    tempList.add(Inventory.lookupPart(mPro_SearchF_txtF.getText()));
                    mPro_A_Par_Table.setItems(tempList);
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Selection Error");
                    alert.setHeaderText("No item have been selected for edit.");
                    alert.setContentText("Please click on item to select for modification!");

                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    void onActionmainMenuModPro(ActionEvent event) throws IOException {
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
   
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Product product = Inventory.lookupProduct(productIndexedId);
        modIndex = Inventory.getAllProducts().indexOf(Inventory.lookupProduct(productIndexedId));
        partForProduct = product.getAllAssociatedParts();
        
        mPro_ID_txtF.setText("000"+Integer.toString(productIndexedId));
        mPro_Name_txtF.setText(productIndexed.getName().getValue());
        mPro_Inv_txtF.setText(Integer.toString(productIndexed.getStock().getValue()));
        mPro_Price_txtF.setText(Double.toString(productIndexed.getPrice().getValue()));
        mPro_Min_txtF.setText(Integer.toString(productIndexed.getMin().getValue()));
        mPro_Max_txtF.setText(Integer.toString(productIndexed.getMax().getValue()));
        

        mPro_A_PartID_Col.setCellValueFactory(cellData->cellData.getValue().getId().asObject());
        mPro_A_PartName_Col.setCellValueFactory(cellData->cellData.getValue().getName());
        mPro_A_Inv_Col.setCellValueFactory(cellData->cellData.getValue().getStock().asObject());
        mPro_A_Price_Col.setCellValueFactory(cellData->cellData.getValue().getPrice().asString());
        

        mPro_D_PartID_Col.setCellValueFactory(cellData->cellData.getValue().getId().asObject());
        mPro_D_PartName_Col.setCellValueFactory(cellData->cellData.getValue().getName());
        mPro_D_Inv_Col.setCellValueFactory(cellData->cellData.getValue().getStock().asObject());
        mPro_D_Price_Col.setCellValueFactory(cellData->cellData.getValue().getPrice().asString());
        
        mPro_A_Par_Table.setItems(Inventory.getAllParts());
        mPro_D_Par_Table.setItems(partForProduct);
    }    
    
}
