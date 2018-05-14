import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
    private static double count = 0;
    private Text text;
    private Player player1 = null;
    private BasePlayer player2 = null;

    private boolean gameOver = false;

    public Game(){
        calculateCollision(0,10, 0,10,   0,10, 11,10);
    }

    public void run(){
        if(player1.punching>0){
            System.out.println(player1.punching);
            player1.punching--;
            if(player1.punching==0){
                player1.changeDisplay(0);
            }
        }
        physics();
        moves();
        collisions();
        count++;
        //text.setText(count/120+"s");
    }

    public Scene createGameScene(){
        Scene scene = new Scene(createUI());
        scene.setOnKeyPressed(keyEvent->{
            if(keyEvent.getCode() == KeyCode.W){
                if(!player1.jumping){
                    player1.setyVel(-10);
                    player1.jumping=true;
                }
            }
            else if(keyEvent.getCode() == KeyCode.A){
                player1.setxVel(-10);
            }
            else if(keyEvent.getCode() == KeyCode.S){

                    player1.smash();

            }
            else if(keyEvent.getCode() == KeyCode.D){
                player1.setxVel(10);
            }
            else if(keyEvent.getCode() == KeyCode.Z){
                player1.punch();
            }
            else if(keyEvent.getCode() == KeyCode.X){

            }
            else if(keyEvent.getCode() == KeyCode.C){

            }
        });
        scene.setOnKeyReleased(keyEvent->{
            if(keyEvent.getCode() == KeyCode.W){
                //player1.setyVel(0);
            }
            else if(keyEvent.getCode() == KeyCode.A){
                player1.setxVel(0);
            }
            else if(keyEvent.getCode() == KeyCode.S){
                //player1.setyVel(0);
            }
            else if(keyEvent.getCode() == KeyCode.D){
                player1.setxVel(0);
            }
            else if(keyEvent.getCode() == KeyCode.Z){

            }
            else if(keyEvent.getCode() == KeyCode.X){

            }
            else if(keyEvent.getCode() == KeyCode.C){

            }
        });
        return scene;
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
        ui.setPrefSize(1024,1000);
        ui.setMaxSize(1024,1000);

        BorderPane top = new BorderPane();
        bar1 = healthBar();
        top.setLeft(bar1);
        bar2 = healthBar();
        top.setRight(bar2);
        text = new Text("KEK");
        top.setCenter(text);
        ui.setTop(top);

        player1 = new Player();
        player1.setPos(50,400);
        player2 = new BasePlayer(1);
        player2.swapOrientation();
        player2.setPos(850,400);

        AnchorPane gameDisplayWrapper = new AnchorPane();
        gameDisplayWrapper.setMaxSize(1024,700);
        gameDisplayWrapper.setMinSize(1024,700);
        Group gameDisplay = new Group();
        gameDisplay.getChildren().add(player1.getView());
        gameDisplay.getChildren().add(player2.getView());
        gameDisplayWrapper.getChildren().add(gameDisplay);
        ui.setCenter(gameDisplayWrapper);



        //ui.setBottom(boundingBox());

        return ui;
    }


    public Group boundingBox(){
        Group group = new Group();

        Polygon head = new Polygon(0,0, 20,0, 20,20, 0,20);
        head.setFill(Color.CHOCOLATE);
        head.setTranslateX(6);
        head.setTranslateY(100);
        group.getChildren().add(head);

        Polygon armL = new Polygon(0,0, 50,0, 50,5, 0,5);
        armL.setFill(Color.GREEN);
        armL.setTranslateX(18);
        armL.setTranslateY(70);
        armL.setRotate(300);
        group.getChildren().add(armL);

        Polygon armR = new Polygon(0,0, 50,0, 50,5, 0,5);
        armR.setFill(Color.GREEN);
        armR.setTranslateX(-36);
        armR.setTranslateY(70);
        armR.setRotate(60);
        group.getChildren().add(armR);

        Polygon body = new Polygon(0,0, 32,0, 32,50, 0,50);
        body.setFill(Color.RED);
        body.setTranslateX(0);
        body.setTranslateY(50);
        group.getChildren().add(body);

        Polygon legL = new Polygon(0,0, 5,0, 5,50, 0,50);
        legL.setFill(Color.BLUE);
        legL.setTranslateX(27);
        legL.setTranslateY(0);
        group.getChildren().add(legL);

        Polygon legR = new Polygon(0,0, 5,0, 5,50, 0,50);
        legR.setFill(Color.BLUE);
        legR.setTranslateX(0);
        legR.setTranslateY(0);
        group.getChildren().add(legR);

        group.setTranslateX(100);
        group.setScaleY(-2);
        group.setScaleX(2);
        return group;
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
        player1.move(0,0);
        player2.move(0,0);
    }

    public void physics(){
        player1.accelY(0.2);
        player2.accelY(0.2);
    }


    public boolean calculateCollision(double x1, double x2, double y1, double y2,    double x3, double x4, double y3, double y4){
        double grad1 = (y1-y2)/(x1-x2);
        double grad2 = (y3-y4)/(x3-x4);

        double c1 = y1-x1*grad1;
        double c2 = y3-x3*grad2;

        double xi = (c1-c2)/(grad2-grad1);
        double yi = xi*grad1+c1;

        if((x1>x2&&xi>x2&&x1>xi || x1<x2&&xi<x2&&x1<xi) && (x3>x4&&xi>x4&&x3>xi|| x3<x4&&xi<x4&&x3<xi) && (y1>y2&&yi>y2&&y1>yi || y1<y2&&yi<y2&&y1<yi) && (y3>y4&&yi>y4&&y3>yi|| y3<y4&&yi<y4&&y3<yi)){
            System.out.println("COLLIS");
            return true;
        }
        System.out.println(grad1 + " " + c1 + " " + grad2 + " " + c2 + " " + xi + " " + yi + " ");
        return false;
    }

    //fix this
    public void collisions(){
        if(player1.xCollide(0,false)){
            player1.correctPosition(2);
        }
        else if(player1.xCollide(1024-100,true)){
            player1.correctPosition(3);
        }
        if(player1.yCollide(0,false)){
            player1.correctPosition(0);
        }
        else if(player1.yCollide(700-200,true)){
            player1.jumping=false;
            player1.smashing=false;
            player1.correctPosition(1);
        }

        if(player2.xCollide(0,false))
            player2.correctPosition(2);
        else if(player2.xCollide(1024-100,true))
            player2.correctPosition(3);
        if(player2.yCollide(0,false))
            player2.correctPosition(0);
        else if(player2.yCollide(700-200,true))
            player2.correctPosition(1);

        if(player1.xCollide(player2.getX()-100,true)){
            player1.correctPosition(2,player2.getX()-100);
        }
        /*
        if(&&player1.yCollide(player2.getY()-200,true)){
            player1.correctPosition(0,player2.getY()-200);
        }*/
    }
}
