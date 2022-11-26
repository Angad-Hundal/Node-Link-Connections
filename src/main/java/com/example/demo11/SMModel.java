package com.example.demo11;

import java.util.ArrayList;
import java.util.List;

public class SMModel {
    private ArrayList<ModelListener> subscribers;
    private ArrayList<SMStateNode> sm_nodes;

    private ArrayList<SMTranisitonLink> all_xlines;

    SMTranisitonLink running_line;

    private ArrayList<SMRectangle> all_rect;


    public SMModel() {
        subscribers = new ArrayList<>();
        sm_nodes = new ArrayList<>();
        all_xlines = new ArrayList<>();
        all_rect = new ArrayList<>();
        //all_shapes = new ArrayList<>();
    }


    public List<SMStateNode> getSMNodes() {
        return sm_nodes;
    }


    public List<SMTranisitonLink> getAll_xlines(){
        return all_xlines;
    }


    public void addSubscriber(ModelListener aSub) {
        subscribers.add(aSub);
    }


    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }


    public void addSMNode(double x, double y) {
        sm_nodes.add(new SMStateNode(x,y));
        //all_shapes.add(new SMStateNode(x,y));
        notifySubscribers();
    }


    public void makeRectangle(SMTranisitonLink xline, double x, double y){

        // make a recatangle
        SMRectangle a  = new SMRectangle(x, y);
        running_line.smRectangle = a;

        running_line.x2 = x;
        running_line.y2 = y;

        running_line.start_node.node_xlines.add(running_line);
        running_line.start_node.addLine(running_line);
        running_line.smRectangle.rect_lines.add(running_line);


        // make a new line
        SMTranisitonLink new_xline = new SMTranisitonLink(x,y);
        new_xline.smRectangle = a;

        // new line starting point rectangle
        new_xline.x1 = x;
        new_xline.y1 = y;
        new_xline.x2 = running_line.end_node.x;
        new_xline.y2 = running_line.end_node.y;


        // THIS ONE  HERE
        new_xline.end_node = running_line.end_node;
        int index = xline.xline_nodes.indexOf(xline.start_node);//
        xline.xline_nodes.remove(index);//
        new_xline.start_node = null;


        running_line.end_node.addLine(new_xline);

        // add new line to rectangle
        running_line.smRectangle.rect_lines.add(new_xline);



        int ind = running_line.xline_nodes.indexOf(running_line.end_node);
        running_line.xline_nodes.remove(ind);

        int ind2 = running_line.end_node.node_xlines.indexOf(running_line);
        running_line.end_node.node_xlines.remove(ind2);

        all_rect.add(a);
        notifySubscribers();
    }


    public void moveSMRectangle(SMRectangle b, double dx, double dy, double presentx, double presenty){


        b.move(dx,dy);

        b.rect_lines.forEach(line -> {

            if (line.start_node != null) {

                //System.out.println("1");
                // start node present
                line.x1 = line.start_node.x;
                line.y1 = line.start_node.y;
                line.x2 = presentx;
                line.y2 = presenty;
            }

            else if (line.end_node != null){
                //System.out.println("2");

                // end node present
                line.x1 = line.end_node.x;
                line.y1 = line.end_node.y;

                line.x2 = presentx;
                line.y2 = presenty;

            }

            line.smRectangle.x = presentx;
            line.smRectangle.y = presenty;


        });

        notifySubscribers();



    }


    public boolean hitSMRectangle(double x, double y ){

        for (SMRectangle b : all_rect) {
            if (b.contains(x,y)) return true;
        }
        return false;

    }


    public SMRectangle whichRectHit(double x, double y){

        for (SMRectangle b : all_rect) {
            if (b.contains(x,y)) return b;
        }
        return null;
    }


    public void moveSMnode(SMStateNode b, double dx, double dy, double presentx, double presenty) {
        b.move(dx,dy);

        b.node_xlines.forEach(line -> {

            if (b==line.start_node){
                line.x1 = presentx;
                line.y1 = presenty;
            }


            else{
                //  b is the end node

                if(line.smRectangle!=null){

                    line.x1 = line.smRectangle.x;
                    line.y1 = line.smRectangle.y;
                    line.x2 = presentx;
                    line.y2 = presenty;
                }

                else{
                    line.x2 = presentx;
                    line.y2 = presenty;
                }
            }
        });

        notifySubscribers();
    }



    public boolean hitSMNode(double x, double y) {

        for (SMStateNode b : sm_nodes) {
            if (b.contains(x,y)) return true;
        }
        return false;
    }

    public SMStateNode whichHit(double x, double y) {

        for (SMStateNode b : sm_nodes) {
            if (b.contains(x,y)) return b;
        }
        return null;
    }

    public void addXLine(SMStateNode smStateNode1, double x, double y){

        SMTranisitonLink xline = new SMTranisitonLink(x,y);

        xline.x1 = smStateNode1.x;
        xline.y1 = smStateNode1.y;
        xline.x2 = x;
        xline.y2 = y;

        xline.add_smnode(smStateNode1);
        smStateNode1.addLine(xline);
        running_line = xline;
        all_xlines.add(xline);
        notifySubscribers();

    }

    public void moveXLine(SMTranisitonLink xline, double x, double y ){

        //xline.move(x,y);

        xline.x2 = x;
        xline.y2 = y;

        running_line = xline;
        notifySubscribers();
    }

    public void removeXline (SMTranisitonLink xline){


        int index = getAll_xlines().indexOf(xline);
        all_xlines.remove(index);

        xline.xline_nodes.clear();
        int index2 = xline.start_node.node_xlines.indexOf(xline);
        xline.start_node.node_xlines.remove(index2);

        xline.start_node =null;
        xline.end_node = null;

        running_line = null;

        notifySubscribers();
    }


    public void deleteitem(SMItem selecteditem) {

        if (selecteditem != null && selecteditem.getClass().getSimpleName() == "SMRectangle") {
            all_rect.remove(selecteditem);
            notifySubscribers();
        }

        else {
            all_xlines.remove(selecteditem);
            notifySubscribers();
        }
    }

    public void RectUpdate(SMRectangle smItem, String event1, String context, String side_effects){

        smItem.event = event1;
        smItem.content = context;
        smItem.side_effects = side_effects;
        notifySubscribers();
    }

    public void NodeUpdate(SMStateNode smitem, String name){

        smitem.name = name;
        notifySubscribers();
    }


    public void NodeDelete(SMStateNode smItem) {


        // for each line associated with that node
        smItem.node_xlines.forEach(line -> {

            SMRectangle smrect = line.smRectangle;


            if (smrect != null){
                // for each line connected with sm rect
                line.smRectangle.rect_lines.forEach(line2 -> {


                    if (line2 != line){
                        line2.xline_nodes.forEach(node ->{
                        });

                        if (line2.start_node != null){
                            line2.start_node.node_xlines.remove(line2);
                            line2.start_node.node_xlines.remove(line2);


                            for (int i=0; i<line2.start_node.node_xlines.size(); i++){

                                if (line2.start_node.node_xlines.get(i).equals(line2)){
                                    line2.start_node.node_xlines.remove(i);
                                }
                            }
                        }

                        else if (line2.end_node!=null){
                            line2.end_node.node_xlines.remove(line2);
                        }
                    }
                });
                smrect.rect_lines.clear();
                line.smRectangle = null;
            }

            line.xline_nodes.clear();
        });

        smItem.node_xlines.clear();
        sm_nodes.remove(smItem);

        notifySubscribers();
    }

    public void RectDelete(SMRectangle smRect) {

        smRect.rect_lines.forEach(line -> {

            if (line.start_node != null){

                line.start_node.node_xlines.remove(line);
                line.start_node.node_xlines.remove(line);
                line.start_node.node_xlines.remove(line);

                if (line.end_node!=null){
                    line.end_node.node_xlines.remove(line);
                }
            }
            else {
                line.end_node.node_xlines.remove(line);
                line.end_node.node_xlines.remove(line);

                if (line.start_node!=null){
                    line.start_node.node_xlines.remove(line);
                }
            }
        });

        smRect.rect_lines.clear();
        notifySubscribers();
    }
}