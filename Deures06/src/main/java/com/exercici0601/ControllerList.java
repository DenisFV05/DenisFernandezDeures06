package com.exercici0601;

import com.utils.UtilsViews;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ControllerList implements Initializable {

    @FXML
    private ImageView imgArrowBack; // flecha para retroceder

    @FXML
    private VBox list;

    @FXML
    private Label lblTitle;

    private String currentType; // tipo de lista, hay 3 opciones distintas

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            URL imageURL = getClass().getResource("/assets/images0601/arrow-back.png"); // cargar la imagen de la flecha
            Image image = new Image(imageURL.toExternalForm());
            imgArrowBack.setImage(image);
        } catch (Exception e) {
            System.err.println("Error loading back arrow image");
            e.printStackTrace();
        }
    }

    public void loadList(String type) { // cargar los datos del json
        currentType = type; 
        lblTitle.setText(type.substring(0, 1).toUpperCase() + type.substring(1));

        try {
            String jsonPath = switch (type) { // elegir entre las 3 opciones
                case "characters" -> "/assets/data/characters.json"; 
                case "games" -> "/assets/data/games.json";
                case "consoles" -> "/assets/data/consoles.json";
                default -> null;
            };
            if (jsonPath == null) {
                System.err.println("Tipo desconocido: " + type);
                return;
            }

            URL jsonFileURL = getClass().getResource(jsonPath);
            if (jsonFileURL == null) {
                System.err.println("No se encontró el archivo JSON: " + jsonPath);
                return;
            }
            Path path = Paths.get(jsonFileURL.toURI());
            String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(content); // json array del contenido

            list.getChildren().clear(); // lista de nodos hijos

            for (int i = 0; i < jsonArray.length(); i++) { // bucle recorre cada pj, game o consola 
                JSONObject item = jsonArray.getJSONObject(i);
                String name = item.optString("name", "Sin nombre");
                String imageName = item.optString("image", null);
                // solo coje foto y nombre en este caso
                HBox hbox = new HBox(10);
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setStyle("-fx-padding: 8; -fx-border-color: lightgray; -fx-border-width: 0 0 1 0;");

                if (imageName != null) {
                    try {
                        URL imageURL = getClass().getResource("/assets/images0601/" + imageName); // foto
                        if (imageURL != null) {
                            ImageView imageView = new ImageView(new Image(imageURL.toExternalForm()));
                            imageView.setFitWidth(40);
                            imageView.setFitHeight(40);
                            imageView.setPreserveRatio(true);
                            HBox.setMargin(imageView, new Insets(0, 10, 0, 0));
                            hbox.getChildren().add(imageView);
                        }
                    } catch (Exception e) {
                        System.err.println("Error cargando imagen: " + imageName);
                        e.printStackTrace();
                    }
                }

                Label label = new Label(name);
                label.setStyle("-fx-font-size: 16px;");
                label.setAlignment(Pos.CENTER_LEFT);
                hbox.getChildren().add(label);

                // Evento click para seleccionar y abrir detalle
                hbox.setOnMouseClicked(ev -> {
                    try {
                        // Resetear estilos de todos los ítems
                        list.getChildren().forEach(node ->
                            node.setStyle("-fx-padding: 8; -fx-border-color: lightgray; -fx-border-width: 0 0 1 0;"));

                        // Marcar seleccionado
                        hbox.setStyle("-fx-padding: 8; -fx-border-color: blue; -fx-border-width: 0 0 1 0; -fx-background-color: #d0e7ff;");

                        // Obtener controlador de la vista detalle según tipo
                        switch (currentType) {
                            case "characters" -> {
                                ControllerCharacterDetail ctrl = (ControllerCharacterDetail) UtilsViews.getController("ViewCharacterDetail");
                                if (ctrl != null) {
                                    ctrl.loadData(item);
                                    UtilsViews.setView("ViewCharacterDetail");
                                }
                            }
                            case "games" -> {
                                ControllerGameDetail ctrl = (ControllerGameDetail) UtilsViews.getController("ViewGameDetail");
                                if (ctrl != null) {
                                    ctrl.loadData(item);
                                    UtilsViews.setView("ViewGameDetail");
                                }
                            }
                            case "consoles" -> {
                                ControllerConsoleDetail ctrl = (ControllerConsoleDetail) UtilsViews.getController("ViewConsoleDetail");
                                if (ctrl != null) {
                                    ctrl.loadData(item);
                                    UtilsViews.setView("ViewConsoleDetail");
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                list.getChildren().add(hbox);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toViewMain(MouseEvent event) {
        UtilsViews.setViewAnimating("ViewMain");
    }
}
