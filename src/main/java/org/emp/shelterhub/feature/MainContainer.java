package org.emp.shelterhub.feature;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.emp.shelterhub.feature.about.AboutScreen;
import org.emp.shelterhub.feature.employee.EmployeeScreen;
import org.emp.shelterhub.feature.footer.Footer;
import org.emp.shelterhub.feature.help.HelpScreen;
import org.emp.shelterhub.feature.init.WelcomeScreen.WelcomeScreen;
import org.emp.shelterhub.feature.navigation.NavigationBar;
import org.emp.shelterhub.feature.navigation.NavigationBarButton;
import org.emp.shelterhub.feature.report.presentation.ReportScreen;
import org.emp.shelterhub.feature.room.RoomScreen;
import org.emp.shelterhub.feature.scheduler.SchedulerScreen;
import org.emp.shelterhub.feature.settings.SettingsScreen;
import org.emp.shelterhub.feature.topBar.TopBar;
import org.emp.shelterhub.lib.infrastructure.repository.UserRepository;

public class MainContainer {

  private final BorderPane layout = new BorderPane();
  private final StackPane screenContainer = new StackPane();
  private final StackPane rootPane; // Store the root pane

  public MainContainer(StackPane root) {
    this.rootPane = root; // Store the root pane
    NavigationBar navigator = new NavigationBar();
    navigator.start(layout, this::showScreen);
    layout.setCenter(screenContainer);

    TopBar topBar = new TopBar(this::performLogout); // Pass logout method reference
    layout.setTop(topBar);

    Footer footer = new Footer();
    layout.setBottom(footer);

    rootPane.getChildren().add(layout);

    showScreen(NavigationBarButton.ABOUT);
  }

  private void performLogout() {
    // 1. Clear user session
    UserRepository.getInstance().logout();

    // 2. Clear the current UI (MainContainer's layout from rootPane)
    if (rootPane != null) {
      rootPane.getChildren().clear();
      // 3. Show the WelcomeScreen again on the same rootPane
      WelcomeScreen.show(rootPane);
    }
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
          case ABOUT -> new AboutScreen();
        };
    screenContainer.getChildren().setAll(view);
  }
}
