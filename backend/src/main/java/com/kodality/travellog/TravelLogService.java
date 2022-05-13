package com.kodality.travellog;

import java.util.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;

@Singleton
public class TravelLogService {
  @Inject
  TravelLogRepository travelLogRepository;

  public TravelLogService(TravelLogRepository travelLogRepository) {this.travelLogRepository = travelLogRepository;}

  public List<TravelLog> getTravelLogs() {
    return travelLogRepository.all();
  }

  public TravelLog getTravelLogById(String id) {
    if (id == null || id == "" || id == " ") {
      throw new IllegalArgumentException("Invalid id");
    }
    return travelLogRepository.find(id);
  }

  public TravelLog saveTravelLog(TravelLog log) {
    log.setId(UUID.randomUUID().toString());
    if (
            log.getOwner() == "" || log.getOwner() == null ||
            log.getRoute() == "" || log.getRoute() == null ||
            log.getEndOdometer() == null ||
            log.getStartOdometer() == null ||
            log.getDescription() == "" || log.getDescription() == null ||
            log.getRegistrationNumber() == "" || log.getRegistrationNumber() == null ||
            log.getDate() == "" || log.getDate() == null) {
      throw new IllegalArgumentException("Invalid travellog data");
    } else if (!log.getDate().matches("\\d{4}-\\d{2}-\\d{2}")) {
      throw new IllegalArgumentException("Date don't match YYYY-MM-DD");
    } else if (log.getStartOdometer() > log.getEndOdometer()) {
      throw new IllegalArgumentException("Initial odometer value is more than end value");
    }

    travelLogRepository.create(log);
    return log;
  }

  public int updateLog(TravelLog log) {
    if (
            log.getOwner() == "" || log.getOwner() == null ||
            log.getRoute() == "" || log.getRoute() == null ||
            log.getEndOdometer() == null ||
            log.getStartOdometer() == null ||
            log.getDescription() == "" || log.getDescription() == null ||
            log.getRegistrationNumber() == "" || log.getRegistrationNumber() == null ||
            log.getDate() == "" || log.getDate() == null) {
      throw new IllegalArgumentException("Invalid travellog data");
    } else if (!log.getDate().matches("\\d{4}-\\d{2}-\\d{2}")) {
      throw new IllegalArgumentException("Date don't match YYYY-MM-DD");
    } else if (log.getStartOdometer() > log.getEndOdometer()) {
      throw new IllegalArgumentException("Initial odometer value is more than end value");
    }

    int result = travelLogRepository.update(log);

    if (result == 0) {
      throw new IllegalArgumentException("Id don't match any log in DB");
    }

    return result;
  }

  public void removeLog(String id) {
    if (id == null || id == "" || id == " ") {
      throw new IllegalArgumentException("Invalid id");
    }

    travelLogRepository.destroy(id);
  }

  public Object[] getTravelLogsReport(TravelLogController.SearchCriteria searchCriteria) {
    if (searchCriteria.getBeginDate() == "" &&
        searchCriteria.getEndDate() == "" &&
        searchCriteria.getRegistrationnumber() == "" &&
        searchCriteria.getOwner() == "") {
      throw new NullPointerException("No search criteria");
    } else if (searchCriteria.getBeginDate() == "" &&
            searchCriteria.getEndDate() != "") {
      throw new NullPointerException("No begin date");
    } else if (searchCriteria.getBeginDate() != "" &&
            searchCriteria.getEndDate() == "") {
      throw new NullPointerException("No end date");
    }

    List<TravelLog> logs = new ArrayList<>();

    if (searchCriteria.getOwner() != "" &&
        searchCriteria.getBeginDate() == "" &&
        searchCriteria.getRegistrationnumber() == "" ) {
      logs.addAll(travelLogRepository.where("owner", searchCriteria.getOwner()));
    } else if (searchCriteria.getOwner() == "" &&
            searchCriteria.getBeginDate() == "" &&
            searchCriteria.getRegistrationnumber() != "") {
      logs.addAll(travelLogRepository.where("registrationnumber", searchCriteria.getRegistrationnumber()));
    } else if (searchCriteria.getOwner() == "" &&
            searchCriteria.getRegistrationnumber() == "" &&
            searchCriteria.getBeginDate() != "" &&
            searchCriteria.getEndDate() != "" ) {
      logs.addAll(travelLogRepository
              .whereDate(searchCriteria.getBeginDate(), searchCriteria.getEndDate()));
    } else if (searchCriteria.getOwner() != "" &&
            searchCriteria.getBeginDate() == "" &&
            searchCriteria.getRegistrationnumber() != "" ) {
      logs.addAll(travelLogRepository
              .whereTwoParams(searchCriteria.getOwner(), searchCriteria.getRegistrationnumber()));
    } else if (searchCriteria.getOwner() != "" &&
            searchCriteria.getBeginDate() != "" &&
            searchCriteria.getEndDate() != "" &&
            searchCriteria.getRegistrationnumber() == "" ) {
      logs.addAll(travelLogRepository
              .whereParamAndDate("owner", searchCriteria.getOwner(), searchCriteria.getBeginDate(), searchCriteria.getEndDate()));
    } else if (searchCriteria.getOwner() == "" &&
            searchCriteria.getBeginDate() != "" &&
            searchCriteria.getEndDate() != "" &&
            searchCriteria.getRegistrationnumber() != "" ) {
      logs.addAll(travelLogRepository
              .whereParamAndDate("registrationnumber", searchCriteria.getRegistrationnumber(), searchCriteria.getBeginDate(), searchCriteria.getEndDate()));
    } else {
      logs.addAll(travelLogRepository
              .whereDateOwnerRegnumber(searchCriteria.getOwner(), searchCriteria.getRegistrationnumber(), searchCriteria.getBeginDate(), searchCriteria.getEndDate()));
    }

    return new Object[]{sortTravelLogs(logs), getTravelLength(logs)};
  }

  public Map<Object, List<TravelLog>> sortTravelLogs(List<TravelLog> logs) {
    Map<Object, List<TravelLog>> sortedByYearMonth = logs.stream().collect(Collectors.groupingBy(e ->
            e.getDate().substring(0, 7))
    );

    for (Map.Entry<Object, List<TravelLog>> list : sortedByYearMonth.entrySet()) {
      list.getValue().sort(Comparator.comparing(TravelLog::getStartOdometer));
    }

    return sortedByYearMonth;
  }

  public int getTravelLength(List<TravelLog> list) {
    int result = 0;

    for (TravelLog log : list) {
      result += log.getEndOdometer() - log.getStartOdometer();
    }

    return result;
  }
}