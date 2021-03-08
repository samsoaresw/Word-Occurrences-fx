package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int WIDTH=600;
    private static final int HEIGHT=600;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Word Analyzer");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));


        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
