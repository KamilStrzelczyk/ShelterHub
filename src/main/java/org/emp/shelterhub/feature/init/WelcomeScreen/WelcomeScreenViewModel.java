package org.emp.shelterhub.feature.init.WelcomeScreen;

import javafx.scene.layout.StackPane;
import org.emp.shelterhub.feature.MainContainer;
import org.emp.shelterhub.feature.navigation.MainNavigator;

public class WelcomeScreenViewModel {
  State state = new State();
  private StackPane root;

  void setRoot(StackPane root) {
    this.root = root;
  }

  void login(String username, String password) {
    state.username = username;
    state.password = password;

    if (root != null) {
      root.getChildren().clear();
    }
    navigateToAboutScreen();
  }

  void navigateToAboutScreen() {
    MainContainer mainContainer = new MainContainer(root);
    MainNavigator navigator = MainNavigator.getInstance();
    navigator.setMainContainer(mainContainer);
    navigator.navigateToMainContainer();
  }
}

class State {
  String username = "";
  String password = "";
}
