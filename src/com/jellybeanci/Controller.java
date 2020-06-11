package com.jellybeanci;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;

import static com.jellybeanci.GetData.readFromWeb;

public class Controller
{

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
            countryListView.setItems(Country.getObservableList().sorted());
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
        ObservableList<Country> selectedCountries = countryListView.getSelectionModel().getSelectedItems();
        for (Country country : selectedCountries)
        {
            System.out.println(country.toString());
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

    }
}