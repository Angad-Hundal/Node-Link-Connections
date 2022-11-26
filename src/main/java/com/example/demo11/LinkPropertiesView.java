package com.example.demo11;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LinkPropertiesView extends StackPane {


    TextField event_tf;
    TextField context_tf;
    TextField side_eff_tf;
    Button update_btn;
    Button delete_btn;


    public LinkPropertiesView(){

        VBox root = new VBox();

        Label event_lb = new Label("Event");
        event_tf = new TextField("No Event");

        Label context_lb = new Label("Context");
        context_tf = new TextField("Context");

        Label side_effect_lb = new Label("Side Effects");
        side_eff_tf = new TextField("Side Effects");

        update_btn = new Button("Update");
        delete_btn = new Button("Delete");


        root.getChildren().addAll(event_lb, event_tf, context_lb, context_tf, side_effect_lb, side_eff_tf, update_btn, delete_btn);
        this.getChildren().add(root);

    }



}
