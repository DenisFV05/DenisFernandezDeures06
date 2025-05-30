package com.exercici0602;

import com.utils.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;

public class ControllerPokeList {

    @FXML
    private VBox list = new VBox();

    public void loadList() {
        AppData db = AppData.getInstance();
        db.connect("./data/pokemons.sqlite");

        // select por number y ponerlo en la lista
        ArrayList<HashMap<String, Object>> llistaPokemons = db.query("SELECT * FROM pokemons ORDER BY number;");
        try {
            setList(llistaPokemons);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void setList(ArrayList<HashMap<String, Object>> llistaPokemons) throws IOException {

        URL resource = this.getClass().getResource("/assets/viewPokeItem.fxml");

        // limpiar lo que hay existente
        list.getChildren().clear();

        // Iterar elementos de Jsonarray
        for (int i = 0; i < llistaPokemons.size(); i++) {
            // Obtenir l'objecte JSON individual del pokemon
            HashMap<String, Object> pokemon = llistaPokemons.get(i);

            // info de cada pokemon
            int number = (int) pokemon.get("number");
            String name = (String) pokemon.get("name");
            String type = (String) pokemon.get("type");
            String imagePath = (String) pokemon.get("image");

            // cargar la template del fxml
            FXMLLoader loader = new FXMLLoader(resource);
            Parent itemTemplate = loader.load();
            ControllerPokeItem itemController = loader.getController();

            // Assignar los valores
            itemController.setNumber(number);
            itemController.setTitle(name);
            itemController.setSubtitle(type);
            itemController.setImatge(imagePath);

            // Añadirlo a la template
            list.getChildren().add(itemTemplate);
        }
    }

    @FXML
    public void addPokemon(ActionEvent event) {
        ControllerPokeForm ctrl = (ControllerPokeForm) UtilsViews.getController("ViewForm");
        ctrl.setStatus(ControllerPokeForm.STATUS_ADD, -1);
        UtilsViews.setViewAnimating("ViewForm");
    }
}