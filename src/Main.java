import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application
{
    public static ObservableList<Node> child;
    //
    private static final String title = "CoronaFX -JellyBeanci";
    public static final int width = 800;
    public static final int height = 800;
    private static Color backcolor = Color.rgb(51, 51, 51);
    private static Font font = new Font("Calibri",15);
    private static Timeline update;

    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane root = new BorderPane();
        child = root.getChildren();
        //
        // TOP LAYOUT BOX
        HBox topLayout = new HBox();
        topLayout.setSpacing(20);
        topLayout.setPadding(new Insets(10,10,10,10));
        //
        // URL Label
        Label urlLabel = new Label("Dataset URL: ");
        urlLabel.setFont(font);
        urlLabel.setAlignment(Pos.BOTTOM_CENTER);
        //
        //
        TextField urlTextbox = new TextField();
        urlTextbox.setFont(font);
        urlTextbox.setMinWidth(320);
        urlTextbox.setMaxHeight(5);



        topLayout.getChildren().addAll(urlLabel, urlTextbox);
        root.setTop(topLayout);


        //
        root.setOnKeyPressed(e -> {
            switch (e.getCode())
            {
                case F1:
                {
                    //PLAY
                    update.play();
                    break;
                }
                case F2:
                {
                    //PAUSE
                    update.pause();
                    break;
                }
                case F3:
                {
                    //Show Child Count
                    System.out.println("Child Count: " + child.size());
                    break;
                }
            }
        });
        update = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            //60 fps
            System.out.println("loop test");

        }));
        update.setCycleCount(Timeline.INDEFINITE);
        update.setRate(1);
        update.setAutoReverse(false);
        //update.play(); //uncomment for play when start
        //
        stage.setTitle(title);
        stage.setMinHeight(600);
        stage.setMinWidth(600);
        stage.setScene(new Scene(root, width - 10, height - 10,backcolor));
        stage.show();
        root.requestFocus();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}