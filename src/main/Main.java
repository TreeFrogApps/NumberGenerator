package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    private Parent mParent;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        setUpStage(primaryStage);
    }

    private void setUpStage(Stage stage) {


        try {
            mParent = FXMLLoader.load(getClass().getResource("number_generator.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display Metrics
        Rectangle2D screen = Screen.getPrimary().getBounds();
        double width = screen.getWidth()/2;
        double height = screen.getHeight()/2;
        double xPos = screen.getMinX() + (width / 2);
        double yPos = screen.getMinY() + (height / 2);

        // Set Scene
        Scene mScene = new Scene(mParent, width, height);
        stage.setScene(mScene);
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("app_icon.png")));
        stage.setTitle("Number Generator");
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setMaxHeight(height);
        stage.setMinWidth(width);
        stage.setX(xPos);
        stage.setY(yPos);
        stage.show();
    }
}
