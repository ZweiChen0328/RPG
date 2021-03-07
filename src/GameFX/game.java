package GameFX;

import database.Connect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class game extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        Scene scene = new Scene(root, 1000, 550);
        Image image=new Image("resource/img/mouse.png");
        Cursor Mouse = new ImageCursor(image,0,0); //表示游標圖片的偏移量
        //在scene中修改游標
        scene.setCursor(Mouse);
        primaryStage.setScene(scene);//將fxml放入Scene，並設置在Stage中
        primaryStage.setMinWidth(1000);//設定畫面大小
        primaryStage.setMinHeight(550);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Connect.createConnection();
        launch(args);
    }
}
