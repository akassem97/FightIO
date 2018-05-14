import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;


public class DisplayableObject {
    protected ImageView display = new ImageView();
    private WritableImage[] images;
    public boolean orientation = false; //false = left facing       true = right facing

    protected DisplayableObject(){
        images = loadImages();
    };


    public ImageView getView(){
        return display;
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
