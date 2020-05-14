import Utils.IServer;
import View.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("./XMLClient.xml");
        IServer server = (IServer)context.getBean("ServerImplementation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginFXML.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.setServer(server);


        stage.setTitle("LOGIN WINDOW");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
