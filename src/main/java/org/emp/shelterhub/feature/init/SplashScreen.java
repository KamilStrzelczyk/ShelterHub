package org.emp.shelterhub.feature.init;

import java.util.Objects;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.emp.shelterhub.lib.infrastructure.utils.Res;

public class SplashScreen {
  private static final String SPLASH_IMAGE_URL = "/images/WilczaTurnia.png";

  public static void show(final Runnable onSplashFinished) {
    Platform.runLater(
        () -> {
          Stage splashStage = new Stage();
          splashStage.initStyle(StageStyle.UNDECORATED);

          final Image splashImage =
              new Image(Objects.requireNonNull(Res.getResourcePath(SPLASH_IMAGE_URL)));
          final ImageView splashImageView = new ImageView(splashImage);
          splashImageView.setFitWidth(300);
          splashImageView.setFitHeight(500);

          final StackPane root = new StackPane(splashImageView);
          final Scene splashScene = new Scene(root, 300, 500);
          splashStage.setScene(splashScene);

          Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
          splashStage.setX(screenBounds.getMinX() + (screenBounds.getWidth() - 300) / 2);
          splashStage.setY(screenBounds.getMinY() + (screenBounds.getHeight() - 500) / 2);

          splashStage.show();

          final PauseTransition delay = new PauseTransition(Duration.seconds(3));
          delay.setOnFinished(
              event -> {
                splashStage.close();
                onSplashFinished.run();
              });
          delay.play();
        });
  }
}
