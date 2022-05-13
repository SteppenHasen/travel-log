package com.kodality.travellog;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.micronaut.serde.annotation.Serdeable;

@MappedEntity
@Serdeable
public class TravelLog {
  @Id
  @Nullable
  private String id;
  private String description, route, registrationnumber, owner;
  private String date;
  private Integer startodometer, endodometer;
  @JsonCreator
  public TravelLog(
          @JsonProperty("id") String id,
          @JsonProperty("date") String date,
          @JsonProperty("registrationnumber") String registrationnumber,
          @JsonProperty("description") String description,
          @JsonProperty("route") String route,
          @JsonProperty("owner") String owner,
          @JsonProperty("startodometer") Integer startodometer,
          @JsonProperty("endodometer") Integer endodometer) {
    this.id = id;
    this.description = description;
    this.endodometer = endodometer;
    this.startodometer = startodometer;
    this.date = date;
    this.owner = owner;
    this.registrationnumber = registrationnumber;
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
  public Integer getEndOdometer() { return endodometer; }
  public void setEndOdometer(Integer endodometer) { this.endodometer = endodometer; }
  public Integer getStartOdometer() { return startodometer; }
  public void setStartOdometer(Integer startodometer) { this.startodometer = startodometer; }
  public String getDate() { return date; }
  public void setDate(String date) { this.date = date; }
  public String getOwner() { return owner; }
  public void setOwner(String owner) { this.owner = owner; }
  public String getRegistrationNumber() { return registrationnumber; }
  public void setRegistrationNumber(String registrationnumber) { this.registrationnumber = registrationnumber; }
  public String getRoute() { return route; }
  public void setRoute(String route) { this.route = route; }
}