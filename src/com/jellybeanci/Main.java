package com.jellybeanci;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application
{

    public static ObservableList<Node> child;
    //
    private static final String title = "CoronaFX -JellyBeanci";
    public static final int width = 600;
    public static final int height = 600;

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
        root.setOnKeyPressed(e -> {
            switch (e.getCode())
            {
                case F1:
                {
                    //Show Child Count
                    System.out.println("Child Count: " + root.getChildrenUnmodifiable().size());
                    break;
                }
            }
        });
        stage.setTitle(title);
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.setScene(new Scene(root, width - 10, height - 10));
        stage.show();
        root.requestFocus();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}