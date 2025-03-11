package org.emp.shelterhub.feature.init;

import java.util.Objects;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.emp.shelterhub.lib.infrastructure.utils.Res;

public class SplashScreen {
  private static final String SPLASH_IMAGE_URL = "/images/splash_screen.png";

  public static void show(final Runnable onSplashFinished) {
    Platform.runLater(
        () -> {
          Stage splashStage = new Stage();
          splashStage.initStyle(StageStyle.UNDECORATED);

          final Image splashImage =
              new Image(Objects.requireNonNull(Res.getResourcePath(SPLASH_IMAGE_URL)));
          final ImageView splashImageView = new ImageView(splashImage);
          splashImageView.setFitWidth(500);
          splashImageView.setFitHeight(400);

          final StackPane root = new StackPane(splashImageView);
          final Scene splashScene = new Scene(root, 500, 400);
          splashStage.setScene(splashScene);
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
