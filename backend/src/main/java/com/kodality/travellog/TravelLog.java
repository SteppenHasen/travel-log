package com.kodality.travellog;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

@MappedEntity
public class TravelLog {
  @Id
  private String id;

  private String description, route, registrationNumber, owner, date;

  private Integer startOdometer, endOdometer;

  public TravelLog(String id, Integer startOdometer, Integer endOdometer, String registrationNumber, String owner, String route, String description, String date) {
    this.id = id;
    this.description = description;
    this.endOdometer = endOdometer;
    this.startOdometer = startOdometer;
    this.date = date;
    this.owner = owner;
    this.registrationNumber = registrationNumber;
    this.route = route;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getEndOdometer() { return endOdometer; }

  public void setEndOdometer(Integer endOdometer) { this.endOdometer = endOdometer; }

  public Integer getStartOdometer() { return startOdometer; }

  public void setStartOdometer(Integer startOdometer) { this.startOdometer = startOdometer; }

  public String getDate() { return date; }

  public void setDate(String date) { this.date = date; }

  public String getOwner() { return owner; }

  public void setOwner(String owner) { this.owner = owner; }

  public String getRegistrationNumber() { return registrationNumber; }

  public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

  public String getRoute() { return route; }

  public void setRoute(String route) { this.route = route; }
}
