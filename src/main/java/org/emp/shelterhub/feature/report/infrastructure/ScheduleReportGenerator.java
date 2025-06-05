package org.emp.shelterhub.feature.report.infrastructure;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.emp.shelterhub.feature.employee.data.Employee;
import org.emp.shelterhub.feature.scheduler.data.ScheduleEntry;

public class ScheduleReportGenerator {

  public static void generateScheduleReport(List<ScheduleEntry> scheduleEntries) {

    try (Workbook workbook = new XSSFWorkbook()) {
      Sheet sheet = workbook.createSheet("Grafik Pracowników");

      Row header = sheet.createRow(0);
      header.createCell(0).setCellValue("Data");
      header.createCell(1).setCellValue("Godzina rozpoczęcia");
      header.createCell(2).setCellValue("Godzina zakończenia");
      header.createCell(3).setCellValue("Pracownik");
      header.createCell(4).setCellValue("Opis zadania");

      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
      DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

      for (int i = 0; i < scheduleEntries.size(); i++) {
        ScheduleEntry entry = scheduleEntries.get(i);
        Row row = sheet.createRow(i + 1);

        row.createCell(0).setCellValue(entry.getDate().format(dateFormatter));
        row.createCell(1).setCellValue(entry.getStartTime().format(timeFormatter));
        row.createCell(2).setCellValue(entry.getEndTime().format(timeFormatter));

        Employee employee = entry.getAssignedEmployee();
        String employeeName = employee.getFirstName() + " " + employee.getLastName();
        row.createCell(3).setCellValue(employeeName);

        row.createCell(4).setCellValue(entry.getTaskDescription());
      }

      for (int col = 0; col < 5; col++) {
        sheet.autoSizeColumn(col);
      }

      String homeDir = System.getProperty("user.home");
      String reportFileName = "grafik_pracownikow.xlsx";

      String os = System.getProperty("os.name").toLowerCase();
      String savePath;
      if (os.contains("mac")) {
        savePath = homeDir + File.separator + "Downloads" + File.separator + reportFileName;
      } else {
        savePath = homeDir + File.separator + reportFileName;
      }

      File reportFile = new File(savePath);

      try (FileOutputStream out = new FileOutputStream(reportFile)) {
        workbook.write(out);
      }

      System.out.println("Raport grafiku wygenerowany: " + reportFile.getAbsolutePath());

    } catch (IOException e) {
      System.err.println("Błąd podczas generowania raportu grafiku: " + e.getMessage());
      e.printStackTrace();
    }
  }
}