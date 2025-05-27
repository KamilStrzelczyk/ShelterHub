package org.emp.shelterhub.feature.employee;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.application.Platform;
import org.emp.shelterhub.feature.employee.data.Employee;
import org.emp.shelterhub.lib.infrastructure.repository.EmployeeRepository;

public class EmployeeScreenViewModel {

  private final BehaviorSubject<EmployeeScreenState> stateSubject =
      BehaviorSubject.createDefault(EmployeeScreenState.initialState());
  private final CompositeDisposable disposables = new CompositeDisposable();

  public EmployeeScreenViewModel() {
    loadEmployeesFromDatabase();
  }

  public Observable<EmployeeScreenState> getState() {
    return stateSubject.hide();
  }

  private void loadEmployeesFromDatabase() {
    stateSubject.onNext(stateSubject.getValue().withLoading(true).withErrorMessage(null));

    disposables.add(
        Single.fromCallable(EmployeeRepository::getAllEmployees)
            .subscribeOn(Schedulers.io())
            .subscribe(
                loadedEmployees ->
                    Platform.runLater(
                        () -> {
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withEmployees(loadedEmployees)
                                  .withLoading(false)
                                  .withErrorMessage(null));
                          System.out.println(
                              "Załadowano "
                                  + loadedEmployees.size()
                                  + " pracowników z bazy danych");
                        }),
                throwable ->
                    Platform.runLater(
                        () -> {
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withLoading(false)
                                  .withErrorMessage(
                                      "Błąd ładowania pracowników: " + throwable.getMessage()));
                          System.err.println(
                              "Błąd ładowania pracowników z bazy danych: "
                                  + throwable.getMessage());
                          throwable.printStackTrace();
                        })));
  }

  public void addNewEmployee(Employee newEmployee) {
    stateSubject.onNext(stateSubject.getValue().withLoading(true).withErrorMessage(null));

    disposables.add(
        Single.fromCallable(
                () -> {
                  boolean success = EmployeeRepository.createEmployee(newEmployee);
                  if (!success) {
                    throw new RuntimeException("Nie udało się dodać pracownika.");
                  }
                  return newEmployee;
                })
            .subscribeOn(Schedulers.io())
            .subscribe(
                createdEmployee ->
                    Platform.runLater(
                        () -> {
                          List<Employee> currentEmployees =
                              new ArrayList<>(stateSubject.getValue().getEmployees());
                          currentEmployees.add(createdEmployee);
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withEmployees(currentEmployees)
                                  .withLoading(false)
                                  .withErrorMessage(null));
                          System.out.println(
                              "Dodano nowego pracownika do bazy danych: "
                                  + createdEmployee.getFirstName()
                                  + " "
                                  + createdEmployee.getLastName()
                                  + " (ID: "
                                  + createdEmployee.getEmployeeId()
                                  + ")");
                        }),
                e ->
                    Platform.runLater(
                        () -> {
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withLoading(false)
                                  .withErrorMessage(
                                      "Nie udało się dodać pracownika: " + e.getMessage()));
                          System.err.println(
                              "Nie udało się dodać pracownika do bazy danych: "
                                  + newEmployee.getFirstName()
                                  + " "
                                  + newEmployee.getLastName()
                                  + ". Błąd: "
                                  + e.getMessage());
                          e.printStackTrace();
                        })));
  }

  public void updateEmployee(Employee updatedEmployee) {
    stateSubject.onNext(stateSubject.getValue().withLoading(true).withErrorMessage(null));

    disposables.add(
        Completable.fromAction(
                () -> {
                  boolean success = EmployeeRepository.updateEmployee(updatedEmployee);
                  if (!success) {
                    throw new RuntimeException(
                        "Nie udało się zaktualizować pracownika w repozytorium.");
                  }
                })
            .subscribeOn(Schedulers.io())
            .subscribe(
                () ->
                    Platform.runLater(
                        () -> {
                          List<Employee> currentEmployees =
                              stateSubject.getValue().getEmployees().stream()
                                  .map(
                                      e ->
                                          e.getEmployeeId() == updatedEmployee.getEmployeeId()
                                              ? updatedEmployee
                                              : e)
                                  .collect(Collectors.toList());
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withEmployees(currentEmployees)
                                  .withLoading(false)
                                  .withErrorMessage(null));
                          System.out.println(
                              "Zaktualizowano pracownika w bazie danych: "
                                  + updatedEmployee.getFirstName()
                                  + " "
                                  + updatedEmployee.getLastName());
                        }),
                e ->
                    Platform.runLater(
                        () -> {
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withLoading(false)
                                  .withErrorMessage(
                                      "Nie udało się zaktualizować pracownika: " + e.getMessage()));
                          System.err.println(
                              "Nie udało się zaktualizować pracownika w bazie danych: "
                                  + updatedEmployee.getFirstName()
                                  + " "
                                  + updatedEmployee.getLastName()
                                  + ". Błąd: "
                                  + e.getMessage());
                          e.printStackTrace();
                        })));
  }

  public void deleteEmployee(Employee employee) {
    stateSubject.onNext(stateSubject.getValue().withLoading(true).withErrorMessage(null));

    disposables.add(
        Single.fromCallable(() -> EmployeeRepository.deleteEmployee(employee.getEmployeeId()))
            .subscribeOn(Schedulers.io())
            .subscribe(
                success ->
                    Platform.runLater(
                        () -> {
                          if (success) {
                            List<Employee> currentEmployees =
                                stateSubject.getValue().getEmployees().stream()
                                    .filter(e -> e.getEmployeeId() != employee.getEmployeeId())
                                    .collect(Collectors.toList());
                            stateSubject.onNext(
                                stateSubject
                                    .getValue()
                                    .withEmployees(currentEmployees)
                                    .withLoading(false)
                                    .withErrorMessage(null));
                            System.out.println(
                                "Usunięto pracownika z bazy danych: "
                                    + employee.getFirstName()
                                    + " "
                                    + employee.getLastName());
                          } else {
                            stateSubject.onNext(
                                stateSubject
                                    .getValue()
                                    .withLoading(false)
                                    .withErrorMessage(
                                        "Nie udało się usunąć pracownika: Repozytorium zgłosiło błąd."));
                            System.err.println(
                                "Nie udało się usunąć pracownika z bazy danych: "
                                    + employee.getFirstName()
                                    + " "
                                    + employee.getLastName()
                                    + ". Repozytorium zgłosiło błąd.");
                          }
                        }),
                e ->
                    Platform.runLater(
                        () -> {
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withLoading(false)
                                  .withErrorMessage(
                                      "Błąd podczas usuwania pracownika: " + e.getMessage()));
                          System.err.println("Błąd podczas usuwania pracownika: " + e.getMessage());
                          e.printStackTrace();
                        })));
  }

  public Map<String, String> validate(
      String firstName,
      String lastName,
      String phoneNumber,
      String dateOfBirth,
      int currentEmployeeId) {
    Map<String, String> errors = new HashMap<>();

    if (firstName == null || firstName.trim().isEmpty()) {
      errors.put("firstName", "Imię jest wymagane");
    }

    if (lastName == null || lastName.trim().isEmpty()) {
      errors.put("lastName", "Nazwisko jest wymagane");
    }

    if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
      errors.put("phoneNumber", "Telefon jest wymagany");
    } else if (!isPhoneNumberUnique(phoneNumber, currentEmployeeId)) {
      errors.put("phoneNumber", "Ten numer telefonu jest już używany przez innego pracownika");
    }

    if (dateOfBirth != null && !dateOfBirth.trim().isEmpty() && !isValidDateFormat(dateOfBirth)) {
      errors.put("dateOfBirth", "Data urodzenia musi być w formacie RRRR-MM-DD");
    }

    return errors;
  }

  private boolean isValidDateFormat(String date) {
    String regex = "^\\d{4}-\\d{2}-\\d{2}$";
    return Pattern.matches(regex, date);
  }

  private boolean isPhoneNumberUnique(String phoneNumber, int excludeEmployeeId) {
    return stateSubject.getValue().getEmployees().stream()
        .filter(e -> e.getEmployeeId() != excludeEmployeeId)
        .noneMatch(e -> phoneNumber.equals(e.getPhoneNumber()));
  }
}
