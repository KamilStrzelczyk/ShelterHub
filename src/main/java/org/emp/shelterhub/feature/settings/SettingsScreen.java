package org.emp.shelterhub.feature.settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.emp.shelterhub.feature.settings.data.User;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.infrastructure.utils.Dimensions;

public class SettingsScreen extends VBox {

  private SettingsScreenViewModel viewModel = new SettingsScreenViewModel();

  private final Label usernameLabel;
  private final Label emailDisplayLabel;
  private final Label phoneNumberDisplayLabel;
  private final Label roleLabel;

  private final PasswordField currentPasswordField;
  private final PasswordField newPasswordField;
  private final Label passwordFeedbackLabel;

  public SettingsScreen() {
    this.setSpacing(Dimensions.SPACING_LARGE);
    this.setPadding(new Insets(Dimensions.SPACING_LARGE));
    this.setAlignment(Pos.TOP_CENTER);
    this.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    Label titleLabel = new Label("Ustawienia Konta");
    titleLabel.setFont(new Font(24));
    titleLabel.setStyle("-fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    VBox userInfoAndEditBox = new VBox(5);
    userInfoAndEditBox.setAlignment(Pos.CENTER_LEFT);
    userInfoAndEditBox.setPadding(new Insets(10, 0, 10, 0));

    User currentUser = viewModel.getCurrentUser();

    usernameLabel = new Label("Nazwa użytkownika: " + currentUser.getUsername());
    usernameLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_BODY, true, AppTheme.TEXT_COLOR_PRIMARY));

    emailDisplayLabel = new Label("Email: " + currentUser.getEmail());
    emailDisplayLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_BODY, false, AppTheme.TEXT_COLOR_SECONDARY));

    phoneNumberDisplayLabel = new Label("Telefon: " + currentUser.getPhoneNumber());
    phoneNumberDisplayLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_BODY, false, AppTheme.TEXT_COLOR_SECONDARY));

    roleLabel = new Label("Rola: " + currentUser.getUserRole().toString());
    roleLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_BODY, false, AppTheme.TEXT_COLOR_SECONDARY));

    Button editDetailsButton = new Button("Edytuj Dane");
    editDetailsButton.setStyle(AppTheme.getButtonStyle(AppTheme.PRIMARY_COLOR));
    editDetailsButton.setOnAction(
        e -> {
          UserEditDialog dialog = new UserEditDialog(currentUser);
          dialog.setOnSave(
              updatedUser -> {
                viewModel.updateUserDetails(updatedUser.getEmail(), updatedUser.getPhoneNumber());
                refreshUserDetails();
              });
          dialog.showAndWait();
        });

    userInfoAndEditBox
        .getChildren()
        .addAll(
            usernameLabel,
            emailDisplayLabel,
            phoneNumberDisplayLabel,
            roleLabel,
            editDetailsButton);

    VBox passwordChangeBox = new VBox(5);
    passwordChangeBox.setAlignment(Pos.CENTER_LEFT);
    passwordChangeBox.setPadding(new Insets(10, 0, 10, 0));

    Label passwordTitle = new Label("Zmień Hasło");
    passwordTitle.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_SUBTITLE, true, AppTheme.TEXT_COLOR_PRIMARY));

    currentPasswordField = new PasswordField();
    currentPasswordField.setPromptText("Obecne hasło");
    currentPasswordField.setStyle(AppTheme.getTextFieldStyle());

    newPasswordField = new PasswordField();
    newPasswordField.setPromptText("Nowe hasło");
    newPasswordField.setStyle(AppTheme.getTextFieldStyle());

    passwordFeedbackLabel = new Label("");
    passwordFeedbackLabel.setStyle("-fx-font-size: 12px;");

    Button changePasswordButton = new Button("Zmień Hasło");
    changePasswordButton.setStyle(AppTheme.getButtonStyle(AppTheme.PRIMARY_COLOR));
    changePasswordButton.setOnAction(
        e -> {
          String currentPass = currentPasswordField.getText();
          String newPass = newPasswordField.getText();

          if (currentPass.isEmpty() || newPass.isEmpty()) {
            passwordFeedbackLabel.setText("Wszystkie pola hasła muszą być wypełnione.");
            passwordFeedbackLabel.setStyle("-fx-text-fill: " + AppTheme.ERROR_COLOR + ";");
          } else {
            boolean success = viewModel.updatePassword(currentPass, newPass);
            if (success) {
              passwordFeedbackLabel.setText("Hasło zmienione pomyślnie.");
              passwordFeedbackLabel.setStyle("-fx-text-fill: " + AppTheme.SUCCESS_COLOR + ";");
              currentPasswordField.clear();
              newPasswordField.clear();
            } else {
              passwordFeedbackLabel.setText(
                  "Obecne hasło jest niepoprawne lub nowe hasło nie spełnia wymagań.");
              passwordFeedbackLabel.setStyle("-fx-text-fill: " + AppTheme.ERROR_COLOR + ";");
            }
          }
        });

    passwordChangeBox
        .getChildren()
        .addAll(
            passwordTitle,
            currentPasswordField,
            newPasswordField,
            passwordFeedbackLabel,
            changePasswordButton);

    VBox deleteAccountBox = new VBox(10);
    deleteAccountBox.setAlignment(Pos.CENTER_LEFT);
    deleteAccountBox.setPadding(new Insets(10, 0, 10, 0));

    Button deleteAccountButton = new Button("Usuń Konto");
    deleteAccountButton.setStyle(AppTheme.getButtonStyle(AppTheme.ERROR_COLOR));
    deleteAccountButton.setOnAction(
        e -> {
          viewModel.deleteAccount();
        });

    deleteAccountBox.getChildren().addAll(deleteAccountButton);

    Region spacer = new Region();
    VBox.setVgrow(spacer, Priority.ALWAYS);

    this.getChildren()
        .addAll(titleLabel, userInfoAndEditBox, passwordChangeBox, deleteAccountBox, spacer);
  }

  private void refreshUserDetails() {
    User currentUser = viewModel.getCurrentUser();
    emailDisplayLabel.setText("Email: " + currentUser.getEmail());
    phoneNumberDisplayLabel.setText("Telefon: " + currentUser.getPhoneNumber());
  }
}
