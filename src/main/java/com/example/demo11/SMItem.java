package com.example.demo11;

public abstract class SMItem {

    double x,y;
    double right, bottom;

    public SMItem(double X, double Y){
        x=X;
        y=Y;
        right = x;
        bottom = y;
    }

    public abstract void addLine(SMTranisitonLink xline);

    public abstract boolean contains(double cx, double cy);


    public abstract void move(double dx, double dy);




}

