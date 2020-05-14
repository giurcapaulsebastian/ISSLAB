package View;

import Utils.IObserver;
import Utils.IServer;
import domainDTO.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class LoginController{
    private IServer server;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    public void setServer(IServer server){
        this.server = server;
    }

    @FXML
    private void doLogIn(ActionEvent actionEvent){
        String usern = usernameTextField.getText();
        String passwd = passwordTextField.getText();

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../menuFXML.fxml"));
            Parent parent = loader.load();
            MenuController controller = loader.getController();
            User user = new User(usern,passwd);

            if(server.login(user,controller)==true){
                controller.setServer(server);
                controller.initTableView();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Main menu");
                stage.show();
            }else{
                MessageAlert.showErrorMessage("Wrong credentials! Try again.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
