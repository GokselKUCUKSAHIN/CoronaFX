package com.jellybeanci;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CountryOverviewController
{

    @FXML
    private TableView<Country> countryTable;

    @FXML
    private TableColumn<Country, String> countryNameColumn;

}
