package com.jellybeanci;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;

public class Main extends Application
{

    //public static ObservableList<Node> child;
    //
    private static final String title = "CoronaFX -JellyBeanci";
    public static final int width = 800;
    public static final int height = 800;

    //private final ObservableList<Country> countryData = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
        root.setOnKeyPressed(e -> {
            switch (e.getCode())
            {
                case F1:
                {
                    System.out.println("There is no HELP!");
                    break;
                }
                case F2:
                {
                    try
                    {
                        Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=l482T0yNkeo").toURI());
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    break;
                }
            }
        });
        stage.setTitle(title);
        stage.setMinHeight(750);
        stage.setMinWidth(750);
        stage.setScene(new Scene(root, width - 10, height - 10));
        stage.show();
        root.requestFocus();
    }
    /*
    public ObservableList<Country> getCountryData()
    {
        return countryData;
    }
    */

    public static void main(String[] args)
    {
        launch(args);
    }
}