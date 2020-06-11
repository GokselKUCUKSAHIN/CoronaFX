package com.jellybeanci;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

import static com.jellybeanci.GetData.readFromWeb;

public class Controller
{

    @FXML
    public VBox mainVBox;

    @FXML
    private TextField textBox;

    @FXML
    private Button btnGetData;

    @FXML
    protected void onGetDataClick()
    {
        btnGetData.setDisable(true);
        Runnable r = () -> {
            ArrayList<String> contList = null;
            try
            {
                buttonChangeText(btnGetData, "Getting Data...");
                contList = GetData.readFromWeb("https://opendata.ecdc.europa.eu/covid19/casedistribution/xml/");
                buttonChangeText(btnGetData, "Data Found Loading...");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            assert contList != null;
            Record.parse(contList);
            buttonChangeText(btnGetData, "Get Data");
            btnGetData.setDisable(false);
            tableView.setItems(Country.getObservableList());
        };
        new Thread(r).start();
    }

    protected void buttonChangeText(Button button, String text)
    {
        Platform.runLater(() -> button.setText(text));
    }

    @FXML
    protected void threadTestButton()
    {
        Platform.runLater(() -> {
            // Update UI here.
            btnGetData.setText(Math.random() + "");
        });
    }

    //
    // Table View
    //
    @FXML
    private TableView<Country> tableView;

    @FXML
    private TableColumn<Country, String> countryName;

    @FXML
    private TableColumn<Country, Integer> totalCases;

    @FXML
    private TableColumn<Country, Integer> newCases;

    @FXML
    private TableColumn<Country, Integer> totalDeaths;

    @FXML
    private TableColumn<Country, Integer> newDeaths;

    @FXML
    private TableColumn<Country, Integer> population;

    @FXML
    private TableColumn<Country, Double> mortality;

    @FXML
    private TableColumn<Country, Double> attackRate;

    @FXML
    private void initialize()
    {
        countryName.setCellValueFactory(new PropertyValueFactory<>("name"));
        totalCases.setCellValueFactory(new PropertyValueFactory<>("totalCases"));
        newCases.setCellValueFactory(new PropertyValueFactory<>("newCases"));
        totalDeaths.setCellValueFactory(new PropertyValueFactory<>("totalDeaths"));
        newDeaths.setCellValueFactory(new PropertyValueFactory<>("newDeaths"));
        population.setCellValueFactory(new PropertyValueFactory<>("population"));
        mortality.setCellValueFactory(new PropertyValueFactory<>("mortality"));
        attackRate.setCellValueFactory(new PropertyValueFactory<>("attackRate"));
    }
}

