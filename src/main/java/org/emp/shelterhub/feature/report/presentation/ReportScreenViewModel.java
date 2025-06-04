package org.emp.shelterhub.feature.report.presentation;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.time.LocalDate;
import java.util.List;
import org.emp.shelterhub.feature.report.infrastructure.RoomReportGenerator;
import org.emp.shelterhub.feature.report.infrastructure.ScheduleReportGenerator;
import org.emp.shelterhub.feature.room.data.Room;
import org.emp.shelterhub.feature.scheduler.data.ScheduleEntry;
import org.emp.shelterhub.lib.infrastructure.repository.RoomRepository;
import org.emp.shelterhub.lib.infrastructure.repository.SchedulerRepository;

public class ReportScreenViewModel {

  private final RoomRepository roomRepository = new RoomRepository();
  private final SchedulerRepository schedulerRepository = new SchedulerRepository();

  public void generateOccupancyReport() {
    System.out.println("Generating report about room occupancy...");

    getRooms()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .subscribe(
            rooms -> {
              RoomReportGenerator.generateRoomReport(rooms);
              System.out.println("Room report generated.");
            },
            error -> {
              System.err.println("Error generating room report: " + error.getMessage());
            });
  }

  private Single<List<Room>> getRooms() {
    return Single.fromCallable(roomRepository::getAllRooms);
  }

  public void generateSchedule(LocalDate currentWeekStart) {
    System.out.println("Generating employee schedule report...");

    getScheduleEntriesForWeek(currentWeekStart)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .subscribe(
            scheduleEntries -> {
              ScheduleReportGenerator.generateScheduleReport(scheduleEntries);
              System.out.println("Schedule report generated.");
            },
            error -> {
              System.err.println("Error generating schedule report: " + error.getMessage());
            });
  }

  private Single<List<ScheduleEntry>> getScheduleEntriesForWeek(LocalDate weekStart) {
    return Single.fromCallable(() -> schedulerRepository.getScheduleForWeek(weekStart));
  }
}
