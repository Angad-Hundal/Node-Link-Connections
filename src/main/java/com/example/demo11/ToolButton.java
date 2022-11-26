package com.example.demo11;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ToolButton extends Button {

    Background selectedBG;
    Background unselectedBG;
    // Color myColor;
    String icon;
    String imageView;

    // added on trial basic

    private BooleanProperty selected;


    public ToolButton(String image, String img_name) {


        super();
        this.setPrefSize(70,50);
        icon = image;


        imageView = img_name;
        Image immagePointer = new Image(imageView);
        //ImageView imageView = new ImageView(getClass().getResource("icons8-cursor-30").toExternalForm());
        ImageView imageView = new ImageView(immagePointer);
        this.setGraphic(imageView);

//        ImageView v = new ImageView(getClass().getResource(image).toExternalForm());
//        this.setGraphic(v);


        //this.setText(image);

        selectedBG = new Background(new BackgroundFill(Color.GRAY,new CornerRadii(10),null));
        unselectedBG = new Background(new BackgroundFill(Color.GRAY,new CornerRadii(10),new Insets(5,5,5,5)));
        setBackground(unselectedBG);
        this.setGraphic(imageView);
    }

    public void select() {

        setBackground(selectedBG);
        System.out.println(icon + " Selected");
    }
    public void unselect() {

        setBackground(unselectedBG);
        System.out.println(icon + " Unselected");
    }
}
