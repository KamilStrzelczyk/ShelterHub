package org.emp.shelterhub.feature.room;

import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.emp.shelterhub.feature.room.data.Room;
import org.emp.shelterhub.feature.room.data.RoomState;
import org.emp.shelterhub.feature.room.data.RoomType;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.presentation.AppIcon;

public class RoomEditDialog extends Stage {

  private Room room;
  private final TextField roomNumberField;
  private final ComboBox<RoomType> roomTypeComboBox;
  private final ComboBox<RoomState> roomStateComboBox;
  private final Label errorMessageLabel;

  private Consumer<Room> onSaveConsumer;

  public RoomEditDialog(Room room) {
    this.room = room;
    this.getIcons().add(AppIcon.getAppIcon());

    initModality(Modality.APPLICATION_MODAL);
    setTitle(room == null ? "Dodaj Nowy Pokój" : "Edytuj Pokój " + room.getRoomNumber());

    VBox root = new VBox(15);
    root.setPadding(new Insets(20));
    root.setAlignment(Pos.TOP_CENTER);
    root.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    Label titleLabel =
        new Label(room == null ? "Dodaj Nowy Pokój" : "Edytuj Pokój " + room.getRoomNumber());
    titleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    errorMessageLabel = new Label("");
    errorMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
    errorMessageLabel.setVisible(false);

    roomNumberField = new TextField();
    roomNumberField.setPromptText("Numer pokoju");
    roomNumberField.setStyle("-fx-font-size: 14px;");
    if (room != null) {
      roomNumberField.setText(String.valueOf(room.getRoomNumber()));
      roomNumberField.setDisable(true); // nie można zmieniać numeru przy edycji
    }

    roomTypeComboBox = new ComboBox<>(FXCollections.observableArrayList(RoomType.values()));
    roomTypeComboBox.setStyle("-fx-font-size: 14px;");
    roomTypeComboBox.setPromptText("Typ pokoju");
    if (room != null) {
      roomTypeComboBox.setValue(room.getRoomType());
    }

    roomStateComboBox = new ComboBox<>(FXCollections.observableArrayList(RoomState.values()));
    roomStateComboBox.setStyle("-fx-font-size: 14px;");
    roomStateComboBox.setPromptText("Stan pokoju");
    if (room != null) {
      roomStateComboBox.setValue(room.getRoomState());
    }

    VBox fieldsContainer = new VBox(10);
    fieldsContainer.setAlignment(Pos.CENTER_LEFT);
    if (room == null) {
      fieldsContainer.getChildren().addAll(new Label("Numer pokoju:"), roomNumberField);
    }
    fieldsContainer
        .getChildren()
        .addAll(
            new Label("Typ pokoju:"),
            roomTypeComboBox,
            new Label("Stan pokoju:"),
            roomStateComboBox);

    HBox buttonBox = createButtonBox();

    Region spacer = new Region();
    VBox.setVgrow(spacer, Priority.ALWAYS);

    root.getChildren().addAll(titleLabel, errorMessageLabel, fieldsContainer, spacer, buttonBox);

    Scene scene = new Scene(root);
    setScene(scene);
    sizeToScene();
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

    HBox buttonBox = new HBox(10, saveButton, cancelButton);
    buttonBox.setAlignment(Pos.CENTER);
    return buttonBox;
  }

  private void handleSave() {
    resetFieldStyles();

    if (!validateInput()) {
      return;
    }

    if (room == null) {
      int roomNumber = Integer.parseInt(roomNumberField.getText().trim());
      room = new Room(roomNumber, roomTypeComboBox.getValue(), roomStateComboBox.getValue());
    }

    room.setRoomType(roomTypeComboBox.getValue());
    room.setRoomState(roomStateComboBox.getValue());

    if (onSaveConsumer != null) {
      onSaveConsumer.accept(room);
    }
    close();
  }

  private boolean validateInput() {
    errorMessageLabel.setVisible(false);

    if (room == null) {
      String input = roomNumberField.getText().trim();
      if (input.isEmpty()) {
        showError("Proszę podać numer pokoju.");
        return false;
      }
      try {
        Integer.parseInt(input);
      } catch (NumberFormatException e) {
        showError("Numer pokoju musi być liczbą całkowitą.");
        return false;
      }
    }

    if (roomTypeComboBox.getValue() == null) {
      showError("Proszę wybrać typ pokoju.");
      return false;
    }

    if (roomStateComboBox.getValue() == null) {
      showError("Proszę wybrać stan pokoju.");
      return false;
    }

    return true;
  }

  private void showError(String message) {
    errorMessageLabel.setText(message);
    errorMessageLabel.setVisible(true);
  }

  private void resetFieldStyles() {
    errorMessageLabel.setVisible(false);
  }

  public void setOnSave(Consumer<Room> onSaveConsumer) {
    this.onSaveConsumer = onSaveConsumer;
  }
}
