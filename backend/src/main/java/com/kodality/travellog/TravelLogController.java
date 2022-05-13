package com.kodality.travellog;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.validation.Validated;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

@Validated
@Controller("/api")
public class TravelLogController {
  @Inject
  TravelLogService travelLogService;
  public TravelLogController(TravelLogService travelLogService) {
    this.travelLogService = travelLogService;
  }

  ObjectMapper objectMapper = new ObjectMapper();
  @Serdeable
  public static class SearchCriteria {

    @Nullable
    private String beginDate, endDate, registrationnumber, owner;

    public SearchCriteria(
            @JsonProperty("registrationnumber") String registrationnumber,
            @JsonProperty("owner") String owner,
            @JsonProperty("beginDate") String beginDate,
            @JsonProperty("endDate") String endDate
    ) {
      this.beginDate = beginDate;
      this.endDate = endDate;
      this.owner = owner;
      this.registrationnumber = registrationnumber;
    }

    @Nullable
    public String getBeginDate() {
      return beginDate;
    }

    @Nullable
    public String getEndDate() {
      return endDate;
    }

    @Nullable
    public String getOwner() {
      return owner;
    }

    @Nullable
    public String getRegistrationnumber() {
      return registrationnumber;
    }

  }

  @Get("/travel-logs")
  public HttpResponse getTravelLogs() throws JsonProcessingException {
    return HttpResponse.ok().body(objectMapper.writeValueAsString(travelLogService.getTravelLogs()));
  }

  @Get("/{id}")
  public HttpResponse getTravelLogById (String id) throws JsonProcessingException {
    return HttpResponse.ok().body(objectMapper.writeValueAsString(travelLogService.getTravelLogById(id)));
  }

  @Post("/{id}:patch")
  public int updateTravelLog (@Body TravelLog travelLog) {
    return travelLogService.updateLog(travelLog);
  }

  @Post("/save")
  public TravelLog saveTravelLog (@Body TravelLog log) { return travelLogService.saveTravelLog(log); }

  @Delete("/{id}:destroy")
  public HttpResponse deleteTravelLog (String id) {
    travelLogService.removeLog(id);
    return HttpResponse.ok();
  }

  @Post("/report")
  @Produces(MediaType.APPLICATION_JSON)
  public String getTravelLogsForReport(@Body SearchCriteria searchCriteria) throws JsonProcessingException {
    return objectMapper.writeValueAsString(travelLogService.getTravelLogsReport(searchCriteria));
  }
}