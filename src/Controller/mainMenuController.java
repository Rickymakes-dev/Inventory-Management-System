/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;
import static Model.Inventory.deletePart;
import static Model.Inventory.deleteProduct;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
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
 *
 * @author rickychau
 */
public class mainMenuController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    public static Part selectedPart;
    public static Product selectedProduct;
    
    @FXML
    private TextField main_Par_SearchF_txtF;
    
    @FXML
    private TableView<Part> main_Par_Table;

    @FXML
    private TableColumn<Part, Integer> main_Par_PartID_Col;

    @FXML
    private TableColumn<Part, String> main_Par_PartName_Col;

    @FXML
    private TableColumn<Part, Integer> main_Par_Inv_Col;

    @FXML
    private TableColumn<Part, String> main_Par_Price_Col;

    @FXML
    private TextField main_Pro_SearchF_txtF;
    
    @FXML
    private TableView<Product> main_Pro_Table;

    @FXML
    private TableColumn<Product, Integer> main_Pro_PartID_Col;

    @FXML
    private TableColumn<Product, String> main_Pro_PartName_Col;

    @FXML
    private TableColumn<Product, Integer> main_Pro_Inv_Col;

    @FXML
    private TableColumn<Product, String> main_Pro_Price_Col;

    @FXML
    private Button main_Par_Search_btn;
    
    @FXML
    private Button main_Par_Add_btn;

    @FXML
    private Button main_Par_Modify_btn;

    @FXML
    private Button main_Par_Delete_btn;

    @FXML
    private Button main_Pro_Search_btn;
    
    @FXML
    private Button main_Pro_Add_btn;

    @FXML
    private Button main_Pro_Modiy_btn;

    @FXML
    private Button main_Pro_Delelte_btn;

    @FXML
    private Button main_Exit_btn;

    @FXML
    void onActionAddPartMenu(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/addParts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    void onActionModifyMenu(ActionEvent event) throws IOException 
    {
        selectedPart = main_Par_Table.getSelectionModel().getSelectedItem();
        
        if(selectedPart == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selection Error");
            alert.setHeaderText("No item have been selected for edit.");
            alert.setContentText("Please click on item to select for modification!");

            alert.showAndWait();
        }
        else
        {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/modParts.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
    @FXML
    void onActionAddProductsMenu(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/addProducts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    void onActionModifyProductsMenu(ActionEvent event) throws IOException 
    {
        selectedProduct = main_Pro_Table.getSelectionModel().getSelectedItem();
        
        if(selectedProduct == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selection Error");
            alert.setHeaderText("No item have been selected for edit.");
            alert.setContentText("Please click on item to select for modification!");

            alert.showAndWait();
        }
        else
        {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/modProducts.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionDeleteParts(ActionEvent event) 
    {
        try
        {
            Part selected = main_Par_Table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete?");
            alert.setHeaderText("Do you wish to delete "+ selected.getName().get()+"?");
            alert.setContentText("This action is permanent!");
            Optional<ButtonType> answer = alert.showAndWait();

            if(answer.isPresent() && answer.get() == ButtonType.OK)
            {
                Inventory.setOnQueuePart(selected.getId().getValue());
                deletePart(selected);
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
    void onActionDeleteProducts(ActionEvent event) 
    {
        try
        {
            Product selected = main_Pro_Table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete?");
            alert.setHeaderText("Do you wish to delete "+ selected.getName().get()+"?");
            alert.setContentText("This action is permanent!");
            Optional<ButtonType> answer = alert.showAndWait();

            if(answer.isPresent() && answer.get() == ButtonType.OK)
            {
                Inventory.setOnQueuePart(selected.getId().getValue());
                deleteProduct(selected);
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
    void onActionMainExit(ActionEvent event) 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you wish to exit the program?");
        Optional<ButtonType> answer = alert.showAndWait();
        
        if(answer.isPresent() && answer.get() == ButtonType.OK)
        {
        System.exit(0);
        }
    }

    @FXML
    void onActionSearchParts(ActionEvent event)
    {
        if(main_Par_SearchF_txtF.getText().isEmpty())
        {
            main_Par_Table.setItems(getAllParts());
        }
        else
        {
            if(Inventory.isNumeric(main_Par_SearchF_txtF.getText()))
            {
                if(Inventory.lookupPart(Integer.parseInt(main_Par_SearchF_txtF.getText())) != null)
                {
                    ObservableList<Part> tempList = FXCollections.observableArrayList();
                    tempList.add(Inventory.lookupPart(Integer.parseInt(main_Par_SearchF_txtF.getText())));
                    main_Par_Table.setItems(tempList);
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
                if(Inventory.lookupPart(main_Par_SearchF_txtF.getText()) != null)
                {
                    ObservableList<Part> tempList = FXCollections.observableArrayList();
                    tempList.add(Inventory.lookupPart(main_Par_SearchF_txtF.getText()));
                    main_Par_Table.setItems(tempList);
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
    void onActionSearchProducts(ActionEvent event) 
    {
        if(main_Pro_SearchF_txtF.getText().isEmpty())
        {
            main_Pro_Table.setItems(getAllProducts());
        }
        else
        {
            if(Inventory.isNumeric(main_Pro_SearchF_txtF.getText()))
            {
                if(Inventory.lookupProduct(Integer.parseInt(main_Pro_SearchF_txtF.getText())) != null)
                {
                    ObservableList<Product> tempList = FXCollections.observableArrayList();
                    tempList.add(Inventory.lookupProduct(Integer.parseInt(main_Pro_SearchF_txtF.getText())));
                    main_Pro_Table.setItems(tempList);
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
                if(Inventory.lookupProduct(main_Pro_SearchF_txtF.getText()) != null)
                {
                    ObservableList<Product> tempList = FXCollections.observableArrayList();
                    tempList.add(Inventory.lookupProduct(main_Pro_SearchF_txtF.getText()));
                    main_Pro_Table.setItems(tempList);
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        main_Par_Table.setItems(Inventory.getAllParts());
        main_Par_PartID_Col.setCellValueFactory(cellData->cellData.getValue().getId().asObject());
        main_Par_PartName_Col.setCellValueFactory(cellData->cellData.getValue().getName());
        main_Par_Inv_Col.setCellValueFactory(cellData->cellData.getValue().getStock().asObject());
        main_Par_Price_Col.setCellValueFactory(cellData->cellData.getValue().getPrice().asString());
        
        main_Pro_Table.setItems(Inventory.getAllProducts());
        main_Pro_PartID_Col.setCellValueFactory(cellData->cellData.getValue().getId().asObject());
        main_Pro_PartName_Col.setCellValueFactory(cellData->cellData.getValue().getName());
        main_Pro_Inv_Col.setCellValueFactory(cellData->cellData.getValue().getStock().asObject());
        main_Pro_Price_Col.setCellValueFactory(cellData->cellData.getValue().getPrice().asString());
        
        sortTableView();
        
        main_Par_SearchF_txtF.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(newValue.isEmpty())
            {
                main_Par_Table.setItems(getAllParts());
            }
        });
        
        main_Pro_SearchF_txtF.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(newValue.isEmpty())
            {
                main_Pro_Table.setItems(getAllProducts());
            }
        });
    }

    private void sortTableView()
    {
        main_Par_PartID_Col.setSortType(TableColumn.SortType.ASCENDING);
        main_Par_Table.getSortOrder().setAll(main_Par_PartID_Col);
        main_Pro_PartID_Col.setSortType(TableColumn.SortType.ASCENDING);
        main_Pro_Table.getSortOrder().setAll(main_Pro_PartID_Col);
    } 
}
