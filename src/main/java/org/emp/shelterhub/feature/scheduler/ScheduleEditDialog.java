package org.emp.shelterhub.feature.scheduler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.emp.shelterhub.feature.employee.data.Employee;
import org.emp.shelterhub.feature.scheduler.data.ScheduleEntry;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.presentation.AppIcon;

public class ScheduleEditDialog extends Stage {

  private ScheduleEntry scheduleEntry;

  private final DatePicker datePicker;
  private final ComboBox<Integer> startHourCombo;
  private final ComboBox<Integer> startMinuteCombo;
  private final ComboBox<Integer> endHourCombo;
  private final ComboBox<Integer> endMinuteCombo;
  private final TextField taskNameField;
  private final ComboBox<Employee> employeeComboBox;
  private final Label errorMessageLabel;

  private Consumer<ScheduleEntry> onSaveConsumer;

  public ScheduleEditDialog(ScheduleEntry entry, List<Employee> employees) {
    this.scheduleEntry = entry;
    this.getIcons().add(AppIcon.getAppIcon());

    initModality(Modality.APPLICATION_MODAL);
    setTitle(entry == null ? "Dodaj Zadanie" : "Edytuj Zadanie");

    VBox root = new VBox(15);
    root.setPadding(new Insets(20));
    root.setAlignment(Pos.TOP_CENTER);
    root.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    Label titleLabel = new Label(entry == null ? "Dodaj Nowe Zadanie" : "Edytuj Zadanie");
    titleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    errorMessageLabel = new Label();
    errorMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
    errorMessageLabel.setVisible(false);

    datePicker = new DatePicker();
    if (entry != null) datePicker.setValue(entry.getDate());
    datePicker.setStyle("-fx-font-size: 14px;");

    startHourCombo = createHourComboBox();
    startMinuteCombo = createMinuteComboBox();
    endHourCombo = createHourComboBox();
    endMinuteCombo = createMinuteComboBox();

    if (entry != null) {
      startHourCombo.setValue(entry.getStartTime().getHour());
      startMinuteCombo.setValue(entry.getStartTime().getMinute());
      endHourCombo.setValue(entry.getEndTime().getHour());
      endMinuteCombo.setValue(entry.getEndTime().getMinute());
    } else {
      startHourCombo.setValue(9);
      startMinuteCombo.setValue(0);
      endHourCombo.setValue(10);
      endMinuteCombo.setValue(0);
    }

    HBox startTimeBox =
        new HBox(5, new Label("Start:"), startHourCombo, new Label(":"), startMinuteCombo);
    startTimeBox.setAlignment(Pos.CENTER_LEFT);
    HBox endTimeBox =
        new HBox(5, new Label("Koniec:"), endHourCombo, new Label(":"), endMinuteCombo);
    endTimeBox.setAlignment(Pos.CENTER_LEFT);

    taskNameField = new TextField();
    taskNameField.setPromptText("Nazwa zadania");
    if (entry != null) {
      taskNameField.setText(entry.getTaskDescription());
    }
    taskNameField.setStyle("-fx-font-size: 14px;");

    employeeComboBox = new ComboBox<>(FXCollections.observableArrayList(employees));
    employeeComboBox.setPromptText("Przypisany pracownik");
    employeeComboBox.setStyle("-fx-font-size: 14px;");

    employeeComboBox.setCellFactory(
        lv ->
            new ListCell<>() {
              @Override
              protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getLastName());
              }
            });
    employeeComboBox.setButtonCell(
        new ListCell<>() {
          @Override
          protected void updateItem(Employee item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty || item == null ? null : item.getLastName());
          }
        });

    if (entry != null) {
      employeeComboBox.setValue(entry.getAssignedEmployee());
    }

    VBox fields =
        new VBox(
            10,
            new Label("Data:"),
            datePicker,
            startTimeBox,
            endTimeBox,
            new Label("Nazwa zadania:"),
            taskNameField,
            new Label("Pracownik:"),
            employeeComboBox);

    HBox buttonBox = createButtonBox();

    root.getChildren().addAll(titleLabel, errorMessageLabel, fields, buttonBox);

    Scene scene = new Scene(root);
    setScene(scene);
    sizeToScene();
  }

  private ComboBox<Integer> createHourComboBox() {
    ComboBox<Integer> combo = new ComboBox<>();
    for (int i = 0; i <= 23; i++) {
      combo.getItems().add(i);
    }
    return combo;
  }

  private ComboBox<Integer> createMinuteComboBox() {
    ComboBox<Integer> combo = new ComboBox<>();
    for (int i = 0; i < 60; i += 5) {
      combo.getItems().add(i);
    }
    return combo;
  }

  private HBox createButtonBox() {
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

    HBox box = new HBox(10, saveButton, cancelButton);
    box.setAlignment(Pos.CENTER);
    return box;
  }

  private void handleSave() {
    resetError();

    if (!validateInput()) {
      return;
    }

    LocalDate date = datePicker.getValue();
    LocalTime startTime = LocalTime.of(startHourCombo.getValue(), startMinuteCombo.getValue());
    LocalTime endTime = LocalTime.of(endHourCombo.getValue(), endMinuteCombo.getValue());
    String taskName = taskNameField.getText().trim();
    Employee employee = employeeComboBox.getValue();

    if (endTime.isBefore(startTime) || endTime.equals(startTime)) {
      showError("Czas zakończenia musi być późniejszy niż czas rozpoczęcia.");
      return;
    }

    if (scheduleEntry == null) {
      scheduleEntry = new ScheduleEntry(0, date, startTime, endTime, employee, taskName);
    } else {
      scheduleEntry.setDate(date);
      scheduleEntry.setStartTime(startTime);
      scheduleEntry.setEndTime(endTime);
      scheduleEntry.setTaskDescription(taskName);
      scheduleEntry.setAssignedEmployee(employee);
    }

    if (onSaveConsumer != null) {
      onSaveConsumer.accept(scheduleEntry);
    }
    close();
  }

  private boolean validateInput() {
    if (datePicker.getValue() == null) {
      showError("Proszę wybrać datę.");
      return false;
    }
    if (taskNameField.getText().trim().isEmpty()) {
      showError("Proszę podać nazwę zadania.");
      return false;
    }
    if (employeeComboBox.getValue() == null) {
      showError("Proszę wybrać pracownika.");
      return false;
    }
    if (startHourCombo.getValue() == null
        || startMinuteCombo.getValue() == null
        || endHourCombo.getValue() == null
        || endMinuteCombo.getValue() == null) {
      showError("Proszę ustawić czas rozpoczęcia i zakończenia.");
      return false;
    }
    return true;
  }

  private void showError(String message) {
    errorMessageLabel.setText(message);
    errorMessageLabel.setVisible(true);
  }

  private void resetError() {
    errorMessageLabel.setVisible(false);
  }

  public void setOnSave(Consumer<ScheduleEntry> onSaveConsumer) {
    this.onSaveConsumer = onSaveConsumer;
  }
}
