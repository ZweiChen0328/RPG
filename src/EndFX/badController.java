package EndFX;

import achievementFX.Achievement;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static profile.setting.*;

public class badController implements Initializable {
    Achievement ach = new Achievement();
    private String[] st = {
            "最後的最後，天使使出了禁忌的咒語用盡了全力\n",
            "只為了將勇者傳送到安全的地方\n",
            "天使回歸了最初的型態，沒了翅膀、沒了身體、沒了聲音\n",
            "剩下意識附在了勇者身上\n",
            "只為了完成與勇者，最初的約定\n", ""};
    private String temp = "";
    private int count = 0;


    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label story, gradually, achShow;
    private FadeTransition achFade = new FadeTransition(
            Duration.millis(7000)
    );
    MediaPlayer mediaPlayer;
    MediaPlayer achMusic;

    public void initialize(URL location, ResourceBundle resources) {
        achFade.setNode(achShow);
        achFade.setFromValue(1);
        achFade.setToValue(0);
        achFade.setCycleCount(1);
        achFade.setAutoReverse(false);
        try {
            if (ach.firstDie() == 1) {
                achShow.setText("接受命運\n成就已達成");
                achShow.setVisible(true);
                achFade.playFromStart();
                Media media1 = new Media(new File("src/resource/music/熊吼.mp3").toURI().toString());
                achMusic = new MediaPlayer(media1);
                achMusic.setStartTime(Duration.millis(0));
                achMusic.setCycleCount(1);
                achMusic.setAutoPlay(true);
                achMusic.setVolume(0.3);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Timeline timeline = new Timeline();
        timeline.setCycleCount(st.length - 1);
        AnimateText(story, st[count]);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                temp += st[count];
                story.setText(temp);
                count++;
                AnimateText(gradually, st[count]);
                gradually.setText("");
            }
        });

        timeline.getKeyFrames().add(keyFrame);

        timeline.setOnFinished((e) -> {
            reLife = true;
            Stage currentStage = (Stage) anchorPane.getScene().getWindow();
            Parent root1 = null;
            try {
                root1 = FXMLLoader.load(getClass().getClassLoader().getResource("storyFX/story.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
            currentStage.setTitle("story");
        });
        timeline.play();
    }
}
