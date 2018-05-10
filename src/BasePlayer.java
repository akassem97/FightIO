import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;


public class BasePlayer extends PhysicsObject{
    private int player;
    public boolean jumping = false;

    public BasePlayer(int pPlayer){
        super();
        player=pPlayer;
        changeDisplay(4*pPlayer);
    }

}
