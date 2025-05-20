package org.emp.shelterhub.feature.about;

import javafx.scene.layout.StackPane;
import org.emp.shelterhub.feature.init.WelcomeScreen.WelcomeScreen;

public class AboutScreenViewModel {
  private StackPane root;

  public AboutScreenViewModel(StackPane root) {
    this.root = root;
  }

  void setRoot(StackPane root) {
    this.root = root;
  }

  void navigateToWelcomeScreen() {
    if (root != null) {
      root.getChildren().clear();
      WelcomeScreen.show(root);
    }
  }
}
