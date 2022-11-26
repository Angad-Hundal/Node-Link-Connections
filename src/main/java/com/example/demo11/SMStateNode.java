package com.example.demo11;

import javafx.scene.control.Label;

import java.util.ArrayList;

public class SMStateNode extends SMItem {

    //double x,y;
    double length, width;
    String name;

    Label name_label;

    ArrayList<SMTranisitonLink> node_xlines;


    public SMStateNode(double x_coordinate, double y_coordinate){

        super(x_coordinate, y_coordinate);

        x = x_coordinate;
        y = y_coordinate;
        length = 150;
        width = 100;
        name = "Default";
        name_label = new Label("Default");
        node_xlines = new ArrayList<SMTranisitonLink>();
    }


    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public boolean contains(double cx, double cy) {


        if ( cx <= x+length && cx >= x && cy <= y+width && cy >= y){
            return true;}

        else{
            return false;
        }

    }



    public void addLine(SMTranisitonLink line){
        node_xlines.add(line);
    }

}


