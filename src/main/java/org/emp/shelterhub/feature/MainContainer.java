package org.emp.shelterhub.feature;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.emp.shelterhub.feature.about.AboutScreen;
import org.emp.shelterhub.feature.employee.EmployeeScreen;
import org.emp.shelterhub.feature.help.HelpScreen;
import org.emp.shelterhub.feature.navigation.NavigationBar;
import org.emp.shelterhub.feature.navigation.NavigationBarButton;
import org.emp.shelterhub.feature.report.ReportScreen;
import org.emp.shelterhub.feature.room.RoomScreen;
import org.emp.shelterhub.feature.scheduler.SchedulerScreen;
import org.emp.shelterhub.feature.settings.SettingsScreen;

public class MainContainer {

  private final BorderPane layout = new BorderPane();
  private final StackPane screenContainer = new StackPane();

  public MainContainer(StackPane root) {
    NavigationBar navigator = new NavigationBar();
    navigator.start(layout, this::showScreen);
    layout.setCenter(screenContainer);
    root.getChildren().add(layout);

    showScreen(NavigationBarButton.ABOUT);
  }

  public void showScreen(NavigationBarButton screen) {
    Node view =
        switch (screen) {
          case ROOM -> new RoomScreen();
          case EMPLOYEE -> new EmployeeScreen();
          case SCHEDULER -> new SchedulerScreen();
          case REPORT -> new ReportScreen();
          case SETTINGS -> new SettingsScreen();
          case HELP -> new HelpScreen();
          case ABOUT -> {
            AboutScreen aboutScreen = new AboutScreen();
            yield aboutScreen.show("Test");
          }
        };
    screenContainer.getChildren().setAll(view);
  }
}
