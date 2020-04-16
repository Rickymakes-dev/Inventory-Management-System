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
import javafx.beans.value.ObservableValue;
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
public class addProductsController implements Initializable {

    Stage stage;
    Parent scene;
    
    private int autoGenProductId;
    
    private ObservableList<Part> addtoProList = FXCollections.observableArrayList();
    
    @FXML
    private TextField aPro_ID_txtF;

    @FXML
    private TextField aPro_Name_txtF;

    @FXML
    private TextField aPro_Inv_txtF;

    @FXML
    private TextField aPro_Price_txtF;

    @FXML
    private TextField aPro_Max_txtF;

    @FXML
    private TextField aPro_Min_txtF;

    @FXML
    private Button aPro_Search_btn;

    @FXML
    private TextField aPro_SearchF_txtF;
    
    @FXML
    private TableView<Part> aPro_A_Par_Table;

    @FXML
    private TableColumn<Part, Integer> aPro_A_PartID_Col;

    @FXML
    private TableColumn<Part, String> aPro_A_PartName_Col;

    @FXML
    private TableColumn<Part, Integer> aPro_A_Inv_Col;

    @FXML
    private TableColumn<Part, String> aPro_A_Price_Col;

    @FXML
    private Button aPro_Add_btn;
    
    @FXML
    private TableView<Part> aPro_D_Par_Table;

    @FXML
    private TableColumn<Part, Integer> aPro_D_PartID_Col;

    @FXML
    private TableColumn<Part, String> aPro_D_PartName_Col;

    @FXML
    private TableColumn<Part, Integer> aPro_D_Inv_Col;

    @FXML
    private TableColumn<Part, String> aPro_D_Price_Col;

    @FXML
    private Button aPro_Delete_btn;

    @FXML
    private Button aPro_Save_btn;

    @FXML
    private Button aPro_Cancel_btn;

    @FXML
    void onActionAddAddPro(ActionEvent event) 
    {
        Part selected = aPro_A_Par_Table.getSelectionModel().getSelectedItem();
        if(selected != null)
        {
            addtoProList.add(selected);
            aPro_D_Par_Table.setItems(addtoProList);
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
    void onActionDeleteAddPro(ActionEvent event) 
    {
        try
        {
            Part selected = aPro_D_Par_Table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete?");
            alert.setHeaderText("Do you wish to delete "+ selected.getName().get()+"?");
            alert.setContentText("This action is permanent!");
            Optional<ButtonType> answer = alert.showAndWait();

            if(answer.isPresent() && answer.get() == ButtonType.OK)
            {
                addtoProList.remove(selected);
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
    void onActionSaveAddPro(ActionEvent event) 
    {
        try
        {
            int addPartTempStock = Integer.parseInt(aPro_Inv_txtF.getText());
            int addPartTempMin = Integer.parseInt(aPro_Min_txtF.getText());
            int addPartTempMax = Integer.parseInt(aPro_Max_txtF.getText());
            
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
                IntegerProperty id      = new SimpleIntegerProperty(this.autoGenProductId);
                StringProperty name     = new SimpleStringProperty(aPro_Name_txtF.getText());
                DoubleProperty price    = new SimpleDoubleProperty(Double.parseDouble(aPro_Price_txtF.getText()));
                IntegerProperty stock   = new SimpleIntegerProperty(Integer.parseInt(aPro_Inv_txtF.getText()));
                IntegerProperty min     = new SimpleIntegerProperty(Integer.parseInt(aPro_Min_txtF.getText()));
                IntegerProperty max     = new SimpleIntegerProperty(Integer.parseInt(aPro_Max_txtF.getText()));

                Product product = new Product(id, name, price, stock, min, max);
                for(Part string: addtoProList)
                {
                    product.addAssociatedPart(string);
                }
                Inventory.addProduct(product);
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
    void onActionSearchAddPro(ActionEvent event) 
     {
        if(aPro_SearchF_txtF.getText().isEmpty())
        {
            aPro_A_Par_Table.setItems(getAllParts());
        }
        else
        {
            if(Inventory.isNumeric(aPro_SearchF_txtF.getText()))
            {
                if(Inventory.lookupPart(Integer.parseInt(aPro_SearchF_txtF.getText())) != null)
                {
                    ObservableList<Part> tempList = FXCollections.observableArrayList();
                    tempList.add(Inventory.lookupPart(Integer.parseInt(aPro_SearchF_txtF.getText())));
                    aPro_A_Par_Table.setItems(tempList);
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
                if(Inventory.lookupPart(aPro_SearchF_txtF.getText()) != null)
                {
                    ObservableList<Part> tempList = FXCollections.observableArrayList();
                    tempList.add(Inventory.lookupPart(aPro_SearchF_txtF.getText()));
                    aPro_A_Par_Table.setItems(tempList);
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
    void onActionmainMenuAddPro(ActionEvent event) throws IOException {
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
        
        autoGenProductId = Inventory.getAutoGenProductId();
        aPro_ID_txtF.setText("000"+autoGenProductId);
        
        aPro_A_Par_Table.setItems(Inventory.getAllParts());
        aPro_A_PartID_Col.setCellValueFactory(cellData->cellData.getValue().getId().asObject());
        aPro_A_PartName_Col.setCellValueFactory(cellData->cellData.getValue().getName());
        aPro_A_Inv_Col.setCellValueFactory(cellData->cellData.getValue().getStock().asObject());
        aPro_A_Price_Col.setCellValueFactory(cellData->cellData.getValue().getPrice().asString());
        
        //aPro_D_Pro_Table.setItems(Inventory.getAllParts());
        aPro_D_PartID_Col.setCellValueFactory(cellData->cellData.getValue().getId().asObject());
        aPro_D_PartName_Col.setCellValueFactory(cellData->cellData.getValue().getName());
        aPro_D_Inv_Col.setCellValueFactory(cellData->cellData.getValue().getStock().asObject());
        aPro_D_Price_Col.setCellValueFactory(cellData->cellData.getValue().getPrice().asString());
        
        sortTableView();
        
        aPro_SearchF_txtF.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(newValue.isEmpty())
            {
                aPro_A_Par_Table.setItems(getAllParts());
            }
        });
    }    
    
    private void sortTableView()
    {
        aPro_A_PartID_Col.setSortType(TableColumn.SortType.ASCENDING);
        aPro_A_Par_Table.getSortOrder().setAll(aPro_A_PartID_Col);
        aPro_D_PartID_Col.setSortType(TableColumn.SortType.ASCENDING);
        aPro_D_Par_Table.getSortOrder().setAll(aPro_D_PartID_Col);
    } 
    
}
