import com.sun.prism.shader.DrawCircle_LinearGradient_PAD_AlphaTest_Loader;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;


public class MeshEditor extends Application{

    ArrayList<CollisionMesh> components = new ArrayList<>();
    Group display = new Group();
    int selectedComponent = 0;
    boolean creatingPoint = false;
    boolean creatingComponent = false;
    boolean movingPoint = false;
    Circle currentPoint;
    Polygon currentComponent;
    int numberOfPoints = 0;
    int selectedPointId;

    Text xView = new Text("0.0");
    Text yView = new Text("0.0");
    Text rotationView = new Text("0.0");


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Fight!");
        primaryStage.show();
        Scene editorScene = new Scene(createUserInterface());
        primaryStage.setScene(editorScene);
    }

    public class PointWrapper{
        public PointWrapper(){

        }


    }

    public BorderPane createUserInterface(){
        BorderPane ui = new BorderPane();
        ui.setPrefSize(1600,900);


        GridPane controller = new GridPane();
        controller.setMinSize(200,900);
        controller.add(xView,0,0);
        controller.add(yView,0,1);
        controller.add(rotationView,0,2);
        Button createPoint = new Button();
        createPoint.setText("Add point");
        createPoint.setOnMouseClicked(event->{
            creatingPoint = true;
            creatingComponent = false;
            movingPoint = false;
            currentPoint = null;
        });
        controller.add(createPoint,0,3);
        Button createComponent = new Button();
        createComponent.setText("Add component");
        createComponent.setOnMouseClicked(event->{
            creatingPoint = false;
            creatingComponent = true;
            movingPoint = false;
            currentPoint = null;
        });
        controller.add(createComponent,0,4);
        ui.setRight(controller);


        AnchorPane view = new AnchorPane();
        Rectangle background = new Rectangle(1400,900);
        background.setFill(Color.WHITE);
        view.setPrefSize(1400,900);

        background.setOnMouseMoved(event->{
            if(movingPoint){
                if(currentPoint.getCenterX()+50>event.getX()||currentPoint.getCenterX()-50<event.getX()){
                    movingPoint=false;
                    currentPoint=null;
                    System.out.println("deselecting point");
                }else if(currentPoint.getCenterY()+50>event.getY()||currentPoint.getCenterY()-50<event.getY()){
                    movingPoint=false;
                    currentPoint=null;
                    System.out.println("deselecting point");
                }
            }
        });
        background.setOnMouseClicked(event->{
            if(creatingComponent) {
                Polygon poly = new Polygon();
                poly.setFill(Color.ORANGE);
                poly.setTranslateX(event.getX());
                poly.setTranslateY(event.getY());
                currentComponent=poly;
                currentComponent.getPoints().add(event.getX()-currentComponent.getTranslateX());
                currentComponent.getPoints().add(event.getY()-currentComponent.getTranslateY());
                display.getChildren().add(poly);
                createNewMeshComponent();
                addMeshComponentPoint(selectedComponent,event.getX(),event.getY());
            }else if(creatingPoint){
                addMeshComponentPoint(selectedComponent,event.getX(),event.getY());
                currentComponent.getPoints().add(event.getX()-currentComponent.getTranslateX());
                currentComponent.getPoints().add(event.getY()-currentComponent.getTranslateY());
            }

        });
        view.setOnMouseDragged(event->{
            if(movingPoint) {
                //components.get(selectedComponent).points.add(event.getX()-currentComponent.getTranslateX());
                //components.get(selectedComponent).points.add(event.getY()-currentComponent.getTranslateY());
                currentPoint.setTranslateX(event.getX());
                currentPoint.setTranslateY(event.getY());
                currentComponent.getPoints().set(selectedPointId,event.getX()-currentComponent.getTranslateX());
                currentComponent.getPoints().set(selectedPointId+1,event.getY()-currentComponent.getTranslateY());
                currentComponent.setFill(Color.ORANGE);
            }
        });
        view.getChildren().add(background);
        view.getChildren().add(display);

        ui.setLeft(view);


        return ui;
    }

    public void createNewMeshComponent(){
        components.add(new CollisionMesh());
        selectedComponent = components.size()-1;
    }

    public void addMeshComponentPoint(int component,double x, double y){
        Circle selectPoint = new Circle(10);
        final int pointId = numberOfPoints;
        numberOfPoints+=2;
        selectPoint.setFill(Color.BLACK);
        selectPoint.setTranslateX(x);
        selectPoint.setTranslateY(y);
        selectPoint.setOnMousePressed(event->{
            selectedPointId=pointId;
            currentPoint=selectPoint;
            creatingPoint=false;
            creatingComponent=false;
            movingPoint=true;
            System.out.println("Moving point");
        });
        display.getChildren().add(selectPoint);
        components.get(component).points.add(x);
        components.get(component).points.add(y);
    }





    public static void main(String[] args) {
        launch(args);
    }
}
