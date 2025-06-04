package org.emp.shelterhub.feature.scheduler;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import javafx.application.Platform;
import org.emp.shelterhub.feature.scheduler.data.ScheduleEntry;
import org.emp.shelterhub.lib.infrastructure.repository.EmployeeRepository;
import org.emp.shelterhub.lib.infrastructure.repository.SchedulerRepository;

public class SchedulerScreenViewModel {

  private final BehaviorSubject<SchedulerScreenState> stateSubject =
      BehaviorSubject.createDefault(SchedulerScreenState.initialState());

  private final CompositeDisposable disposables = new CompositeDisposable();

  private final SchedulerRepository schedulerRepository = new SchedulerRepository();

  public SchedulerScreenViewModel() {
    loadEmployeesFromRepository();
    loadScheduleEntriesFromRepository();
  }

  public BehaviorSubject<SchedulerScreenState> getState() {
    return stateSubject;
  }

  private void loadEmployeesFromRepository() {
    stateSubject.onNext(stateSubject.getValue().withLoading(true).withErrorMessage(null));

    disposables.add(
        Single.fromCallable(EmployeeRepository::getAllEmployees)
            .subscribeOn(Schedulers.io())
            .subscribe(
                employees ->
                    Platform.runLater(
                        () ->
                            stateSubject.onNext(
                                stateSubject
                                    .getValue()
                                    .withEmployees(employees)
                                    .withLoading(false)
                                    .withErrorMessage(null))),
                error ->
                    Platform.runLater(
                        () -> {
                          error.printStackTrace();
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withLoading(false)
                                  .withErrorMessage(
                                      "Błąd ładowania pracowników: " + error.getMessage()));
                        })));
  }

  private void loadScheduleEntriesFromRepository() {
    stateSubject.onNext(stateSubject.getValue().withLoading(true).withErrorMessage(null));

    disposables.add(
        Single.fromCallable(schedulerRepository::getAllEntries)
            .subscribeOn(Schedulers.io())
            .subscribe(
                entries ->
                    Platform.runLater(
                        () ->
                            stateSubject.onNext(
                                stateSubject
                                    .getValue()
                                    .withScheduleEntries(entries)
                                    .withLoading(false)
                                    .withErrorMessage(null))),
                error ->
                    Platform.runLater(
                        () -> {
                          error.printStackTrace();
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withLoading(false)
                                  .withErrorMessage(
                                      "Błąd ładowania wpisów harmonogramu: " + error.getMessage()));
                        })));
  }

  public void addScheduleEntry(ScheduleEntry entry) {
    stateSubject.onNext(stateSubject.getValue().withLoading(true).withErrorMessage(null));

    disposables.add(
        Single.fromCallable(() -> schedulerRepository.addEntry(entry))
            .subscribeOn(Schedulers.io())
            .subscribe(
                success ->
                    Platform.runLater(
                        () -> {
                          if (success) {
                            loadScheduleEntriesFromRepository();
                          } else {
                            stateSubject.onNext(
                                stateSubject
                                    .getValue()
                                    .withLoading(false)
                                    .withErrorMessage("Nie udało się dodać wpisu."));
                          }
                        }),
                error ->
                    Platform.runLater(
                        () -> {
                          error.printStackTrace();
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withLoading(false)
                                  .withErrorMessage("Błąd dodawania wpisu: " + error.getMessage()));
                        })));
  }

  public void updateScheduleEntry(ScheduleEntry updated) {
    stateSubject.onNext(stateSubject.getValue().withLoading(true).withErrorMessage(null));

    disposables.add(
        Single.fromCallable(() -> schedulerRepository.updateEntry(updated))
            .subscribeOn(Schedulers.io())
            .subscribe(
                success ->
                    Platform.runLater(
                        () -> {
                          if (success) {
                            loadScheduleEntriesFromRepository();
                          } else {
                            stateSubject.onNext(
                                stateSubject
                                    .getValue()
                                    .withLoading(false)
                                    .withErrorMessage("Nie udało się zaktualizować wpisu."));
                          }
                        }),
                error ->
                    Platform.runLater(
                        () -> {
                          error.printStackTrace();
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withLoading(false)
                                  .withErrorMessage(
                                      "Błąd aktualizacji wpisu: " + error.getMessage()));
                        })));
  }

  public void deleteScheduleEntry(int entryId) {
    stateSubject.onNext(stateSubject.getValue().withLoading(true).withErrorMessage(null));

    disposables.add(
        Single.fromCallable(() -> schedulerRepository.deleteEntry(String.valueOf(entryId)))
            .subscribeOn(Schedulers.io())
            .subscribe(
                success ->
                    Platform.runLater(
                        () -> {
                          if (success) {
                            loadScheduleEntriesFromRepository();
                          } else {
                            stateSubject.onNext(
                                stateSubject
                                    .getValue()
                                    .withLoading(false)
                                    .withErrorMessage("Nie udało się usunąć wpisu."));
                          }
                        }),
                error ->
                    Platform.runLater(
                        () -> {
                          error.printStackTrace();
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withLoading(false)
                                  .withErrorMessage("Błąd usuwania wpisu: " + error.getMessage()));
                        })));
  }
}
