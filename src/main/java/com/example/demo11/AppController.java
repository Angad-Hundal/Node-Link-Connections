package com.example.demo11;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class AppController {


    InteractionModel iModel;
    SMModel model;
    double prevX, prevY;
    double dX, dY;

    protected enum State {
        READY, PREPARE_CREATE, DRAGGING, PANNING, RESIZING
    }

    // READY :
    // PREPARE_CREATE :
    // DRAGGING :

    protected State currentState;

    public AppController(){
        currentState = State.READY;
    }

    public void setiModel(InteractionModel newModel) {
        iModel = newModel;
    }

    public void setModel(SMModel newModel) {
        model = newModel;
    }



    public void handlePressed(MouseEvent event, double x, double y) {

        if (iModel.getSelectedButton().icon == "Arrow"){

            switch (currentState) {
                case READY -> {
                    // select a SMNode
                    if (model.hitSMNode(event.getX(),event.getY())) {
                        SMStateNode b = model.whichHit(event.getX() + iModel.viewLeft ,event.getY() + iModel.viewTop);
                        iModel.setSelected(b);
                        prevX = event.getX();
                        prevY = event.getY();
                        currentState = State.DRAGGING;  // pass it to dragging
                        //model.setRightView();

                    }


                    else if (model.hitSMRectangle(event.getX(), event.getY())){

                        SMRectangle b = model.whichRectHit(event.getX() + iModel.viewLeft ,event.getY() + iModel.viewTop);

                        iModel.setSelected(b);
                        prevX = event.getX();
                        prevY = event.getY();
                        currentState = State.DRAGGING;  // pass it to dragging

                    }

                    // create a SMNode
                    else {
                        currentState = State.PREPARE_CREATE;  // pass it to create
                    }
                }
            }

        }

        else if (iModel.getSelectedButton().icon == "Move"){

            switch (currentState) {

                case READY -> {

                   System.out.println("MOUSE PRESSED ");
                   currentState = State.DRAGGING;
                }
            }







        }

        else{

            // LINK CASE

            switch (currentState) {

                case READY -> {

                    // select a SMNode
                    if (model.hitSMNode(event.getX() + iModel.viewLeft ,event.getY() + iModel.viewTop)) {
                        // pressed on a node

                        SMStateNode b = model.whichHit(event.getX() + iModel.viewLeft ,event.getY() + iModel.viewTop);
                        iModel.setSelected(b);

                        prevX = event.getX();
                        prevY = event.getY();

                        currentState = State.DRAGGING;  // pass it to dragging
                        //System.out.println(" PRESSED ON NODE");

                        model.addXLine(b, event.getX() + iModel.viewLeft, event.getY() + iModel.viewTop);
                        model.running_line.start_node = b;

                        // selected b

                    }

                    // create a SMNode
                    else {
                       // System.out.println("PRESSED OUTSIDE THE NODE");
                    }
                }
            }


        }

    }



    public void handleDragged(MouseEvent event , double x, double y) {

        if (iModel.getSelectedButton().icon == "Arrow"){

            switch (currentState) {

                case PREPARE_CREATE -> {
                    currentState = State.READY;
                }
                case DRAGGING -> {

                    dX = event.getX() - prevX;
                    dY = event.getY() - prevY;
                    prevX = event.getX();
                    prevY = event.getY();

                    if (iModel.getSelected().getClass().getSimpleName().equals("SMStateNode")) {
                        model.moveSMnode((SMStateNode) iModel.getSelected(), dX + iModel.viewLeft ,dY + iModel.viewTop, event.getX(), event.getY());
                    }

                    else if (iModel.getSelected().getClass().getSimpleName().equals("SMRectangle")){
                        model.moveSMRectangle((SMRectangle) iModel.getSelected(), dX + iModel.viewLeft ,dY+ iModel.viewTop, event.getX(), event.getY());
                    }
                }
            }
        }

        else if (iModel.getSelectedButton().icon == "Move"){

            // UNDER CONSTRUCTION



            switch (currentState) {

                case DRAGGING -> {

                    dX = event.getX() - prevX;
                    dY = event.getY() - prevY;
                    prevX = event.getX();
                    prevY = event.getY();

//                    if (iModel.getSelected().getClass().getSimpleName().equals("SMStateNode")) {
//                        model.moveSMnode((SMStateNode) iModel.getSelected(), dX,dY, event.getX(), event.getY());
//                    }
//
//                    else if (iModel.getSelected().getClass().getSimpleName().equals("SMRectangle")){
//                        model.moveSMRectangle((SMRectangle) iModel.getSelected(), dX,dY, event.getX(), event.getY());
//                    }

                    System.out.println("MOVE MOVE MOVE MOVE");
                    iModel.moveView(dX+ iModel.viewLeft , dY + iModel.viewTop );


                }
            }




        }

        else{

            switch (currentState) {

                case PREPARE_CREATE -> {


                    SMStateNode node = (SMStateNode) iModel.getSelected();

                    //System.out.println("PASS INTO MAKING A LINE");
                    currentState = State.DRAGGING;

                }

                case DRAGGING -> {

                    dX = event.getX() - prevX;
                    dY = event.getY() - prevY;
                    prevX = event.getX();
                    prevY = event.getY();

                    SMStateNode sb = (SMStateNode) iModel.getSelected();

                    //System.out.println("MOVING THE X LINE");
                    model.moveXLine(model.running_line, event.getX() + iModel.viewTop , event.getY() + iModel.viewLeft);

                }
            }
        }
    }

    public void handleReleased(MouseEvent event ,  double x, double y) {

        if (iModel.getSelectedButton().icon == "Arrow"){


            switch (currentState) {

                // clicked and blob created
                case PREPARE_CREATE -> {
                    model.addSMNode(event.getX() + iModel.viewLeft ,event.getY() + iModel.viewTop);
                    currentState = State.READY;
                }

                // dragged and blob in ready state
                case DRAGGING -> {
                    iModel.unselect();
                    currentState = State.READY;
                }
            }

        }

        else if (iModel.getSelectedButton().icon == "Move"){




            switch (currentState) {


                // dragged and blob in ready state
                case DRAGGING -> {
                    currentState = State.READY;
                    System.out.println("RELEASED ");
                }
            }





        }

        else{

            // CASE OF LINK

            switch (currentState) {

                // clicked and blob created
                case PREPARE_CREATE -> {

                    System.out.println("");
                    currentState = State.READY;
                }

                // dragged and blob in ready state
                case DRAGGING -> {

                    if (model.hitSMNode(event.getX() + iModel.viewLeft ,event.getY() + iModel.viewTop )){

                        //System.out.println("RELEASED ON OTHER NODE !!!");

                        SMStateNode b = model.whichHit(event.getX() + iModel.viewLeft , event.getY() + iModel.viewTop );
                        model.running_line.end_node = b;
                        model.running_line.add_smnode(b);
                        model.running_line.setEnd_node(b);
                        b.addLine(model.running_line);

                        double rect_x = (model.running_line.x1 + model.running_line.x2)/2;
                        double rect_y = (model.running_line.y1 + model.running_line.y2)/2;

                        model.makeRectangle(model.running_line, rect_x, rect_y);
                        model.running_line = null;
                    }


                    else{
                        //System.out.println("RELEASED OUTSIDE ANY NODE");

                        model.removeXline(model.running_line);
                    }

                    iModel.unselect();
                    currentState = State.READY;
                }
            }
        }
    }

    public void handleButtonClick(ToolButton mytb) {

        iModel.setSelectedButton(mytb);
    }

    public void handleKeyPressed(KeyEvent keyEvent) {

        if (keyEvent.getCode().equals(KeyCode.DELETE)) {
            model.deleteitem(iModel.selected);
        }
    }


    public void handleRectUpdate(ActionEvent event, LinkPropertiesView rect_view, SMItem smItem){

        System.out.println("RECT UPDATE BUTTON");

        String event1 = rect_view.event_tf.getText();
        String context = rect_view.context_tf.getText();
        String side_effects = rect_view.side_eff_tf.getText();


        System.out.println(event1 + " "+ context + " " + side_effects);

        model.RectUpdate((SMRectangle) smItem, event1, context, side_effects);


    }

    public void handleNodeUpdate(ActionEvent event, NodePropertiesView node_view, SMItem smItem){

        System.out.println("NODE UPDATE BUTTON");

        String state_name = node_view.state_name_tf.getText();
        System.out.println(state_name);

        model.NodeUpdate((SMStateNode) smItem, state_name);
    }

    public void handleNodeDelete( ActionEvent event, NodePropertiesView node_view, SMItem smItem ){

        model.NodeDelete( (SMStateNode) smItem);

    }

    public void handleRectDelete(ActionEvent even, LinkPropertiesView rect_view, SMItem smItem){

        model.RectDelete((SMRectangle) smItem);

    }
}
