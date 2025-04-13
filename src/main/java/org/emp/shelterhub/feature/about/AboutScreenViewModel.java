package org.emp.shelterhub.feature.about;

import javafx.scene.layout.StackPane;
import org.emp.shelterhub.feature.init.WelcomeScreen;

public class AboutScreenViewModel {
    State state = new State();
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

class State {
    String name;
    String password;
}