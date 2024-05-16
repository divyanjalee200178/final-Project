package lk.ijse.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DB;
import lk.ijse.db.DbConnection;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static javafx.fxml.FXMLLoader.load;
import static lk.ijse.db.DB.Password;


public class LoginFormController {
@FXML
private Button btnLogin;

@FXML
private Button btnRegister;

@FXML
private TextField txtPassword;

@FXML
private TextField txtUserID;
@FXML
private AnchorPane rootNode;

@FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
    String userID = txtUserID.getText();
    String password = txtPassword.getText();

    try {
        checkCredential(userID, password);
    } catch (SQLException e) {
        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
    }
}

   private void checkCredential(String userId, String password) throws SQLException, IOException {
        String sql = "SELECT userId, password FROM users WHERE userId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String dbPw = resultSet.getString("password");

            if(password.equals(dbPw)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!").show();
            }

        } else {
            new Alert(Alert.AlertType.INFORMATION, "sorry! user id can't be find!").show();
        }
    }



    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }


    public void btnRegisterOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/register_form.fxml"));
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        Scene scene = new Scene(rootNode);
        //Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Registration Form");
        stage.show();
    }






    @FXML
    void txtUsernameOnAction(ActionEvent event) {

    }

    void txtPassword(ActionEvent event){

    }

    public void txtPasswordOnAction(MouseEvent mouseEvent) {
    }

    public void hypForgetOnAction(ActionEvent actionEvent) throws IOException {
        navigateToThePasswordChangeForm();
    }

    private void navigateToThePasswordChangeForm() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/Passwordchange_form.fxml"));
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        Scene scene = new Scene(rootNode);
        //Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Change Password Form");
        stage.show();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void txtUserIdOnAction(ActionEvent event) {

    txtPassword.requestFocus();
    }
}



