package org.emp.shelterhub.feature.init;

import javafx.scene.layout.StackPane;
import org.emp.shelterhub.feature.about.AboutScreen;

public class WelcomeScreenViewModel {
    State state = new State();
    private StackPane root;

    public WelcomeScreenViewModel(StackPane root) {
        this.root = root;
    }

    public WelcomeScreenViewModel() {
    }

    void setRoot(StackPane root) {
        this.root = root;
    }

    void login(String username, String password) {
        state.username = username;
        state.password = password;

        navigateToAboutScreen();
    }

    void navigateToAboutScreen() {
        if (root != null) {
            root.getChildren().clear();
            AboutScreen.show(root, state.username);
        }
    }
}

class State {
    String username = "";
    String password = "";
}