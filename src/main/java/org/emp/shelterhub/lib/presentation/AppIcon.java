package org.emp.shelterhub.lib.presentation;

import java.util.Objects;
import javafx.scene.image.Image;
import org.emp.shelterhub.lib.infrastructure.utils.Res;

public class AppIcon {
  private static final String ICON = "/images/SHELTERHUB.Logo_short.png";
  private static Image appIcon;

  public static Image getAppIcon() {
    if (appIcon == null) {
      appIcon = new Image(Objects.requireNonNull(Res.getResourcePath(ICON)));
    }
    return appIcon;
  }
}
