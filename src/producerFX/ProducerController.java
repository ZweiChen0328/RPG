package producerFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProducerController implements Initializable {
    ObservableList<String> producerList = FXCollections.<String>observableArrayList();
    @FXML
    AnchorPane ap;
    @FXML
    ListView<String> listview;
    @FXML
    Button btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        producerList.addAll("程式介面設計", "陳佑軒", "程式邏輯", "黃揚勛", "資料庫處理","唐瑋晨", "美術設計", "傅俞竣");
        ap.setBackground(new Background(new BackgroundImage(new Image("resource/img/開場背景.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(720, 374, false, false, false, true))));
        listview.setStyle("-fx-background-color: #00000000; -fx-text-fill: #00000000; -fx-control-inner-background: #00000000; -fx-font-size: 20px;");
        listview.setItems(producerList);
    }

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
    private void clickReturnButtonAction(Event e) throws IOException {
        Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("StartFX/start.fxml"));
        currentStage.setScene(new Scene(root1, currentStage.getWidth(), currentStage.getHeight()));
        currentStage.setTitle("Game");
    }
}
