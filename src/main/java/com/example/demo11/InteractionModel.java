package com.example.demo11;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {


    List<IModelListener> subscribers;

    SMItem selected;

    SMRectangle selected_rect;

    ToolButton selected_button;


    double viewLeft, viewTop;
    double worldWidth, worldHeight;
    double viewWidth, viewHeight;




    public InteractionModel() {

        subscribers = new ArrayList<>();
        viewTop=0;
        viewLeft=0;

    }

    public void addSubscriber(IModelListener sub) {
        subscribers.add(sub);
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.iModelChanged());
    }


    // CHANGED
    public void setSelected(SMItem b) {
        selected = b;
        notifySubscribers();
    }

    public void unselect() {
        selected = null;
    }


    // CHANGED
    public SMItem getSelected() {
        return selected;
    }


//    public SMRectangle getSelected_rect(){
//        return selected_rect;
//    }

//    public void setSelected_rect(SMRectangle a){
//        selected_rect = a;
//    }

    public void unselectRect(){
        selected_rect = null;
    }

    public ToolButton getSelectedButton() {
        return selected_button;
    }


    public void setSelectedButton(ToolButton c) {

        selected_button = c;
        notifySubscribers();
    }


    // CHANGED
    // FROM
    // HERE

    public boolean checkViewfinderHit(double x, double y) {
        //System.out.println("checkViewfinder:  viewLeft: " + viewLeft + "  viewTop: " + viewTop + "   viewWidth:" +
        //        viewWidth + "   viewHeight:" + viewHeight + "   " + x + "," + y);
        return x >= viewLeft && x <= viewLeft + viewWidth &&
                y >= viewTop && y <= viewTop + viewHeight;
    }


    public void setViewExtents(double newWidth, double newHeight) {
        //public void setViewExtents(double newWidth, double newHeight, double newRight, double newBottom) {
//        viewMaxRight = newRight;
//        viewMaxBottom = newBottom;
        viewWidth = newWidth;
        viewHeight = newHeight;
        notifySubscribers();
    }



    public void setWorldExtents(double newWidth, double newHeight) {
        worldWidth = newWidth;
        worldHeight = newHeight;
    }


    public void moveView(double dX, double dY) {
        viewLeft -= dX;
        viewTop -= dY;
        if (viewLeft < 0) viewLeft = 0;
        if (viewLeft > (1.0 - viewWidth)) viewLeft = 1.0 - viewWidth;
        if (viewTop < 0) viewTop = 0;
        if (viewTop > (1.0 - viewHeight)) viewTop = 1.0 - viewHeight;
        //System.out.println("viewLeft: " + viewLeft + "  viewTop: " + viewTop + "   " + dX + "," + dY);
        notifySubscribers();
    }

}
