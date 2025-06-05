module ShelterHub.main {
  requires io.reactivex.rxjava3;
  requires java.desktop;
  requires java.sql;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.graphics;
  requires org.apache.poi.ooxml;
  requires org.apache.poi.poi;
  requires org.apache.commons.lang3;
  requires commons.math3;

  exports org.emp.shelterhub.app to
      javafx.graphics;

  requires org.xerial.sqlitejdbc;
}
