package com.example.demo11;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import java.util.ArrayList;



public class DiagramView extends StackPane implements ModelListener, IModelListener{


    GraphicsContext gc;
    Canvas myCanvas;
    SMModel model;
    InteractionModel iModel;
    NodePropertiesView node_edit_view;
    LinkPropertiesView rect_edit_view;
    BorderPane root;
    SMItem temp_select;

    double sp_width, sp_height;
    double viewScale;
    double shapeLeft, shapeTop, shapeRight, shapeBottom, shapeWidth, shapeHeight;


    public DiagramView(){

        root = new BorderPane();
        root.setPrefSize(800,800);
        root.setMinSize(800,800);

        //MiniView miniView = new MiniView();

        sp_width = 1600;
        sp_height = 1600;
        myCanvas = new Canvas(sp_height, sp_width);
        gc = myCanvas.getGraphicsContext2D();
        setStyle("-fx-background-color: skyblue;");
        gc.setFill(Color.BLUE);

        node_edit_view = new NodePropertiesView();
        rect_edit_view = new LinkPropertiesView();


        root.getChildren().add( myCanvas);

        this.getChildren().add(root);
    }

    public void setModel(SMModel newModel) {
        model = newModel;
        model.addSubscriber(this);
    }

    public void setIModel(InteractionModel newIModel) {
        iModel = newIModel;
        iModel.addSubscriber(this);
        iModel.setViewExtents(myCanvas.getWidth()/ sp_width, myCanvas.getHeight()/ sp_height);
        iModel.setWorldExtents(sp_width, sp_height);
    }


    private void draw() {

        gc.clearRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());


        // for all SM Nodes

        if (iModel.getSelected() == null){
        }

        else if (iModel.getSelected().getClass().getSimpleName().equals("SMRectangle")){

            temp_select = iModel.getSelected();
            root.setRight(rect_edit_view);

        }

        else if(iModel.getSelected().getClass().getSimpleName().equals("SMStateNode")){

            temp_select = iModel.getSelected();
            root.setRight(node_edit_view);
            String state_name = node_edit_view.state_name_tf.getText();

        }


        model.getSMNodes().forEach(b -> {

            ArrayList<SMTranisitonLink> lines_of_node = b.node_xlines;


            // for each line with that Sm node
            lines_of_node.forEach( line -> {


                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1.0);

                //gc.setStroke(new BasicStroke());
                gc.strokeLine(line.x1, line.y1, line.x2, line.y2);


                if (line.smRectangle != null){

                    gc.setFill(Color.LIGHTGRAY);
                    gc.setStroke(Color.BLACK);
                    // FILL TEXT
                    //gc.fillText( "EVENT : " + line.smRectangle.event  + "\r\n" +"Context : " + line.smRectangle.content  + "Side effects : " + line.smRectangle.side_effects, line.smRectangle.x + line.smRectangle.length/2 , line.smRectangle.y + line.smRectangle.width/2 );
                    gc.fillRect(line.smRectangle.x, line.smRectangle.y, line.smRectangle.length, line.smRectangle.width);
                    gc.strokeRect(line.smRectangle.x, line.smRectangle.y, line.smRectangle.length, line.smRectangle.width);
                    //STROKE TEXT
                    gc.setStroke(Color.BLACK);
                    gc.strokeText( "EVENT : " + line.smRectangle.event + "\n" + "Context : " + line.smRectangle.content +"\n" + "Side effects : " + line.smRectangle.side_effects, line.smRectangle.x  , line.smRectangle.y + 20 );

                }
            });


            if (b == iModel.getSelected()) {
                gc.setFill(Color.YELLOW);
                gc.setStroke(Color.RED);
                gc.fillText(b.name, b.x + b.length/2 , b.y + b.width/2 );

            } else {
                gc.setFill(Color.YELLOW);
                gc.setStroke(Color.BLACK);
                gc.fillText(b.name, b.x + b.length/2 , b.y + b.width/2 );
            }

            gc.fillRect(b.x, b.y, b.length, b.width);
            gc.strokeRect(b.x, b.y, b.length, b.width);
            gc.strokeText(b.name , b.x +20 , b.y + 20 );

        });

    }


    public void modelChanged() {
        draw();
    }

    @Override
    public void iModelChanged() {
        draw();
    }


    protected void calculateShapeBounds(SMItem sm) {

        shapeLeft = (sm.x - iModel.viewLeft) * sp_width * viewScale;
        shapeTop = (sm.y - iModel.viewTop) * sp_height * viewScale;
        shapeRight = (sm.right - iModel.viewLeft) * sp_width * viewScale;
        shapeBottom = (sm.bottom - iModel.viewTop) * sp_height * viewScale;
        shapeWidth = shapeRight - shapeLeft;
        shapeHeight = shapeBottom - shapeTop;
    }

    public void setController(AppController controller) {


        myCanvas.setOnMousePressed(e -> {
            controller.handlePressed(e, e.getX()/ sp_width, e.getY()/ sp_height);
        });

        myCanvas.setOnMouseDragged(e ->{
            controller.handleDragged(e, e.getX()/ sp_width, e.getY()/ sp_height);
        });

        myCanvas.setOnMouseReleased( e -> {
            controller.handleReleased(e, e.getX()/ sp_width, e.getY()/ sp_height);
        });


//        myCanvas.setOnMousePressed(controller::handlePressed);
//        myCanvas.setOnMouseDragged(controller::handleDragged);
//        myCanvas.setOnMouseReleased(controller::handleReleased);


        rect_edit_view.update_btn.setOnAction(event -> {
            controller.handleRectUpdate(event, rect_edit_view , temp_select);
        });

        node_edit_view.update_btn.setOnAction(event -> {
            controller.handleNodeUpdate(event, node_edit_view , temp_select);
        });

        node_edit_view.delete_btn.setOnAction(event -> {
            controller.handleNodeDelete(event, node_edit_view, temp_select);
        });

        rect_edit_view.delete_btn.setOnAction(event -> {
            controller.handleRectDelete(event, rect_edit_view, temp_select);
        });
    }

}
