
public class Player extends BasePlayer{
    public boolean smashing=false;
    public int punching = 0;
    public Player(){
        super(0);
    }

    public void punch(){
        punching=15;
        changeDisplay(1);
    }

    public void smash(){
        if(jumping&&!smashing){
            smashing=true;
            setyVel(10);
        }
    }
}
