package org.emp.shelterhub.feature.init;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.emp.shelterhub.feature.about.AboutScreen;

public class WelcomeScreen {
  private static final String WELCOME_MESSAGE = "Witaj w systemie ShelterHub!";
  private static final String SUBTITLE_MESSAGE =
      "System zarządzania Schroniskiem Górskim Wilcza Turnia";
  private static final String PRIMARY_BUTTON_COLOR = "#2196F3";
  private static final String HOVER_BUTTON_COLOR = "#1976D2";

  public static void show(StackPane root) {
    VBox loginContainer = createContainer();

    VBox header = createHeader();
    VBox userInput = createUserInputSection();
    Button submitButton = createLoginButton();

    configureLoginAction(submitButton, root, userInput);

    loginContainer.getChildren().addAll(header, userInput, submitButton);

    root.setStyle("-fx-background-color: #f5f5f5;");
    root.getChildren().add(loginContainer);
    StackPane.setAlignment(loginContainer, Pos.CENTER);
  }

  private static VBox createContainer() {
    VBox container = new VBox(25);
    container.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
    container.setPadding(new Insets(40));
    container.setPrefWidth(Region.USE_COMPUTED_SIZE);
    container.setMaxWidth(600);
    container.setMaxHeight(500);
    container.setAlignment(Pos.CENTER);

    DropShadow shadow = new DropShadow();
    shadow.setColor(Color.rgb(0, 0, 0, 0.2));
    shadow.setRadius(20);
    container.setEffect(shadow);

    return container;
  }

  private static VBox createHeader() {
    VBox header = new VBox(10);
    header.setAlignment(Pos.CENTER);

    Label welcomeLabel = new Label(WELCOME_MESSAGE);
    welcomeLabel.setStyle(
        "-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_BUTTON_COLOR + ";");

    Label subtitleLabel = new Label(SUBTITLE_MESSAGE);
    subtitleLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #757575; -fx-font-weight: normal;");

    header.getChildren().addAll(welcomeLabel, subtitleLabel);
    return header;
  }

  private static VBox createUserInputSection() {
    VBox inputSection = new VBox(20);
    inputSection.setAlignment(Pos.CENTER);
    inputSection.setPadding(new Insets(25, 0, 25, 0));
    inputSection.setMaxWidth(450);

    VBox usernameField = createInputField("Nazwa użytkownika", "Wprowadź nazwę użytkownika", false);
    VBox passwordField = createInputField("Hasło", "Wprowadź hasło", true);

    inputSection.getChildren().addAll(usernameField, passwordField);
    return inputSection;
  }

  private static VBox createInputField(String labelText, String promptText, boolean isPassword) {
    VBox fieldContainer = new VBox(5);
    fieldContainer.setMaxWidth(Double.MAX_VALUE);

    Label label = new Label(labelText);
    label.setStyle("-fx-font-size: 14px; -fx-text-fill: #424242;");

    String fieldStyle =
        "-fx-background-color: #f5f5f5; -fx-background-radius: 5; "
            + "-fx-border-color: #e0e0e0; -fx-border-radius: 5; "
            + "-fx-padding: 10; -fx-font-size: 14px;";

    if (isPassword) {
      PasswordField passwordField = new PasswordField();
      passwordField.setStyle(fieldStyle);
      passwordField.setPromptText(promptText);
      passwordField.setMaxWidth(Double.MAX_VALUE);
      fieldContainer.getChildren().addAll(label, passwordField);
    } else {
      TextField textField = new TextField();
      textField.setStyle(fieldStyle);
      textField.setPromptText(promptText);
      textField.setMaxWidth(Double.MAX_VALUE);
      fieldContainer.getChildren().addAll(label, textField);
    }

    return fieldContainer;
  }

  private static Button createLoginButton() {
    Button loginButton = new Button("Zaloguj się");
    String buttonStyle =
        "-fx-background-color: "
            + PRIMARY_BUTTON_COLOR
            + "; -fx-text-fill: white; "
            + "-fx-font-size: 14px; -fx-font-weight: bold; "
            + "-fx-padding: 12 30; -fx-background-radius: 5;";

    loginButton.setStyle(buttonStyle);
    loginButton.setPrefWidth(200);
    loginButton.setMaxWidth(Region.USE_PREF_SIZE);

    loginButton.setOnMouseEntered(
        e -> loginButton.setStyle(buttonStyle.replace(PRIMARY_BUTTON_COLOR, HOVER_BUTTON_COLOR)));

    loginButton.setOnMouseExited(e -> loginButton.setStyle(buttonStyle));

    return loginButton;
  }

  private static void configureLoginAction(Button loginButton, StackPane root, VBox userInput) {
    loginButton.setOnAction(
        e -> {
          TextField usernameField =
              (TextField) ((VBox) userInput.getChildren().get(0)).getChildren().get(1);
          PasswordField passwordField =
              (PasswordField) ((VBox) userInput.getChildren().get(1)).getChildren().get(1);

          String username = usernameField.getText();
          String password = passwordField.getText();

          root.getChildren().clear();
          AboutScreen.show(root, username);
        });
  }
}
