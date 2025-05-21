package org.emp.shelterhub.feature.about;

import javafx.scene.layout.StackPane;

public class AboutScreenViewModel {
  private StackPane root;

  public AboutScreenViewModel(StackPane root) {
    this.root = root;
  }

  void setRoot(StackPane root) {
    this.root = root;
  }
}
