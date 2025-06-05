package org.emp.shelterhub.feature.init.WelcomeScreen;

import io.reactivex.rxjava3.disposables.Disposable;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.infrastructure.utils.Dimensions;
import org.emp.shelterhub.lib.presentation.UIComponents;

public class WelcomeScreen {
  private static final String WELCOME_MESSAGE = "Witaj w systemie ShelterHub!";
  private static final String SUBTITLE_MESSAGE =
      "System zarządzania Schroniskiem Górskim Wilcza Turnia";
  private static final String LOGO_IMAGE_URL = "/images/SHELTERHUB.Logo.png";

  private WelcomeScreenViewModel viewModel;
  private Disposable stateSubscription;

  public void show(StackPane root) {
    viewModel = new WelcomeScreenViewModel();
    if (stateSubscription != null && !stateSubscription.isDisposed()) {
      stateSubscription.dispose();
    }

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
    ProgressIndicator loadingIndicator = new ProgressIndicator();
    loadingIndicator.setVisible(false);
    loadingIndicator.setPrefSize(30, 30);

    Label errorLabel = new Label();
    errorLabel.setStyle("-fx-text-fill: red;");
    errorLabel.setVisible(false);

    loginButton.setOnAction(
        e -> {
          errorLabel.setVisible(false);
          viewModel.login(usernameField.getText(), passwordField.getText());
        });

    VBox footer = new VBox(10, errorLabel, loadingIndicator, loginButton);
    footer.setAlignment(Pos.CENTER);

    loginContainer.getChildren().addAll(header, userInput, footer);
    root.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR_LIGHT + ";");
    root.getChildren().add(loginContainer);
    StackPane.setAlignment(loginContainer, Pos.CENTER);

    stateSubscription =
        viewModel
            .getState()
            .subscribe(
                state ->
                    Platform.runLater(
                        () -> {
                          loadingIndicator.setVisible(state.isLoading);
                          loginButton.setDisable(state.isLoading);

                          if (state.errorMessage != null) {
                            errorLabel.setText(state.errorMessage);
                            errorLabel.setVisible(true);
                          } else {
                            errorLabel.setVisible(false);
                          }
                        }));
  }
}
