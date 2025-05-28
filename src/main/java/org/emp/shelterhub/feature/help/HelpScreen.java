package org.emp.shelterhub.feature.help;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.infrastructure.utils.Dimensions;

public class HelpScreen extends VBox {

  private static final String HELP_TITLE = "Pomoc i Kontakt";
  private static final String PHONE_NUMBER = "Telefon: +48 123 456 789";
  private static final String EMAIL_ADDRESS = "Email: kontakt@shelterhub.com";

  public HelpScreen() {
    this.setSpacing(Dimensions.SPACING_LARGE);
    this.setPadding(new Insets(Dimensions.SPACING_LARGE));
    this.setAlignment(Pos.CENTER);
    this.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    Label titleLabel = new Label(HELP_TITLE);
    titleLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_TITLE, true, AppTheme.TEXT_COLOR_PRIMARY));

    Label phoneLabel = new Label(PHONE_NUMBER);
    phoneLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_BODY, false, AppTheme.TEXT_COLOR_SECONDARY));

    Label emailLabel = new Label(EMAIL_ADDRESS);
    emailLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_BODY, false, AppTheme.PRIMARY_COLOR)
            + "-fx-underline: true;"); // Underline the email
    emailLabel.setOnMouseClicked(
        e -> {
          try {
            if (Desktop.isDesktopSupported()
                && Desktop.getDesktop().isSupported(Desktop.Action.MAIL)) {
              Desktop.getDesktop().mail(new URI("mailto:" + EMAIL_ADDRESS.replace("Email: ", "")));
            } else {
              System.out.println("Desktop mail not supported on this platform.");
            }
          } catch (IOException | URISyntaxException ex) {
            System.err.println("Error opening mail client: " + ex.getMessage());
          }
        });

    this.getChildren().addAll(titleLabel, phoneLabel, emailLabel);
  }
}
