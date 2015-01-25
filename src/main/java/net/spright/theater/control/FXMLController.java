package net.spright.theater.control;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class FXMLController extends BorderPane {
    @FXML
    private Button hotBtn;
    @FXML
    private Button randomBtn;
    @FXML
    private Button favoriteBtn;
    @FXML
    private Text titleText;
    public FXMLController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();     
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
