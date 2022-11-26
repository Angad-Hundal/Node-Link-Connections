package com.example.demo11;

import java.util.ArrayList;

public class SMTranisitonLink {

    double x1,y1,x2,y2;


    double left,top,right,bottom;
    double startX,startY;


    SMRectangle smRectangle;



    ArrayList<SMStateNode> xline_nodes;  // can contain only 2 nodes

    SMStateNode start_node;
    SMStateNode end_node;

    public SMTranisitonLink(double x_1, double y_1){


        startX = x_1;
        startY = y_1;
        left = x_1;
        top = y_1;
        right = x_1;
        bottom = y_1;


        xline_nodes = new ArrayList<SMStateNode>();
    }

    public void add_smnode(SMStateNode node){
        xline_nodes.add(node);
    }

    public void setEnd_node(SMStateNode node){
        this.end_node = node;
        xline_nodes.add(node);
    }



    public void setSmRectangle(SMRectangle rect){
        smRectangle =rect;
    }



}
