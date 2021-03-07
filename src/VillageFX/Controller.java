package VillageFX;

import achievementFX.Achievement;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static GameFX.Controller.*;
import static profile.setting.fadeIn;
import static profile.setting.reLife;

public class Controller implements Initializable {
    private String[] st1 = {
            "莉莉亞:我帶你去見村長\n",
            "這個村子不大，但看起來卻十分先進，建築物從外觀看起來都像研究機構\n",
            "延著路走下去後，看見的卻是一間茅屋，看起來年代久遠跟旁邊的建築格格不入\n",
            "打開大門，一個約莫六十歲左右的中年大叔出現在眼前\n",
            "村長:我已經從感官同化中得知你的事情了，我是XXX"},
            st2 = {
                    "村長:其實這裡是專門生產出勇者的村落\n",
                    "這裡自從二代勇者創立以來，便都是從這裡獲得勇者的頭銜\n",
                    "或許你沒有降臨在這個世上，剛剛帶你來的獵人就是勇者了\n",
                    "不知道是不是詛咒，每個勇者的姓都長得一樣\n",
                    "下場都差不多非死即傷，像我就少了條手臂，我的前一代甚至連骨灰都看不到\n",
                    "如果你還沒失去戰鬥的意志，那就來選把武器，或許你就是把魔王終結的人", ""},
            st3 = {
                    "勇者眼光不錯!我是你也選這把武器\n",
                    "既然都選擇好了武器那就做好迎戰的準備吧!\n",
                    "保持好你的決心\n",
                    "勇敢的戰鬥吧!", ""},
            st4 = {
                    "不愧是傳說中使用霸氣的男人\n",
                    "對付強大的魔王果然絲毫不虛!\n",
                    "但是這樣我還是覺得太危險了....\n",
                    "這裡還有一些食物還有很多香菜都拿去吧!!", ""};

    public static int[] A = {0, 0, 0, 0};
    private String temp = "";
    private int count = 0;
    public static int damage = 0, adddefense = 0, hp = 0;
    static String temp1;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private Label story, gradually, info, achShow;
    @FXML
    private ImageView armsImg;
    @FXML
    private HBox arms;
    @FXML
    private VBox armsInfo;

    @FXML
    private Button sword, axe, spear, confirm, button;


    private FadeTransition achFade = new FadeTransition(
            Duration.millis(10000)
    );
    private boolean flag = true;

    int a = 0;
    MediaPlayer mediaPlayer, achMusic;
    Timeline timeline = new Timeline();
    Timeline timeline2 = new Timeline();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        itemnumber[0] = 1;
        itemnumber[1] = 3;
        itemnumber[2] = 3;
        itemnumber[3] = 2;
        itemnumber[4] = 1;
        Media media = new Media(new File("src/resource/music/Undertale.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setStartTime(Duration.millis(0));
        mediaPlayer.setCycleCount(500);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.3);

        fadeIn.setNode(stackPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(false);

        achFade.setNode(achShow);
        achFade.setFromValue(1);
        achFade.setToValue(0);
        achFade.setCycleCount(1);
        achFade.setAutoReverse(false);

        stackPane.setBackground(new Background(
                new BackgroundImage(new Image("resource/img/village.png"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(720, 374, false, false, false, true))));

        timeline.setCycleCount(st1.length - 1);
        AnimateText(story, st1[count]);

        timeline.getKeyFrames().add(storyStart(st1));
        timeline.setOnFinished((e) -> {
            storyFinish(st2, timeline);
        });
        timeline.play();
    }

    private KeyFrame storyStart(String[] te) {
        KeyFrame k = new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!stackPane.isVisible()) {
                    stackPane.setVisible(true);
                    fadeIn.playFromStart();
                    button.setDisable(false);
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

    private KeyFrame storyChange(String[] te) {
        KeyFrame k = new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (count == 0) {
                    AnimateText(story, te[count]);
                    gradually.setText("");
                    count++;
                } else {
                    temp += te[count - 1];
                    story.setText(temp);
                    AnimateText(gradually, te[count]);
                    count++;
                    gradually.setText("");
                }
            }
        });
        return k;
    }

    private void storyFinish(String[] te, Timeline timeline) {
        temp = "";
        story.setText(temp);
        count = 0;
        timeline.stop();
        timeline.getKeyFrames().clear();
        if (te.equals(st2)) {
            timeline.setCycleCount(st2.length);
            timeline.getKeyFrames().add(storyChange(st2));
            timeline.setOnFinished((e) -> {
                stackPane.setOpacity(0.5);
                arms.setDisable(false);
                arms.setVisible(true);
            });
            timeline.play();
        }
    }

    @FXML
    private void skipButton(ActionEvent e) throws Exception {
        if (flag) {
            button.setDisable(true);
            timeline.stop();
            temp = "";
            story.setText(temp);
            AnimateText(gradually, "如果你還沒失去戰鬥的意志，那就來選把武器，或許你就是把魔王終結的人");
            stackPane.setOpacity(0.5);
            arms.setDisable(false);
            arms.setVisible(true);
        } else {
            Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Parent root1 = null;
            mediaPlayer.stop();
            timeline2.stop();
            try {
                root1 = FXMLLoader.load(getClass().getClassLoader().getResource("GameFX/game.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
            currentStage.setTitle("Battle");
        }
    }

    @FXML
    public void armsButton(ActionEvent e) throws Exception {
        button_voice();
        armsInfo.setVisible(true);
        armsInfo.setDisable(false);
        temp1 = ((Button) e.getSource()).getText();
        switch (temp1) {
            case "劍":
                armsImg.setImage(new Image(new File("src/resource/img/sword.png").toURI().toString()));
                info.setText("攻擊力+300 防禦力+100");
                if (A[0] == 1) {
                    confirm.setDisable(true);
                    confirm.setText("你已選擇過這個武器");
                } else {
                    confirm.setDisable(false);
                    confirm.setText("選擇這把武器");
                }
                break;
            case "斧":
                armsImg.setImage(new Image(new File("src/resource/img/axe.png").toURI().toString()));
                info.setText("攻擊力+300 生命+1000");
                if (A[1] == 1) {
                    confirm.setDisable(true);
                    confirm.setText("你已選擇過這個武器");
                } else {
                    confirm.setDisable(false);
                    confirm.setText("選擇這把武器");
                }
                break;
            case "矛":
                armsImg.setImage(new Image(new File("src/resource/img/spear.jpg").toURI().toString()));
                info.setText("攻擊力+200 生命+500 防禦力+50");
                if (A[2] == 1) {
                    confirm.setDisable(true);
                    confirm.setText("你已選擇過這個武器");
                } else {
                    confirm.setDisable(false);
                    confirm.setText("選擇這把武器");
                }
                break;
            case "赤手空拳":
                armsImg.setImage(new Image(new File("src/resource/img/拳頭.png").toURI().toString()));
                info.setText("什麼都不拿!老子用拳頭嚕死魔王");
                confirm.setText("武裝色霸氣(媽媽的拳頭");
                break;
        }
    }

    @FXML
    public void keyEnter(KeyEvent e) throws Exception {
        if (e.getCode() == KeyCode.ENTER) {
            if (!button.isDisable()) {
                if (flag) {
                    button.setDisable(true);
                    timeline.stop();
                    temp = "";
                    story.setText(temp);
                    AnimateText(gradually, "如果你還沒失去戰鬥的意志，那就來選把武器，或許你就是把魔王終結的人");
                    stackPane.setOpacity(0.5);
                    arms.setDisable(false);
                    arms.setVisible(true);
                } else {
                    Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    Parent root1 = null;
                    mediaPlayer.stop();
                    timeline2.stop();
                    try {
                        root1 = FXMLLoader.load(getClass().getClassLoader().getResource("GameFX/game.fxml"));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
                    currentStage.setTitle("Battle");
                }
            }
        }
    }

    @FXML
    public void testButton(ActionEvent e) throws Exception {
        stackPane.setOpacity(1);
        arms.setDisable(true);
        arms.setVisible(false);
        armsInfo.setVisible(false);
        armsInfo.setDisable(true);
        button.setDisable(false);
        flag = false;
        gradually.setText("");
        Achievement ach = new Achievement();
        button_voice();
        switch (temp1) {
            case "劍":
                damage += 300;
                adddefense += 100;
                A[0] = 1;
                break;
            case "斧":
                damage += 300;
                hp += 1000;
                A[1] = 1;
                break;
            case "矛":
                damage += 200;
                hp += 500;
                adddefense += 50;
                A[2] = 1;
                break;
            case "赤手空拳":
                a = 1;
                itemnumber[0] = 99;
                itemnumber[1] = 4;
                itemnumber[2] = 4;
                itemnumber[3] = 3;
                itemnumber[4] = 2;
                if (ach.noTakeWeapon() == 1) {
                    achShow.setText("勇往直前\n成就已達成");
                    achShow.setVisible(true);
                    achFade.playFromStart();
                    Media media1 = new Media(new File("src/resource/music/熊吼.mp3").toURI().toString());
                    achMusic = new MediaPlayer(media1);
                    achMusic.setStartTime(Duration.millis(0));
                    achMusic.setCycleCount(1);
                    achMusic.setAutoPlay(true);
                    achMusic.setVolume(0.3);
                }
                break;
        }
        if (ach.takeAllWeapon(A) == 1) {
            achShow.setText("我全都要\n成就已達成");
            achShow.setVisible(true);
            achFade.playFromStart();
            Media media1 = new Media(new File("src/resource/music/熊吼.mp3").toURI().toString());
            achMusic = new MediaPlayer(media1);
            achMusic.setStartTime(Duration.millis(0));
            achMusic.setCycleCount(1);
            achMusic.setAutoPlay(true);
            achMusic.setVolume(0.3);
        }

        temp = "";
        story.setText(temp);
        count = 0;
        if (a == 0) {
            AnimateText(story, st3[count]);
            timeline2.setCycleCount(st3.length - 1);
            timeline2.getKeyFrames().add(storyStart(st3));
        } else {
            AnimateText(story, st4[count]);
            timeline2.setCycleCount(st4.length - 1);
            timeline2.getKeyFrames().add(storyStart(st4));
        }
        timeline2.setOnFinished((event) -> {
            Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Parent root1 = null;
            mediaPlayer.stop();
            try {
                root1 = FXMLLoader.load(getClass().getClassLoader().getResource("GameFX/game.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
            currentStage.setTitle("Battle");
        });
        timeline2.play();
    }
}