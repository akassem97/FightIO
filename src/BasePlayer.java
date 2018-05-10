import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;


public class BasePlayer {
    private double x = 0;
    private double y = 0;
    private double xVel=0;
    private double yVel=0;
    private int height = 200;
    private int width = 100;
    private int player;
    public boolean jumping = false;
    public boolean orientation = false; //false = left facing       true = right facing
    private ImageView display = new ImageView();
    private WritableImage[] images;

    public BasePlayer(int pPlayer){
        player=pPlayer;
        images = loadImages();
        display.setImage(images[player*4]);
    }

    public ImageView getView(){
        return display;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void accelX(double pVel){
        xVel+=pVel;
    }

    public void accelY(double pVel){
        yVel+=pVel;
    }

    public void accel(double pxVel, double pyVel){
        xVel+=pxVel;
        yVel+=pyVel;
    }

    public void setxVel(double pVel){
        xVel=pVel;
    }

    public void setyVel(double pVel){
        yVel=pVel;
    }

    public void correctPosition(int direction){//    up, down,   left, right
        switch(direction){
            case 0:
                y=0;
                setyVel(0);
                display.setTranslateY(y);
                break;
            case 1:
                y=700-height;
                setyVel(0);
                display.setTranslateY(y);
                break;
            case 2:
                x=0;
                setxVel(0);
                display.setTranslateX(x);
                break;
            case 3:
                x=1024-width;
                setxVel(0);
                display.setTranslateX(x);
                break;
        }
    }

    public void move(double pX, double pY){
        accelX(pX*5);
        accelY(pY*5);
        x+=xVel;
        y+=yVel;
        display.setTranslateX(x);
        display.setTranslateY(y);
    }

    public void setPos(double pX, double pY){
        x=pX;
        y=pY;
        display.setTranslateX(x);
        display.setTranslateY(y);
    }

    public boolean xCollide(double targetX, boolean targetsLeftFace){
        double previousX = x-xVel;
        double newX = x;

        if(previousX==targetX && newX > targetX && !targetsLeftFace){
            return false;
        }else if(previousX==targetX && newX < targetX && targetsLeftFace){
            return false;
        }else if(previousX<=targetX && newX>=targetX){
            System.out.println(previousX + " " + newX + " " + targetX);
            return true;
        }else if(previousX>=targetX && newX<=targetX){
            System.out.println(previousX + " " + newX + " " + targetX);
            return true;
        }

        return false;
    }

    public boolean yCollide(double targetY, boolean targetsTopFace){
        double previousY = y-yVel;
        double newY = y;

        if(previousY==targetY && newY > targetY && !targetsTopFace){
            return false;
        }else if(previousY==targetY && newY < targetY && targetsTopFace){
            return false;
        }else if(previousY<=targetY && newY>=targetY){
            return true;
        }else if(previousY>=targetY && newY<=targetY){
            return true;
        }

        return false;
    }

    public void changeDisplay(int i){
        display.setImage(images[i]);
    }

    public void swapOrientation(){
        if(orientation){
            display.setScaleX(-1);
            orientation=false;
        }else{
            display.setScaleX(-1);
            orientation=true;
        }
    }

    protected WritableImage[] loadImages(){
        int tileSetWidth = 100;
        int tileSetHeight = 200;
        int tileSetColumns = 8;
        int tileSetRows = 1;


        Image img = new Image(Main.class.getResourceAsStream("imageSet.png"));
        PixelReader reader = img.getPixelReader();
        int y,x;
        WritableImage[] imageSet = new WritableImage[tileSetColumns*tileSetRows];
        for(int i = 0; i < tileSetColumns * tileSetRows; i++){
            x=(i%tileSetColumns);
            y=(i/tileSetColumns);
            imageSet[i] = new WritableImage(reader, x*tileSetWidth, y*tileSetHeight, tileSetWidth, tileSetHeight);
        }
        return imageSet;
    }
}
