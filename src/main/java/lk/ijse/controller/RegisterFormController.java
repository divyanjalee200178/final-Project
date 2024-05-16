package lk.ijse.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RegisterFormController {
    @FXML
    private Button btnExit;
    @FXML
    private TextField txtname;
    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSave;
    @FXML
    private TextField txtUserID;
    public AnchorPane rootNode;

   public void initialize(){
       addRegex(txtUserID);
   }

    private void addRegex(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (textField == txtUserID && !newValue.matches("^U.*$")){
                txtUserID.setStyle("-fx-focus-color:#f21e0f");
                txtUserID.clear();
            }else {
                txtUserID.setStyle("-fx-focus-color:#c4c1c0");
            }
        });


    }
    @FXML
    void btnExitOnAction(ActionEvent event) throws IOException {
        navigateToTheLoginForm();
    }
    private void navigateToTheLoginForm() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String userId = txtUserID.getText();
        String name = txtname.getText();
        String password = txtPassword.getText();

        try {
            boolean isSaved = saveUser(userId, name, password);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean saveUser(String userId, String name, String password) throws SQLException {
        String sql = "INSERT INTO users VALUES(?, ?, ?)";

       /* DbConnection dbConnection = DbConnection.getInstance();
        Connection connection = dbConnection.getConnection();*/

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);
        pstm.setObject(2, name);
        pstm.setObject(3, password);

        return pstm.executeUpdate() > 0;
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtUserID.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        txtname.requestFocus();
    }

    @FXML
    void txtUserIdOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

}



