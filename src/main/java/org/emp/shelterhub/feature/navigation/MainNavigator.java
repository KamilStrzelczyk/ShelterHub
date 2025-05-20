package org.emp.shelterhub.feature.navigation;

import javafx.scene.layout.StackPane;
import org.emp.shelterhub.feature.MainContainer;
import org.emp.shelterhub.feature.init.WelcomeScreen.WelcomeScreen;

public class MainNavigator {
    private static MainNavigator instance;
    private MainContainer mainContainer;

    private MainNavigator() {
        // Prywatny konstruktor
    }

    public static MainNavigator getInstance() {
        if (instance == null) {
            instance = new MainNavigator();
        }
        return instance;
    }

    public void setMainContainer(MainContainer mainContainer) {
        this.mainContainer = mainContainer;
    }

    public void navigateToWelcomeScreen() {
        if (mainContainer != null) {
            StackPane root = new StackPane();
            WelcomeScreen.show(root);
        }
    }

    public void navigateToMainContainer() {
        if (mainContainer != null) {
            StackPane root = new StackPane();
            new MainContainer(root);
        }
    }
}