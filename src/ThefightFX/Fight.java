package ThefightFX;

import database.Connect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Fight extends Application {
    Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fight.fxml"));
        primaryStage.setScene(new Scene(root, 1000, 550));//將fxml放入Scene，並設置在Stage中
        primaryStage.setMinWidth(1000);//設定畫面大小
        primaryStage.setMinHeight(550);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Connect.createConnection();
        launch(args);
    }

    public void showWindow() throws Exception {
        start(stage);
    }
}
