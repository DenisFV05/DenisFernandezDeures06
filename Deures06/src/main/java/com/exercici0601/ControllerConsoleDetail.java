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

public class ControllerConsoleDetail implements Initializable {

    @FXML private ImageView imgBack;
    @FXML private Label lblName;
    @FXML private ImageView imgConsole;
    @FXML private Label lblDate;
    @FXML private Label lblProcessor;
    @FXML private Label lblUnitsSold;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL backUrl = getClass().getResource("/assets/images0601/arrow-back.png");
        if (backUrl != null) imgBack.setImage(new Image(backUrl.toExternalForm()));
    }

    public void loadData(org.json.JSONObject json) {
        lblName.setText(json.optString("name", ""));
        lblDate.setText(json.optString("date", ""));
        lblProcessor.setText(json.optString("procesador", ""));
        lblUnitsSold.setText(String.valueOf(json.optInt("units_sold", 0)));
        String img = json.optString("image", null);
        if (img != null) {
            URL url = getClass().getResource("/assets/images0601/" + img);
            if (url != null) imgConsole.setImage(new Image(url.toExternalForm()));
        }
    }

    @FXML
    private void goBack(MouseEvent e) {
        UtilsViews.setViewAnimating("ViewConsoles");
    }
}
