package org.emp.shelterhub.feature.topBar;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.infrastructure.utils.Dimensions;

public class TopBar extends BorderPane {

  private ImageView logoImageView;
  private Button logoutButton;
  private ImageView userIconImageView;
  private Runnable onLogoutAction;

  public TopBar(Runnable onLogoutAction) {
    this.onLogoutAction = onLogoutAction;
    initializeComponents();
    setupLayout();
    styleComponents();
    setupActions();
  }

  private void initializeComponents() {
    try {
      Image logoImage = new Image(getClass().getResourceAsStream("/images/SHELTERHUB.Logo.png"));
      logoImageView = new ImageView(logoImage);
      logoImageView.setFitHeight(88);
      logoImageView.setPreserveRatio(true);
    } catch (Exception e) {
      System.err.println("Could not load logo image: " + e.getMessage());
      logoImageView = new ImageView();
    }

    logoutButton = new Button("WYLOGUJ");

    try {
      Image userIcon = new Image(getClass().getResourceAsStream("/images/IkonaMSZ.png"));
      userIconImageView = new ImageView(userIcon);
      userIconImageView.setFitHeight(70);
      userIconImageView.setFitWidth(70);
      userIconImageView.setPreserveRatio(true);
    } catch (Exception e) {
      System.err.println("Could not load user icon: " + e.getMessage());
      userIconImageView = new ImageView();
    }
  }

  private void setupLayout() {
    HBox leftContainer = new HBox(logoImageView);
    leftContainer.setAlignment(Pos.CENTER_LEFT);

    HBox rightContainer = new HBox(Dimensions.SPACING_MEDIUM);
    rightContainer.getChildren().addAll(logoutButton, userIconImageView);
    rightContainer.setAlignment(Pos.CENTER_RIGHT);

    setLeft(leftContainer);
    setRight(rightContainer);

    setPadding(new Insets(Dimensions.SPACING_MEDIUM));

    setMinHeight(98);
  }

  private void styleComponents() {
    setStyle("-fx-background-color: #CCCCCC;");

    String logoutButtonStyle =
        "-fx-background-color: "
            + AppTheme.PRIMARY_COLOR
            + "; "
            + "-fx-text-fill: white; "
            + "-fx-font-size: "
            + Dimensions.FONT_SIZE_SUBTITLE
            + "px; "
            + "-fx-font-weight: bold; "
            + "-fx-padding: 12 30; "
            + "-fx-background-radius: "
            + Dimensions.BORDER_RADIUS_XLARGE
            + "px; "
            + "-fx-min-width: 100px;";

    logoutButton.setStyle(logoutButtonStyle);
  }

  private void setupActions() {
    logoutButton.setOnAction(event -> {
      if (onLogoutAction != null) {
        onLogoutAction.run();
      }
    });
  }
}
