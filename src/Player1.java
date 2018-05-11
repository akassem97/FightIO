/**
 * Created by Danny on 10/05/2018.
 */
public class Player1 extends BasePlayer{
    public boolean smashing=false;
    public int punching = 0;
    public int kicking = 0;
    public Player1(){
        super(0);
    }

    public void punch(){
        punching=15;
        changeDisplay(1);
    }
    
    public void kick(){
        kicking=15;
        changeDisplay(4);
    }

    public void smash(){
        if(jumping&&!smashing){
            smashing=true;
            setyVel(10);
        }
    }
}
