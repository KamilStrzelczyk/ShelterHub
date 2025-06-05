package org.emp.shelterhub.feature.report;

import static org.emp.shelterhub.lib.infrastructure.utils.Dimensions.FONT_SIZE_TITLE_LARGE;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;

public class ReportScreen extends StackPane {

  private final ReportScreenViewModel viewModel = new ReportScreenViewModel();

  public ReportScreen() {
    this.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");
    this.setAlignment(Pos.CENTER);

    Label label = new Label("Raport o stanie zajętości pokoi");
    label.setFont(new Font(FONT_SIZE_TITLE_LARGE));
    label.setStyle("-fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    Button generateReportButton = new Button("Generuj Raport");
    generateReportButton.setStyle(AppTheme.getButtonStyle(AppTheme.PRIMARY_COLOR));
    generateReportButton.setOnAction(
        e -> {
          viewModel.generateOccupancyReport();
          System.out.println("Przycisk 'Generuj Raport' został naciśnięty na ekranie raportu.");
        });

    VBox contentBox = new VBox(20);
    contentBox.setAlignment(Pos.CENTER);
    contentBox.getChildren().addAll(label, generateReportButton);

    this.getChildren().add(contentBox);
  }
}
