package StartFX;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.scene.media.MediaPlayer.INDEFINITE;

public class StartController implements Initializable {
    @FXML
    AnchorPane ap;
    @FXML
    Button gameStart;
    @FXML
    Button achievement;
    @FXML
    Button exit;
    @FXML
    Button producer;
    public static MediaPlayer background;
    private static boolean judge = false;


    @FXML
    private void moveInButtonAction(Event e) {
        Object source = e.getSource();
        Button btn = (Button) source;
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: green");
        btn.setFont(new Font(90));
    }

    @FXML
    private void moveOutButtonAction(Event e) {
        Object source = e.getSource();
        Button btn = (Button) source;
        btn.setFont(new Font(70));
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
    }

    @FXML
    private void clickGameStartButtonAction(Event e) throws IOException {
        Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("plot1FX/plot1.fxml"));
        currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
    }

    @FXML
    private void clickAchievementButtonAction(Event e) throws IOException {
        Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("achievementFX/achievement.fxml"));
        currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
    }

    @FXML
    private void clickProducerButtonAction(Event e) throws IOException {
        Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("producerFX/producer.fxml"));
        currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
    }

    @FXML
    private void clickExitButtonAction(Event e) {
        background.stop();
        Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        currentStage.close();
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ap.setBackground(new Background(new BackgroundImage(new Image("resource/img/開場背景.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(720, 374, false, false, false, true))));
        if(!judge) {
            String path = "src/resource/music/startBGM.mp3";
            Media media = new Media(new File(path).toURI().toString());
            background = new MediaPlayer(media);
            background.setVolume(0.3);
            background.setCycleCount(INDEFINITE);
            background.setAutoPlay(true);
            judge = true;
        }
    }
}

