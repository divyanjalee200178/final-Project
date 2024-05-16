package lk.ijse.controller;

import javafx.beans.Observable;
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
import lk.ijse.model.tm.CustomerTm;
import lk.ijse.repository.CustomerRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
public class CustomerFormController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private AnchorPane rootNode;

    @FXML
     private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;


    public void initialize(){
        setCellValueFactory();
        loadAllCustomer();
        addRegex(txtId);
        addRegex(txtTel);
    }

    private void addRegex(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtId && !newValue.matches("^C.*$")){
                txtId.setStyle("-fx-focus-color:#f21e0f");
                txtId.clear();
            }else {
                txtId.setStyle("-fx-focus-color:#c4c1c0");
            }
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtTel && !newValue.matches("^\\d{1,10}$")){
                txtTel.setStyle("-fx-focus-color:#f21e0f");
                txtTel.clear();
            }else {
                txtTel.setStyle("-fx-focus-color:#c4c1c0");
            }
        });
    }

   private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
   }

   private void loadAllCustomer(){
       ObservableList<CustomerTm> obList= FXCollections.observableArrayList();

       try{
           List<Customer> customerList= CustomerRepo.getAll();
           for(Customer customer : customerList){
               CustomerTm tm=new CustomerTm(
                       customer.getId(),
                       customer.getName(),
                       customer.getAddress(),
                       customer.getEmail(),
                       customer.getTel()

               );
               obList.add(tm);
           }
           tblCustomer.setItems(obList);

       }catch (SQLException e){
           throw new RuntimeException(e);
       }
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
    void btnDeleteOnAction(ActionEvent event) {
        String id=txtId.getText();

        String sql="DELETE FROM Customer WHERE id=?";

        try{
            Connection connection=DbConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement(sql);
            pstm.setObject(1,id);

            if(pstm.executeUpdate()>0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted !").show();
                clearFields();
            }else{
                new Alert(Alert.AlertType.INFORMATION,"Customer id can't be found !");

            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    @FXML
    void btnExitOnAction(ActionEvent event) throws IOException{
        AnchorPane anchorPane=FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage=(Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnSavetOnAction(ActionEvent event) {
        String id=txtId.getText();
        String name=txtName.getText();
        String address=txtAddress.getText();
        String email=txtEmail.getText();
        String tel=txtTel.getText();

        String sql="INSERT INTO Customer VALUES(?,?,?,?,?)";

        try{
            Connection connection= DbConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement(sql);
            pstm=connection.prepareStatement(sql);
            pstm.setObject(1,id);
            pstm.setObject(2,name);
            pstm.setObject(3,address);
            pstm.setObject(4,email);
            pstm.setObject(5,tel);

            boolean isSaved=pstm.executeUpdate()>0;
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer saved !").show();
            }
            }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id=txtId.getText();

        String sql="SELECT * FROM Customer WHERE id=?";
        try{
            Connection connection=DbConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement(sql);
            pstm.setObject(1,id);

            ResultSet resultSet=pstm.executeQuery();
            if(resultSet.next()){
                String name=resultSet.getString(2);
                String address=resultSet.getString(3);
                String email=resultSet.getString(4);
                String tel=resultSet.getString(5);

                txtName.setText(name);
                txtAddress.setText(address);
                txtEmail.setText(email);
                txtTel.setText(tel);

            }else{
                new Alert(Alert.AlertType.INFORMATION, "Customer id not found !");

            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id=txtId.getText();
        String name=txtName.getText();
        String address=txtAddress.getText();
        String email=txtEmail.getText();
        String tel=txtTel.getText();


        //String sql = "UPDATE Customer SET name = ?, address = ?, email = ?, number=? WHERE id = ?";
        String sql = "UPDATE Customer SET name = ?, number = ?, address = ?, email=? WHERE id = ?";
        try {
            Connection connection=DbConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement(sql);
            pstm.setObject(1,name);
            pstm.setObject(2,tel);
            pstm.setObject(3,address);
            pstm.setObject(4,email);
            pstm.setObject(5,id);

            if(pstm.executeUpdate()>0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer updated !").show();
                clearFields();
            }
            }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    private void clearFields(){
        txtId.setText("");
        txtName.setText("");
        txtTel.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
    }

    @FXML
    void txtAddressOnAction(ActionEvent event) {
        txtEmail.requestFocus();
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {

    }

    @FXML
    void txtIdOnAction(ActionEvent event) {
        txtName.requestFocus();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtTel.requestFocus();
    }

    @FXML
    void txtTelOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }


}
