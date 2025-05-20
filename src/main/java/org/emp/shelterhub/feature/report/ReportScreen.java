package org.emp.shelterhub.feature.report;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ReportScreen extends Pane {

  public ReportScreen() {
    Label label = new Label("Report");
    label.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
    double centerX = this.getWidth() / 2 - label.getWidth() / 2;
    double centerY = this.getHeight() / 2 - label.getHeight() / 2;
    label.setLayoutX(centerX);
    label.setLayoutY(centerY);
    this.getChildren().add(label);
  }
}
