package com.example.demo11;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ToolPalette extends StackPane implements IModelListener{



    SMModel model;
    InteractionModel iModel;
    String[] buttonNames = {"Arrow", "Move", "Link"};
    String[] buttonimage = {"file:icons8-cursor-30.png", "file:icons8-move-48.png", "file:icons8-plus-math-24.png"};
    // insert image into the buttons

    ArrayList<ToolButton> buttons;


    public ToolPalette() {

        VBox root = new VBox();
        buttons = new ArrayList<>();
        ToolButton cb;

        int i=0;

        for (String button_name : buttonNames) {

            //cb.imageView = buttonimage[i];
            cb = new ToolButton(button_name, buttonimage[i]);
            buttons.add(cb);
            i++;
        }

        root.getChildren().addAll(buttons);
        this.getChildren().add(root);
    }

    public void setInteractionModel(InteractionModel im) {
        iModel = im;
    }

    public void setController(AppController controller) {

        buttons.forEach(b -> {
            b.setOnAction(e -> controller.handleButtonClick(b));
        });
    }

    public void init() {
        buttons.get(0).fire();
    }

    public void iModelChanged() {

        buttons.forEach(b -> {

            b.unselect();
            if (b.equals(iModel.getSelectedButton())) {
                b.select();}
        });
    }

}
