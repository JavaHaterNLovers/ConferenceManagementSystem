package main;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import javafx.application.Application;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import vendor.Config;
import vendor.Container;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.getLoadWorker().stateProperty()
            .addListener((obs, oldValue, newValue) -> {
              if (newValue == State.SUCCEEDED) {
                System.out.println("finished loading");
              }
            }); // addListener()

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
        Config.initialize("src/main/resources/config/config.xml");
        Container.initialize("src/main/resources/config/services.xml");

//        UserRepository repo = (UserRepository) Container.getContainer().get("repo.user");
        Application.launch(args);
	}
}
