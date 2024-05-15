package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardFormController {

    @FXML
    private Button btnBack1;

    @FXML
    private Button btnCus;

    @FXML
    private Button btnDash;

    @FXML
    private Button btnEmp;

    @FXML
    private Button btnItem;

    @FXML
    private Button btnOrder;

    @FXML
    private Button btnPay;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private AnchorPane miniDash;
    @FXML
    private AnchorPane rootItem;

    @FXML
    private Label lblItemCount;

    @FXML
    private Label lblEmployeeCount;

    @FXML
    private Label lblCustomerCount;

    @FXML
    private Label lblOrderCount;

    @FXML
    private AnchorPane root;

    private int ItemCount;
    private int EmployeeCount;
    private  int CustomerCount;
    private int OrderCount;
    public void initialize() {
        tryItem();
        tryEmployee();
        tryCustomer();
        tryOrders();
    }
    public void tryItem() {
        try {
            ItemCount = getItemCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setItemCount(ItemCount);
    }
private void tryEmployee(){
        try {
            EmployeeCount = getEmployeeCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setEmployeeCount(EmployeeCount);
    }
    private void setItemCount(int ItemCount) {
        lblItemCount.setText(String.valueOf(ItemCount));
    }
    private int getItemCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS ItemCount FROM Item";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("ItemCount");
        }
        return 0;
    }

    private void setEmployeeCount(int EmployeeCount) {
        lblEmployeeCount.setText(String.valueOf(EmployeeCount));
    }
    private int getEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS EmployeeCount FROM Employee";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("EmployeeCount");
        }
        return 0;
    }

    private void tryCustomer(){
        try {
            CustomerCount = getCustomerCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setCustomerCount(CustomerCount);
    }
    private void setCustomerCount(int CustomerCount) {
        lblCustomerCount.setText(String.valueOf(CustomerCount));
    }
    private int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS CustomerCount FROM Customer";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("CustomerCount");
        }
        return 0;
    }

    private void tryOrders(){
        try {
            OrderCount = getOrderCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setOrderCount(OrderCount);
    }
    private void setOrderCount(int OrderCount) {
        lblOrderCount.setText(String.valueOf(OrderCount));
    }
    private int getOrderCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS OrderCount FROM Orders";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("OrderCount");
        }
        return 0;
    }


    @FXML
    void btnBack1OnAction(ActionEvent event) throws IOException {
        navigateToTheLoginForm();
    }
    private void navigateToTheLoginForm() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }

    @FXML
    void btnCusOnAction(ActionEvent event) throws IOException {
        navigateToTheCustomerForm();
    }

    private void navigateToTheCustomerForm() throws IOException {
        try {
            AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));
            miniDash.getChildren().clear();
            miniDash.getChildren().add(rootNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException {
        navigateToThePlaceOrderForm();
    }

    private void navigateToThePlaceOrderForm() throws IOException {
        try {
            AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/placeorder_form.fxml"));
            miniDash.getChildren().clear();
            miniDash.getChildren().add(rootNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDashOnAction(ActionEvent event) throws IOException {
            navigateToTheDashboardrForm();
    }

    private void navigateToTheDashboardrForm() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    @FXML
    void btnEmpOnAction(ActionEvent event) throws IOException {
            navigateToTheEmployeeForm();
    }

    private void navigateToTheEmployeeForm() throws IOException {
        try {
            AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));
            miniDash.getChildren().clear();
            miniDash.getChildren().add(rootNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnItemOnAction(ActionEvent event) throws IOException {
            navigateToTheItemForm();
    }

    private void navigateToTheItemForm() throws IOException {
        /*AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/item_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Item Form");*/
        try {
            AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/item_form.fxml"));
            miniDash.getChildren().clear();
            miniDash.getChildren().add(rootNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        navigateToTheSupplierForm();
    }


    private void navigateToTheSupplierForm() throws IOException {
        try {
            AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));
            miniDash.getChildren().clear();
            miniDash.getChildren().add(rootNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPayOnAction(ActionEvent event) throws IOException {
            navigateToThePaymentForm();
    }

    private void navigateToThePaymentForm() throws IOException {
        try {
            AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/payment_form.fxml"));
            miniDash.getChildren().clear();
            miniDash.getChildren().add(rootNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

