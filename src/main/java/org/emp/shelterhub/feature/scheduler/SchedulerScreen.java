package org.emp.shelterhub.feature.scheduler;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import org.emp.shelterhub.feature.employee.data.Employee;
import org.emp.shelterhub.feature.scheduler.data.ScheduleEntry;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.infrastructure.utils.Dimensions;

public class SchedulerScreen extends VBox {

  private final SchedulerScreenViewModel viewModel = new SchedulerScreenViewModel();
  private final CompositeDisposable disposables = new CompositeDisposable();

  private GridPane scheduleGrid;
  private Label weekDateRangeLabel;
  private ComboBox<Employee> employeeFilterComboBox;
  private final Label loadingLabel;
  private final Label errorLabel;

  private LocalDate currentWeekStart;
  private Employee selectedEmployee;
  private Map<Employee, String> employeeColorMap;

  private static final int DAYS_IN_WEEK = 7;
  private static final int START_HOUR = 6;
  private static final int END_HOUR = 24;
  private static final double HOUR_SLOT_HEIGHT = 60.0;
  private static final Insets SCREEN_PADDING = new Insets(20);
  private static final double SCREEN_SPACING = 10;
  private static final double GRID_HGAP = 5;
  private static final double GRID_VGAP = 5;
  private static final Insets GRID_PADDING = new Insets(10);
  private static final double TIME_COLUMN_PREF_WIDTH = 80;
  private static final double DAY_COLUMN_MIN_WIDTH = 150;
  private static final double WEEK_NAV_BAR_SPACING = 10;
  private static final double ENTRY_BOX_PADDING = 2;
  private static final double ENTRY_BOX_SPACING = 2;

  private static final String TITLE_TEXT = "Grafik Pracowników";
  private static final String PREV_WEEK_BUTTON_TEXT = "Poprzedni Tydzień";
  private static final String NEXT_WEEK_BUTTON_TEXT = "Następny Tydzień";
  private static final String NEW_TASK_BUTTON_TEXT = "Zaplanuj zadanie";
  private static final String EMPLOYEE_FILTER_PROMPT_TEXT = "Wybierz Pracownika";
  private static final String ALL_EMPLOYEES_TEXT = "Wszyscy Pracownicy";
  private static final String DATE_FORMAT = "dd.MM.yyyy";
  private static final String DAY_MONTH_FORMAT = "dd.MM";
  private static final String TIME_FORMAT = "HH:mm";
  private static final String HOUR_FORMAT = "%02d:00";
  private static final String[] DAYS_OF_WEEK_NAMES = {
    "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"
  };

  private static final String[] TASK_COLORS = {
    "#AEC6CF", "#FFB347", "#77DD77", "#FF6961", "#CFCFC4", "#FDFD96", "#B39EB5", "#9AD0EC"
  };

  public SchedulerScreen() {
    setupScreenLayout();
    Label title = createTitleLabel();
    HBox weekNavBar = createWeekNavBar();
    ScrollPane scrollPane = createScheduleGridAndScrollPane();

    loadingLabel = new Label("Ładowanie danych...");
    loadingLabel.setStyle(
        "-fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + "; -fx-font-weight: bold;");
    loadingLabel.setVisible(false);

    errorLabel = new Label();
    errorLabel.setStyle("-fx-text-fill: " + AppTheme.ERROR_COLOR + "; -fx-font-weight: bold;");
    errorLabel.setVisible(false);

    this.getChildren().addAll(title, weekNavBar, loadingLabel, errorLabel, scrollPane);
    VBox.setVgrow(scrollPane, Priority.ALWAYS);
    this.setMaxWidth(Double.MAX_VALUE);

    disposables.add(viewModel.getState().subscribe(this::render));
  }

  private void render(SchedulerScreenState state) {
    loadingLabel.setVisible(state.isLoading());

    if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
      errorLabel.setText(state.getErrorMessage());
      errorLabel.setVisible(true);
    } else {
      errorLabel.setVisible(false);
    }

    updateEmployeeFilterComboBox(state.getEmployees());

    initializeEmployeeColors(state.getEmployees());

    populateScheduleGrid(state.getScheduleEntries());
  }

  private void setupScreenLayout() {
    this.setSpacing(SCREEN_SPACING);
    this.setPadding(SCREEN_PADDING);
    this.setAlignment(Pos.TOP_CENTER);
    this.setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");
  }

  private Label createTitleLabel() {
    Label title = new Label(TITLE_TEXT);
    title.setFont(new Font(Dimensions.FONT_SIZE_TITLE_LARGE));
    title.setStyle("-fx-text-fill: " + AppTheme.TEXT_COLOR_PRIMARY + ";");
    return title;
  }

  private HBox createWeekNavBar() {
    currentWeekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

    Button prevWeekButton = new Button(PREV_WEEK_BUTTON_TEXT);
    prevWeekButton.setStyle(AppTheme.getButtonStyle(AppTheme.PRIMARY_COLOR));
    prevWeekButton.setOnAction(
        e -> {
          currentWeekStart = currentWeekStart.minusWeeks(1);
          populateScheduleGrid(viewModel.getState().getValue().getScheduleEntries());
        });

    weekDateRangeLabel = new Label();
    weekDateRangeLabel.setStyle(
        AppTheme.getLabelStyle(Dimensions.FONT_SIZE_BODY, true, AppTheme.TEXT_COLOR_PRIMARY));

    Button nextWeekButton = new Button(NEXT_WEEK_BUTTON_TEXT);
    nextWeekButton.setStyle(AppTheme.getButtonStyle(AppTheme.PRIMARY_COLOR));
    nextWeekButton.setOnAction(
        e -> {
          currentWeekStart = currentWeekStart.plusWeeks(1);
          populateScheduleGrid(viewModel.getState().getValue().getScheduleEntries());
        });

    employeeFilterComboBox = new ComboBox<>();
    employeeFilterComboBox.setPromptText(EMPLOYEE_FILTER_PROMPT_TEXT);
    employeeFilterComboBox.setConverter(
        new StringConverter<Employee>() {
          @Override
          public String toString(Employee employee) {
            if (employee == null) return ALL_EMPLOYEES_TEXT;
            return employee.getFirstName() + " " + employee.getLastName();
          }

          @Override
          public Employee fromString(String string) {
            return null;
          }
        });

    employeeFilterComboBox.setOnAction(
        e -> {
          selectedEmployee = employeeFilterComboBox.getSelectionModel().getSelectedItem();
          populateScheduleGrid(viewModel.getState().getValue().getScheduleEntries());
        });

    Button newTaskButton = new Button(NEW_TASK_BUTTON_TEXT);
    newTaskButton.setStyle(AppTheme.getButtonStyle(AppTheme.PRIMARY_COLOR));
    newTaskButton.setOnAction(
        e -> {
          ScheduleEditDialog dialog =
              new ScheduleEditDialog(null, viewModel.getState().getValue().getEmployees());
          dialog.setOnSave(viewModel::addScheduleEntry);
          dialog.showAndWait();
        });

    HBox weekNavBar = new HBox(WEEK_NAV_BAR_SPACING);
    weekNavBar.setAlignment(Pos.CENTER);
    weekNavBar.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(weekNavBar, Priority.ALWAYS);
    weekNavBar
        .getChildren()
        .addAll(
            prevWeekButton,
            weekDateRangeLabel,
            nextWeekButton,
            employeeFilterComboBox,
            newTaskButton);
    return weekNavBar;
  }

  private void updateEmployeeFilterComboBox(List<Employee> employees) {
    Employee previouslySelected = employeeFilterComboBox.getSelectionModel().getSelectedItem();

    employeeFilterComboBox.getItems().clear();
    employeeFilterComboBox.getItems().add(null);
    employeeFilterComboBox.getItems().addAll(employees);

    if (previouslySelected != null && employees.contains(previouslySelected)) {
      employeeFilterComboBox.getSelectionModel().select(previouslySelected);
    } else {
      employeeFilterComboBox.getSelectionModel().select(null);
    }
    selectedEmployee = employeeFilterComboBox.getSelectionModel().getSelectedItem();
  }

  private ScrollPane createScheduleGridAndScrollPane() {
    scheduleGrid = new GridPane();
    scheduleGrid.setHgap(GRID_HGAP);
    scheduleGrid.setVgap(GRID_VGAP);
    scheduleGrid.setPadding(GRID_PADDING);
    scheduleGrid.setStyle(
        "-fx-background-color: "
            + AppTheme.CARD_BACKGROUND_COLOR
            + "; -fx-border-color: "
            + AppTheme.BORDER_COLOR
            + "; -fx-border-radius: 5; -fx-background-radius: 5;");
    scheduleGrid.setMaxWidth(Double.MAX_VALUE);

    setupScheduleGridStructure();

    ScrollPane scrollPane = new ScrollPane(scheduleGrid);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
    scrollPane.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(scrollPane, Priority.ALWAYS);

    return scrollPane;
  }

  private void setupScheduleGridStructure() {
    ColumnConstraints timeColumn = new ColumnConstraints();
    timeColumn.setPrefWidth(TIME_COLUMN_PREF_WIDTH);
    scheduleGrid.getColumnConstraints().add(timeColumn);

    for (int i = 0; i < DAYS_IN_WEEK; i++) {
      ColumnConstraints dayColumn = new ColumnConstraints();
      dayColumn.setHgrow(Priority.ALWAYS);
      dayColumn.setMinWidth(DAY_COLUMN_MIN_WIDTH);
      scheduleGrid.getColumnConstraints().add(dayColumn);
    }

    for (int i = 0; i <= (END_HOUR - START_HOUR); i++) {
      RowConstraints rowConstraints = new RowConstraints();
      rowConstraints.setMinHeight(HOUR_SLOT_HEIGHT);
      rowConstraints.setVgrow(Priority.ALWAYS);
      scheduleGrid.getRowConstraints().add(rowConstraints);
    }
  }

  private void initializeEmployeeColors(List<Employee> employees) {
    employeeColorMap = new HashMap<>();
    for (int i = 0; i < employees.size(); i++) {
      Employee employee = employees.get(i);
      String color = TASK_COLORS[i % TASK_COLORS.length];
      employeeColorMap.put(employee, color);
    }
  }

  private void populateScheduleGrid(List<ScheduleEntry> scheduleEntries) {
    scheduleGrid.getChildren().clear();
    updateWeekDateRangeLabel();
    addDayHeadersToGrid();
    addTimeLabelsToGrid();
    addBackgroundCellsToGrid();
    addScheduleEntriesToGrid(scheduleEntries);
  }

  private void updateWeekDateRangeLabel() {
    LocalDate weekEnd = currentWeekStart.plusDays(6);
    weekDateRangeLabel.setText(
        currentWeekStart.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
            + " - "
            + weekEnd.format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
  }

  private void addDayHeadersToGrid() {
    for (int i = 0; i < DAYS_IN_WEEK; i++) {
      LocalDate day = currentWeekStart.plusDays(i);
      Label dayLabel =
          new Label(
              DAYS_OF_WEEK_NAMES[i]
                  + "\n"
                  + day.format(DateTimeFormatter.ofPattern(DAY_MONTH_FORMAT)));
      dayLabel.setStyle(
          AppTheme.getLabelStyle(Dimensions.FONT_SIZE_BODY, true, AppTheme.TEXT_COLOR_PRIMARY));
      dayLabel.setAlignment(Pos.CENTER);
      scheduleGrid.add(dayLabel, i + 1, 0);
      GridPane.setHalignment(dayLabel, HPos.CENTER);
    }
  }

  private void addTimeLabelsToGrid() {
    int row = 1;
    for (int hour = START_HOUR; hour < END_HOUR; hour++) {
      Label timeLabel = new Label(String.format(HOUR_FORMAT, hour));
      timeLabel.setStyle(
          AppTheme.getLabelStyle(Dimensions.FONT_SIZE_BODY, true, AppTheme.TEXT_COLOR_SECONDARY));
      timeLabel.setAlignment(Pos.CENTER_RIGHT);
      scheduleGrid.add(timeLabel, 0, row);
      GridPane.setHalignment(timeLabel, HPos.RIGHT);
      GridPane.setValignment(timeLabel, VPos.TOP);
      row++;
    }
  }

  private void addBackgroundCellsToGrid() {
    for (int r = 1; r <= (END_HOUR - START_HOUR) + 1; r++) {
      for (int c = 1; c <= DAYS_IN_WEEK; c++) {
        Region backgroundCell = new Region();
        backgroundCell.setStyle(
            "-fx-background-color: "
                + AppTheme.BACKGROUND_COLOR_LIGHT
                + "; -fx-border-color: "
                + AppTheme.BORDER_COLOR
                + "; -fx-border-width: 0.5;");
        scheduleGrid.add(backgroundCell, c, r);
        GridPane.setHgrow(backgroundCell, Priority.ALWAYS);
        GridPane.setVgrow(backgroundCell, Priority.ALWAYS);
      }
    }
  }

  private void addScheduleEntriesToGrid(List<ScheduleEntry> scheduleEntries) {
    List<ScheduleEntry> filteredEntries =
        scheduleEntries.stream()
            .filter(
                entry ->
                    !entry.getDate().isBefore(currentWeekStart)
                        && !entry.getDate().isAfter(currentWeekStart.plusDays(6)))
            .filter(
                entry ->
                    selectedEmployee == null
                        || (selectedEmployee != null
                            && entry.getAssignedEmployee() != null
                            && entry.getAssignedEmployee().equals(selectedEmployee)))
            .sorted(Comparator.comparing(ScheduleEntry::getStartTime))
            .toList();

    for (ScheduleEntry entry : filteredEntries) {
      int colIndex = entry.getDate().getDayOfWeek().getValue();
      int startHour = entry.getStartTime().getHour();
      int startHourGridRow = startHour - START_HOUR + 1;

      if (startHourGridRow >= 1 && startHourGridRow <= (END_HOUR - START_HOUR)) {
        VBox entryDetailsBox = createScheduleEntryBox(entry);

        scheduleGrid.add(
            entryDetailsBox,
            colIndex,
            startHourGridRow,
            1,
            calculateRowSpan(entry, startHourGridRow));
        GridPane.setHgrow(entryDetailsBox, Priority.ALWAYS);
        GridPane.setVgrow(entryDetailsBox, Priority.ALWAYS);
        GridPane.setValignment(entryDetailsBox, VPos.TOP);
        GridPane.setHalignment(entryDetailsBox, HPos.LEFT);
      }
    }
  }

  private int calculateRowSpan(ScheduleEntry entry, int startHourGridRow) {
    double durationInHours =
        (entry.getEndTime().toSecondOfDay() - entry.getStartTime().toSecondOfDay()) / 3600.0;
    int calculatedRowSpan =
        (int) Math.ceil(durationInHours + (entry.getStartTime().getMinute() / 60.0));

    if (calculatedRowSpan == 0 && durationInHours > 0) calculatedRowSpan = 1;

    int maxPossibleRowSpan = (END_HOUR - START_HOUR) - (startHourGridRow - 1);
    calculatedRowSpan = Math.min(calculatedRowSpan, maxPossibleRowSpan);

    if (calculatedRowSpan < 1) calculatedRowSpan = 1;
    return calculatedRowSpan;
  }

  private VBox createScheduleEntryBox(ScheduleEntry entry) {
    double durationInHours =
        (entry.getEndTime().toSecondOfDay() - entry.getStartTime().toSecondOfDay()) / 3600.0;
    double translateY = (entry.getStartTime().getMinute() / 60.0) * HOUR_SLOT_HEIGHT;

    VBox entryDetailsBox = new VBox(ENTRY_BOX_SPACING);
    entryDetailsBox.setPadding(new Insets(ENTRY_BOX_PADDING));

    String taskBgColor = employeeColorMap.getOrDefault(entry.getAssignedEmployee(), TASK_COLORS[0]);
    entryDetailsBox.setStyle(
        "-fx-background-color: "
            + taskBgColor
            + "80; -fx-border-color: "
            + taskBgColor
            + "; -fx-border-radius: 3; -fx-background-radius: 3;");
    entryDetailsBox.setPrefHeight(HOUR_SLOT_HEIGHT * durationInHours);
    entryDetailsBox.setMaxHeight(HOUR_SLOT_HEIGHT * durationInHours);
    entryDetailsBox.setTranslateY(translateY);

    Label taskLabel = new Label(entry.getTaskDescription());
    taskLabel.setStyle(
        "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: "
            + AppTheme.TEXT_COLOR_PRIMARY
            + ";");
    taskLabel.setWrapText(true);

    Label employeeLabel =
        new Label(
            entry.getAssignedEmployee().getFirstName()
                + " "
                + entry.getAssignedEmployee().getLastName());
    employeeLabel.setStyle(
        "-fx-font-size: 10px; -fx-text-fill: " + AppTheme.TEXT_COLOR_SECONDARY + ";");
    employeeLabel.setWrapText(true);

    Label timeRangeLabel =
        new Label(
            entry.getStartTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT))
                + " - "
                + entry.getEndTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
    timeRangeLabel.setStyle(
        "-fx-font-size: 9px; -fx-text-fill: " + AppTheme.TEXT_COLOR_SECONDARY + ";");

    entryDetailsBox.getChildren().addAll(taskLabel, employeeLabel, timeRangeLabel);

    entryDetailsBox.setOnMouseClicked(
        event -> {
          if (event.getClickCount() == 1) {
            ScheduleEditDialog dialog =
                new ScheduleEditDialog(entry, viewModel.getState().getValue().getEmployees());
            dialog.setOnSave(viewModel::updateScheduleEntry);
            dialog.showAndWait();
          }
        });
    return entryDetailsBox;
  }
}
