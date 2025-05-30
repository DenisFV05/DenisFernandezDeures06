package com.exercici0601;

import com.utils.UtilsViews;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGameDetail implements Initializable {

    @FXML private ImageView imgBack;
    @FXML private Label lblName;
    @FXML private ImageView imgGame;
    @FXML private Label lblYear;
    @FXML private Label lblType;
    @FXML private Label lblPlot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL backUrl = getClass().getResource("/assets/images0601/arrow-back.png"); // flecha para retroceder
        if (backUrl != null) imgBack.setImage(new Image(backUrl.toExternalForm()));
    }

    public void loadData(org.json.JSONObject json) { // carga todos los datos
        lblName.setText(json.optString("name", ""));
        lblYear.setText(String.valueOf(json.optInt("year", 0)));
        lblType.setText(json.optString("type", ""));
        lblPlot.setText(json.optString("plot", ""));
        String img = json.optString("image", null);
        if (img != null) {
            URL url = getClass().getResource("/assets/images0601/" + img);
            if (url != null) imgGame.setImage(new Image(url.toExternalForm()));
        }
    }

    @FXML
    private void goBack(MouseEvent e) {
        UtilsViews.setViewAnimating("ViewGames");
    }
}
