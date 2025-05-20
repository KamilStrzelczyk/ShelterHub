package org.emp.shelterhub.feature.footer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.infrastructure.utils.Dimensions;

public class Footer extends BorderPane {
  private Label copyrightLabel;

  public Footer() {
    initializeComponents();
    setupLayout();
    styleComponents();
  }

  private void initializeComponents() {
    copyrightLabel = new Label("© Shelter Hub by EMP");
  }

  private void setupLayout() {
    setCenter(copyrightLabel);
    setPadding(new Insets(Dimensions.SPACING_SMALL));
    setMinHeight(30);
  }

  private void styleComponents() {
    setStyle(
        "-fx-background-color: "
            + AppTheme.BACKGROUND_COLOR
            + "; "
            + "-fx-border-color: "
            + AppTheme.PRIMARY_COLOR
            + "; "
            + "-fx-border-width: 1px;");

    copyrightLabel.setStyle(
        "-fx-text-fill: "
            + AppTheme.TEXT_COLOR_PRIMARY
            + "; "
            + "-fx-font-size: "
            + Dimensions.FONT_SIZE_BODY
            + "px; "
            + "-fx-font-style: italic;");

    BorderPane.setAlignment(copyrightLabel, Pos.CENTER);
  }
}
