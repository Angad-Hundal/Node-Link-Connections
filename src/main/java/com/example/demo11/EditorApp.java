
package com.example.demo11;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EditorApp extends Application {

    AppController controller;

    @Override
    public void start(Stage stage) throws IOException {


        controller = new AppController();
        MainUI root = new MainUI();
        Scene scene = new Scene(root);
        stage.setTitle("381 A3");
        stage.setScene(scene);
        stage.show();

        //scene.setOnKeyPressed(controller::handleKeyPressed);


    }

    public static void main(String[] args) {
        launch();
    }
}