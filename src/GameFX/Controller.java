package GameFX;

import achievementFX.Achievement;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import profile.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import static VillageFX.Controller.*;
import static StoryFX.Controller.*;
import static plot1FX.plot1Controller.angel;

//------------------------------------------------------------------------------------------------------------
public class Controller implements Initializable {
    @FXML
    HBox THEHBOX;
    @FXML
    ProgressBar progressBar, progressBar2;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView Yusha, Mao, anime, property, attack, attack2, defense;
    @FXML
    private Button atk, item, coward, uses, cancel, skill;
    @FXML
    private Label Script, soliderhp1, bosshp1, attackmessage, introduce, skillround2, soliderfa, bossfa, solidernamelable, abc;
    @FXML
    private ListView<String> listView;
    @FXML
    private ListView<String> achShowList;

    private int skillnumber, count = 0;
    static int round = 0, round_2 = -1, round_3 = -1, trans = 0,boss_heal=0;//回合
    private boolean flag = true, flag2 = true;
    private FadeTransition achFade = new FadeTransition(
            Duration.millis(5000)
    );
    Achievement ach = new Achievement();
    ObservableList<String> achList = FXCollections.observableArrayList();
    //------------------------------------------------------------------------
    String[] st = {"狂風呼嘯，強大的氣場朝你席捲而來，你緊握刀的雙手正在不停冒汗\n面對這樣強大的敵人，你也認為戰勝的機率十分渺茫....", "世界上的人類將命運寄託在勇者的身上，若你不勇敢戰鬥\n傷亡將會不停擴大，世界崩壞，生靈塗炭．．．AAAAAA", "在強大的壓力下，你想起了村莊人的期望，還有身邊天使的加持\n你握緊了刀眼神也更加堅定準備背水一戰",
            "［天使］：勇者大人！！請你握緊手中的刀，帶著大家的期望，勇敢的戰鬥吧！！！"};
    String[] st1 = {"[魔王]:在你們這些無趣的肥宅面前\n我也該展現出我全部的實力了", "這個時候魔王舉起雙手!魔王開始變身!背景音樂響起!\n轟隆隆隆隆隆隆，衝衝衝衝拉風，引擎發動!",
            "［天使］：勇者大人請小心！他為了要看國棟的直播\n已經獲得了無比的力量!"};
    String[] st2 = {"[魔王]:平時我也會看看國棟的影片..", "[魔王]:抓到你囉，鬼來電，滋滋滋滋", "[魔王]:你誰都可以得罪，就不要得罪瘋子R", "［天使］：注意技能的回合冷卻!", "［天使］：物資缺乏!合理使用物品", "［天使］：魔王在使用技能後出現屬性!"
            , "［天使］：使用屬性克制可有可觀傷害!", "［天使］：克制的技能屬性傷害會降低!", "[魔王]:直升飛機起飛囉", "[揚勛]:我不再大家要想我喔~揚勛好帥", "[魔王]:哭，去旁邊給我哭一個小時", "[魔王]:這到底是甚麼閃現"};
    public static int[] itemnumber = {1, 3, 3, 2, 1}/*物品剩餘數量*/;
    static int[] skillround = {0, 0, 0, 0, 0, 0};//技能回合數
    //------------------------------------------
    Soilder soilder1 = new Soilder();
    boss boss1 = new boss();
    Random random = new Random();
    MediaPlayer mediaPlayer, mediaPlayer2, achMusic;
    static MediaPlayer mediaPlayer3;

    //-----------------------------------
    public void initialize(URL location, ResourceBundle resources) {
        progressBar.setVisible(false);
        progressBar2.setVisible(false);
        soliderhp1.setVisible(false);
        bosshp1.setVisible(false);
        progressBar.setStyle("-fx-accent: red;");
        progressBar2.setStyle("-fx-accent: #00FF00;");
        achShowList.setStyle("-fx-background-color: #000000; -fx-control-inner-background: #000000; -fx-border-color: white; -fx-text-fill: #00000000; -fx-font-size: 15px;");
        //成就顯示列表CSS
        achFade.setNode(achShowList);
        achFade.setFromValue(1);
        achFade.setToValue(0);
        achFade.setCycleCount(1);
        achFade.setAutoReverse(false);
        //成就顯示慢慢消失
        try {
            if (ach.modifyMaoHighestLife(boss1) == 1) {
                achList.removeAll(achList);
                achList.addAll("魔王的靈壓消失了", "成就已達成");
                achShowList.setItems(achList);
                achShowList.setVisible(true);
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
        //判斷魔王血量是否被改成0
        soliderfa.setTextFill(Paint.valueOf("red"));
        soilder1.setName(solidername);
        soilder1.setAttack(soilder1.attack + damage);
        soilder1.setDefend(soilder1.defend + adddefense);
        soilder1.setLife(soilder1.life + hp);
        soilder1.setHighest_life(soilder1.highest_life + hp);
        solidernamelable.setText(soilder1.name);
        disableButtom();
        Script.setText("劇情旁白");
        AnimateText(introduce, "同樣的敵人再次站在你面前，曾經被擊敗的滋味不停湧上心頭\n可怕的單眼皮讓你感受到曾經被支配的恐懼.....");
        Timeline timeline = new Timeline();
        timeline.setCycleCount(st.length);
        Timeline timeline1 = new Timeline();
        timeline1.setCycleCount(1);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(4200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String temp = st[count];
                count++;
                AnimateText(introduce, temp);
            }
        });
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(22000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Yusha.setImage(new Image(new File("src/resource/img/勇者閉眼.gif").toURI().toString()));
                Mao.setImage(new Image(new File("src/resource/img/mao.gif").toURI().toString()));
                introduce.setText("太可怕ㄖ~~~~~~~");
                openButtom();
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        timeline1.getKeyFrames().add(keyFrame1);
        timeline1.play();
        anchorPane.setBackground(new Background(
                new BackgroundImage(new Image("resource/img/fight.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(1200, 675, false, false, false, true))));
        attack.setImage(new Image(new File("src/resource/img/技能++.png").toURI().toString()));
        defense.setImage(new Image(new File("src/resource/img/防禦++.jpg").toURI().toString()));
        attack2.setImage(new Image(new File("src/resource/img/攻擊力++.png").toURI().toString()));
        Mao.setImage(new Image(new File("src/resource/img/mao.png").toURI().toString()));
        Yusha.setImage(new Image(new File("src/resource/img/solider.png").toURI().toString()));
        attack.setVisible(false);
        Mao.setFitWidth(600);
        Mao.setFitHeight(600);
        Yusha.setFitWidth(600);
        Yusha.setFitHeight(600);
        defense.setVisible(false);
        attack2.setVisible(false);
        hpreset();
        //ListViewChange();
        Media media = new Media(new File("src/resource/music/undyne.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setStartTime(Duration.millis(1500));
        mediaPlayer.setCycleCount(500);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.2);
    }

    private void ListViewChange() {//listview的所有變化
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
                            //對Label進行css設定
                            if (item.equals(listView.getSelectionModel().getSelectedItem())) {
                                label.setStyle("-fx-text-fill:yellow;-fx-font-size:22;-fx-pref-height:25;");//有被選擇的物品或技能，外觀設定
                            } else
                                label.setStyle("-fx-text-fill:white;-fx-font-size:22;-fx-pref-height:25;");//沒被選擇的物品或技能，外觀設定
                            this.setGraphic(label);//listview中的欄位設定使用label顯示
                        }
                    }
                };
                cell.setStyle("-fx-background-color:black");
                return cell;
            }
        });
    }

    @FXML
    public void atkButton(ActionEvent e) throws SQLException {//攻擊按鈕事件
        soliderfa.setTextFill(Paint.valueOf("red"));
        Script.setText("普通攻擊");
        introduce.setText("");
        skillround2.setText("");
        flag = true;
        flag2 = true;
        listView.setVisible(false);
        listView.setDisable(true);
        round += 1;
        System.out.println(round);
        disableButtom();
            Timeline timeline = new Timeline();
            Timeline timeline2 = new Timeline();
            Timeline timeline3 = new Timeline();
            timeline.setCycleCount(1);
            timeline2.setCycleCount(1);
            timeline3.setCycleCount(1);
            KeyFrame start3 = new KeyFrame(Duration.millis(4500), new EventHandler<ActionEvent>() {//勇者攻擊
                @Override
                public void handle(ActionEvent actionEvent) {
                    resetskilltime();
                    if (trans == 0)
                        Mao.setImage(new Image(new File("src/resource/img/mao.gif").toURI().toString()));
                    else
                        Mao.setImage(new Image(new File("src/resource/img/mao2.gif").toURI().toString()));
                    inallmessage();
                }
            });

            KeyFrame start2 = new KeyFrame(Duration.millis(3000), new EventHandler<ActionEvent>() {//魔王攻擊
                @Override
                public void handle(ActionEvent actionEvent) {
                    attackmessage.setTextFill(Paint.valueOf("red"));
                    try {
                        bossattack();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    angelsay("attack");
                    if (soilder1.die()) {
                        timeline3.getKeyFrames().addAll(start3);
                        timeline3.play();//取消數字
                    } else {
                        mediaPlayer.stop();
                        Stage currentStage = (Stage) anchorPane.getScene().getWindow();
                        Parent root1 = null;
                        try {
                            root1 = FXMLLoader.load(getClass().getClassLoader().getResource("EndFX/badend.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
                        currentStage.setTitle("Badend");
                    }
                }
            });
            KeyFrame start = new KeyFrame(Duration.millis(1500), new EventHandler<ActionEvent>() {//勇者攻擊
                Achievement a = new Achievement();
                @Override
                public void handle(ActionEvent actionEvent) {
                    playmusic("src/resource/music/勇者普攻.mp3");
                    if (trans == 0)
                        Mao.setImage(new Image(new File("src/resource/img/mao1普攻.gif").toURI().toString()));
                    else
                        Mao.setImage(new Image(new File("src/resource/img/mao2普攻.gif").toURI().toString()));
                    try {
                        bossfa.setText(Integer.toString(boss1.fangyu(soilder1.attack)));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    hpreset();
                    if (boss1.die()) {
                        timeline2.getKeyFrames().addAll(start2);
                        timeline2.play();//魔王攻擊動畫
                    } else {
                        Timeline timeline5 = new Timeline();
                        timeline5.setCycleCount(1);
                        Timeline timeline6 = new Timeline();
                        timeline5.setCycleCount(1);
                        Timeline timeline7 = new Timeline();
                        timeline5.setCycleCount(1);
                        KeyFrame start8 = new KeyFrame(Duration.millis(3000), new EventHandler<ActionEvent>() {//勇者攻擊
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    if (a.moreSpeed(round, boss1) == 1) {
                                        achList.removeAll(achList);
                                        achList.addAll("快還要再快", "成就已達成");
                                    }
                                    if (a.otkMao(round, boss1) == 1) {
                                        achList.addAll("真材實料", "成就已達成");
                                    }
                                    if (a.hackHero(soilder1, boss1) == 1) {
                                        achList.addAll("接受命運", "成就已達成");
                                    }
                                    achShowList.setItems(achList);
                                    achShowList.setVisible(true);
                                    achFade.playFromStart();
                                    Media media1 = new Media(new File("src/resource/music/熊吼.mp3").toURI().toString());
                                    achMusic = new MediaPlayer(media1);
                                    achMusic.setStartTime(Duration.millis(0));
                                    achMusic.setCycleCount(1);
                                    achMusic.setAutoPlay(true);
                                    achMusic.setVolume(0.3);
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                mediaPlayer.stop();
                                Stage currentStage = (Stage) anchorPane.getScene().getWindow();
                                Parent root1 = null;
                                try {
                                    root1 = FXMLLoader.load(getClass().getClassLoader().getResource("EndFX/normal.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
                                currentStage.setTitle("Normalend");
                            }
                        });
                        KeyFrame start7 = new KeyFrame(Duration.millis(3000), new EventHandler<ActionEvent>() {//勇者攻擊
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                bossdie();
                                anchorPane.setStyle("-fx-background-color:white");
                                timeline7.getKeyFrames().addAll(start8);
                                timeline7.play();//勇者攻擊動畫
                            }
                        });
                        KeyFrame start4 = new KeyFrame(Duration.millis(5000), new EventHandler<ActionEvent>() {//勇者攻擊
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Script.setText("打贏MAO了");
                                Mao.setImage(new Image(new File("src/resource/img/魔王死亡.gif").toURI().toString()));
                                AnimateText(introduce, "[魔王]:你真的變了很強了呢!兒子..原諒爸爸這樣測試你..\n你必需要越來越強....今晚的直播..不能看了呢..?");
                                timeline6.getKeyFrames().addAll(start7);
                                timeline6.play();//勇者攻擊動畫
                            }
                        });
                        timeline5.getKeyFrames().addAll(start4);
                        timeline5.play();
                    }
                }
            });
            timeline.getKeyFrames().addAll(start);
            timeline.play();//勇者攻擊動畫
        }


    @FXML
    public void skillButton(ActionEvent e) throws Exception {
        Script.setText("技能選單");
        button_voice();
        skillround2.setText("");
        listView.getItems().clear();
        ObservableList<String> items = FXCollections.observableArrayList("千陽新星", "螺旋巨渦", "遠古巨木", "末日龍捲", "暗影偽典", "天鷹之耀");
        listView.setItems(items);//寫入資料
        ListViewChange();
        introduce.setText("");
        if (flag) {
            listView.setVisible(true);//顯示技能列表
            listView.setDisable(false);
            flag = false;
            flag2 = true;
        } else {
            listView.setVisible(false);
            listView.setDisable(true);
            flag = true;
        }
        uses.setVisible(false);//設定使用按鈕不可看不可用
        uses.setDisable(true);
        cancel.setVisible(false);//設定取消按鈕不可看不可用
        cancel.setDisable(true);
    }

    @FXML
    public void cowardButton(ActionEvent e) throws Exception {//逃跑按鈕事件  成就的部分
        soliderfa.setTextFill(Paint.valueOf("red"));
        uses.setVisible(false);//設定使用按鈕不可看不可用
        uses.setDisable(true);
        cancel.setVisible(false);//設定取消按鈕不可看不可用
        cancel.setDisable(true);
        listView.setVisible(false);
        listView.setDisable(true);
        flag = true;
        flag2 = true;
        try {
            if (ach.runAway() == 1) {
                achList.removeAll(achList);
                achList.addAll("懦弱的選擇", "成就已達成");
                achShowList.setItems(achList);
                achShowList.setVisible(true);
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
        button_voice();
        Script.setText("你不可以逃跑!");
        introduce.setText("{天使}:勇者大人,你必須堅持你的決心!");//成就部分
        skillround2.setText("");
    }

    @FXML
    public void itemButton(ActionEvent e) throws Exception {//物品列表顯示事件
        soliderfa.setTextFill(Paint.valueOf("red"));
        Script.setText("物品選單");
        button_voice();
        skillround2.setText("");
        listView.getItems().clear();
        ObservableList<String> items = FXCollections.observableArrayList("香菜                       " + Integer.toString(itemnumber[0])
                , "茄子                       " + Integer.toString(itemnumber[1]), "青椒                       " + Integer.toString(itemnumber[2]), "苦瓜                       " + Integer.toString(itemnumber[3])
                , "披薩加鳳梨           " + Integer.toString(itemnumber[4]));
        listView.setItems(items);//寫入資料
        ListViewChange();
        introduce.setText("");
        if (flag2) {
            listView.setVisible(true);//顯示物品列表
            listView.setDisable(false);
            flag2 = false;
            flag = true;
        } else {
            listView.setVisible(false);//收起物品列表
            listView.setDisable(true);
            flag2 = true;
            Script.setText("");
        }
        uses.setVisible(false);//設定使用按鈕不可看不可用
        uses.setDisable(true);
        cancel.setVisible(false);//設定取消按鈕不可看不可用
        cancel.setDisable(true);
    }

    @FXML
    public void listClick(MouseEvent e) {//點擊列表欄位觸發事件
        if (listView.getSelectionModel().getSelectedItem() != null) {
            introduce.setText("");
            ListViewChange();
            String a = listView.getSelectionModel().getSelectedItem();//從點擊的列表取得該列表文字資料
            String[] a1 = a.split("\\s+");
            button_voice();
            skillround2.setText("");
            uses.setVisible(true);//設定使用按鈕可看可用
            uses.setDisable(false);
            cancel.setVisible(true);//設定取消按鈕可看可用
            cancel.setDisable(false);
            Script.setText(a1[0]);//從點擊的欄位取得該欄位的資訊
            switch (a1[0]) {
                case "香菜"://判斷
                    if (itemnumber[0] == 0)
                        uses.setDisable(true);
                    else
                        uses.setDisable(false);
                    introduce.setText("聞起來很噁心看起來也很噁心，村長給得奇怪補品\n似乎沒什麼效果..真想來碗大腸麵線絕配一下");
                    skillround2.setText("好吃的香菜來一口嗎?");
                    break;
                case "茄子":
                    if (itemnumber[1] == 0)
                        uses.setDisable(true);
                    else
                        uses.setDisable(false);
                    introduce.setText("老子最喜歡硬硬長長粗粗壯壯的東西了\n香甜可口 吹彈可破");
                    skillround2.setText("回復2500hp");
                    break;
                case "青椒":
                    if (itemnumber[2] == 0)
                        uses.setDisable(true);
                    else
                        uses.setDisable(false);
                    introduce.setText("[大姊姊你喜歡吃青椒嗎??我也不喜歡吃喔!]\n--蠟筆小新第87集");
                    skillround2.setText("回復3500hp");
                    break;
                case "苦瓜":
                    if (itemnumber[3] == 0)
                        uses.setDisable(true);
                    else
                        uses.setDisable(false);
                    introduce.setText("勇者是怎麼鍛鍊出來的?就是把苦瓜!!.....\n吃得苦中苦方為人上人!");
                    skillround2.setText("攻擊力堤身持續5回合");
                    break;
                case "披薩加鳳梨":
                    if (itemnumber[4] == 0)
                        uses.setDisable(true);
                    else
                        uses.setDisable(false);
                    introduce.setText("珍珠奶茶pizza? no! 拉麵pizza? no!\n老子只吃鳳梨夏威夷pizza的啦!!的啦!?");
                    skillround2.setText("回復全部的血量至全滿");
                    break;
                case "千陽新星":
                    if (skillround[0] <= 0)
                        uses.setDisable(false);
                    else
                        uses.setDisable(true);
                    introduce.setText("家族傳承下來的秘術\n發出火屬性般強烈的斬擊");
                    skillround2.setText("回合冷卻時間: 2回合");
                    break;
                case "螺旋巨渦":
                    if (skillround[1] <= 0)
                        uses.setDisable(false);
                    else
                        uses.setDisable(true);
                    introduce.setText("勇者的手，噴水利器！\n發出水屬性般強烈的水柱");
                    skillround2.setText("回合冷卻時間: 2回合");
                    break;
                case "遠古巨木":
                    if (skillround[2] <= 0)
                        uses.setDisable(false);
                    else
                        uses.setDisable(true);
                    introduce.setText("木遁！花樹界降臨？\n發出木屬性般強烈的衝擊");
                    skillround2.setText("回合冷卻時間: 2回合");
                    break;
                case "末日龍捲":
                    if (skillround[3] <= 0)
                        uses.setDisable(false);
                    else
                        uses.setDisable(true);
                    introduce.setText("使用全身力量從手中召喚龍捲\n造成大量攻擊");
                    skillround2.setText("回合冷卻時間: 10回合");
                    break;
                case "暗影偽典":
                    if (skillround[4] <= 0)
                        uses.setDisable(false);
                    else
                        uses.setDisable(true);
                    introduce.setText("暗影包覆全身，力量湧上\n攻擊力防禦力提升5回合");
                    skillround2.setText("回合冷卻時間: 7回合");
                    break;
                case "天鷹之耀":
                    if (skillround[5] <= 0)
                        uses.setDisable(false);
                    else
                        uses.setDisable(true);
                    introduce.setText("天使般的光照耀，傷口漸漸癒合\n回復自身5000HP");
                    skillround2.setText("回合冷卻時間: 5回合");
                    break;
            }
        }
    }

    @FXML
    public void usesButton() {
        skillround2.setText("");
        flag = true;
        flag2 = true;
        listView.setVisible(false);
        listView.setDisable(true);
        introduce.setText("");
        disableButtom();
        round += 1;
        System.out.println(round);
        if (boss1.die()) {
            Timeline timeline = new Timeline();
            Timeline timeline2 = new Timeline();
            Timeline timeline3 = new Timeline();
            timeline.setCycleCount(1);
            timeline2.setCycleCount(1);
            timeline3.setCycleCount(1);
            KeyFrame start3 = new KeyFrame(Duration.millis(4500), new EventHandler<ActionEvent>() {//勇者攻擊
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (trans == 0)
                        Mao.setImage(new Image(new File("src/resource/img/mao.gif").toURI().toString()));
                    else
                        Mao.setImage(new Image(new File("src/resource/img/mao2.gif").toURI().toString()));
                    inallmessage();
                    resetskilltime();
                    Yusha.setImage(new Image(new File("src/resource/img/勇者閉眼.gif").toURI().toString()));
                }
            });
            KeyFrame start2 = new KeyFrame(Duration.millis(3000), new EventHandler<ActionEvent>() {//魔王攻擊
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        bossattack();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    angelsay("attack");
                    anime.setVisible(false);
                    if (soilder1.die()) {
                        timeline3.getKeyFrames().addAll(start3);
                        timeline3.play();//取消數字
                    } else {
                        //勇者戰敗
                        Stage currentStage = (Stage) anchorPane.getScene().getWindow();
                        Parent root1 = null;
                        try {
                            root1 = FXMLLoader.load(getClass().getClassLoader().getResource("EndFX/badend.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
                        currentStage.setTitle("Badend");
                    }
                }
            });
            KeyFrame start = new KeyFrame(Duration.millis(1500), new EventHandler<ActionEvent>() {//勇者攻擊
                @Override
                public void handle(ActionEvent actionEvent) {
                    String a = listView.getSelectionModel().getSelectedItem();//從點擊的列表取得該列表文字資料
                    String[] a1 = a.split("\\s+");
                    attackmessage.setTextFill(Paint.valueOf("white"));
                    anime.setFitWidth(250);
                    anime.setFitHeight(250);
                    switch (a1[0]) {
                        case "香菜"://判斷
                            Achievement ach = new Achievement();
                            attackmessage.setText("使用了香菜什麼都沒有發生");//這可以是成就
                            try {
                                if (ach.eatParsley() == 1) {
                                    achList.removeAll(achList);
                                    achList.addAll("真正的勇者", "成就已達成");
                                    achShowList.setItems(achList);
                                    achShowList.setVisible(true);
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
                            itemnumber[0]--;
                            Script.setText("使用了香菜");
                            break;
                        case "茄子":
                            playmusic("src/resource/music/補血.mp3");
                            Yusha.setImage(new Image(new File("src/resource/img/勇者補血.gif").toURI().toString()));
                            soilder1.usefood(2500);
                            soliderfa.setTextFill(Paint.valueOf("green"));
                            soliderfa.setText("+2500");
                            Script.setText("使用了茄子");
                            attackmessage.setText("紫色的，長長的，真是好用！使用了茄子回復了2500HP");
                            itemnumber[1]--;
                            break;
                        case "青椒":
                            playmusic("src/resource/music/補血.mp3");
                            Yusha.setImage(new Image(new File("src/resource/img/勇者補血.gif").toURI().toString()));
                            soilder1.usefood(3500);
                            soliderfa.setTextFill(Paint.valueOf("green"));
                            soliderfa.setText("+3500");
                            Script.setText("使用了青椒");
                            attackmessage.setText("姊姊喜歡吃喔!使用了青椒回復了3500HP");
                            itemnumber[2]--;
                            break;
                        case "苦瓜":
                            playmusic("src/resource/music/補血.mp3");
                            Yusha.setImage(new Image(new File("src/resource/img/勇者補血.gif").toURI().toString()));
                            soilder1.setAttack(soilder1.attack + 1000);
                            round_2 = round + 5;
                            Script.setText("使用了苦瓜");
                            attackmessage.setText("使用了苦瓜獲得了攻擊力提升...幹好苦喔!");
                            attack2.setVisible(true);
                            itemnumber[3]--;
                            break;
                        case "披薩加鳳梨":
                            playmusic("src/resource/music/補血.mp3");
                            Yusha.setImage(new Image(new File("src/resource/img/勇者補血.gif").toURI().toString()));
                            soilder1.setLife(soilder1.highest_life);
                            soliderfa.setTextFill(Paint.valueOf("green"));
                            Script.setText("使用了披薩加鳳梨");
                            soliderfa.setText("+" + String.valueOf(soilder1.highest_life));
                            attackmessage.setText("嘿嘿!重生囉!!");
                            attackmessage.setText("使用了披薩加鳳梨，HP回復至全滿！魔王似乎很討厭這種搭配，魔王的攻擊更加兇猛了");
                            itemnumber[4]--;
                            break;
                        case "千陽新星":
                            anime.setImage(new Image(new File("src/resource/img/太陽.gif").toURI().toString()));
                            anime.setVisible(true);
                            Script.setText("使用了千陽新星");
                            playmusic("src/resource/music/千陽新星.mp3");
                            skillround[0] = 2;
                            if (boss1.property == "water") {
                                try {
                                    bossfa.setText(Integer.toString(boss1.fangyu(soilder1.attack / 2)));
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                attackmessage.setText("使用千陽新星,看起來沒效果");
                            } else if (boss1.property == "wood") {
                                try {
                                    bossfa.setText(Integer.toString(boss1.fangyu(soilder1.attack * 2)));//攻擊函式
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                attackmessage.setText("使用千陽新星,效果很顯著");
                            } else {
                                try {
                                    bossfa.setText(Integer.toString(boss1.fangyu(soilder1.attack)));
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                attackmessage.setText("使用千陽新星,效果很普通");
                            }
                            break;
                        case "螺旋巨渦":
                            playmusic("src/resource/music/螺旋丸.mp3");
                            anime.setImage(new Image(new File("src/resource/img/螺旋丸.gif").toURI().toString()));
                            anime.setVisible(true);
                            Script.setText("使用了螺旋巨渦");
                            skillround[1] = 2;
                            if (boss1.property == "water") {
                                try {
                                    bossfa.setText(Integer.toString(boss1.fangyu(soilder1.attack)));
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                attackmessage.setText("使用螺旋巨渦,看起來很普通");
                            } else if (boss1.property == "wood") {
                                try {
                                    bossfa.setText(Integer.toString(boss1.fangyu(soilder1.attack / 2)));//攻擊函式
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                attackmessage.setText("使用螺旋巨渦,看起來沒效果");
                            } else {
                                try {
                                    bossfa.setText(Integer.toString(boss1.fangyu(soilder1.attack * 2)));
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                attackmessage.setText("使用螺旋巨渦,效果很顯著");
                            }
                            break;
                        case "遠古巨木":
                            anime.setImage(new Image(new File("src/resource/img/巨木.gif").toURI().toString()));
                            anime.setVisible(true);
                            Script.setText("使用了遠古巨木");
                            skillround[2] = 2;
                            if (boss1.property == "water") {
                                try {
                                    bossfa.setText(Integer.toString(boss1.fangyu(soilder1.attack * 2)));
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                attackmessage.setText("使用遠古巨木,效果很顯著");
                            } else if (boss1.property == "wood") {
                                try {
                                    bossfa.setText(Integer.toString(boss1.fangyu(soilder1.attack)));
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                attackmessage.setText("使用遠古巨木,看起來很普通");
                            } else {
                                try {
                                    bossfa.setText(Integer.toString(boss1.fangyu(soilder1.attack / 2)));//攻擊函式
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                attackmessage.setText("使用遠古巨木,看起來沒有效果");
                            }
                            break;
                        case "末日龍捲":
                            playmusic("src/resource/music/龍捲風.mp3");
                            anime.setImage(new Image(new File("src/resource/img/龍捲風.gif").toURI().toString()));
                            anime.setVisible(true);
                            Script.setText("使用了末日龍捲");
                            skillround[3] = 10;
                            try {
                                bossfa.setText(Integer.toString(boss1.fangyu(soilder1.attack * 3)));
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            attackmessage.setText("大絕招給他死!");
                            break;
                        case "暗影偽典":
                            playmusic("src/resource/music/補血.mp3");
                            Script.setText("使用了暗影偽典");
                            Yusha.setImage(new Image(new File("src/resource/img/勇者補血.gif").toURI().toString()));
                            skillround[4] = 5;
                            soilder1.setAttack(soilder1.attack + 1000);
                            soilder1.setDefend(soilder1.defend + 800);
                            attackmessage.setText("喔喔喔充滿了力量!!");
                            attack.setVisible(true);
                            defense.setVisible(true);
                            round_3 = round + 7;
                            break;
                        case "天鷹之耀":
                            playmusic("src/resource/music/補血.mp3");
                            Script.setText("使用了天鷹之耀");
                            Yusha.setImage(new Image(new File("src/resource/img/勇者補血.gif").toURI().toString()));
                            skillround[5] = 5;
                            soilder1.usefood(5000);
                            soliderfa.setTextFill(Paint.valueOf("green"));
                            soliderfa.setText("+5000");
                            attackmessage.setText("嘿嘿!重生囉!!");
                            break;
                    }
                    hpreset();
                    if (boss1.die()) {
                        timeline2.getKeyFrames().addAll(start2);
                        timeline2.play();//魔王攻擊動畫
                    } else {
                        //魔王死掉了
                        Achievement ach = new Achievement();
                        try {
                            if (ach.moreSpeed(round, boss1) == 1) {
                                achList.removeAll(achList);
                                achList.addAll("快還要再快", "成就已達成");
                                achShowList.setItems(achList);
                                if (ach.otkMao(round, boss1) == 1) {
                                    achList.addAll("真材實料", "成就已達成");
                                    achShowList.setItems(achList);
                                    if (ach.hackHero(soilder1, boss1) == 1) {
                                        achList.addAll("命運", "成就已達成");
                                        achShowList.setItems(achList);
                                    }
                                }
                                achShowList.setVisible(true);
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
                        Timeline timeline5 = new Timeline();
                        timeline5.setCycleCount(1);
                        Timeline timeline6 = new Timeline();
                        timeline5.setCycleCount(1);
                        Timeline timeline7 = new Timeline();
                        timeline5.setCycleCount(1);
                        KeyFrame start8 = new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {//勇者攻擊
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                mediaPlayer.stop();
                                Stage currentStage = (Stage) anchorPane.getScene().getWindow();
                                Parent root1 = null;
                                try {
                                    root1 = FXMLLoader.load(getClass().getClassLoader().getResource("EndFX/normal.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
                                currentStage.setTitle("Normalend");
                            }
                        });
                        KeyFrame start7 = new KeyFrame(Duration.millis(5000), new EventHandler<ActionEvent>() {//勇者攻擊
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                bossdie();
                                anchorPane.setStyle("-fx-background-color:white");
                                timeline7.getKeyFrames().addAll(start8);
                                timeline7.play();//勇者攻擊動畫
                            }
                        });
                        KeyFrame start4 = new KeyFrame(Duration.millis(5000), new EventHandler<ActionEvent>() {//勇者攻擊
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Script.setText("打贏MAO了");
                                Mao.setImage(new Image(new File("src/resource/img/魔王死亡.gif").toURI().toString()));
                                AnimateText(introduce, "[魔王]:你真的變了很強了呢!兒子..原諒爸爸這樣測試你..\n你必需要越來越強....今晚的直播..不能看了呢..?");
                                timeline6.getKeyFrames().addAll(start7);
                                timeline6.play();//勇者攻擊動畫
                            }
                        });
                        timeline5.getKeyFrames().addAll(start4);
                        timeline5.play();//勇者攻擊動畫
                    }
                }
            });
            timeline.getKeyFrames().addAll(start);
            timeline.play();//勇者攻擊動畫
        }
    }

    //-------------------------------------------------------------------------
    public static void AnimateText2(Label lbl, String descImp) {//打字機對話
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
    public void cancelButton() {//顯示資料回到初始值
        soliderfa.setTextFill(Paint.valueOf("red"));
        button_voice();
        saying();
        uses.setVisible(false);//設定使用按鈕不可看不可用
        uses.setDisable(true);
        cancel.setVisible(false);//設定取消按鈕不可看不可用
        cancel.setDisable(true);
    }

    //---------------------------------內部控制function--------------------
    public static void AnimateText(Label lbl, String descImp) {//打字機
        String content = descImp;
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(3500));
            }
            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                lbl.setText(content.substring(0, n));
            }
        };
        animation.play();
    }

    //-----------------------------------------------------------------
    public void bossattack() throws SQLException {
        soliderfa.setTextFill(Paint.valueOf("red"));
        if (trans == 1&&boss_heal<=0) {
            skillnumber = random.nextInt(4);
        } else {
            skillnumber = random.nextInt(3);
        }
        skillnumber++;
        property.setFitWidth(150);
        property.setFitHeight(150);
        int temp;
        switch (skillnumber) {
            case 1: //魔王的木屬性攻擊
                temp = soilder1.fangyu(boss1.attack);
                soliderfa.setText(Integer.toString(temp));
                if (ach.noTakeDamage(temp) == 1) {
                    achList.removeAll(achList);
                    achList.addAll("你什麼時候產生了有傷到我的錯覺", "成就已達成");
                    achShowList.setItems(achList);
                    achShowList.setVisible(true);
                    achFade.playFromStart();
                    Media media1 = new Media(new File("src/resource/music/熊吼.mp3").toURI().toString());
                    achMusic = new MediaPlayer(media1);
                    achMusic.setStartTime(Duration.millis(0));
                    achMusic.setCycleCount(1);
                    achMusic.setAutoPlay(true);
                    achMusic.setVolume(0.3);
                }
                attackmessage.setText("魔王使用了普通攻擊");
                attackmessage.setText("魔王使用了木屬性攻擊");
                boss1.setProperty("wood");
                playmusic("src/resource/music/木屬攻擊.mp3");
                property.setImage(new Image(new File("src/resource/img/wood.png").toURI().toString()));
                Yusha.setImage(new Image(new File("src/resource/img/woodAtk.gif").toURI().toString()));
                property.setVisible(true);
                break;
            case 2: //魔王的水屬性攻擊
                temp = soilder1.fangyu(boss1.attack);
                soliderfa.setText(Integer.toString(temp));
                if (ach.noTakeDamage(temp) == 1) {
                    achList.removeAll(achList);
                    achList.addAll("你什麼時候產生了有傷到我的錯覺", "成就已達成");
                    achShowList.setItems(achList);
                    achShowList.setVisible(true);
                    achFade.playFromStart();
                    Media media1 = new Media(new File("src/resource/music/熊吼.mp3").toURI().toString());
                    achMusic = new MediaPlayer(media1);
                    achMusic.setStartTime(Duration.millis(0));
                    achMusic.setCycleCount(1);
                    achMusic.setAutoPlay(true);
                    achMusic.setVolume(0.3);
                }
                attackmessage.setText("魔王使用了水屬性攻擊");
                boss1.setProperty("water");
                playmusic("src/resource/music/水屬攻擊.mp3");
                property.setImage(new Image(new File("src/resource/img/water.png").toURI().toString()));
                Yusha.setImage(new Image(new File("src/resource/img/waterAtk.gif").toURI().toString()));
                property.setVisible(true);
                break;
            case 3: //魔王的火屬性攻擊
                temp = soilder1.fangyu(boss1.attack);
                soliderfa.setText(Integer.toString(temp));
                if (ach.noTakeDamage(temp) == 1) {
                    achList.removeAll(achList);
                    achList.addAll("你什麼時候產生了有傷到我的錯覺", "成就已達成");
                    achShowList.setItems(achList);
                    achShowList.setVisible(true);
                    achFade.playFromStart();
                    Media media1 = new Media(new File("src/resource/music/熊吼.mp3").toURI().toString());
                    achMusic = new MediaPlayer(media1);
                    achMusic.setStartTime(Duration.millis(0));
                    achMusic.setCycleCount(1);
                    achMusic.setAutoPlay(true);
                    achMusic.setVolume(0.3);
                }
                attackmessage.setText("魔王使用了火屬性攻擊");
                boss1.setProperty("fire");
                playmusic("src/resource/music/火屬攻擊.mp3");
                property.setImage(new Image(new File("src/resource/img/fire.png").toURI().toString()));
                Yusha.setImage(new Image(new File("src/resource/img/fireAtk.gif").toURI().toString()));
                property.setVisible(true);
                break;
            case 4: //魔王的補血能力
                boss_heal=4;
                boss1.usefood(9999);
                bossfa.setTextFill(Paint.valueOf("green"));
                bossfa.setText("+9999");
                playmusic("src/resource/music/補血.mp3");
                Mao.setImage(new Image(new File("src/resource/img/mao2補血jpg.gif").toURI().toString()));
                attackmessage.setText("魔王使用了技能回復血量");
                break;
            default:
                break;
        }
        boss_heal--;
        hpreset();
    }

    //------------------------------初始化所有狀態---------------------------------//
    public void inallmessage() {
        saying();
        bossfa.setTextFill(Paint.valueOf("red"));
        soliderfa.setTextFill(Paint.valueOf("red"));
        soliderfa.setText("");
        bossfa.setText("");
        Yusha.setImage(new Image(new File("src/resource/img/勇者閉眼.gif").toURI().toString()));
        level_boss();
        attackmessage.setText("");
    }

    //-------------------------------按鈕反白且不能使用避免重複攻擊----------------------------//
    public void disableButtom() {//把按鈕取消fun
        atk.setDisable(true);
        atk.setVisible(false);
        item.setDisable(true);
        item.setVisible(false);
        coward.setDisable(true);
        coward.setVisible(false);
        uses.setDisable(true);
        uses.setVisible(false);
        cancel.setDisable(true);
        cancel.setVisible(false);
        skill.setDisable(true);
        skill.setVisible(false);
    }

    //-------------------------------按鈕出現---------------------------------------------//
    public void openButtom() {//把按鈕出現fun
        progressBar2.setVisible(true);
        progressBar.setVisible(true);
        soliderhp1.setVisible(true);
        bosshp1.setVisible(true);
        anime.setVisible(false);
        atk.setDisable(false);
        atk.setVisible(true);
        item.setDisable(false);
        item.setVisible(true);
        coward.setDisable(false);
        coward.setVisible(true);
        uses.setDisable(false);
        cancel.setDisable(false);
        skill.setDisable(false);
        skill.setVisible(true);
    }

    //---------------------------------------------------------------------------
    public void saying() {//每回合的劇情對白
        introduce.setText("");
        skillround2.setText("");
        Script.setText("劇情旁白");
        Random random = new Random();
        int temp = random.nextInt(st2.length);
        if (soilder1.life > soilder1.highest_life / 3)
            AnimateText2(introduce, st2[temp]);
        else
            angelsay("hp");
    }

    //-------------------------------------------------------
    public void level_boss() {//boss2階進化fun
        if (boss1.life <= boss1.highest_life*0.7&& trans == 0) {
            property.setVisible(false);
            attack.setVisible(false);
            attack2.setVisible(false);
            defense.setVisible(false);
            progressBar.setVisible(false);
            progressBar2.setVisible(false);
            soliderhp1.setVisible(false);
            bosshp1.setVisible(false);
            trans = 1;
            anime.setVisible(false);
            disableButtom();
            introduce.setText("");
            Script.setText("劇情旁白");
            boss1.setAttack(boss1.attack + 250);
            AnimateText(introduce, "{魔王}:你的成長獲得了我的認可!或許是你得到了什麼驚人的武器?\n又或是你在哪裡得到了什麼特別的加強");
            Timeline timeline = new Timeline();
            timeline.setCycleCount(st1.length);
            Timeline timeline1 = new Timeline();
            timeline1.setCycleCount(1);
            count = 0;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(3800), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String temp = st1[count];
                    count++;
                    AnimateText(introduce, temp);
                }
            });
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
            KeyFrame keyFrame1 = new KeyFrame(Duration.millis(19000), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    abc.setText("不滅的Mao");
                    property.setVisible(true);
                    if(round_3>round) {
                        attack.setVisible(true);
                        defense.setVisible(true);
                    }
                    if (round_2>round)
                        attack2.setVisible(true);
                    anchorPane.setBackground(new Background(
                            new BackgroundImage(new Image("resource/img/unnamed.jpg"),
                                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                    BackgroundPosition.CENTER, new BackgroundSize(720, 374, false, false, false, true))));
                    try {
                        Mao.setImage(new Image(new File("src/resource/img/mao2.gif").toURI().toString()));
                    } catch (Exception e) {
                    }
                    introduce.setText("今天晚上我也好想看國棟的直播喔......");
                    //-------------------------------按鈕出現----------------------------//
                    openButtom();
                    //---------------------------------------------------------------
                }
            });
            timeline1.getKeyFrames().add(keyFrame1);
            timeline1.play();
            mediaPlayer.stop();
            Media media = new Media(new File("src/resource/music/undyne_2.mp3").toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setStartTime(Duration.millis(0));
            mediaPlayer.setCycleCount(200);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setVolume(0.3);
        } else {
            openButtom();
        }
    }

    //-----------------------------------------------------------------------
    public static void button_voice() {//按鈕的聲音
        Media media = new Media(new File("src/resource/music/dog.mp3").toURI().toString());
        mediaPlayer3 = new MediaPlayer(media);
        mediaPlayer3.setStartTime(Duration.millis(0));
        mediaPlayer3.setAutoPlay(true);
        mediaPlayer3.setVolume(0.1);
    }

    //--------------------------------------------------------------------
    public void hpreset() {//寫條更新與判斷
        double hishp = (double) soilder1.life / (double) soilder1.highest_life;
        double hishp2 = (double) boss1.life / (double) boss1.highest_life;
        progressBar.setProgress(hishp);
        progressBar2.setProgress(hishp2);
        if (soilder1.getLife() < soilder1.highest_life / 2)
            soliderhp1.setTextFill(Paint.valueOf("red"));
        else
            soliderhp1.setTextFill(Paint.valueOf("white"));
        String soliderhp = (Integer.toString(soilder1.getLife()) + "/" + Integer.toString(soilder1.getHighest_life()));
        String bosshp = (Integer.toString(boss1.getLife()) + "/" + Integer.toString(boss1.getHighest_life()));
        soliderhp1.setText(soliderhp);
        bosshp1.setText(bosshp);
    }

    //---------------------------------------------------------------
    public void resetskilltime() {
        for (int j = 0; j < skillround.length; j++)
            skillround[j]--;
        if (round_2 == round) {
            attack2.setVisible(false);
            soilder1.setAttack(soilder1.attack - 1000);
        } else if (round_3 == round) {
            attack.setVisible(false);
            defense.setVisible(false);
            soilder1.setAttack(soilder1.attack - 1000);
            soilder1.setDefend(soilder1.defend - 1000);
        }
    }

    //---------------------------------------------------------------
    public void playmusic(String path) {
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer2 = new MediaPlayer(media);
        mediaPlayer2.setStartTime(Duration.millis(0));
        mediaPlayer2.setAutoPlay(true);
        mediaPlayer2.setVolume(0.3);
    }

    public void angelsay(String thing) {
        if (thing == "attack") {
            switch (angel) {
                case 1:
                    AnimateText(introduce, "[天使]:你專心點好不好，這樣很痛欸");
                    break;
                case 2:
                    AnimateText(introduce, "[天使]:勇者大人，下次可以嘗試往左邊閃");
                    break;
                case 3:
                    AnimateText(introduce, "[天使]:加油阿，勇者大人!");
                    break;
            }
        } else if (thing == "hp") {
            switch (angel) {
                case 1:
                    AnimateText(introduce, "[天使]:快點吃補包阿!笨蛋");
                    break;
                case 2:
                    AnimateText(introduce, "[天使]:背包裡有可以補血的食物!");
                    break;
                case 3:
                    AnimateText(introduce, "[天使]:加油阿，勇者大人!");
                    break;
            }
        }
    }
    public void  bossdie(){
        Mao.setImage(new Image(new File("src/resource/img/魔王死亡.gif").toURI().toString()));
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
        abc.setVisible(false);
        soliderhp1.setVisible(false);
        introduce.setVisible(false);
        progressBar.setVisible(false);
        progressBar2.setVisible(false);
        bossfa.setVisible(false);
        THEHBOX.setVisible(false);
        attackmessage.setVisible(false);
        anime.setVisible(false);
        attack.setVisible(false);
        attack2.setVisible(false);
        defense.setVisible(false);
        property.setVisible(false);
    }
    @FXML
    private void moveInButtonAction(Event e) {
        Object source = e.getSource();
        Button btn = (Button) source;
        btn.setStyle(" -fx-background-color: white");
    }

    @FXML
    private void moveOutButtonAction(Event e) {
        Object source = e.getSource();
        Button btn = (Button) source;
        btn.setStyle("-fx-text-fill: black;-fx-border-color: orange");
    }
}
