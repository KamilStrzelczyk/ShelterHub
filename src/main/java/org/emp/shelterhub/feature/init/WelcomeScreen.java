package org.emp.shelterhub.feature.init;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.emp.shelterhub.lib.infrastructure.utils.Res;

import java.util.Objects;
import java.util.function.Consumer;

public class WelcomeScreen {
  private static final String WELCOME_MESSAGE = "Witaj w systemie ShelterHub!";
  private static final String SUBTITLE_MESSAGE = "System zarządzania Schroniskiem Górskim Wilcza Turnia";
  private static final String PRIMARY_BUTTON_COLOR = "#6A3A56";
  private static final String HOVER_BUTTON_COLOR = "#5c324b";
  private static final String LOGO_IMAGE_URL = "/images/SHELTERHUB.Logo.png";

  static WelcomeScreenViewModel viewModel = new WelcomeScreenViewModel();
  State state = new State();

  public static void show(StackPane root) {
    viewModel.setRoot(root);

    UICreate ui = new UICreate();

    VBox loginContainer = ui.createBox(25, "#F7E0D6", 15, new Insets(40), 600, 500);
    ui.addShadow(loginContainer, 20, 0.2);

    ImageView logoImageView = ui.createLogo(LOGO_IMAGE_URL, 400, 80);

    Label welcomeLabel = ui.createLabel(WELCOME_MESSAGE, 28, true, PRIMARY_BUTTON_COLOR);
    Label subtitleLabel = ui.createLabel(SUBTITLE_MESSAGE, 16, false, PRIMARY_BUTTON_COLOR);
    VBox header = ui.createBox(10, null, 0, null, 0, 0);

    // Add logo to header before the text
    header.getChildren().addAll(logoImageView, welcomeLabel, subtitleLabel);

    TextField usernameField = ui.createTextField("Wprowadź nazwę użytkownika");
    PasswordField passwordField = ui.createPasswordField("Wprowadź hasło");

    VBox usernameContainer = ui.createFieldContainer("Nazwa użytkownika", usernameField);
    VBox passwordContainer = ui.createFieldContainer("Hasło", passwordField);

    VBox userInput = ui.createBox(20, null, 0, new Insets(25, 0, 25, 0), 450, 0);
    userInput.getChildren().addAll(usernameContainer, passwordContainer);

    Button loginButton = ui.createButton("Zaloguj się", PRIMARY_BUTTON_COLOR, HOVER_BUTTON_COLOR);
    loginButton.setOnAction(e ->
            viewModel.login(usernameField.getText(), passwordField.getText())

    );

    loginContainer.getChildren().addAll(header, userInput, loginButton);

    root.setStyle("-fx-background-color: #f5f5f5;");
    root.getChildren().add(loginContainer);
    StackPane.setAlignment(loginContainer, Pos.CENTER);
  }

  private static class UICreate {

    VBox createBox(int spacing, String bgColor, int radius, Insets padding,
                   double maxWidth, double maxHeight) {
      VBox box = new VBox(spacing);
      box.setAlignment(Pos.CENTER);

      if (bgColor != null) {
        box.setStyle("-fx-background-color: " + bgColor +
                (radius > 0 ? "; -fx-background-radius: " + radius : ""));
      }

      if (padding != null) box.setPadding(padding);
      if (maxWidth > 0) box.setMaxWidth(maxWidth);
      if (maxHeight > 0) box.setMaxHeight(maxHeight);

      return box;
    }

    void addShadow(VBox container, int radius, double opacity) {
      DropShadow shadow = new DropShadow();
      shadow.setColor(Color.rgb(0, 0, 0, opacity));
      shadow.setRadius(radius);
      container.setEffect(shadow);
    }

    // Method to create logo ImageView
    ImageView createLogo(String imageUrl, int width, int height) {
      final Image logoImage =
              new Image(Objects.requireNonNull(Res.getResourcePath(imageUrl)));
      final ImageView logoImageView = new ImageView(logoImage);
      logoImageView.setFitWidth(width);
      logoImageView.setFitHeight(height);
      return logoImageView;
    }

    Label createLabel(String text, int size, boolean isBold, String color) {
      Label label = new Label(text);
      label.setStyle("-fx-font-size: " + size + "px" +
              (isBold ? "; -fx-font-weight: bold" : "") +
              "; -fx-text-fill: " + color + ";");
      return label;
    }

    TextField createTextField(String promptText) {
      TextField field = new TextField();
      field.setPromptText(promptText);
      field.setMaxWidth(Double.MAX_VALUE);
      field.setStyle(getFieldStyle());
      return field;
    }

    PasswordField createPasswordField(String promptText) {
      PasswordField field = new PasswordField();
      field.setPromptText(promptText);
      field.setMaxWidth(Double.MAX_VALUE);
      field.setStyle(getFieldStyle());
      return field;
    }

    private String getFieldStyle() {
      return "-fx-background-color: #f5f5f5; -fx-background-radius: 5; " +
              "-fx-border-color: #e0e0e0; -fx-border-radius: 5; " +
              "-fx-padding: 10; -fx-font-size: 14px;";
    }

    VBox createFieldContainer(String labelText, Control field) {
      VBox container = new VBox(5);
      container.setMaxWidth(Double.MAX_VALUE);

      Label label = createLabel(labelText, 14, false, "#424242");
      container.getChildren().addAll(label, field);

      return container;
    }

    Button createButton(String text, String primaryColor, String hoverColor) {
      Button button = new Button(text);

      String buttonStyle = "-fx-background-color: " + primaryColor + "; " +
              "-fx-text-fill: white; " +
              "-fx-font-size: 14px; -fx-font-weight: bold; " +
              "-fx-padding: 12 30; -fx-background-radius: 5;";

      button.setStyle(buttonStyle);
      button.setPrefWidth(200);
      button.setMaxWidth(Region.USE_PREF_SIZE);

      button.setOnMouseEntered(e ->
              button.setStyle(buttonStyle.replace(primaryColor, hoverColor)));

      button.setOnMouseExited(e ->
              button.setStyle(buttonStyle));

      return button;
    }
  }

  void test(String title, Consumer<Boolean> textConsumer) {
       textConsumer.accept(true);
  }
}