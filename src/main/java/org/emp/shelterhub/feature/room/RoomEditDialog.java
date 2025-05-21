package org.emp.shelterhub.feature.room;

import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region; // Import Region for spacer
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.emp.shelterhub.feature.room.data.Room;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;

public class RoomEditDialog extends Stage {

  private Room room;
  private CheckBox isOccupiedCheckBox;
  private CheckBox isCleanCheckBox;
  private CheckBox isAvailableCheckBox;
  private CheckBox hasMalfunctionCheckBox;

  private Consumer<Room> onSaveConsumer;

  public RoomEditDialog(Room room) {
    this.room = room;
    initModality(Modality.APPLICATION_MODAL);
    setTitle("Edytuj Pokój " + room.getRoomNumber());

    VBox root = new VBox(15);
    root.setPadding(new Insets(20));
    root.setAlignment(Pos.TOP_CENTER);
    root.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");

    Label titleLabel = new Label("Edytuj status pokoju " + room.getRoomNumber());
    titleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    VBox checkboxContent = new VBox(10);
    checkboxContent.setAlignment(Pos.CENTER_LEFT);
    isOccupiedCheckBox = new CheckBox("Zajęty");
    isOccupiedCheckBox.setSelected(room.isOccupied());
    isOccupiedCheckBox.setStyle(
        "-fx-font-size: 14px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    isCleanCheckBox = new CheckBox("Czysty");
    isCleanCheckBox.setSelected(room.isClean());
    isCleanCheckBox.setStyle(
        "-fx-font-size: 14px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    isAvailableCheckBox = new CheckBox("Dostępny");
    isAvailableCheckBox.setSelected(room.isAvailable());
    isAvailableCheckBox.setStyle(
        "-fx-font-size: 14px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    hasMalfunctionCheckBox = new CheckBox("Awaria");
    hasMalfunctionCheckBox.setSelected(room.hasMalfunction());
    hasMalfunctionCheckBox.setStyle(
        "-fx-font-size: 14px; -fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    checkboxContent
        .getChildren()
        .addAll(isOccupiedCheckBox, isCleanCheckBox, isAvailableCheckBox, hasMalfunctionCheckBox);

    HBox checkboxContainerWrapper = new HBox(checkboxContent);
    checkboxContainerWrapper.setAlignment(Pos.CENTER);

    HBox buttonBox = getHBox();

    Region spacerAfterTitle = new Region();
    VBox.setVgrow(spacerAfterTitle, Priority.ALWAYS);

    Region spacerBeforeButtons = new Region();
    VBox.setVgrow(spacerBeforeButtons, Priority.ALWAYS);

    root.getChildren()
        .addAll(
            titleLabel, spacerAfterTitle, checkboxContainerWrapper, spacerBeforeButtons, buttonBox);

    Scene scene = new Scene(root, 300, 350);
    setScene(scene);
  }

  private HBox getHBox() {
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
    room.setOccupied(isOccupiedCheckBox.isSelected());
    room.setClean(isCleanCheckBox.isSelected());
    room.setAvailable(isAvailableCheckBox.isSelected());
    room.setMalfunction(hasMalfunctionCheckBox.isSelected());

    if (onSaveConsumer != null) {
      onSaveConsumer.accept(room);
    }
    close();
  }

  public void setOnSave(Consumer<Room> onSaveConsumer) {
    this.onSaveConsumer = onSaveConsumer;
  }
}
