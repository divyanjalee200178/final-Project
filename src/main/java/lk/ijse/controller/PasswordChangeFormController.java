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
import lk.ijse.model.Employee;
import lk.ijse.model.User;
import lk.ijse.repository.EmployeeRepo;
import lk.ijse.repository.UserRepo;

import java.io.IOException;
import java.sql.SQLException;

public class PasswordChangeFormController {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnUpdate;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtConfirmPass;

    @FXML
    private TextField txtNewPass;

    @FXML
    private TextField txtUserId;

    @FXML
    void btnExitOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane= FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage=(Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id=txtUserId.getText();
        String name=txtConfirmPass.getText();
        String pw=txtNewPass.getText();


        User user=new User(id,name,pw);

        try{
            boolean isUpdated= UserRepo.update(user);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "User updated !").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

}
