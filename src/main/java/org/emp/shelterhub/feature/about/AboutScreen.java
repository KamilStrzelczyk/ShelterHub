package org.emp.shelterhub.feature.about;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.infrastructure.utils.Dimensions;

public class AboutScreen extends VBox {
  private static final String ABOUT_TITLE = "O aplikacji ShelterHub";
  private static final String ABOUT_DESCRIPTION =
      "ShelterHub to system zarządzania Schroniskiem Górskim Wilcza Turnia";

  private AboutScreenViewModel viewModel = new AboutScreenViewModel();

  private Label userLabel = new Label();
  private Label roleLabel = new Label();
  private Label versionLabel = new Label();

  public AboutScreen() {
    this.setSpacing(Dimensions.SPACING_LARGE);
    this.setPadding(new Insets(Dimensions.SPACING_LARGE));
    this.setAlignment(Pos.CENTER);
    this.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    Label titleLabel = new Label(ABOUT_TITLE);
    titleLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_TITLE, true, AppTheme.TEXT_COLOR_PRIMARY));

    Label descriptionLabel = new Label(ABOUT_DESCRIPTION);
    descriptionLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_BODY, false, AppTheme.TEXT_COLOR_SECONDARY));

    // Pobieramy stan z ViewModelu
    AboutScreenState state = viewModel.getState();

    userLabel.setText("Zalogowany jako: " + state.getUsername());
    userLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_SUBTITLE, true, AppTheme.PRIMARY_COLOR));

    roleLabel.setText("Rola: " + state.getRole());
    roleLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_SUBTITLE, true, AppTheme.PRIMARY_COLOR));

    versionLabel.setText("Wersja aplikacji: " + state.getVersion());
    versionLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_SUBTITLE, true, AppTheme.PRIMARY_COLOR));

    this.getChildren().addAll(titleLabel, descriptionLabel, userLabel, roleLabel, versionLabel);
  }
}
