package util;

import java.io.IOException;
import java.util.function.Consumer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UIUtil
{
    /**
     * Aceasta clasa va fi folosita doar in context static.
     */
    private UIUtil() {}

    /**
     * Afiseaza un mesaj informativ intr-un popup.
     *
     * @param header
     *   Mesajul din header.
     * @param msg
     *   Mesajul propriu-zis.
     */
    public static void showMessage(String header, String msg)
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(header);
        alert.setHeaderText("");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Afiseaza un mesaj de confirmare si executa o actiune.
     *
     * @param msg
     *   Mesajul care va fi afisat.
     * @param fct
     *   Functia care se executa.
     */
    public static void showConfirmation(String msg, Consumer<? super ButtonType> fct) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmati Actiunea");
        alert.setHeaderText("");
        alert.setContentText(msg);
        alert.showAndWait().ifPresent(fct);
    }

    /**
     * Afiseaza un mesaj de eroare.
     *
     * @param msg
     *   Mesajul de eroare.
     */
    public static void errorAlert(String msg)
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText("");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Deschide o noua fereastra dintr-un fisier fxml cu un titlu dat.
     *
     * @param fileName
     * @param title
     * @return
     */
    public static Object openWindow(String fileName, String title) {
        return UIUtil.openWindow(fileName, title, Modality.APPLICATION_MODAL);
    }

    /**
     * Deschide o noua fereastra dintr-un fisier fxml cu un titlu si modalitate data.
     * @param fileName
     * @param title
     * @param m
     * @return
     */
    public static Object openWindow(String fileName, String title, Modality m) {
        FXMLLoader loader = new FXMLLoader(UIUtil.class.getResource(fileName));

        try {
            Parent node = loader.load();

            UIUtil.openWindow(node, title, m);

            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Deschide o fereastra cu un nod parinte dat, un titlu si o modalitate.
     *
     * @param node
     * @param title
     * @param m
     */
    public static void openWindow(Parent node, String title, Modality m) {
        Scene scene = new Scene(node);

        scene.getStylesheets().add(UIUtil.class.getResource("/fxml/style.css").toExternalForm());

        Stage stage = new Stage();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.initModality(m);
        stage.show();
    }
}
