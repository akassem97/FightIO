
public class BasePlayer extends PhysicsObject{
    private int player;
    public boolean jumping = false;

    public BasePlayer(int pPlayer){
        super();
        player=pPlayer;
        changeDisplay(4*pPlayer);
    }

}
