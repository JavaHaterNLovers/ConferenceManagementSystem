package main;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.application.Application;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

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

        Application.launch(args);
	}

    public static ApplicationContext getContainer() {
        return container;
    }
}
