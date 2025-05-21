package org.emp.shelterhub.feature.about;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.infrastructure.utils.Dimensions;

public class AboutScreen extends Pane {
  private static final String ABOUT_TITLE = "O aplikacji ShelterHub";
  private static final String ABOUT_DESCRIPTION =
      "ShelterHub to system zarządzania Schroniskiem Górskim Wilcza Turnia";

  private StackPane rootPane = new StackPane();
  private AboutScreenViewModel viewModel = new AboutScreenViewModel(rootPane);

  public StackPane show(String username) {
    viewModel.setRoot(rootPane);

    BorderPane mainLayout = new BorderPane();
    mainLayout.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    VBox content = new VBox(Dimensions.SPACING_LARGE);
    content.setAlignment(Pos.CENTER);
    content.setPadding(new Insets(Dimensions.SPACING_LARGE));

    Label titleLabel = new Label(ABOUT_TITLE);
    titleLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_TITLE, true, AppTheme.TEXT_COLOR_PRIMARY));

    Label descriptionLabel = new Label(ABOUT_DESCRIPTION);
    descriptionLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_BODY, false, AppTheme.TEXT_COLOR_SECONDARY));

    Label userLabel = new Label("Zalogowany jako: " + username);
    userLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_SUBTITLE, true, AppTheme.PRIMARY_COLOR));

    content.getChildren().addAll(titleLabel, descriptionLabel, userLabel);

    mainLayout.setCenter(content);

    rootPane.getChildren().add(mainLayout);

    return rootPane;
  }
}
