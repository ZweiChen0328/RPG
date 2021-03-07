package EndFX;

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
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static profile.setting.*;

public class norController implements Initializable {

    private String[] st = {"天使:我們終於打贏mao，這個世界終於可以恢復和平了\n",
            "經過了那麼久我已經忘記我們是怎麼相遇的，也漸漸忘記我們冒險得點點滴滴\n",
            "其實呢，我早已不再了\n",
            "或許也說明我沒有這之間的記憶吧\n",
            "在你被魔王打敗後，我用了我的性命去拯救了你\n",
            "或許你可能會好奇現在與你對話的是誰\n",
            "與你一起戰鬥的又是誰\n",
            "我也很好奇呢\n",
            "但我畢竟只是你不想面對現實所創造出來的天使\n",
            "相信以後就算沒有我，你也能過得好好的\n",
            "再見了，勇者\n",
            "真的希望能再見呢\n", ""},
            st1 = {"製作人員表\n","\n", "超級佑軒\n", "他完美的控制了不同界面的轉換\n", "還有優化整體的比例\n","感謝佑軒讚嘆佑軒!\n","\n","超級揚勛\n","負責了整個戰鬥系統的控制邏輯\n"
                    ,"還有陽春的戰鬥動畫?\n","\n","超級瑋晨\n","超級美麗的開始界面!\n","還有對你們最重要的成就系統!!\n","解出最多的成就來獲得秘密獎勵吧!\n","\n","超級俞竣\n","不多說了..\n",
                    "那帥氣的mao跟勇者\n","還有那些美麗的音效\n","oh my god!\n",""},
            st2 = {"資源出處\n","\n", "感謝undertale的各種音樂 the神作\n","\n", "感謝國棟大神貢獻所有的梗\n", "\n","感謝所有幫忙測試bug的人們\n","\n","感謝遊玩無盡戰記天使的祝福!\n",""};
    private String temp = "";
    private int count = 0;


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private StackPane stackPane;
    @FXML
    private Label story, gradually;

    MediaPlayer mediaPlayer;

    public void initialize(URL location, ResourceBundle resources) {
        fadeIn.setNode(stackPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(false);
        stackPane.setBackground(new Background(
                new BackgroundImage(new Image("resource/img/end.png"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(720, 374, false, false, false, true))));
        Timeline timeline = new Timeline();
        timeline.setCycleCount(st.length - 1);
        Timeline timeline1 = new Timeline();
        timeline1.setCycleCount(st1.length - 1);
        Timeline timeline2 = new Timeline();
        timeline2.setCycleCount(st2.length - 1);
        stackPane.setVisible(true);
        fadeIn.playFromStart();
        AnimateText(story, st[count]);
        timeline.getKeyFrames().add(endChange(st));
        timeline.setOnFinished((e) -> {
            endFinish();
            mediaPlayer.stop();
            Media media = new Media(new File("src/resource/music/sprider.mp3").toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setStartTime(Duration.millis(0));
            mediaPlayer.setCycleCount(500);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setVolume(0.5);
            timeline1.getKeyFrames().add(endChange(st1));
            timeline1.play();
        });

        timeline1.setOnFinished((e) -> {
            endFinish();
            timeline1.stop();
            timeline2.getKeyFrames().add(endChange(st2));
            timeline2.play();
        });

        timeline2.setOnFinished((e) -> {
            endFinish(0);
            timeline2.stop();
        });
        timeline.play();

        Media media = new Media(new File("src/resource/music/Ending.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setStartTime(Duration.millis(5000));
        mediaPlayer.setCycleCount(500);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.5);
    }

    private KeyFrame endChange(String[] te) {
        KeyFrame k = new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (stackPane.getOpacity() == 0) {
                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);
                    fadeIn.setCycleCount(1);
                    fadeIn.playFromStart();
                }
                temp += te[count];
                story.setText(temp);
                count++;
                AnimateText(gradually, te[count]);
                gradually.setText("");
            }
        });
        return k;
    }

    private void endFinish(int a) {
        temp = "";
        story.setText(temp);
        count = 0;
        fadeIn.setFromValue(1);
        fadeIn.setToValue(0);
        fadeIn.setCycleCount(1);
        fadeIn.playFromStart();
        fadeIn.setOnFinished((e) -> {
            mediaPlayer.stop();
            Stage currentStage = (Stage) anchorPane.getScene().getWindow();
            Parent root1 = null;
            try {
                root1 = FXMLLoader.load(getClass().getClassLoader().getResource("StartFX/start.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
            currentStage.setTitle("Game");
        });
    }

    private void endFinish() {
        temp = "";
        story.setText(temp);
        count = 0;
        fadeIn.setFromValue(1);
        fadeIn.setToValue(0);
        fadeIn.setCycleCount(1);
        fadeIn.playFromStart();
    }
}
