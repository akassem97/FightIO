import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;


public class BasePlayer {
    int x = 0;
    int y = 0;
    int height = 0;
    int width = 0;
    int player;
    boolean orientation = false; //false = left facing       true = right facing
    ImageView display = new ImageView();
    WritableImage[] images;

    public BasePlayer(int h,int w,int pPlayer){
        height=h;
        width=w;
        player=pPlayer;
        images = loadImages();
        display.setImage(images[player*2]);
    }

    public ImageView getView(){
        return display;
    }

    public void move(int pX, int pY){
        x-=pX*20;
        y-=pY*20;
        setPos(x,y);
    }

    public void setPos(int pX, int pY){
        x=pX;
        y=pY;
        display.setTranslateX(x);
        display.setTranslateY(y);
    }

    public void changeDisplay(int i){
        display.setImage(null);
        if(orientation){

        }else{

        }
    }

    protected WritableImage[] loadImages(){
        Image img = new Image(Main.class.getResourceAsStream("shitset.png"));
        PixelReader reader = img.getPixelReader();
        int y,x;
        WritableImage[] imageSet = new WritableImage[8*1];
        for(int i = 0; i < 8 * 1; i++){
            x=(i%8);
            y=(i/8);
            imageSet[i] = new WritableImage(reader, x*100, y*200, 100, 200);
        }
        return imageSet;
    }
}
