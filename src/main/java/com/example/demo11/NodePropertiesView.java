package com.example.demo11;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class NodePropertiesView extends StackPane {

    String state_name;
    TextField state_name_tf;
    Button update_btn;
    Button delete_btn;


    public NodePropertiesView(){

        state_name = "Default";

        VBox root = new VBox();

        Label name_label = new Label("State Name: ");
        state_name_tf = new TextField("Default");

        update_btn = new Button("Update");
        delete_btn = new Button("Delete");

        root.getChildren().addAll(name_label, state_name_tf, update_btn, delete_btn);
        this.getChildren().add(root);
    }


    public void setState_name(String a){
        state_name = a;
    }


    public String getState_name(){
        return state_name;
    }
}
