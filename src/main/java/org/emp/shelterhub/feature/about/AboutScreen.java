package org.emp.shelterhub.feature.about;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.infrastructure.utils.Dimensions;

public class AboutScreen {
  private static final String ABOUT_TITLE = "O aplikacji ShelterHub";
  private static final String ABOUT_DESCRIPTION =
      "ShelterHub to system zarządzania Schroniskiem Górskim Wilcza Turnia";

  private StackPane rootPane = new StackPane();
  private AboutScreenViewModel viewModel = new AboutScreenViewModel(rootPane);

    public void show( String username) {
//        viewModel.setRoot(root);

    // Create main layout
    BorderPane mainLayout = new BorderPane();
    mainLayout.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    // Create content for the center
    VBox content = new VBox(Dimensions.SPACING_LARGE);
    content.setAlignment(Pos.CENTER);
    content.setPadding(new Insets(Dimensions.SPACING_LARGE));

    Label titleLabel = new Label(ABOUT_TITLE);
    titleLabel.setStyle(AppTheme.getLabelStyle(Dimensions.FONT_SIZE_TITLE, true, AppTheme.TEXT_COLOR_PRIMARY));

    Label descriptionLabel = new Label(ABOUT_DESCRIPTION);
    descriptionLabel.setStyle(AppTheme.getLabelStyle(Dimensions.FONT_SIZE_BODY, false, AppTheme.TEXT_COLOR_SECONDARY));

    // Display user data
    Label userLabel = new Label("Zalogowany jako: " + username);
    userLabel.setStyle(AppTheme.getLabelStyle(Dimensions.FONT_SIZE_SUBTITLE, true, AppTheme.PRIMARY_COLOR));

    Button backButton = new Button("Powrót");
    backButton.setStyle(AppTheme.getButtonStyle(AppTheme.PRIMARY_COLOR));
    backButton.setOnAction(
        e -> {
          viewModel.navigateToWelcomeScreen();
        });

    content.getChildren().addAll(titleLabel, descriptionLabel, userLabel, backButton);

    // Set content to center of BorderPane
    mainLayout.setCenter(content);

    // Add the BorderPane to the root
    root.getChildren().add(mainLayout);
  }

  public void show(StackPane root) {
    show(root, "Gość");
  }
}
