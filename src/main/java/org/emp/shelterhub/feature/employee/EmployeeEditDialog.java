package org.emp.shelterhub.feature.employee;

import java.util.Map;
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
import org.emp.shelterhub.feature.employee.data.Employee;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;

public class EmployeeEditDialog extends Stage {

  private Employee employee;
  private final EmployeeScreenViewModel viewModel;

  private final TextField firstNameField;
  private final TextField middleNameField;
  private final TextField lastNameField;
  private final TextField dateOfBirthField;
  private final TextField addressField;
  private final TextField phoneNumberField;

  private final Label errorMessageLabel;

  private Consumer<Employee> onSaveConsumer;

  public EmployeeEditDialog(EmployeeScreenViewModel viewModel, Employee employee) {
    this.viewModel = viewModel;
    this.employee = employee;

    initModality(Modality.APPLICATION_MODAL);
    setTitle(
        employee == null
            ? "Dodaj Nowego Pracownika"
            : "Edytuj Pracownika " + employee.getFirstName() + " " + employee.getLastName());

    VBox root = new VBox(15);
    root.setPadding(new Insets(20));
    root.setAlignment(Pos.TOP_CENTER);
    root.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    Label titleLabel =
        new Label(employee == null ? "Dodaj Nowego Pracownika" : "Edytuj Pracownika");
    titleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    errorMessageLabel = new Label("");
    errorMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
    errorMessageLabel.setVisible(false);

    firstNameField = createTextField("Imię:", employee != null ? employee.getFirstName() : "");
    middleNameField =
        createTextField("Drugie Imię:", employee != null ? employee.getMiddleName() : "");
    lastNameField = createTextField("Nazwisko:", employee != null ? employee.getLastName() : "");
    dateOfBirthField =
        createTextField(
            "Data Urodzenia (RRRR-MM-DD):", employee != null ? employee.getDateOfBirth() : "");
    addressField = createTextField("Adres:", employee != null ? employee.getAddress() : "");
    phoneNumberField =
        createTextField("Numer Telefonu:", employee != null ? employee.getPhoneNumber() : "");

    VBox fieldsContainer = new VBox(10);
    fieldsContainer.setAlignment(Pos.CENTER_LEFT);
    fieldsContainer
        .getChildren()
        .addAll(
            firstNameField,
            middleNameField,
            lastNameField,
            dateOfBirthField,
            addressField,
            phoneNumberField);

    HBox fieldsWrapper = new HBox(fieldsContainer);
    fieldsWrapper.setAlignment(Pos.CENTER);

    Button saveButton = new Button("Zapisz");
    saveButton.setStyle(
        "-fx-background-color: "
            + AppTheme.PRIMARY_COLOR
            + "; -fx-text-fill: "
            + AppTheme.TEXT_COLOR_LIGHT
            + "; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;");
    saveButton.setOnAction(e -> handleSave());

    Button cancelButton = new Button("Anuluj");
    cancelButton.setStyle(
        "-fx-background-color: "
            + AppTheme.SECONDARY_COLOR
            + "; -fx-text-fill: "
            + AppTheme.TEXT_COLOR_LIGHT
            + "; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;");
    cancelButton.setOnAction(e -> close());

    HBox buttonBox = new HBox(10, saveButton, cancelButton);
    buttonBox.setAlignment(Pos.CENTER);

    Region spacer = new Region();
    VBox.setVgrow(spacer, Priority.ALWAYS);

    root.getChildren().addAll(titleLabel, errorMessageLabel, fieldsWrapper, spacer, buttonBox);

    Scene scene = new Scene(root, 350, 500);
    setScene(scene);
  }

  private TextField createTextField(String labelText, String initialValue) {
    TextField textField = new TextField(initialValue);
    textField.setPromptText(labelText);
    textField.setStyle(AppTheme.getTextFieldStyle());
    return textField;
  }

  private void handleSave() {
    resetFieldStyles();

    if (!validateInput()) {
      return;
    }

    if (employee != null) {
      employee.setFirstName(firstNameField.getText());
      employee.setMiddleName(middleNameField.getText());
      employee.setLastName(lastNameField.getText());
      employee.setDateOfBirth(dateOfBirthField.getText());
      employee.setAddress(addressField.getText());
      employee.setPhoneNumber(phoneNumberField.getText());
    } else {
      employee =
          new Employee(
              0,
              firstNameField.getText(),
              middleNameField.getText(),
              lastNameField.getText(),
              dateOfBirthField.getText(),
              addressField.getText(),
              phoneNumberField.getText());
    }

    if (onSaveConsumer != null) {
      onSaveConsumer.accept(employee);
    }
    close();
  }

  private boolean validateInput() {
    errorMessageLabel.setVisible(false);

    String firstName = firstNameField.getText();
    String lastName = lastNameField.getText();
    String phoneNumber = phoneNumberField.getText();
    String dateOfBirth = dateOfBirthField.getText();
    int currentEmployeeId = employee != null ? employee.getEmployeeId() : 0;

    Map<String, String> errors =
        viewModel.validate(firstName, lastName, phoneNumber, dateOfBirth, currentEmployeeId);

    if (!errors.isEmpty()) {
      for (Map.Entry<String, String> error : errors.entrySet()) {
        switch (error.getKey()) {
          case "firstName":
            showError(error.getValue(), firstNameField);
            break;
          case "lastName":
            showError(error.getValue(), lastNameField);
            break;
          case "phoneNumber":
            showError(error.getValue(), phoneNumberField);
            break;
          case "dateOfBirth":
            showError(error.getValue(), dateOfBirthField);
            break;
        }
      }
      return false;
    }

    return true;
  }

  private void showError(String message, TextField field) {
    errorMessageLabel.setText(message);
    errorMessageLabel.setVisible(true);
    field.setStyle(field.getStyle() + "; -fx-border-color: red;");
  }

  private void resetFieldStyles() {
    errorMessageLabel.setVisible(false);
    firstNameField.setStyle(AppTheme.getTextFieldStyle());
    middleNameField.setStyle(AppTheme.getTextFieldStyle());
    lastNameField.setStyle(AppTheme.getTextFieldStyle());
    dateOfBirthField.setStyle(AppTheme.getTextFieldStyle());
    addressField.setStyle(AppTheme.getTextFieldStyle());
    phoneNumberField.setStyle(AppTheme.getTextFieldStyle());
  }

  public void setOnSave(Consumer<Employee> onSaveConsumer) {
    this.onSaveConsumer = onSaveConsumer;
  }
}
