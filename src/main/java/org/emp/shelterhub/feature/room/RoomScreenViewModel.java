package org.emp.shelterhub.feature.room;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Platform;
import org.emp.shelterhub.feature.room.data.Room;
import org.emp.shelterhub.lib.infrastructure.repository.RoomRepository;

public class RoomScreenViewModel {

  private final BehaviorSubject<RoomScreenState> stateSubject =
      BehaviorSubject.createDefault(RoomScreenState.initialState());

  private final CompositeDisposable disposables = new CompositeDisposable();
  private final RoomRepository roomRepository;

  public RoomScreenViewModel() {
    this.roomRepository = new RoomRepository();
    loadRoomsFromDatabase();
  }

  public Observable<RoomScreenState> getState() {
    return stateSubject.hide();
  }

  private void loadRoomsFromDatabase() {
    stateSubject.onNext(stateSubject.getValue().withLoading(true).withErrorMessage(null));

    disposables.add(
        Single.fromCallable(roomRepository::getAllRooms)
            .subscribeOn(Schedulers.io())
            .subscribe(
                rooms -> {
                  Platform.runLater(
                      () ->
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withRooms(rooms)
                                  .withLoading(false)
                                  .withErrorMessage(null)));
                },
                throwable ->
                    Platform.runLater(
                        () -> {
                          stateSubject.onNext(stateSubject.getValue().withLoading(false));
                          throwable.printStackTrace();
                        })));
  }

  public void addNewRoom(Room newRoom) {
    stateSubject.onNext(stateSubject.getValue().withLoading(true).withErrorMessage(null));
    disposables.add(
        Single.fromCallable(
                () -> {
                  boolean success = roomRepository.addRoom(newRoom);
                  if (!success) {
                    throw new RuntimeException("Nie udało się dodać pokoju.");
                  }
                  return newRoom;
                })
            .subscribeOn(Schedulers.io())
            .subscribe(
                createdRoom ->
                    Platform.runLater(
                        () -> {
                          List<Room> currentRooms = stateSubject.getValue().getRooms();
                          currentRooms.add(createdRoom);
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withRooms(currentRooms)
                                  .withLoading(false)
                                  .withErrorMessage(null));
                        }),
                e ->
                    Platform.runLater(
                        () -> {
                          stateSubject.onNext(stateSubject.getValue().withLoading(false));
                          e.printStackTrace();
                        })));
  }

  public void updateRoom(Room updatedRoom) {
    stateSubject.onNext(stateSubject.getValue().withLoading(true).withErrorMessage(null));

    disposables.add(
        Completable.fromAction(
                () -> {
                  boolean success = roomRepository.updateRoom(updatedRoom);
                  if (!success) {
                    throw new RuntimeException("Nie udało się zaktualizować pokoju.");
                  }
                })
            .subscribeOn(Schedulers.io())
            .subscribe(
                () ->
                    Platform.runLater(
                        () -> {
                          List<Room> currentRooms =
                              stateSubject.getValue().getRooms().stream()
                                  .map(
                                      r ->
                                          r.getRoomNumber() == updatedRoom.getRoomNumber()
                                              ? updatedRoom
                                              : r)
                                  .collect(Collectors.toList());
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withRooms(currentRooms)
                                  .withLoading(false)
                                  .withErrorMessage(null));
                          System.out.println(
                              "Zaktualizowano pokój: " + updatedRoom.getRoomNumber());
                        }),
                e ->
                    Platform.runLater(
                        () -> {
                          stateSubject.onNext(stateSubject.getValue().withLoading(false));
                          e.printStackTrace();
                        })));
  }

  public void deleteRoom(Room room) {
    stateSubject.onNext(stateSubject.getValue().withLoading(true).withErrorMessage(null));

    disposables.add(
        Single.fromCallable(() -> roomRepository.deleteRoom(room.getRoomNumber()))
            .subscribeOn(Schedulers.io())
            .subscribe(
                success ->
                    Platform.runLater(
                        () -> {
                          if (success) {
                            List<Room> currentRooms =
                                stateSubject.getValue().getRooms().stream()
                                    .filter(r -> r.getRoomNumber() != room.getRoomNumber())
                                    .collect(Collectors.toList());
                            stateSubject.onNext(
                                stateSubject
                                    .getValue()
                                    .withRooms(currentRooms)
                                    .withLoading(false)
                                    .withErrorMessage(null));
                            System.out.println("Usunięto pokój: " + room.getRoomNumber());
                          } else {
                            stateSubject.onNext(
                                stateSubject
                                    .getValue()
                                    .withLoading(false)
                                    .withErrorMessage("Nie udało się usunąć pokoju."));
                            System.err.println("Repozytorium zgłosiło błąd przy usuwaniu pokoju.");
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
                                      "Błąd podczas usuwania pokoju: " + e.getMessage()));
                          System.err.println("Błąd podczas usuwania pokoju: " + e.getMessage());
                          e.printStackTrace();
                        })));
  }
}
