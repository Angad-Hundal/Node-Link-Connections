package com.example.demo11;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class MainUI extends StackPane {

    public MainUI(){


        BorderPane root = new BorderPane();

        //Image img = new Image();
//        ImageView v = new ImageView(getClass().getResource("arrow.png").toExternalForm());
//        Button b1 = new Button();
//        b1.setGraphic(v);


        // Join 2 views here
        DiagramView view = new DiagramView();
        ToolPalette left_view = new ToolPalette();
        MiniView miniView = new MiniView();
        miniView.setPrefSize(300,300);
        miniView.setMaxSize(300,300);
        miniView.setMinSize(300,300);
        miniView.setAlignment(Pos.TOP_LEFT);
        //miniView.setLayoutX(0);
        //miniView.setLayoutY(0);


        AppController controller = new AppController();
        SMModel model = new SMModel();
        InteractionModel iModel = new InteractionModel();

        controller.setModel(model);
        controller.setiModel(iModel);

        NodePropertiesView node_edit_view = new NodePropertiesView();
        LinkPropertiesView rect_edit_view = new LinkPropertiesView();


        controller.setModel(model);

        view.setModel(model);
        miniView.setModel(model);  //

        controller.setiModel(iModel);
        view.setIModel(iModel);
        miniView.setIModel(iModel); //
        model.addSubscriber(view);
        iModel.addSubscriber(view);

        model.addSubscriber(miniView);   //
        iModel.addSubscriber(miniView);  //

        view.setController(controller);


        left_view.setInteractionModel(iModel);
        left_view.setController(controller);
        iModel.addSubscriber(left_view);
        root.setLeft(left_view);


        //view.getChildren().add(miniView);
        StackPane.setAlignment(miniView, Pos.TOP_LEFT);
        root.setLeft(left_view);
        root.setCenter(view);

        view.getChildren().add(miniView);
        //root.getChildren().add(miniView);
        //root.setTop(miniView);
        //StackPane.setAlignment(miniView, Pos.TOP_LEFT);


        this.getChildren().add(root);

        left_view.init();

    }
}
