package org.emp.shelterhub.feature.navigation;

import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;

public class NavigationBar {

  public void start(BorderPane layout, Consumer<NavigationBarButton> onNavigate) {
    VBox navBar = new VBox(10);
    navBar.setStyle(AppTheme.getContainerStyle());
    navBar.setAlignment(Pos.CENTER);

    double buttonWidth = 150;
    double buttonHeight = 40;

    for (NavigationBarButton button : NavigationBarButton.values()) {
      Button btn = new Button(button.getName());
      btn.setStyle(AppTheme.getButtonStyle(AppTheme.PRIMARY_COLOR));
      btn.setPrefWidth(buttonWidth);
      btn.setPrefHeight(buttonHeight);
      btn.setOnAction(e -> onNavigate.accept(button));
      navBar.getChildren().add(btn);
    }

    layout.setLeft(navBar);
  }
}
