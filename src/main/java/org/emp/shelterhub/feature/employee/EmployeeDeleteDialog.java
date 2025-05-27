package org.emp.shelterhub.feature.employee;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.emp.shelterhub.feature.employee.data.Employee;

public class EmployeeDeleteDialog {

  public interface DeleteCallback {
    void onDeleteConfirmed(Employee employee);
  }

  private final Employee employee;
  private final DeleteCallback callback;

  public EmployeeDeleteDialog(Employee employee, DeleteCallback callback) {
    this.employee = employee;
    this.callback = callback;
  }

  public void showAndWait() {
    Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
    confirmDialog.setTitle("Potwierdź usunięcie");
    confirmDialog.setHeaderText("Czy na pewno chcesz usunąć pracownika?");
    confirmDialog.setContentText(
        "Pracownik: "
            + employee.getFirstName()
            + " "
            + employee.getLastName()
            + " zostanie trwale usunięty z bazy danych.");
    Optional<ButtonType> result = confirmDialog.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      callback.onDeleteConfirmed(employee);
    }
  }
}
