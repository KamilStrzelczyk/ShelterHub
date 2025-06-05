package org.emp.shelterhub.feature.report.infrastructure;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.emp.shelterhub.feature.room.data.Room;

public class RoomReportGenerator {

  public static void generateRoomReport(List<Room> rooms) {

    try (Workbook workbook = new XSSFWorkbook()) {
      Sheet sheet = workbook.createSheet("Raport Pokoi");

      Row header = sheet.createRow(0);
      header.createCell(0).setCellValue("Numer pokoju");
      header.createCell(1).setCellValue("Dostępność");
      header.createCell(2).setCellValue("Stan");
      header.createCell(3).setCellValue("Typ");

      CellStyle styleAvailable = workbook.createCellStyle();
      styleAvailable.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
      styleAvailable.setFillPattern(FillPatternType.SOLID_FOREGROUND);

      CellStyle styleUnavailable = workbook.createCellStyle();
      styleUnavailable.setFillForegroundColor(IndexedColors.ROSE.getIndex());
      styleUnavailable.setFillPattern(FillPatternType.SOLID_FOREGROUND);

      for (int i = 0; i < rooms.size(); i++) {
        Room room = rooms.get(i);
        Row row = sheet.createRow(i + 1);

        row.createCell(0).setCellValue(room.getRoomNumber());

        Cell availabilityCell = row.createCell(1);
        String dostepnosc = room.isAvailable() ? "Dostępny" : "Niedostępny";
        availabilityCell.setCellValue(dostepnosc);

        if (room.isAvailable()) {
          availabilityCell.setCellStyle(styleAvailable);
        } else {
          availabilityCell.setCellStyle(styleUnavailable);
        }

        row.createCell(2).setCellValue(room.getRoomState().getDbValue());
        row.createCell(3).setCellValue(room.getRoomType().getDbValue());
      }

      for (int col = 0; col < 4; col++) {
        sheet.autoSizeColumn(col);
      }

      String homeDir = System.getProperty("user.home");
      String reportFileName = "raport_pokoi.xlsx";

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
      System.out.println("Raport wygenerowany: " + reportFile.getAbsolutePath());

    } catch (IOException e) {
      System.err.println("Błąd podczas generowania raportu: " + e.getMessage());
      e.printStackTrace();
    }
  }
}