package org.emp.shelterhub.feature.report;

public class ReportScreenViewModel {

  public void generateOccupancyReport() {

    System.out.println("Generating report about room occupancy...");

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.err.println("Report generation interrupted.");
    }
    System.out.println("Report generated (mocked).");
  }
}
