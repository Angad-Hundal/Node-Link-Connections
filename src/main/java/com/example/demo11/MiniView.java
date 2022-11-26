package com.example.demo11;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class MiniView extends StackPane implements IModelListener, ModelListener {



    GraphicsContext gc;
    Canvas myCanvas;
    SMModel model;
    InteractionModel iModel;
    BorderPane root;
    SMItem temp_select;

    double width_whole, height_whole;

    double ratio;


    public MiniView() {

        root = new BorderPane();
        root.setPrefSize(300,300);
        root.setMaxSize(300,300);
        root.setMinSize(300,300);

        ratio = 3;
        width_whole = 2000;
        height_whole = 2000;
        myCanvas = new Canvas(300,300);
        gc = myCanvas.getGraphicsContext2D();
        setStyle("-fx-background-color: rgba(0, 100, 100, 0.5);");
        //gc.setFill(Color.BLUE);


        root.getChildren().add(myCanvas);
        root.setLayoutX(0);
        root.setLayoutY(0);
        this.getChildren().add(root);
    }

    public void setModel(SMModel newModel) {
        model = newModel;
        model.addSubscriber(this);
    }

    public void setIModel(InteractionModel newIModel) {
        iModel = newIModel;
        iModel.addSubscriber(this);
        //iModel.setViewExtents((myCanvas.getWidth()/workspaceWidth)/ratio, (myCanvas.getHeight()/workspaceHeight)/ratio);
        //iModel.setWorldExtents(workspaceWidth/ratio,workspaceHeight/ratio);
    }


    private void draw() {


        gc.clearRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());

        // for all SM Nodes


        model.getSMNodes().forEach(b -> {

            ArrayList<SMTranisitonLink> lines_of_node = b.node_xlines;


            // for each line with that Sm node
            lines_of_node.forEach( line -> {


                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1.0);

                gc.strokeLine(line.x1 / ratio, line.y1 /ratio, line.x2/ ratio, line.y2/ratio);


                if (line.smRectangle != null){

                    gc.setFill(Color.LIGHTGRAY);
                    gc.setStroke(Color.BLACK);
                    // FILL TEXT
                    //gc.fillText( "EVENT : " + line.smRectangle.event + "\n" + "Context : " + line.smRectangle.content + "\n" + "Side effects : " + line.smRectangle.side_effects,(line.smRectangle.x)/ratio , (line.smRectangle.y)/ratio );
                    gc.fillRect(line.smRectangle.x / ratio, line.smRectangle.y / ratio, line.smRectangle.length / ratio, line.smRectangle.width/ ratio);
                    gc.strokeRect(line.smRectangle.x / ratio, line.smRectangle.y / ratio, line.smRectangle.length / ratio, line.smRectangle.width / ratio);
                    //STROKE TEXT
                    gc.setStroke(Color.BLACK);
                    gc.strokeText( "EVENT : " + line.smRectangle.event + "\n"  + "Context : " + line.smRectangle.content  + "\n" + "Side effects : " + line.smRectangle.side_effects, line.smRectangle.x /ratio , line.smRectangle.y /ratio );

                }
            });


            if (b == iModel.getSelected()) {
                gc.setFill(Color.YELLOW);
                gc.setStroke(Color.RED);
                //gc.fillText(b.name , (b.x + b.length/2) / ratio , (b.y + b.width/2) / ratio );

            } else {
                gc.setFill(Color.YELLOW);
                gc.setStroke(Color.BLACK);
                //gc.fillText(b.name, (b.x + b.length/2)/ratio , (b.y + b.width/2)/ratio );
            }

            gc.fillRect(b.x / ratio, b.y/ratio, b.length/ratio, b.width/ratio);
            gc.strokeRect(b.x/ ratio, b.y/ratio, b.length/ratio, b.width/ratio);
            gc.strokeText(b.name , (b.x)/ratio + 3 , (b.y)/ratio+10 );


        });

    }


    public void modelChanged() {
        draw();
    }

    @Override
    public void iModelChanged() {
        draw();
    }


}
