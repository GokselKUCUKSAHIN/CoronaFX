package com.jellybeanci;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class Controller
{

    @FXML
    private LineChart<String, Integer> lineChart;

    @FXML
    private Button btnShow;

    @FXML
    private TextField textBox;

    @FXML
    private Button btnGetData;

    @FXML
    protected void onGetDataClick()
    {
        btnGetData.setDisable(true);
        Runnable r = () -> {
            String input = textBox.getText().trim();
            if (input.length() >= 1)
            {
                ArrayList<String> contList = null;
                try
                {
                    buttonChangeText(btnGetData, "Getting Data...");
                    //contList = GetData.readFromWeb("https://opendata.ecdc.europa.eu/covid19/casedistribution/xml/");
                    contList = GetData.getDataFromAnywhere(input);
                    buttonChangeText(btnGetData, "Data Found Loading...");
                    assert contList != null;
                    Record.parse(contList);
                    tableView.setItems(Country.getObservableList());
                    countryListView.setItems(Country.getObservableList().sorted());
                }
                catch (IOException e)
                {
                    showMessage("ERROR!", "URL or File Not Found!", Alert.AlertType.ERROR);
                }
            } else
            {
                showMessage("Warning!", "Textfild can not be Empty!", Alert.AlertType.WARNING);
            }
            buttonChangeText(btnGetData, "Get Data");
            btnGetData.setDisable(false);
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

    @FXML
    protected void getSelectedCountries()
    {
        lineChart.getData().clear();
        ObservableList<Country> selectedCountries = countryListView.getSelectionModel().getSelectedItems();
        for (Country country : selectedCountries)
        {
            XYChart.Series dataSeries = new XYChart.Series();
            dataSeries.setName(country.getCode());
            ObservableList<Record> recs = country.getRecordList();
            //recs.sorted(Comparator.reverseOrder());
            int total = 0;
            for (Record record : recs)
            {
                total += record.getCases();
                dataSeries.getData().add(new XYChart.Data<String, Integer>(record.getDate().toString(), total));
            }
            lineChart.getData().add(dataSeries);
        }
    }

    //
    // List View
    //
    @FXML
    private ListView<Country> countryListView;

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
        //Table View
        countryName.setCellValueFactory(new PropertyValueFactory<>("name"));
        totalCases.setCellValueFactory(new PropertyValueFactory<>("totalCases"));
        newCases.setCellValueFactory(new PropertyValueFactory<>("newCases"));
        totalDeaths.setCellValueFactory(new PropertyValueFactory<>("totalDeaths"));
        newDeaths.setCellValueFactory(new PropertyValueFactory<>("newDeaths"));
        population.setCellValueFactory(new PropertyValueFactory<>("population"));
        mortality.setCellValueFactory(new PropertyValueFactory<>("mortality"));
        attackRate.setCellValueFactory(new PropertyValueFactory<>("attackRate"));
        //List View
        countryListView.setCellFactory(new Callback<ListView<Country>, ListCell<Country>>()
        {
            @Override
            public ListCell<Country> call(ListView<Country> lv)
            {
                return new ListCell<Country>()
                {
                    @Override
                    public void updateItem(Country item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (item == null)
                        {
                            setText(null);
                        } else
                        {
                            setText(item.getCode());
                        }
                    }
                };
            }
        });
        countryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lineChart.getXAxis().setAutoRanging(true);
    }

    private static void showMessage(String title, String message, Alert.AlertType alertType)
    {
        Platform.runLater(() -> {
            // Update UI here.
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });

    }
}