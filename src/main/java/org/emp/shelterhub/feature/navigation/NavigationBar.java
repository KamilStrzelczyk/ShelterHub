package org.emp.shelterhub.feature.navigation;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;


public class NavigationBar {

    public void start(BorderPane layout, Consumer<NavigationBarButton> onNavigate) {
        VBox navBar = new VBox(10);
        navBar.setAlignment(Pos.CENTER);

        for (NavigationBarButton button : NavigationBarButton.values()) {
            Button btn = new Button(button.getName());
            btn.setStyle("-fx-text-fill: white;");
            btn.setOnAction(e -> onNavigate.accept(button));
            navBar.getChildren().add(btn);
        }

        navBar.setStyle("-fx-background-color: #2C3E50; -fx-padding: 10;");
        layout.setLeft(navBar);
    }
}
