package org.emp.shelterhub.feature.help;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class HelpScreen extends Pane {

    public HelpScreen() {
        Label label = new Label("Help");
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
        double centerX = this.getWidth() / 2 - label.getWidth() / 2;
        double centerY = this.getHeight() / 2 - label.getHeight() / 2;
        label.setLayoutX(centerX);
        label.setLayoutY(centerY);
        this.getChildren().add(label);
    }
}
