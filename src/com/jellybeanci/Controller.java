package com.jellybeanci;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller
{

    @FXML
    private TextField textBox;

    @FXML
    private Button btnGetData;

    @FXML
    protected void onGetDataClick()
    {
        btnGetData.setText(Math.random() + "");

    }
}
