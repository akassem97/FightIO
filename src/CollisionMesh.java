import javafx.scene.shape.Polygon;

import java.util.ArrayList;

/**
 * Created by Danny on 14/05/2018.
 */
public class CollisionMesh {
    public ArrayList<Double> points = new ArrayList<>();
    public double x = 0.0;
    public double y = 0.0;
    public double rotation = 0.0;
    public int numberOfPoints = 0;
    public String name;

    public CollisionMesh(String pName){
        name=pName;

    }

    public String toString(){
        return name +"  X:"+x+"  Y:"+y+"  Points:"+numberOfPoints;
    }

}
