package org.emp.shelterhub.feature.employee;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.emp.shelterhub.feature.employee.data.Employee;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;

public class EmployeeScreen extends VBox {
  private static final int COLUMNS = 2;

  EmployeeScreenViewModel viewModel = new EmployeeScreenViewModel();
  private GridPane employeeGrid;
  private ScrollPane scrollPane;

  public EmployeeScreen() {
    this.setSpacing(10);
    this.setPadding(new Insets(20));
    this.setAlignment(Pos.TOP_CENTER);
    this.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    Label title = new Label("Lista Pracowników");
    title.setFont(new Font(24));
    title.setStyle("-fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    Button addEmployeeButton = new Button("Dodaj Pracownika");
    addEmployeeButton.setStyle(
        "-fx-background-color: "
            + AppTheme.PRIMARY_COLOR
            + "; -fx-text-fill: "
            + AppTheme.TEXT_COLOR_LIGHT
            + "; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;");
    addEmployeeButton.setOnAction(
        e -> {
          EmployeeEditDialog dialog = new EmployeeEditDialog(null);
          dialog.setOnSave(
              newEmployee -> {
                viewModel.addNewEmployee(newEmployee);
                refreshEmployeeList();
              });
          dialog.showAndWait();
        });

    HBox topBar = new HBox(10);
    topBar.setAlignment(Pos.CENTER_LEFT);
    topBar.getChildren().addAll(title);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    topBar.getChildren().add(spacer);

    topBar.getChildren().add(addEmployeeButton);

    employeeGrid = new GridPane();
    employeeGrid.setHgap(10);
    employeeGrid.setVgap(10);

    for (int i = 0; i < COLUMNS; i++) {
      ColumnConstraints column = new ColumnConstraints();
      column.setPercentWidth(100.0 / COLUMNS);
      column.setHgrow(Priority.ALWAYS);
      employeeGrid.getColumnConstraints().add(column);
    }

    VBox contentContainer = new VBox(10);
    contentContainer.getChildren().addAll(topBar, employeeGrid);
    contentContainer.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    scrollPane = new ScrollPane();
    scrollPane.setContent(contentContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

    this.getChildren().add(scrollPane);
    VBox.setVgrow(scrollPane, Priority.ALWAYS);
    populateEmployeeGrid(employeeGrid);
  }

  private void populateEmployeeGrid(GridPane grid) {
    grid.getChildren().clear();
    int columnIndex = 0;
    int rowIndex = 0;
    for (Employee employee : viewModel.getEmployees()) {
      GridPane employeeItem = createEmployeeItem(employee);
      grid.add(employeeItem, columnIndex, rowIndex);
      GridPane.setHgrow(employeeItem, Priority.ALWAYS);

      columnIndex++;
      if (columnIndex >= COLUMNS) {
        columnIndex = 0;
        rowIndex++;
      }
    }
  }

  private void refreshEmployeeList() {
    populateEmployeeGrid(employeeGrid);
  }

  private GridPane createEmployeeItem(Employee employee) {
    GridPane container = new GridPane();
    container.setPadding(new Insets(10));
    container.setHgap(10);
    container.setVgap(5);
    container.setStyle(
        "-fx-background-color: "
            + AppTheme.CARD_BACKGROUND_COLOR
            + ";"
            + "-fx-border-color: "
            + AppTheme.BORDER_COLOR
            + ";"
            + "-fx-border-radius: 5; -fx-background-radius: 5;");
    container.setOnMouseClicked(
        e -> {
          EmployeeEditDialog dialog = new EmployeeEditDialog(employee);
          dialog.setOnSave(
              updatedEmployee -> {
                viewModel.updateEmployee(updatedEmployee);
                refreshEmployeeList();
              });
          dialog.showAndWait();
        });

    Label nameLabel =
        new Label(
            "Imię: "
                + employee.getFirstName()
                + " "
                + (employee.getMiddleName() != null && !employee.getMiddleName().isEmpty()
                    ? employee.getMiddleName() + " "
                    : "")
                + employee.getLastName());
    nameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    Label dobLabel = new Label("Data ur.: " + employee.getDateOfBirth());
    dobLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: " + AppTheme.TEXT_COLOR_SECONDARY + ";");

    Label addressLabel = new Label("Adres: " + employee.getAddress());
    addressLabel.setStyle(
        "-fx-font-size: 14px; -fx-text-fill: " + AppTheme.TEXT_COLOR_SECONDARY + ";");

    Label phoneLabel = new Label("Telefon: " + employee.getPhoneNumber());
    phoneLabel.setStyle(
        "-fx-font-size: 14px; -fx-text-fill: " + AppTheme.TEXT_COLOR_SECONDARY + ";");

    container.add(nameLabel, 0, 0);
    container.add(dobLabel, 0, 1);
    container.add(addressLabel, 0, 2);
    container.add(phoneLabel, 0, 3);

    return container;
  }
}
