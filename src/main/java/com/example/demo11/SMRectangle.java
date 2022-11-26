package com.example.demo11;


import java.util.ArrayList;

public class SMRectangle extends SMItem {

    //double x,y;
    double length, width;
    String event, content, side_effects;
    ArrayList<SMTranisitonLink> rect_lines;

    String state_name;


    public SMRectangle(double X, double Y){

        super(X,Y);
        x=X;
        y=Y;
        length = 150;
        width = 90;
        rect_lines = new ArrayList<>();
        event = "No Event";
        content = "No Context";
        side_effects = "No Side Effects";

    }

    public void setEvent(String e){
        event = e;
    }

    public void setContent(String c){
        content = c;
    }

    public void setSide_effects(String se){
        side_effects = se;
    }

    public String getEvent(){
        return event;
    }

    public String getContent(){
        return content;
    }

    public String getSide_effects(){
        return side_effects;
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
        rect_lines.add(line);
    }
}
