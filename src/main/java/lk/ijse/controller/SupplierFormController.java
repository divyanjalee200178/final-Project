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
import lk.ijse.model.Employee;
import lk.ijse.model.Supplier;
import lk.ijse.model.tm.CustomerTm;
import lk.ijse.model.tm.EmployeeTm;
import lk.ijse.model.tm.SupplierTm;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.EmployeeRepo;
import lk.ijse.repository.SupplierRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.input.KeyEvent;
import lk.ijse.util.Regex;

public class SupplierFormController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnClear;

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
    private TableView<SupplierTm> tblSupplier;

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

    @FXML
    private Button btnEnter;

    @FXML
    private TextField txtTelSearch;

    public void initialize(){
        setCellValueFactory();
        loadAllSupplier();
        addRegex(txtId);
        addRegex(txtTel);
    }

    private void addRegex(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtId && !newValue.matches("^S.*$")){
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
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadAllSupplier(){
        ObservableList<SupplierTm> obList= FXCollections.observableArrayList();

        try{
            List<Supplier> supplierList= SupplierRepo.getAll();
            for(Supplier supplier : supplierList){
                SupplierTm tm=new SupplierTm(
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getAddress(),
                        supplier.getEmail(),
                        supplier.getTel()

                );
                obList.add(tm);
            }
            tblSupplier.setItems(obList);

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id=txtId.getText();

        String sql="DELETE FROM Supplier WHERE id=?";

        try{
            Connection connection=DbConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement(sql);
            pstm.setObject(1,id);

            if(pstm.executeUpdate()>0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier deleted !").show();
                clearFields();
            }else{
                new Alert(Alert.AlertType.INFORMATION,"Supplier id can't be found !");

            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    @FXML
    void btnExitOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String tel = txtTel.getText();

        String sql = "INSERT INTO Supplier VALUES(?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm = connection.prepareStatement(sql);
            pstm.setObject(1, id);
            pstm.setObject(2, name);
            pstm.setObject(3, address);
            pstm.setObject(4, email);
            pstm.setObject(5, tel);

            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier saved !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }



    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {
        String id=txtId.getText();

        Supplier supplier=SupplierRepo.searchById(id);
        if(supplier !=null){
            txtId.setText(supplier.getId());
            txtName.setText(supplier.getName());
            txtAddress.setText(supplier.getAddress());
            txtEmail.setText(supplier.getEmail());
            txtTel.setText(supplier.getTel());
        }else {
            new Alert(Alert.AlertType.INFORMATION, "Supplier not found !").show();

        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id=txtId.getText();
        String name=txtName.getText();
        String address=txtAddress.getText();
        String email=txtEmail.getText();
        String tel=txtTel.getText();


        //String sql = "UPDATE Supplier SET name = ?, address = ?, email = ?, number=? WHERE id = ?";
        String sql= "UPDATE Supplier SET name = ?, number = ?, address = ?, email=? WHERE id = ?";
        try {
            Connection connection=DbConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement(sql);
            pstm.setObject(1,name);
            pstm.setObject(2,tel);
            pstm.setObject(3,address);
            pstm.setObject(4,email);
            pstm.setObject(5,id);

            if(pstm.executeUpdate()>0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated !").show();
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

   /* @FXML
    void txtSupplierIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.ID,txtId);
    }*/

    @FXML
    void txtAddressOnAction(ActionEvent event) {
        txtEmail.requestFocus();
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {
        txtTel.requestFocus();
    }

    @FXML
    void txtIdOnAction(ActionEvent event) {
        txtName.requestFocus();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

    @FXML
    void txtTelOnAction(ActionEvent event) {

    }

    @FXML
    void btnEnterOnAction(ActionEvent event) throws SQLException {
        String tele=txtTelSearch.getText();

        Supplier supplier=SupplierRepo.searchByNumber(tele);
        if(supplier !=null){
            txtId.setText(supplier.getId());
            txtName.setText(supplier.getName());
            txtAddress.setText(supplier.getAddress());
            txtEmail.setText(supplier.getEmail());
            txtTel.setText(supplier.getTel());
        }else {
            new Alert(Alert.AlertType.INFORMATION, "Supplier not found !").show();

        }

    }
}
