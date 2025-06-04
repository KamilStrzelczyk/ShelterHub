package org.emp.shelterhub.feature.report.presentation;

import static org.emp.shelterhub.lib.infrastructure.utils.Dimensions.FONT_SIZE_TITLE_LARGE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;

public class ReportScreen extends StackPane {

  private final ReportScreenViewModel viewModel = new ReportScreenViewModel();

  private LocalDate currentWeekStart;
  private Label weekDateRangeLabel;

  public ReportScreen() {
    this.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");
    this.setAlignment(Pos.CENTER);

    currentWeekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

    VBox reportSection = new VBox(10);
    reportSection.setAlignment(Pos.CENTER);

    Label reportLabel = new Label("Raport o stanie pokoi");
    reportLabel.setFont(Font.font("System", FontWeight.BOLD, FONT_SIZE_TITLE_LARGE));
    reportLabel.setStyle("-fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    Button generateReportButton = new Button("Generuj Raport");
    generateReportButton.setStyle(AppTheme.getButtonStyle(AppTheme.PRIMARY_COLOR));
    generateReportButton.setOnAction(
        e -> {
          viewModel.generateOccupancyReport();
          System.out.println("Generowanie raportu o stanie zajętości pokoi");
        });

    reportSection.getChildren().addAll(reportLabel, generateReportButton);

    VBox scheduleSection = new VBox(15);
    scheduleSection.setAlignment(Pos.CENTER);

    Label scheduleLabel = new Label("Grafik pracowników");
    scheduleLabel.setFont(Font.font("System", FontWeight.BOLD, FONT_SIZE_TITLE_LARGE));
    scheduleLabel.setStyle("-fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");

    HBox weekAndButtonBar = createWeekAndButtonBar();

    scheduleSection.getChildren().addAll(scheduleLabel, weekAndButtonBar);

    VBox contentBox = new VBox(40);
    contentBox.setAlignment(Pos.CENTER);
    contentBox.getChildren().addAll(reportSection, scheduleSection);

    this.getChildren().add(contentBox);
  }

  private HBox createWeekAndButtonBar() {
    Button prevWeekButton = new Button("Poprzedni Tydzień");
    prevWeekButton.setStyle(AppTheme.getButtonStyle(AppTheme.PRIMARY_COLOR));
    prevWeekButton.setOnAction(
        e -> {
          currentWeekStart = currentWeekStart.minusWeeks(1);
          updateWeekDateRangeLabel();
        });

    weekDateRangeLabel = new Label();
    weekDateRangeLabel.setFont(Font.font("System", FontWeight.BOLD, 16)); // zwiększony font
    weekDateRangeLabel.setStyle("-fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");
    updateWeekDateRangeLabel();

    Button nextWeekButton = new Button("Następny Tydzień");
    nextWeekButton.setStyle(AppTheme.getButtonStyle(AppTheme.PRIMARY_COLOR));
    nextWeekButton.setOnAction(
        e -> {
          currentWeekStart = currentWeekStart.plusWeeks(1);
          updateWeekDateRangeLabel();
        });

    Button generateScheduleButton = new Button("Generuj Grafik");
    generateScheduleButton.setStyle(AppTheme.getButtonStyle(AppTheme.PRIMARY_COLOR));
    generateScheduleButton.setOnAction(
        e -> {
          viewModel.generateSchedule(currentWeekStart);
          System.out.println(
              "Generowanie grafiku dla tygodnia: "
                  + currentWeekStart.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        });

    HBox navBar =
        new HBox(10, prevWeekButton, weekDateRangeLabel, nextWeekButton, generateScheduleButton);
    navBar.setAlignment(Pos.CENTER);
    return navBar;
  }

  private void updateWeekDateRangeLabel() {
    LocalDate weekEnd = currentWeekStart.plusDays(6);
    weekDateRangeLabel.setText(
        currentWeekStart.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            + " - "
            + weekEnd.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
  }
}
