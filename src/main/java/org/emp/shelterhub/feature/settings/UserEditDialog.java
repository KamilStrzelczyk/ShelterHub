package org.emp.shelterhub.feature.settings;

import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.emp.shelterhub.feature.settings.data.User;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.infrastructure.utils.Dimensions;

public class UserEditDialog extends Stage {

  private User user;
  private TextField emailField;
  private TextField phoneNumberField;

  private Consumer<User> onSaveConsumer;

  public UserEditDialog(User user) {
    this.user = user;
    initModality(Modality.APPLICATION_MODAL);
    setTitle("Edytuj Dane Użytkownika");

    VBox root = new VBox(15);
    root.setPadding(new Insets(Dimensions.SPACING_LARGE));
    root.setAlignment(Pos.TOP_CENTER);
    root.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    Label titleLabel = new Label("Edytuj Dane Użytkownika");
    titleLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_TITLE, true, AppTheme.TEXT_COLOR_PRIMARY));

    emailField = createTextField("Email:", user.getEmail());
    phoneNumberField = createTextField("Numer Telefonu:", user.getPhoneNumber());

    VBox fieldsContainer = new VBox(10);
    fieldsContainer.setAlignment(Pos.CENTER_LEFT);
    fieldsContainer.getChildren().addAll(emailField, phoneNumberField);

    HBox fieldsWrapper = new HBox(fieldsContainer);
    fieldsWrapper.setAlignment(Pos.CENTER);

    Button saveButton = new Button("Zapisz");
    saveButton.setStyle(AppTheme.getButtonStyle(AppTheme.PRIMARY_COLOR));
    saveButton.setOnAction(e -> handleSave());

    Button cancelButton = new Button("Anuluj");
    cancelButton.setStyle(AppTheme.getButtonStyle(AppTheme.SECONDARY_COLOR));
    cancelButton.setOnAction(e -> close());

    HBox buttonBox = new HBox(10, saveButton, cancelButton);
    buttonBox.setAlignment(Pos.CENTER);

    Region spacer = new Region();
    VBox.setVgrow(spacer, Priority.ALWAYS);

    root.getChildren().addAll(titleLabel, fieldsWrapper, spacer, buttonBox);

    Scene scene = new Scene(root, 350, 350);
    setScene(scene);
  }

  private TextField createTextField(String promptText, String initialValue) {
    TextField textField = new TextField(initialValue);
    textField.setPromptText(promptText);
    textField.setStyle(AppTheme.getTextFieldStyle());
    return textField;
  }

  private void handleSave() {
    user.setEmail(emailField.getText());
    user.setPhoneNumber(phoneNumberField.getText());

    if (onSaveConsumer != null) {
      onSaveConsumer.accept(user);
    }
    close();
  }

  public void setOnSave(Consumer<User> onSaveConsumer) {
    this.onSaveConsumer = onSaveConsumer;
  }
}
