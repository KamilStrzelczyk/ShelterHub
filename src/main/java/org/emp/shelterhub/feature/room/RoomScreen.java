package org.emp.shelterhub.feature.room;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.emp.shelterhub.feature.room.data.Room;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;

public class RoomScreen extends VBox {
  private static final int COLUMNS = 3;

  RoomScreenViewModel viewModel = new RoomScreenViewModel();

  public RoomScreen() {
    this.setSpacing(10);
    this.setPadding(new Insets(20));
    this.setAlignment(Pos.TOP_CENTER);
    this.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    Label title = new Label("Lista Pokoi");
    title.setFont(new Font(24));
    title.setStyle("-fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);

    for (int i = 0; i < COLUMNS; i++) {
      ColumnConstraints column = new ColumnConstraints();
      column.setPercentWidth(100.0 / COLUMNS);
      column.setHgrow(Priority.ALWAYS);
      grid.getColumnConstraints().add(column);
    }

    int columnIndex = 0;
    int rowIndex = 0;

    for (Room room : viewModel.getRooms()) {
      GridPane roomItem = createRoomItem(room);
      grid.add(roomItem, columnIndex, rowIndex);
      GridPane.setHgrow(roomItem, Priority.ALWAYS);

      columnIndex++;
      if (columnIndex >= COLUMNS) {
        columnIndex = 0;
        rowIndex++;
      }
    }

    VBox contentContainer = new VBox(10);
    contentContainer.getChildren().addAll(title, grid);
    contentContainer.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(contentContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

    this.getChildren().add(scrollPane);

    VBox.setVgrow(scrollPane, Priority.ALWAYS);
  }

  private GridPane createRoomItem(Room room) {
    GridPane container = getGridPane(room);

    Label roomNumberLabel = new Label("Pokój: " + room.getRoomNumber());
    roomNumberLabel.setStyle(
        "-fx-font-size: 16px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    Label roomTypeLabel = new Label("Typ: " + room.getRoomType());
    roomTypeLabel.setStyle(
        "-fx-font-size: 14px; -fx-text-fill: " + AppTheme.TEXT_COLOR_SECONDARY + ";");

    Label roomStatusLabel = new Label(room.isOccupied() ? "Zajęty" : "Wolny");
    roomStatusLabel.setStyle(
        "-fx-font-size: 14px; -fx-text-fill: "
            + (room.isOccupied() ? AppTheme.ERROR_COLOR : AppTheme.SUCCESS_COLOR)
            + ";");

    Label isCleanTextLabel = new Label("Czysty:");
    isCleanTextLabel.setStyle(
        "-fx-font-size: 14px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");
    Label isCleanStatusLabel = createBooleanStatusLabel(room.isClean());

    Label isAvailableTextLabel = new Label("Dostępny:");
    isAvailableTextLabel.setStyle(
        "-fx-font-size: 14px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");
    Label isAvailableStatusLabel = createBooleanStatusLabel(room.isAvailable());

    Label hasMalfunctionTextLabel = new Label("Awaria:");
    hasMalfunctionTextLabel.setStyle(
        "-fx-font-size: 14px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");
    Label hasMalfunctionStatusLabel = createBooleanStatusLabel(room.hasMalfunction());

    container.add(roomNumberLabel, 0, 0, 2, 1);
    container.add(roomTypeLabel, 0, 1, 2, 1);
    container.add(roomStatusLabel, 0, 2, 2, 1);

    container.add(isCleanTextLabel, 0, 3);
    container.add(isCleanStatusLabel, 1, 3);

    container.add(isAvailableTextLabel, 0, 4);
    container.add(isAvailableStatusLabel, 1, 4);

    container.add(hasMalfunctionTextLabel, 0, 5);
    container.add(hasMalfunctionStatusLabel, 1, 5);

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
          dialog.setOnSave(
              updatedRoom -> {
                viewModel.handleRoomUpdate(updatedRoom);
              });
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
