package com.exercici0602;

import com.utils.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {

    final int WINDOW_WIDTH = 600;
    final int WINDOW_HEIGHT = 400;
    final int MIN_WIDTH = 600;
    final int MIN_HEIGHT = 400;

    @Override
    public void start(Stage stage) throws Exception {

        // Cambia el estilo general de fuente
        UtilsViews.parentContainer.setStyle("-fx-font: 14 'Segoe UI';");

        // Carga las vistas
        UtilsViews.addView(getClass(), "ViewList", "/assets/viewPokeList.fxml");
        UtilsViews.addView(getClass(), "ViewCard", "/assets/viewPokeCard.fxml");
        UtilsViews.addView(getClass(), "ViewForm", "/assets/viewPokeForm.fxml");

        // Escena y ajustes
        Scene scene = new Scene(UtilsViews.parentContainer);
        stage.setScene(scene);
        stage.setTitle("JavaFX App");
        stage.setMinWidth(MIN_WIDTH);
        stage.setWidth(WINDOW_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
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

        // Carga la lista inicial
        ControllerPokeList crtl = (ControllerPokeList) UtilsViews.getController("ViewList");
        crtl.loadList();

        // Añadir icono si no es macOS
        if (!System.getProperty("os.name").contains("Mac")) {
            Image icon = new Image("file:icons/icon.png");
            stage.getIcons().add(icon);
        }
    }

    @Override
    public void stop() throws Exception {
        AppData db = AppData.getInstance();
        db.close();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
