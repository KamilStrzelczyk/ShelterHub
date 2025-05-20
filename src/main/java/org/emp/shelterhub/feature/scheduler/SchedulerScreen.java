package org.emp.shelterhub.feature.scheduler;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class SchedulerScreen extends Pane {

    public SchedulerScreen() {
        Label label = new Label("Scheduler");
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
        double centerX = this.getWidth() / 2 - label.getWidth() / 2;
        double centerY = this.getHeight() / 2 - label.getHeight() / 2;
        label.setLayoutX(centerX);
        label.setLayoutY(centerY);
        this.getChildren().add(label);
    }
}
