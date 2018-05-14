
public class PhysicsObject extends DisplayableObject{
    private double x = 0;
    private double y = 0;
    private double xVel=0;
    private double yVel=0;
    public int height = 200;
    public int width = 100;

    protected PhysicsObject(){
        super();
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

    public void correctPosition(int direction){
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

    public void correctPosition(int direction,double n){//    up, down,   left, right
        switch(direction){
            case 0:
                y=n;
                setyVel(0);
                display.setTranslateY(y);
                break;
            case 1:
                y=n-height;
                setyVel(0);
                display.setTranslateY(y);
                break;
            case 2:
                x=n;
                setxVel(0);
                display.setTranslateX(x);
                break;
            case 3:
                x=n-width;
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

}
