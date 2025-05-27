package org.emp.shelterhub.feature.scheduler;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.emp.shelterhub.feature.employee.EmployeeScreenViewModel;
import org.emp.shelterhub.feature.employee.data.Employee;
import org.emp.shelterhub.feature.scheduler.data.ScheduleEntry;

public class SchedulerScreenViewModel {

  private List<ScheduleEntry> scheduleEntries;
  private EmployeeScreenViewModel employeeViewModel;

  public SchedulerScreenViewModel() {
    this.scheduleEntries = new ArrayList<>();
    this.employeeViewModel = new EmployeeScreenViewModel();
    generateDummySchedule();
  }

  public List<ScheduleEntry> getScheduleEntries() {
    return scheduleEntries;
  }

  public List<Employee> getEmployees() {
    return new ArrayList<>();
  }

  private void generateDummySchedule() {
    List<Employee> employees = new ArrayList<>();
    if (employees.isEmpty()) {
      System.out.println("Brak dostępnych przykładowych pracowników do harmonogramowania.");
      return;
    }

    LocalDate today = LocalDate.now();

    scheduleEntries.add(
        new ScheduleEntry(
            "sch1",
            today.with(DayOfWeek.MONDAY),
            LocalTime.of(8, 0),
            LocalTime.of(12, 0),
            employees.get(0),
            "Opieka nad zwierzętami"));
    scheduleEntries.add(
        new ScheduleEntry(
            "sch2",
            today.with(DayOfWeek.MONDAY),
            LocalTime.of(10, 0),
            LocalTime.of(14, 0),
            employees.get(1),
            "Sprzątanie boksów"));
    scheduleEntries.add(
        new ScheduleEntry(
            "sch3",
            today.with(DayOfWeek.TUESDAY),
            LocalTime.of(9, 0),
            LocalTime.of(13, 0),
            employees.get(2),
            "Przyjmowanie dostaw"));
    scheduleEntries.add(
        new ScheduleEntry(
            "sch11",
            today.with(DayOfWeek.TUESDAY),
            LocalTime.of(10, 15),
            LocalTime.of(12, 45),
            employees.get(2),
            "Rozmowy kwalifikacyjne"));

    scheduleEntries.add(
        new ScheduleEntry(
            "sch4",
            today.with(DayOfWeek.WEDNESDAY),
            LocalTime.of(14, 0),
            LocalTime.of(18, 0),
            employees.get(0),
            "Spacer z psami"));
    scheduleEntries.add(
        new ScheduleEntry(
            "sch5",
            today.with(DayOfWeek.WEDNESDAY),
            LocalTime.of(8, 0),
            LocalTime.of(16, 0),
            employees.get(3),
            "Prace biurowe"));

    scheduleEntries.add(
        new ScheduleEntry(
            "sch6",
            today.with(DayOfWeek.THURSDAY),
            LocalTime.of(11, 0),
            LocalTime.of(15, 0),
            employees.get(1),
            "Konserwacja sprzętu"));

    scheduleEntries.add(
        new ScheduleEntry(
            "sch7",
            today.with(DayOfWeek.FRIDAY),
            LocalTime.of(9, 0),
            LocalTime.of(17, 0),
            employees.get(4),
            "Szkolenia"));

    scheduleEntries.add(
        new ScheduleEntry(
            "sch8",
            today.with(DayOfWeek.SATURDAY),
            LocalTime.of(8, 0),
            LocalTime.of(13, 0),
            employees.get(2),
            "Opieka weekendowa"));

    scheduleEntries.add(
        new ScheduleEntry(
            "sch9",
            today.with(DayOfWeek.SUNDAY),
            LocalTime.of(10, 0),
            LocalTime.of(15, 0),
            employees.get(3),
            "Opieka weekendowa"));
  }

  public void addScheduleEntry(ScheduleEntry entry) {
    this.scheduleEntries.add(entry);
    System.out.println("Dodano nowy wpis harmonogramu: " + entry.getTaskDescription());
  }

  public void updateScheduleEntry(ScheduleEntry updatedEntry) {
    scheduleEntries.stream()
        .filter(e -> e.getId().equals(updatedEntry.getId()))
        .findFirst()
        .ifPresent(
            e -> {
              e.setDate(updatedEntry.getDate());
              e.setStartTime(updatedEntry.getStartTime());
              e.setEndTime(updatedEntry.getEndTime());
              e.setAssignedEmployee(updatedEntry.getAssignedEmployee());
              e.setTaskDescription(updatedEntry.getTaskDescription());
              System.out.println("Zaktualizowano wpis harmonogramu: " + e.getTaskDescription());
            });
  }

  public void deleteScheduleEntry(String entryId) {
    scheduleEntries.removeIf(e -> e.getId().equals(entryId));
    System.out.println("Usunięto wpis harmonogramu o ID: " + entryId);
  }
}
