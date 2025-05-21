package org.emp.shelterhub.feature.employee;

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
  private TextField firstNameField;
  private TextField middleNameField;
  private TextField lastNameField;
  private TextField dateOfBirthField;
  private TextField addressField;
  private TextField phoneNumberField;

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

    root.getChildren().addAll(titleLabel, fieldsWrapper, spacer, buttonBox);

    Scene scene = new Scene(root, 350, 500);
  }

  private TextField createTextField(String labelText, String initialValue) {
    TextField textField = new TextField(initialValue);
    textField.setPromptText(labelText);
    textField.setStyle(AppTheme.getTextFieldStyle());
    return textField;
  }

  private void handleSave() {
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

  public void setOnSave(Consumer<Employee> onSaveConsumer) {
    this.onSaveConsumer = onSaveConsumer;
  }
}
