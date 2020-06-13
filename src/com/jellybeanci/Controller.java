package com.jellybeanci;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Controller
{

    @FXML
    private StackedBarChart<?, ?> barChart;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private CategoryAxis continentAxis;
    
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

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
                ArrayList<String> contList;
                try
                {
                    buttonChangeText(btnGetData, "Getting Data...");
                    //contList = GetData.readFromWeb("https://opendata.ecdc.europa.eu/covid19/casedistribution/xml/");
                    contList = GetData.getDataFromAnywhere(input);
                    buttonChangeText(btnGetData, "Data Found Loading...");
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
        Platform.runLater(() -> {
            lineChart.getData().clear();
            ObservableList<Country> selectedCountries = countryListView.getSelectionModel().getSelectedItems();
            SortedList<String> categories = getCategories(selectedCountries);
            x.invalidateRange(categories);
            x.autosize();
            for (Country country : selectedCountries)
            {
                XYChart.Series dataSeries = new XYChart.Series();
                dataSeries.setName(country.getCode());
                ObservableList<Record> recs = country.getRecordList();
                int total = 0;
                for (Record record : recs)
                {
                    total += record.getCases();
                    dataSeries.getData().add(new XYChart.Data<>(record.getDateString(), total));
                }
                lineChart.getData().add(dataSeries);
            }
        });
    }

    private SortedList<String> getCategories(ObservableList<Country> selectedCountries)
    {

        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (Country country : selectedCountries)
        {
            ObservableList<Record> records = country.getRecordList();
            for (Record record : records)
            {
                if (!observableList.contains(record.getDateString()))
                {
                    observableList.add(record.getDateString());
                }
            }
        }
        return observableList.sorted();
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