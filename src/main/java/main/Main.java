package main;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import domain.User;
import domain.User.UserRole;
import javafx.application.Application;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import repo.BaseRepository;

public class Main extends Application
{
    private static ApplicationContext container;

    @Override
    public void start(Stage primaryStage) throws Exception {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.getLoadWorker().stateProperty()
            .addListener((obs, oldValue, newValue) -> {
              if (newValue == State.SUCCEEDED) {
                System.out.println("finished loading");
              }
            });

        InputStream stream = getClass().getResourceAsStream("/html/index.html");

        webEngine.loadContent(IOUtils.toString(stream));

        Scene scene = new Scene(browser, 300, 250);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Initializeaza aplicatia.
     *
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception {
	    container = new ClassPathXmlApplicationContext("services.xml");

	    BaseRepository<User> userRepo = (BaseRepository<User>) container.getBean("repo.user");
	    if (userRepo.all().isEmpty()) {
	        userRepo.save(new User("nume", "nume", "pass", UserRole.admin));
	    }
	    System.out.println(userRepo.all());
//	    userRepo.delete(userRepo.all().get(0));
//	    System.out.println(userRepo.all());

        Application.launch(args);
	}

    public static ApplicationContext getContainer() {
        return container;
    }
}
