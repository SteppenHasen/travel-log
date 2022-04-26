package com.kodality.travellog;

import java.util.List;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class TravelLogService {

  private TravelLogRepository travelLogRepository;

  public List<TravelLog> getTravelLogs() {
    return travelLogRepository.all();
  }

  public TravelLog getTravelLogById(String id) {
    return travelLogRepository.findById(id);
  }

  public void saveTravelLog(TravelLog log) {
    log.setId(UUID.randomUUID().toString());
    travelLogRepository.save(log);
  }

  public void updateLog(TravelLog log) {
    for (TravelLog l : getTravelLogs()) {
      if (log.getId() == l.getId()) {
        travelLogRepository.update(log);
      }
    }
  }

  public void removeLog(String id) {
    travelLogRepository.destroy(id);
  }

}
