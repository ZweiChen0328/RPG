package plot1FX;

import StartFX.StartController;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class plot1Controller implements Initializable {
    @FXML
    ImageView angel1;
    @FXML
    ImageView angel2;
    @FXML
    ImageView angel3;
    @FXML
    Label angelTalk, title, angel1Adj, angel2Adj, angel3Adj;
    @FXML
    Button check;

    public static int angel=1;

    private FadeTransition fadeOut = new FadeTransition(
            Duration.millis(5000)
    );

    private FadeTransition fadeIn = new FadeTransition(
            Duration.millis(50)
    );

    Timeline timeline = new Timeline();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fadeOut.setNode(title);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
        title.setVisible(true);
        title.setText("無盡戰記‧天使的祝福");
        title.setFont(Font.font(0));
        timeline.setCycleCount(88);
        KeyFrame titleMove = new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            int k = 1;
            double i = 1.0;

            @Override
            public void handle(ActionEvent actionEvent) {
                for (int j = 0; j < k; j++) {
                    i += 0.1;
                    title.setFont(Font.font(0 + j));
                }
                k++;
                title.setTranslateY(0 + i);
            }
        });
        KeyFrame angelAppear = new KeyFrame(Duration.millis(5000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chooseAngel();
            }
        });
        timeline.getKeyFrames().add(titleMove);
        timeline.setOnFinished((e) -> {
            fadeOut.playFromStart();
            timeline.getKeyFrames().removeAll();
            timeline.getKeyFrames().add(angelAppear);
            timeline.play();
        });
        timeline.play();
    }

    private void chooseAngel() {
        timeline.stop();
        fadeIn.setNode(title);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(false);
        fadeIn.playFromStart();//讓label重新出現

        title.setTranslateY(0);
        title.setText("\n請選擇天使性格");
        title.setVisible(true);
        title.setFont(Font.font(40));//設定label

        angel1.setVisible(true);
        angel2.setVisible(true);
        angel3.setVisible(true);
        angel1Adj.setVisible(true);
        angel2Adj.setVisible(true);
        angel3Adj.setVisible(true);
        angelTalk.setVisible(true);//讓天使圖片、按鈕跟天使說的話出現
    }

    public static void AnimateText(Label lbl, String descImp) {//打字機
        String content = descImp;
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }

            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                lbl.setText(content.substring(0, n));
            }
        };
        animation.play();
    }

    @FXML
    private void clickAngel1Action(Event e) {
        AnimateText(angelTalk, "幹你娘，不要選我");
        check.setVisible(true);
        check.setDisable(false);
        angel=1;
    }

    @FXML
    private void clickAngel2Action(Event e) {
        AnimateText(angelTalk, "可以請不要選我嗎?死肥宅");
        check.setVisible(true);
        check.setDisable(false);
        angel=2;
    }

    @FXML
    private void clickAngel3Action(Event e) {
        AnimateText(angelTalk, "...................");
        check.setVisible(true);
        check.setDisable(false);
        angel=3;
    }

    @FXML
    private void checkButtonAction(Event e) throws IOException {
        StartController.background.stop();
        //揚勛畫面接這裡
        Stage currentStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("ThefightFX/fight.fxml"));
        currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
        currentStage.setTitle("劇情2");
    }
}
