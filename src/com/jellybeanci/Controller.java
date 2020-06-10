package com.jellybeanci;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller
{

    @FXML
    private Button btnGetData;

    @FXML
    public void onButtonClick()
    {
        btnGetData.setText(Math.random() + "");
    }
}
