package com.exercici0601;

import com.utils.*;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class ControllerMain {

    @FXML
    private void toViewCharacters(MouseEvent event) {
        ControllerList ctrlList = (ControllerList) UtilsViews.getController("ViewCharacters");
        ctrlList.loadList("characters");
        UtilsViews.setViewAnimating("ViewCharacters");
    }

    @FXML
    private void toViewGames(MouseEvent event) {
        ControllerList ctrlList = (ControllerList) UtilsViews.getController("ViewGames");
        ctrlList.loadList("games");
        UtilsViews.setViewAnimating("ViewGames");
    }

    @FXML
    private void toViewConsoles(MouseEvent event) {
        ControllerList ctrlList = (ControllerList) UtilsViews.getController("ViewConsoles");
        ctrlList.loadList("consoles");
        UtilsViews.setViewAnimating("ViewConsoles");
    }


}
