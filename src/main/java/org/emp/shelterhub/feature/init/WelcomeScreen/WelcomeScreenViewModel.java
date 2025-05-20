package org.emp.shelterhub.feature.init.WelcomeScreen;

import javafx.scene.layout.StackPane;
import org.emp.shelterhub.feature.about.AboutScreen;
import org.emp.shelterhub.feature.navigation.MainNavigator;

public class WelcomeScreenViewModel {
    MainNavigator mainNavigator;
    State state = new State();
    private StackPane root;

    public WelcomeScreenViewModel(MainNavigator mainNavigator) {
        this.mainNavigator = mainNavigator;
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
        mainNavigator.navigateToMainContainer();
    }
}
