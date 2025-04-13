package org.emp.shelterhub.feature.init;

import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.infrastructure.utils.Dimensions;
import org.emp.shelterhub.lib.infrastructure.utils.UIComponents;

public class WelcomeScreen {
  private static final String WELCOME_MESSAGE = "Witaj w systemie ShelterHub!";
  private static final String SUBTITLE_MESSAGE =
      "System zarządzania Schroniskiem Górskim Wilcza Turnia";
  private static final String LOGO_IMAGE_URL = "/images/SHELTERHUB.Logo.png";

  static WelcomeScreenViewModel viewModel = new WelcomeScreenViewModel();
  State state = new State();

  public static void show(StackPane root) {
    viewModel.setRoot(root);

    UIComponents ui = new UIComponents();

    VBox loginContainer =
        ui.createBox(
            Dimensions.SPACING_XLARGE,
            AppTheme.BACKGROUND_COLOR,
            Dimensions.BORDER_RADIUS_LARGE,
            new Insets(40),
            600,
            500);
    ui.addShadow(loginContainer, Dimensions.SHADOW_RADIUS, Dimensions.SHADOW_OPACITY);

    ImageView logoImageView = ui.createLogo(LOGO_IMAGE_URL, 400, 80);

    Label welcomeLabel =
        ui.createLabel(WELCOME_MESSAGE, Dimensions.FONT_SIZE_TITLE, true, AppTheme.PRIMARY_COLOR);
    Label subtitleLabel =
        ui.createLabel(
            SUBTITLE_MESSAGE, Dimensions.FONT_SIZE_SUBTITLE, false, AppTheme.PRIMARY_COLOR);
    VBox header = ui.createBox(Dimensions.SPACING_MEDIUM, null, 0, null, 0, 0);

    // Add logo to header before the text
    header.getChildren().addAll(logoImageView, welcomeLabel, subtitleLabel);

    TextField usernameField = ui.createTextField("Wprowadź nazwę użytkownika");
    PasswordField passwordField = ui.createPasswordField("Wprowadź hasło");

    VBox usernameContainer = ui.createFieldContainer("Nazwa użytkownika", usernameField);
    VBox passwordContainer = ui.createFieldContainer("Hasło", passwordField);

    VBox userInput =
        ui.createBox(Dimensions.SPACING_LARGE, null, 0, new Insets(25, 0, 25, 0), 450, 0);
    userInput.getChildren().addAll(usernameContainer, passwordContainer);

    Button loginButton =
        ui.createButton("Zaloguj się", AppTheme.PRIMARY_COLOR, AppTheme.PRIMARY_COLOR_HOVER);
    loginButton.setOnAction(e -> viewModel.login(usernameField.getText(), passwordField.getText()));

    loginContainer.getChildren().addAll(header, userInput, loginButton);

    root.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR_LIGHT + ";");
    root.getChildren().add(loginContainer);
    StackPane.setAlignment(loginContainer, Pos.CENTER);
  }

  private static class State {
    // State information as needed
  }

  void test(String title, Consumer<Boolean> textConsumer) {
    textConsumer.accept(true);
  }
}
