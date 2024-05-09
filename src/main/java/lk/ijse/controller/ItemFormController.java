package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Item;
import lk.ijse.model.tm.CustomerTm;
import lk.ijse.model.tm.ItemTm;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.ItemRepo;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ItemFormController {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<ItemTm, String> colCode;

    @FXML
    private TableColumn<ItemTm, String> colDesc;

    @FXML
    private TableColumn<ItemTm,String > colLoacation;

    @FXML
    private TableColumn<ItemTm, Integer> colQty;

    @FXML
    private TableColumn<ItemTm, Double> colUnitPrice;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;


    public void initialize() {
        setCellValueFactory();
        loadAllItem();

        addRegex(txtCode);
        addRegex(txtUnitPrice);
        addRegex(txtQty);

    }

    private void addRegex(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtCode && !newValue.matches("^I.*$")){
                new Alert(Alert.AlertType.INFORMATION,"You can only use 'I'").show();
                txtCode.clear();
            }
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtUnitPrice && !newValue.matches("^\\d+(\\.\\d{1,2})?$")){
                new Alert(Alert.AlertType.INFORMATION,"you can only use 'double'").show();
                txtUnitPrice.clear();
            }
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtQty && !newValue.matches("^-?\\d+$")){
                new Alert(Alert.AlertType.INFORMATION,"you can only use 'int'").show();
                txtQty.clear();
            }
        });
    }


    private void loadAllItem(){
        ObservableList<ItemTm> obList= FXCollections.observableArrayList();

        try{
            List<Item> ItemList= ItemRepo.getAllItem();
            for(Item item : ItemList){
                ItemTm tm=new ItemTm(
                        item.getCode(),
                        item.getDescription(),
                        item.getUnitPrice(),
                        item.getQtyOnHand(),
                        item.getLocation()

                );
                obList.add(tm);
            }
            tblItem.setItems(obList);

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colLoacation.setCellValueFactory(new PropertyValueFactory<>("location"));
    }



    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields(){
        txtCode.setText("");
        txtDescription.setText("");
        txtUnitPrice.setText("");
        txtQty.setText("");
        txtLocation.setText("");
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String code=txtCode.getText();

        try{
            boolean isDeleted= ItemRepo.delete(code);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item deleted !").show();
            }
            }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String code=txtCode.getText();
        String description=txtDescription.getText();
        double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int qty=Integer.parseInt(txtQty.getText());
        String location=txtLocation.getText();

        String sql="INSERT INTO Item VALUES(?,?,?,?,?)";

        try{
            Connection connection= DbConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement(sql);
            pstm=connection.prepareStatement(sql);
            pstm.setObject(1,code);
            pstm.setObject(2,description);
            pstm.setObject(3,unitPrice);
            pstm.setObject(4,qty);
            pstm.setObject(5,location);

            boolean isSaved=pstm.executeUpdate()>0;
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item saved Succsessfully!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }



    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {
        String code=txtCode.getText();

        Item item=ItemRepo.searchByCode(code);
        if(item !=null){
            txtCode.setText(item.getCode());
            txtDescription.setText(item.getDescription());
            txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            txtQty.setText(String.valueOf(item.getQtyOnHand()));
            txtLocation.setText(item.getLocation());
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Item not found");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        String location = txtLocation.getText();

        Item item = new Item(code, description, qty, unitPrice, location);

        try{
            boolean isUpdated=ItemRepo.update(item);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Item updated !").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane= FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage=(Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }


    @FXML
    void btnExitOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane= FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage=(Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();

    }

}
