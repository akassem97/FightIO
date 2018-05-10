import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class Game implements Runnable{
    private int hp1=100;
    private int hp2=100;
    private Group bar1=null;
    private Group bar2=null;
    private static int count = 0;

    private BasePlayer player1 = null;
    private BasePlayer player2 = null;

    private boolean gameOver = false;

    public Game(){

    }

    public void run(){
        moves();
        physics();
        collisions();
    }


    public void reduceHP(int player, int amount){
        if(player==1){
            for(int i = hp1; amount>0; hp1--, amount--){
                bar1.getChildren().remove(hp1-1);
            }
        }else if(player==2){
            for(int i = hp2; amount>0; hp2--, amount--){
                bar2.getChildren().remove(hp2-1);
            }
        }
    }

    public BorderPane createUI() {
        BorderPane ui = new BorderPane();
        ui.setPrefSize(1024,768);
        ui.setMaxSize(1024,768);
        BorderPane top = new BorderPane();
        bar1 = healthBar();
        top.setLeft(bar1);
        bar2 = healthBar();
        top.setRight(bar2);
        Text text = new Text("KEK");
        top.setCenter(text);
        ui.setTop(top);

        player1 = new BasePlayer(80,200,0);
        player1.orientation=true;
        player1.setPos(50,400);
        player2 = new BasePlayer(80,200,1);
        player2.orientation=false;
        player2.setPos(850,400);

        AnchorPane gameDisplayWrapper = new AnchorPane();
        gameDisplayWrapper.setMaxSize(1024,700);
        gameDisplayWrapper.setMinSize(1024,700);
        Group gameDisplay = new Group();
        gameDisplay.getChildren().add(player1.getView());
        gameDisplay.getChildren().add(player2.getView());
        gameDisplayWrapper.getChildren().add(gameDisplay);
        ui.setCenter(gameDisplayWrapper);

        return ui;
    }

    public Group healthBar(){
        Group bar= new Group();
        for(int i=0;i<100;i++){
            Polygon block = new Polygon(0,0, 3,0, 3,10, 0,10);
            block.setFill(Color.LIGHTGREEN);
            block.setTranslateX(4*i);
            bar.getChildren().add(block);
        }
        return bar;
    }

    public void moves(){
        count++;
        System.out.println("afsdf "+count);
    }

    public void physics(){
        player1.move(0,-1);
        player2.move(0,-1);
        System.out.println("Phyicsas");
    }

    public void collisions(){
        if(player1.x<0)player1.x=0;
        else if(player1.x>=1024-100)player1.x=1024-100;
        if(player1.y<0)player1.y=0;
        else if(player1.y>=700-200)player1.y=700-200;

        if(player2.x<0)player2.x=0;
        else if(player2.x>=1024-100)player2.x=1024-100;
        if(player2.y<0)player2.y=0;
        else if(player2.y>=700-200)player2.y=700-200;

        int xDiff=Math.abs(player2.x-player1.x);
        if(xDiff<100)player1.x-=xDiff;
    }
}
