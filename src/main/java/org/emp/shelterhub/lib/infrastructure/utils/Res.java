package org.emp.shelterhub.lib.infrastructure.utils;

import java.net.URL;

public class Res {
  public static String getResourcePath(String resourceName) {
    final URL resource = Res.class.getResource(resourceName);
    if (resource == null) {
      System.err.println("Błąd: Nie znaleziono pliku " + resourceName);
      return null;
    }
    return resource.toExternalForm();
  }
}
