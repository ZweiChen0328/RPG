package ThefightFX;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import profile.Soilder;
import profile.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


//------------------------------------------------------------------------------------------------------------
public class Controller implements Initializable {
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView Yusha, Mao;
    @FXML
    private Button Y1, Y, N;
    @FXML
    private Label Script, soliderhp1, bosshp1, attackmessage, introduce, skillround2, soliderfa, solidernamelable, bossname;
    @FXML
    private ListView<String> listView;
    @FXML
    private FileChooser filechooser = new FileChooser();


    private int count = 0;
    private boolean flag = true, flag2 = true;

    //------------------------------------------------------------------------
    String[] st = {"[勇者]:來吧!該做最後的了結我不會再讓你留在世上禍害人間!", "你拿起了刀，用力握緊向魔王衝去．．．．．．．", "[魔王]:痛苦吧！怨恨我吧！佐助？", "魔王輕易的躲過勇者揮下去的攻擊", "勇者的攻擊十分強大，但不能擊中魔王也是無濟於事"
            , "[勇者]:不可以..我不能在這裡停下來", "我的家人，我的朋友，世上剩餘的人類", "勇者用盡全力使出了他獨特招式末日龍捲!", "儘管這樣會使他失去多年來累積的能力"
            , "又或者這可能會使他失去生命!", "勇者還是不停的蓄力，要與魔王同歸於盡", "[魔王]:愚蠢的人類..你的能耐也不過如此!", "[魔王]:來吧．帶著怨恨死去吧！", "[天使]:勇者大人!不可以這麼做...."
            , "勇者奮力衝向魔王.....", "[勇者]:消失吧!末日龍捲!"};
    //------------------------------------------
    Soilder soilder1 = new Soilder();
    boss boss1 = new boss();
    MediaPlayer mediaPlayer;

    //-----------------------------------
    public void nameButton(ActionEvent e) throws Exception {
        Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Parent root1 = null;
        try {
            root1 = FXMLLoader.load(getClass().getClassLoader().getResource("storyFX/story.fxml"));
        } catch (IOException a) {
            a.printStackTrace();
        }
        currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
        currentStage.setTitle("Battle");
    }

    public void initialize(URL location, ResourceBundle resources) {
        Mao.setFitWidth(600);
        Mao.setFitHeight(600);
        Yusha.setFitWidth(600);
        Yusha.setFitHeight(600);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), attackmessage);
        Script.setText("劇情旁白");
        attackmessage.setTextFill(Paint.valueOf("white"));
        Timeline timeline = new Timeline();
        AnimateText(introduce, "魔王降臨100年後，終於出現了首位勇者可與之一戰");
        pane.setBackground(new Background(
                new BackgroundImage(new Image("resource/img/fight.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(1200, 675, false, false, false, true))));
        timeline.setCycleCount(st.length);
        Timeline timeline1 = new Timeline();
        timeline1.setCycleCount(1);
        Timeline timeline2 = new Timeline();
        timeline2.setCycleCount(1);
        Timeline timeline3 = new Timeline();
        timeline3.setCycleCount(1);
        Timeline timeline4 = new Timeline();
        timeline4.setCycleCount(1);
        Timeline timeline5 = new Timeline();
        timeline5.setCycleCount(1);
        KeyFrame keyFrame6 = new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {//接到下一個畫面
                mediaPlayer.stop();
                timeline2.setOnFinished((e) -> {
                    Stage currentStage = (Stage) pane.getScene().getWindow();
                    Parent root1 = null;
                    try {
                        root1 = FXMLLoader.load(getClass().getClassLoader().getResource("StoryFX/story.fxml"));
                    } catch (IOException ioException) {
                        System.out.println("error");
                        ioException.printStackTrace();
                    }
                    currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
                    currentStage.setTitle("story");
                });
            }
        });
        KeyFrame keyFrame4 = new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                attackmessage.setText("[天使]:趕快醒醒啊!勇者!");
                attackmessage.setOpacity(0);
                fadeTransition.setFromValue(0);//起始透明度为1
                fadeTransition.setToValue(1);//终止透明度为0.1
                fadeTransition.setCycleCount(1);//无限期动画
                fadeTransition.play();
                timeline2.getKeyFrames().add(keyFrame6);
                timeline2.play();
            }
        });
        KeyFrame keyFrame = new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String temp = st[count];
                count++;
                AnimateText(introduce, temp);
            }
        });
        KeyFrame keyFrame5 = new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fadeTransition.setFromValue(1);//起始透明度为1
                fadeTransition.setToValue(0);//终止透明度为0.1
                fadeTransition.setCycleCount(1);//无限期动画
                fadeTransition.play();
                timeline4.getKeyFrames().add(keyFrame4);
                timeline4.play();
            }
        });
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mediaPlayer.stop();
                Media media = new Media(new File("src/resource/music/鳥.mp3").toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(2);
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.setVolume(0.1);
                pane.setBackground(new Background(new BackgroundFill(Paint.valueOf("black"), CornerRadii.EMPTY, Insets.EMPTY)));
                attackmessage.setText("[天使]:勇者...快醒醒!");
                attackmessage.setOpacity(0);
                fadeTransition.setFromValue(0);//起始透明度为1
                fadeTransition.setToValue(1);//终止透明度为0.1
                fadeTransition.setCycleCount(1);//无限期动画
                fadeTransition.play();
                timeline3.getKeyFrames().add(keyFrame5);
                timeline3.play();
            }
        });
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(68000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mediaPlayer.stop();
                Media media = new Media(new File("src/resource/music/轟隆.mp3").toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(1);
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.setVolume(0.1);
                Mao.setVisible(false);
                Yusha.setVisible(false);
                soliderfa.setVisible(false);
                bosshp1.setVisible(false);
                introduce.setText("");
                Script.setText("");
                solidernamelable.setVisible(false);
                bossname.setVisible(false);
                soliderhp1.setVisible(false);
                introduce.setVisible(false);
                pane.setBackground(new Background(new BackgroundFill(Paint.valueOf("white"), CornerRadii.EMPTY, Insets.EMPTY)));
                timeline5.getKeyFrames().add(keyFrame2);
                timeline5.play();
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        timeline1.getKeyFrames().add(keyFrame1);
        timeline1.play();
        /*try {
            Mao.setImage(new Image(new File("src/resource/img/mao.png").toURI().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Yusha.setImage(new Image(new File("src/resource/img/solider.png").toURI().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                Label label = new Label();//設定Label作為容器
                ListCell<String> cell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {//有值的情况下 给label设值
                            label.setText(item);//將字串設定在label的文字
                            label.setStyle("-fx-text-fill:white;-fx-font-size:12;-fx-pref-height:20;");
                            //對Label進行css設定
                            this.setGraphic(label);//listview中的欄位設定使用label顯示
                        }
                    }
                };
                cell.setStyle("-fx-background-color:black");
                return cell;
            }
        });
        Media media = new Media(new File("src/resource/music/fightmusic.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        //mediaPlayer.setStartTime(Duration.millis(3500));
        mediaPlayer.setCycleCount(20);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.1);
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
}
