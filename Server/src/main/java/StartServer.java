import domainDTO.User;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StartServer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Properties props = new Properties();
        props.load(new FileReader("D:\\Faculta\\GymApp\\Server\\src\\main\\resources\\db.config"));

        ApplicationContext context = new ClassPathXmlApplicationContext("/XMLApplication.xml");

    }
}
