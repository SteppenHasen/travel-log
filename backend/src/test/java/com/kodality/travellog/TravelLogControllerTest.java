package com.kodality.travellog;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class TravelLogControllerTest {

    @Mock
    TravelLogService travelLogService;
    
    @Inject
    @Client("/api")
    HttpClient client;

    @BeforeEach
    void initUseCase() {
        MockitoAnnotations.openMocks(this);
        t = new TravelLogService(travelLogRepository);
    }

    @Test
    @DisplayName("Get all logs")
    public void getTravelLogs() {
        HttpRequest<String> request = HttpRequest.GET("/travel-logs");
        String body = client.toBlocking().retrieve(request);

        assertNotNull(body);
    }

    @Test
    @DisplayName("Get log with ID")
    void getTravelLogById() {
        HttpRequest<String> request = HttpRequest.GET("/1121fcb0-0bc0-4417-98ba-5c46dc5e2a86");
        String body = client.toBlocking().retrieve(request);
        assertNotNull(body);
    }

    @Test
    @DisplayName("Update log")
    void updateTravelLog() {
    }

    @Test
    @DisplayName("Save new log")
    void saveTravelLog() {
    }

    @Test
    @DisplayName("Delete log with ID")
    void deleteTravelLog() {
    }

    @Test
    @DisplayName("Get report data")
    void getTravelLogsForReport() {
    }
}