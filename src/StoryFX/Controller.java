package StoryFX;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import static profile.setting.*;
import achievementFX.Achievement;

public class Controller implements Initializable {
    MediaPlayer mediaPlayer,reMusic, achMusic;
    public static String solidername = "勇者";

    Achievement a = new Achievement();


    @FXML
    private Label yourName;

    private String[] st1 = {
            "和熙的陽光透過樹葉間的縫隙，照亮了周圍的環境\n",
            "突然半空中出現漩渦，一道人影從中落下\n",
            "幸運地，" + solidername + "剛好落在附近的深山裡，不會被魔物打擾，而且水源充足\n",
            "但" + solidername + "虛弱的身體，不知道甚麼時候才會恢復\n",
            "忽然一隻熊從旁邊的灌木叢出現\n",
            "天使:勇者大人，勇者大人，快點起來阿，不要再睡了，要被當成今天的晚餐了，快點起來阿"
    },
            st2 = {
                    "熊撲了過來，天使從" + solidername + "的嗅覺中感覺到了血的味道\n",
                    "身體上也多了一些重量，但奇蹟的是並沒有感覺到任何疼痛\n",
                    solidername + "依然沉睡，原來是一旁的灌木叢有位女獵人等待熊狩獵的瞬間\n",
                    "將弓箭精準地貫穿熊的心臟\n",
                    "給予牠致命一擊後，女獵人突然發出了慘叫\n",
                    "不是因為" + solidername + "受了傷，也不是因為沒有一發讓熊斷氣\n",
                    "而是熊在斷氣後順勢落下，接觸到了" + solidername + "的手\n",
                    "卻瞬間爆開\n", "熊的屍體分散在四處\n",
                    "除了女獵人以外沒有人會相信這原本是一隻健全的熊\n",
                    "女獵人:????"
            },
            st3 = {
                    "聽到慘叫的" + solidername + "，身體本能的從沉睡中醒來並進入了戰鬥狀態\n",
                    "觀察了四周，除了一名拿者弓箭、背著箭袋\n",
                    "穿著輕便衣服跟短褲的女生以外，就只剩下四處散落的屍塊與鮮血\n",
                    "無法理解當前狀況的" + solidername + "，腦中出現了天使的聲音，天使簡略地解釋了當前的狀況\n",
                    solidername + "理解狀況後跟女獵人說明了自己的能力並表明勇者的身分\n",
                    "也感謝女獵人對於自己的救命之恩\n",
                    "女獵人收起了張大的嘴巴，便開口說道:\n",
                    "女獵人:我的名字是莉莉亞，那...你的名字是?"
            },
            reSt1 = {"似曾相識的場景，黯淡的陽光、潮濕的紅土、還有...那浮在半空中的漩渦\n",
                    "人影重重地砸向地面，" + solidername + "躺在地上\n",
                    "不久，棕熊的身影從草叢竄出\n",
                    "天使:勇者大人，勇者大人，快點起來阿，不要再睡了，要被當成今天的晚餐了，快點起來阿"
            },
            reSt2 = {"在熊的影子幾乎籠罩" + solidername + "全身時\n",
                    "銀光忽現，箭矢射穿了熊的心臟，壯碩的身影抖動著\n",
                    "窸窸窣窣的聲響，草叢被撥開，莉莉亞從中走了出來\n",
                    "一切再度重演，爆炸聲響與莉莉亞的叫聲，喚醒了" + solidername},
            reSt3 = {"四處張望著，觀察周圍的" + solidername + "，視線看向莉莉亞，兩人就這樣對視著\n",
                    "天使向" + solidername + "解釋著當前的情況，卻刻意地沒有提及「重生」的事\n",
                    solidername + "也彷彿全然不記得之前的事一般，同樣的自我介紹\n",
                    "同樣地報上自己的名字"};


    private String temp = "", name = "";
    private int count = 0;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private Label story, gradually, achShow;
    @FXML
    private Button button, nameCheck;
    @FXML
    private TextField nameInput;
    @FXML
    private ImageView monster;

    private FadeTransition achFade = new FadeTransition(
            Duration.millis(7000)
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (reLife) {
            st1 = reSt1;
            st2 = reSt2;
            st3 = reSt3;
            Media resurrection = new Media(new File("src/resource/music/復活倒轉.mp3").toURI().toString());
            reMusic = new MediaPlayer(resurrection);
            reMusic.setStartTime(Duration.millis(0));
            reMusic.setCycleCount(1);
            reMusic.setAutoPlay(true);
            reMusic.setVolume(0.1);
        }
        monster.setFitWidth(600);
        monster.setFitHeight(600);
        achShow.setText("命中注定\n 成就已達成");
        Media media = new Media(new File("src/resource/music/Undertale.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setStartTime(Duration.millis(0));
        mediaPlayer.setCycleCount(500);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.1);

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
                new BackgroundImage(new Image("resource/img/ground.png"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(720, 374, false, false, false, true))));

        Timeline timeline = new Timeline();
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
                if (!stackPane.isVisible() && reLife) {
                    stackPane.setVisible(true);
                    System.out.println("test");
                    fadeIn.setNode(stackPane);
                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);
                    fadeIn.setCycleCount(1);
                    fadeIn.playFromStart();
                }
                if (!monster.isVisible() && reLife && count > 0) {
                    monster.setVisible(true);
                    if (te.equals(st1))
                        monster.setImage(new Image(new File("src/resource/img/grassbear.png").toURI().toString()));
                    else
                        monster.setImage(new Image(new File("src/resource/img/雲朵怪獸.png").toURI().toString()));
                    fadeIn.setNode(monster);
                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);
                    fadeIn.setCycleCount(1);
                    fadeIn.playFromStart();
                }

                if (!stackPane.isVisible() && count > 1) {
                    stackPane.setVisible(true);
                    fadeIn.playFromStart();
                }
                if (!monster.isVisible() && count > 2) {
                    monster.setVisible(true);
                    if (te.equals(st1)) {
                        monster.setImage(new Image(new File("src/resource/img/grassbear.png").toURI().toString()));
                    } else {
                        monster.setImage(new Image(new File("src/resource/img/雲朵怪獸.png").toURI().toString()));
                    }
                    fadeIn.setNode(monster);
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

    private KeyFrame storyChange(String[] te) {
        KeyFrame k = new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!monster.isVisible() && reLife) {
                    System.out.println("tetet");
                    monster.setVisible(true);
                    monster.setImage(new Image(new File("src/resource/img/雲朵怪獸.png").toURI().toString()));
                    fadeIn.setNode(monster);
                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);
                    fadeIn.setCycleCount(1);
                    fadeIn.playFromStart();
                }

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
            monster.setImage(new Image(new File("src/resource/img/bear.png").toURI().toString()));
            fadeIn.setNode(monster);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);
            fadeIn.playFromStart();
            timeline.setOnFinished((e) -> {
                fadeIn.setNode(monster);
                fadeIn.setFromValue(1);
                fadeIn.setToValue(0);
                fadeIn.setCycleCount(1);
                fadeIn.playFromStart();
                storyFinish(st3, timeline);
            });
            timeline.play();
        } else if (te.equals(st3)) {
            timeline.setCycleCount(st3.length);
            timeline.getKeyFrames().add(storyChange(st3));
            timeline.setOnFinished((e) -> {
                stackPane.setOpacity(0.5);
                nameInput.setDisable(false);
                nameInput.setVisible(true);
                nameCheck.setDisable(false);
                nameCheck.setVisible(true);
                yourName.setVisible(true);
            });
            timeline.play();
        }
    }

    @FXML
    public void testButton(ActionEvent e) throws Exception {
        mediaPlayer.stop();
        Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("VillageFX/village.fxml"));
        currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
        currentStage.setTitle("Battle");
    }

    @FXML
    public void keyEnter(KeyEvent e) throws Exception {
        if (e.getCode() == KeyCode.ENTER) {
            mediaPlayer.stop();
            Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("VillageFX/village.fxml"));
            currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
            currentStage.setTitle("Battle");
        }
    }

    @FXML
    public void nameButton(ActionEvent e) throws Exception {
        monster.setVisible(false);
        solidername = nameInput.getText();
        String[] st4 = {
                "莉莉亞驚訝的張開口，二話不說請" + solidername + "一定要到村子裡見見村長\n",
                "天使:(勇者大人，我可以感覺到您現在很累了，可以先去村裡休息\n",
                "而且她剛剛也出手救了勇者大人您，我想不會遇到甚麼危險)\n",
                "發現說不過天使的" + solidername + "，便隨莉莉亞前往了村落\n",
                "來到附近的樹海裡發現兩隻像棉花糖一樣的魔獸趴在一個鐵板上\n",
                "發現有外來者的魔獸便進入了警戒狀態\n",
                "莉莉亞:我們到了"},
                st5 = {
                        "莉莉亞先摸摸魔獸的身體安撫它們的情緒，站在了鐵板上\n",
                        "催促著" + solidername + "也站上來，無法理解現況的" + solidername + "只能隨者莉莉亞的指令移動\n",
                        "莉莉亞念了段咒語，魔獸發出了藍光後，魔獸肚子變成了一個黑洞\n",
                        "兩人便被魔獸吸進了黑洞了，天使發出了慘叫，但也只有" + solidername + "聽得到\n",
                        solidername + "知道就算摀上耳朵也無法阻止，便放棄了抵抗\n",
                        ""},

                reSt4 = {"跟隨著莉莉亞，" + solidername + "來到棉花糖魔獸前\n",
                        "受到外來者的刺激，魔獸發起警戒的姿態\n",
                        "莉莉亞:稍微等我一下"},
                reSt5 = {
                        "正打算上前安撫魔獸的莉莉亞，" + solidername + "卻先行邁出步伐\n",
                        "熟練地撫摸著魔獸地軀體，不久便讓魔獸都沉靜下來\n",
                        "隨後看著地板上的鐵板，不自覺地站了上去，並用目光示意莉莉亞上前\n",
                        "莉莉亞楞愣地看著" + solidername + "幾秒，回過神來後念起咒語\n",
                        "閃耀著藍光的魔獸，黑洞強大的引力將兩人吸入，在即將無法看見周遭的一切時\n",
                        "莉莉亞疑惑地看向" + solidername + "，說道\n",
                        "莉莉亞:你曾經來過?\n", ""};
        if (reLife) {
            st4 = reSt4;
            button.setDisable(false);
        }

        if (a.beHero() == 1) {
            reLife = true;
            achShow.setText("命中注定\n成就已達成");
            achShow.setVisible(true);
            achFade.playFromStart();
            Media media1 = new Media(new File("src/resource/music/熊吼.mp3").toURI().toString());
            achMusic = new MediaPlayer(media1);
            achMusic.setStartTime(Duration.millis(0));
            achMusic.setCycleCount(1);
            achMusic.setAutoPlay(true);
            achMusic.setVolume(0.3);
        }

        stackPane.setOpacity(1);
        gradually.setText("");
        temp = "";
        count = 0;
        nameInput.setDisable(true);
        nameInput.setVisible(false);
        nameCheck.setDisable(true);
        nameCheck.setVisible(false);
        yourName.setVisible(false);
        Timeline timeline = new Timeline();
        Timeline timeline1 = new Timeline();
        Timeline timeline2 = new Timeline();
        timeline.setCycleCount(st4.length - 1);

        timeline2.setCycleCount(1);
        AnimateText(story, st4[count]);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stackPane.setVisible(false);
                Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                Parent root1 = null;
                try {
                    root1 = FXMLLoader.load(getClass().getClassLoader().getResource("VillageFX/village.fxml"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
                currentStage.setTitle("village");
            }
        });

        timeline.getKeyFrames().add(storyStart(st4));
        timeline.setOnFinished((event) -> {
            temp = "";
            story.setText(temp);
            count = 0;
            if (reLife) {
                timeline1.setCycleCount(reSt5.length);
                timeline1.getKeyFrames().add(storyChange(reSt5));
            } else {
                timeline1.setCycleCount(st5.length);
                timeline1.getKeyFrames().add(storyChange(st5));
            }
            timeline1.play();
        });

        timeline1.setOnFinished((event) -> {
            try {
                Thread.currentThread().sleep(1000);//毫秒
            } catch (Exception exc) {
            }
            temp = "";
            story.setText(temp);
            count = 0;
            mediaPlayer.stop();
            monster.setVisible(false);
            fadeIn.setNode(stackPane);
            fadeIn.setFromValue(1);
            fadeIn.setToValue(0);
            fadeIn.setCycleCount(1);
            fadeIn.playFromStart();
            timeline2.getKeyFrames().add(keyFrame2);
            timeline2.play();
        });
        timeline.play();
    }
}