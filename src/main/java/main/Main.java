package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import controller.LoginCtrl;
import domain.User.UserRole;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.UserService;
import util.UIUtil;

public class Main extends Application
{
    private static ApplicationContext container;

    private void openLogin() {
        LoginCtrl ctrl = (LoginCtrl) UIUtil.openWindow("/fxml/LoginForm.fxml", "Login", Modality.NONE);

        Button loginBtn = ctrl.getLoginBtn();
        loginBtn.getScene().addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                loginBtn.fire();
                ev.consume();
            }
        });

        UIUtil.openWindow("/fxml/CreateConfForm.fxml", "Login", Modality.NONE);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        WebView browser = new WebView();
//        WebEngine webEngine = browser.getEngine();
//        webEngine.getLoadWorker().stateProperty()
//            .addListener((obs, oldValue, newValue) -> {
//              if (newValue == State.SUCCEEDED) {
//                System.out.println("finished loading");
//              }
//            });
//
//        InputStream stream = getClass().getResourceAsStream("/html/index.html");
//
//        webEngine.loadContent(IOUtils.toString(stream));

        // Se va creea o functie asemanatoare cu aceasta pentru fiecare fereastra nou adaugata.
        // Ca sa testati, se pot comenta unele functii si laste aici sa fie executata doar functia voastra.
        openLogin();
    }

    /**
     * Initializeaza aplicatia.
     *
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception {
	    container = new ClassPathXmlApplicationContext("services.xml");

	    UserService userService = (UserService) container.getBean("service.user");
	    if (userService.all().isEmpty()) {
	        userService.add("admin@admin.com", "admin", "Super", "Admin", UserRole.superAdmin);
	    }
//	    BaseRepository<Conference> repo = (BaseRepository<Conference>) Main.getContainer().getBean("repo.conference");
//	    repo.save(new Conference("Conferinta 1", userService.all().get(0), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance()));

	    System.out.println(userService.all());
	    System.out.println(userService.size());
//	    userService.delete(user);
//	    System.out.println(userService.all());

        Application.launch(args);
	}

    public static ApplicationContext getContainer() {
        return container;
    }
}
