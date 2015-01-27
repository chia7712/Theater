package net.spright.theater.control;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class MainApp extends Application {
    private static class ImageViewWrapper extends ImageView {
        @Override
        public void resize(double width, double height) {
        }
    }
    private final FXMLLoader myLoader = new FXMLLoader();
    private PrimaryPane pane;
    @Override
    public void start(Stage stage) throws Exception {
        pane = new PrimaryPane();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Custom Control");
        stage.setWidth(1024);
        stage.setHeight(768);
        stage.show();

    }
    @Override
    public void stop() throws Exception {
        if (pane != null) {
            pane.close();
        }

    }
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
