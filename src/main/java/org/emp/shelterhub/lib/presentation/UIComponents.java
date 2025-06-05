package org.emp.shelterhub.lib.presentation;

import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.infrastructure.utils.Dimensions;
import org.emp.shelterhub.lib.infrastructure.utils.Res;

public class UIComponents {

  public VBox createBox(
      int spacing, String bgColor, int radius, Insets padding, double maxWidth, double maxHeight) {
    VBox box = new VBox(spacing);
    box.setAlignment(Pos.CENTER);

    if (bgColor != null) {
      box.setStyle(
          "-fx-background-color: "
              + bgColor
              + (radius > 0 ? "; -fx-background-radius: " + radius : ""));
    }

    if (padding != null) box.setPadding(padding);
    if (maxWidth > 0) box.setMaxWidth(maxWidth);
    if (maxHeight > 0) box.setMaxHeight(maxHeight);

    return box;
  }

  public void addShadow(VBox container, int radius, double opacity) {
    DropShadow shadow = new DropShadow();
    shadow.setColor(Color.rgb(0, 0, 0, opacity));
    shadow.setRadius(radius);
    container.setEffect(shadow);
  }

  public ImageView createLogo(String imageUrl, int width, int height) {
    final Image logoImage = new Image(Objects.requireNonNull(Res.getResourcePath(imageUrl)));
    final ImageView logoImageView = new ImageView(logoImage);
    logoImageView.setFitWidth(width);
    logoImageView.setFitHeight(height);
    return logoImageView;
  }

  public Label createLabel(String text, int size, boolean isBold, String color) {
    Label label = new Label(text);
    label.setStyle(AppTheme.getLabelStyle(size, isBold, color));
    return label;
  }

  public TextField createTextField(String promptText) {
    TextField field = new TextField();
    field.setPromptText(promptText);
    field.setMaxWidth(Double.MAX_VALUE);
    field.setStyle(AppTheme.getTextFieldStyle());
    return field;
  }

  public PasswordField createPasswordField(String promptText) {
    PasswordField field = new PasswordField();
    field.setPromptText(promptText);
    field.setMaxWidth(Double.MAX_VALUE);
    field.setStyle(AppTheme.getTextFieldStyle());
    return field;
  }

  public VBox createFieldContainer(String labelText, Control field) {
    VBox container = new VBox(Dimensions.SPACING_SMALL);
    container.setMaxWidth(Double.MAX_VALUE);

    Label label =
        createLabel(labelText, Dimensions.FONT_SIZE_BODY, false, AppTheme.TEXT_COLOR_PRIMARY);
    container.getChildren().addAll(label, field);

    return container;
  }

  public Button createButton(String text, String primaryColor, String hoverColor) {
    Button button = new Button(text);
    String buttonStyle = AppTheme.getButtonStyle(primaryColor);

    button.setStyle(buttonStyle);
    button.setPrefWidth(Dimensions.BUTTON_WIDTH);
    button.setMaxWidth(Region.USE_PREF_SIZE);

    button.setOnMouseEntered(e -> button.setStyle(buttonStyle.replace(primaryColor, hoverColor)));

    button.setOnMouseExited(e -> button.setStyle(buttonStyle));

    return button;
  }
}
