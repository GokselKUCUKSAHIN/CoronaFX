package com.jellybeanci;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;

import static com.jellybeanci.GetData.readFromWeb;

public class Controller
{

    @FXML
    public TableView tableView;

    @FXML
    private TextField textBox;

    @FXML
    private Button btnGetData;

    @FXML
    protected void onGetDataClick()
    {
        btnGetData.setDisable(true);
        Runnable r = new Runnable()
        {
            public void run()
            {
                ArrayList<String> contList = null;
                try
                {
                    contList = GetData.readFromWeb("https://opendata.ecdc.europa.eu/covid19/casedistribution/xml/");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                Record.parse(contList);
                for (Country country : Country.countries.values())
                {
                    System.out.println(country.toString());
                }
                btnGetData.setDisable(false);
            }
        };
        new Thread(r).start();
    }

    @FXML
    protected void threadTestButton()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                // Update UI here.
                btnGetData.setText(Math.random() + "");
            }
        });
    }
}

