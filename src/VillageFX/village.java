package VillageFX;

import database.Connect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class village extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("village.fxml"));
        primaryStage.setScene(new Scene(root));//將fxml放入Scene，並設置在Stage中
        primaryStage.setMinWidth(1000);//設定畫面大小
        primaryStage.setMinHeight(550);
//        primaryStage.setMaximized(true);
//        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("請不要按ESC");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Connect.createConnection();
        launch(args);
    }
}
