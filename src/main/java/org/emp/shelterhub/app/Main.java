package org.emp.shelterhub.app;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.emp.shelterhub.feature.MainContainer;
import org.emp.shelterhub.feature.init.SplashScreen;
import org.emp.shelterhub.feature.init.WelcomeScreen.WelcomeScreen;
import org.emp.shelterhub.feature.navigation.MainNavigator;
import org.emp.shelterhub.feature.navigation.NavigationBarButton;

public class Main extends Application {
  private static final String MAIN_TITLE = "ShelterHub";

  @Override
  public void start(Stage primaryStage) {
    SplashScreen.show(() -> mainContainer(primaryStage));
  }

  private void mainContainer(Stage primaryStage) {
    primaryStage.setTitle(MAIN_TITLE);

    StackPane root = new StackPane();
    Scene scene = createFullScreenScene(root);

    primaryStage.setScene(scene);
    primaryStage.show();


    MainContainer mainContainer = new MainContainer(root);
    MainNavigator navigator = MainNavigator.getInstance();
    navigator.setMainContainer(mainContainer);

    navigator.navigateToWelcomeScreen();
//    MainContainer mainContainer = new MainContainer(root);
//    mainContainer.showScreen(NavigationBarButton.HELP);
  }

  private Scene createFullScreenScene(StackPane root) {
    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    double screenWidth = screenBounds.getWidth();
    double screenHeight = screenBounds.getHeight();
    return new Scene(root, screenWidth, screenHeight);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
