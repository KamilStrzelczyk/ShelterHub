module org.emp.shelterhub {
  requires javafx.controls;
  requires javafx.fxml;

  opens org.emp.shelterhub.app to
      javafx.fxml;

  exports org.emp.shelterhub.app;
  exports org.emp.shelterhub.lib.infrastructure.utils;

  opens org.emp.shelterhub.lib.infrastructure.utils to
      javafx.fxml;

  exports org.emp.shelterhub.feature.init;

  opens org.emp.shelterhub.feature.init to
      javafx.fxml;
}
