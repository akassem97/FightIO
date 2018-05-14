import com.sun.prism.shader.DrawCircle_LinearGradient_PAD_AlphaTest_Loader;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
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
    ListView<String> listView = new ListView<>();
    Group display = new Group();
    Group polygons = new Group();
    int controlStage=0;
    // 1 = creating point
    // 2 = creating component
    // 3 = moving point
    Circle currentPoint;
    Polygon currentComponent;
    int numberOfPoints = 0;
    int numberOfComponents = 0;
    int selectedPointId;
    int selectedComponent = 0;

    Text xView = new Text("n.a.");
    Text yView = new Text("n.a");
    Text rotationView = new Text("n.a.");


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Fight! Mesh Editor");
        primaryStage.show();
        Scene editorScene = new Scene(createUserInterface());
        primaryStage.setScene(editorScene);
    }

    public BorderPane createUserInterface(){
        BorderPane ui = new BorderPane();
        ui.setPrefSize(1600,900);


        GridPane controller = new GridPane();
        controller.setVgap(20);
        controller.setHgap(20);
        controller.setMinSize(200,900);
        controller.add(xView,0,0);
        controller.add(yView,0,1);
        controller.add(rotationView,0,2);
        Button createPoint = new Button();
        createPoint.setText("Add point");
        createPoint.setOnMouseClicked(event->{
            controlStage=1;
            currentPoint = null;
        });
        controller.add(createPoint,0,3);
        Button createComponent = new Button();
        createComponent.setText("Add component");
        createComponent.setOnMouseClicked(event->{
            controlStage=2;
            currentPoint = null;
        });
        controller.add(createComponent,0,4);
        ui.setRight(controller);


        AnchorPane view = new AnchorPane();
        Rectangle background = new Rectangle(1200,900);
        background.setFill(Color.WHITE);
        display.getChildren().add(polygons);
        view.setPrefSize(1200,900);

        background.setOnMouseMoved(event->{
            if(controlStage==3){
                if(currentPoint.getCenterX()+50>event.getX()||currentPoint.getCenterX()-50<event.getX()){
                    controlStage=0;
                    currentPoint=null;
                    System.out.println("deselecting point");
                }else if(currentPoint.getCenterY()+50>event.getY()||currentPoint.getCenterY()-50<event.getY()){
                    controlStage=0;
                    currentPoint=null;
                    System.out.println("deselecting point");
                }
            }
        });
        background.setOnMouseClicked(event->{
            if(controlStage==2) {
                final int componentId = numberOfComponents;
                numberOfPoints=0;
                Polygon poly = new Polygon();
                poly.setFill(Color.ORANGE);
                poly.setTranslateX(event.getX());
                poly.setTranslateY(event.getY());
                poly.setOnMouseClicked(polyEvent->{
                    selectComponent(componentId);
                    listView.getSelectionModel().select(componentId);
                });
                currentComponent=poly;
                createNewMeshComponent(event.getX(),event.getY());
            }else if(controlStage==1){
                addMeshComponentPoint(selectedComponent,event.getX(),event.getY());
            }
        });
        view.setOnMouseDragged(event->{
            if(controlStage==3) {
                updateMeshComponentPoint(event.getX(),event.getY());
            }
        });
        view.getChildren().add(background);
        view.getChildren().add(display);

        ui.setCenter(view);


        listView.setOnMouseClicked(event->{
            selectComponent(listView.getSelectionModel().getSelectedIndex());
        });
        ui.setLeft(listView);


        return ui;
    }

    public void createNewMeshComponent(double x, double y){
        components.add(new CollisionMesh("Component "+numberOfComponents));
        selectedComponent = numberOfComponents;
        numberOfComponents++;
        listView.getItems().add(components.get(selectedComponent).toString());
        addMeshComponentPoint(selectedComponent,x,y);
        polygons.getChildren().add(currentComponent);
    }

    public void addMeshComponentPoint(int component,double x, double y){
        Circle selectPoint = new Circle(10);
        final int pointId = numberOfPoints;
        final int componentId = numberOfComponents-1;
        numberOfPoints+=2;
        selectPoint.setFill(Color.BLACK);
        selectPoint.setTranslateX(x);
        selectPoint.setTranslateY(y);
        selectPoint.setOnMousePressed(event->{
            if(selectedComponent==componentId){
                selectedPointId=pointId;
                currentPoint=selectPoint;
                controlStage=3;
                System.out.println("Moving point");
            }
        });
        display.getChildren().add(selectPoint);
        currentComponent.getPoints().add(x-currentComponent.getTranslateX());
        currentComponent.getPoints().add(y-currentComponent.getTranslateY());
        components.get(component).points.add(x);
        components.get(component).points.add(y);
        components.get(component).numberOfPoints++;
        listView.getItems().set(selectedComponent,components.get(selectedComponent).toString());
    }

    public void updateMeshComponentPoint(double x, double y){
        currentPoint.setTranslateX(x);
        currentPoint.setTranslateY(y);
        currentComponent.getPoints().set(selectedPointId,x-currentComponent.getTranslateX());
        currentComponent.getPoints().set(selectedPointId+1,y-currentComponent.getTranslateY());
        components.get(selectedComponent).points.set(selectedPointId,x-currentComponent.getTranslateX());
        components.get(selectedComponent).points.set(selectedPointId+1,y-currentComponent.getTranslateY());
        currentComponent.setFill(Color.ORANGE);
    }

    public void selectComponent(int index){
        selectedComponent =  index;
        currentComponent = (Polygon) polygons.getChildren().get(selectedComponent);
        numberOfPoints=components.get(selectedComponent).numberOfPoints;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
