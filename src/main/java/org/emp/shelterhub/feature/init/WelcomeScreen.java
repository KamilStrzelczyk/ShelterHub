package org.emp.shelterhub.feature.init;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class WelcomeScreen {

  private static final String WELCOME_MESSAGE = "Witamy w systemie ShelterHub!";

  public static void show(StackPane root) {
    Label welcomeLabel = new Label(WELCOME_MESSAGE);
    welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    root.getChildren().add(welcomeLabel); // Dodanie tekstu do środka
  }
}
