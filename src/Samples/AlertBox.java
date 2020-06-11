package Samples;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertBox
{

    private void template()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Err: Hello There");
        alert.setHeaderText(null);
        alert.setContentText("You do not have the right to create Problems.");
        ButtonType buttonTypeOne = new ButtonType("Hi");
        ButtonType buttonTypeTwo = new ButtonType("Hey");
        ButtonType buttonTypeThree = new ButtonType("General Kenobi");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeThree)
        {
        }
    }
}
