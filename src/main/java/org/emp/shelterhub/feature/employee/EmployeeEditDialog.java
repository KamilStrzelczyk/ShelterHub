package org.emp.shelterhub.feature.employee;

import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;
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
import org.emp.shelterhub.feature.employee.service.EmployeeService;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;

public class EmployeeEditDialog extends Stage {

  private Employee employee;
  private TextField firstNameField;
  private TextField middleNameField;
  private TextField lastNameField;
  private TextField dateOfBirthField;
  private TextField addressField;
  private TextField phoneNumberField;

  private Label errorMessageLabel;

  private Consumer<Employee> onSaveConsumer;

  public EmployeeEditDialog(Employee employee) {
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
    if (firstNameField.getText().trim().isEmpty()) {
      showError("Imię jest wymagane", firstNameField);
      return false;
    }

    if (lastNameField.getText().trim().isEmpty()) {
      showError("Nazwisko jest wymagane", lastNameField);
      return false;
    }

    String dateOfBirth = dateOfBirthField.getText().trim();
    if (!dateOfBirth.isEmpty() && !isValidDateFormat(dateOfBirth)) {
      showError("Data urodzenia musi być w formacie RRRR-MM-DD", dateOfBirthField);
      return false;
    }

    String phoneNumber = phoneNumberField.getText().trim();
    if (!phoneNumber.isEmpty()) {
      if (!isPhoneNumberUnique(phoneNumber)) {
        showError("Ten numer telefonu jest już używany przez innego pracownika", phoneNumberField);
        return false;
      }
    }

    return true;
  }

  private boolean isValidDateFormat(String date) {
    String regex = "^\\d{4}-\\d{2}-\\d{2}$";
    if (!Pattern.matches(regex, date)) {
      return false;
    }


    return true;
  }

  private boolean isPhoneNumberUnique(String phoneNumber) {
    if (employee != null && phoneNumber.equals(employee.getPhoneNumber())) {
      return true;
    }

    List<Employee> allEmployees = EmployeeService.getAllEmployees();
    for (Employee emp : allEmployees) {
      if (phoneNumber.equals(emp.getPhoneNumber())) {
        return false;
      }
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
