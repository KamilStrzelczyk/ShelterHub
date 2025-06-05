package org.emp.shelterhub.feature.room;

import io.reactivex.rxjava3.disposables.Disposable;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import org.emp.shelterhub.feature.room.data.Room;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;

public class RoomScreen extends VBox {

  private static final int COLUMNS = 3;

  private final RoomScreenViewModel viewModel;
  private final GridPane grid;
  private Disposable stateDisposable;

  public RoomScreen() {
    this.viewModel = new RoomScreenViewModel();

    this.setSpacing(10);
    this.setPadding(new Insets(20));
    this.setAlignment(Pos.TOP_CENTER);
    this.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    Label title = new Label("Lista Pokoi");
    title.setFont(new Font(24));
    title.setStyle("-fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    Button addRoomButton = new Button("Dodaj Pokój");
    addRoomButton.setStyle(
        "-fx-background-color: "
            + AppTheme.PRIMARY_COLOR
            + "; -fx-text-fill: "
            + AppTheme.TEXT_COLOR_LIGHT
            + "; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;");
    addRoomButton.setOnAction(
        e -> {
          RoomEditDialog dialog = new RoomEditDialog(null);
          dialog.setOnSave(viewModel::addNewRoom);
          dialog.showAndWait();
        });

    HBox topBar = new HBox(10);
    topBar.setAlignment(Pos.CENTER_LEFT);
    topBar.getChildren().add(title);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    topBar.getChildren().add(spacer);
    topBar.getChildren().add(addRoomButton);

    grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);

    for (int i = 0; i < COLUMNS; i++) {
      ColumnConstraints column = new ColumnConstraints();
      column.setPercentWidth(100.0 / COLUMNS);
      column.setHgrow(Priority.ALWAYS);
      grid.getColumnConstraints().add(column);
    }

    VBox contentContainer = new VBox(10);
    contentContainer.getChildren().addAll(topBar, grid);
    contentContainer.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    ScrollPane scrollPane = new ScrollPane(contentContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

    this.getChildren().add(scrollPane);
    VBox.setVgrow(scrollPane, Priority.ALWAYS);

    stateDisposable =
        viewModel
            .getState()
            .observeOn(io.reactivex.rxjava3.schedulers.Schedulers.trampoline())
            .subscribe(state -> Platform.runLater(() -> updateUI(state)));
  }

  private void updateUI(RoomScreenState state) {
    grid.getChildren().clear();

    List<Room> rooms = state.getRooms();

    int columnIndex = 0;
    int rowIndex = 0;
    for (Room room : rooms) {
      GridPane roomItem = createRoomItem(room);
      grid.add(roomItem, columnIndex, rowIndex);
      GridPane.setHgrow(roomItem, Priority.ALWAYS);

      columnIndex++;
      if (columnIndex >= COLUMNS) {
        columnIndex = 0;
        rowIndex++;
      }
    }

    if (state.isLoading()) {
      System.out.println("Ładowanie danych...");
    }

    if (state.getErrorMessage() != null) {
      System.err.println("Błąd: " + state.getErrorMessage());
    }
  }

  private GridPane createRoomItem(Room room) {
    GridPane container = getGridPane(room);

    Label roomNumberLabel = new Label("Room: " + room.getRoomNumber());
    roomNumberLabel.setStyle(
        "-fx-font-size: 16px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    Label roomTypeLabel = new Label("Type: " + room.getRoomType());
    roomTypeLabel.setStyle(
        "-fx-font-size: 14px; -fx-text-fill: " + AppTheme.TEXT_COLOR_SECONDARY + ";");

    Label roomStateLabel = new Label("Status: " + room.getRoomState());
    String color;
    switch (room.getRoomState()) {
      case OCCUPIED, OUT_OF_ORDER -> color = AppTheme.ERROR_COLOR;
      case DIRTY -> color = AppTheme.WARNING_COLOR;
      case FREE -> color = AppTheme.SUCCESS_COLOR;
      default -> color = AppTheme.TEXT_COLOR_PRIMARY;
    }
    roomStateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: " + color + ";");

    Label isAvailableTextLabel = new Label("Available:");
    isAvailableTextLabel.setStyle(
        "-fx-font-size: 14px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");
    Label isAvailableStatusLabel = createBooleanStatusLabel(room.isAvailable());

    container.add(roomNumberLabel, 0, 0, 2, 1);
    container.add(roomTypeLabel, 0, 1, 2, 1);
    container.add(roomStateLabel, 0, 2, 2, 1);

    container.add(isAvailableTextLabel, 0, 3);
    container.add(isAvailableStatusLabel, 1, 3);

    return container;
  }

  private GridPane getGridPane(Room room) {
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
          RoomEditDialog dialog = new RoomEditDialog(room);
          dialog.setOnSave(updatedRoom -> viewModel.updateRoom(updatedRoom));
          dialog.showAndWait();
        });
    return container;
  }

  private Label createBooleanStatusLabel(boolean status) {
    Label statusLabel = new Label(status ? "Tak" : "Nie");
    statusLabel.setStyle(
        "-fx-font-size: 14px; -fx-text-fill: "
            + (status ? AppTheme.SUCCESS_COLOR : AppTheme.ERROR_COLOR)
            + ";");
    return statusLabel;
  }
}
