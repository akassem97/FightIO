import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    private Game game;
    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Fight!");
        primaryStage.show();
        game = new Game();
        Scene gameScene = game.createGameScene();
        primaryStage.setScene(gameScene);
        executorService.scheduleAtFixedRate(game, 0, 1000/120, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
