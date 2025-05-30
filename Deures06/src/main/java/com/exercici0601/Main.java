package com.exercici0601;

import com.utils.UtilsViews;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {

    final int WINDOW_WIDTH = 300;
    final int WINDOW_HEIGHT = 400;

    @Override
    public void start(Stage stage) throws Exception {

        UtilsViews.parentContainer.setStyle("-fx-font: 14 arial;");
        UtilsViews.addView(getClass(), "ViewMain", "/assets/viewMain.fxml");
        UtilsViews.addView(getClass(), "ViewCharacters", "/assets/viewList.fxml");
        UtilsViews.addView(getClass(), "ViewGames", "/assets/viewList.fxml");
        UtilsViews.addView(getClass(), "ViewConsoles", "/assets/viewList.fxml");

         // Cargar vistas de detalle
        UtilsViews.addView(getClass(), "ViewCharacterDetail",  "/assets/character_detail.fxml");
        UtilsViews.addView(getClass(), "ViewGameDetail",       "/assets/game_detail.fxml");
        UtilsViews.addView(getClass(), "ViewConsoleDetail",    "/assets/console_detail.fxml");

        Scene scene = new Scene(UtilsViews.parentContainer);
        stage.setScene(scene);
        stage.setTitle("Nintendo DB");
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);

        // Diálogo de confirmación al cerrar
        stage.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Salir");
            alert.setHeaderText("¿Seguro que quieres cerrar?");
            alert.setContentText("Esto cerrará la aplicación completamente.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() != ButtonType.OK) {
                e.consume(); // Cancela el cierre
            } else {
                Platform.exit();
                System.exit(0); // Forzamos cierre limpio
            }
        });

        stage.show();

        if (!System.getProperty("os.name").contains("Mac")) {
            Image icon = new Image("file:icons/icon.png");
            stage.getIcons().add(icon);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
