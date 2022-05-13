package com.kodality.travellog;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class TravelLogRepositoryTest {

    @Inject
    TestTravelLogRepository travelLogRepository;

    @BeforeEach
    void initUseCase() {
        List<TravelLog> travellogs = Arrays.asList(
                new TravelLog("852a751c-d99f-4eb8-aded-844ddb386ab8", "2022-12-02", "121 ABT", "going to Tartu", "Tallin-Tartu", "Baltazar", 12554, 2546),
                new TravelLog("b3204c4c-c6f0-4db2-bc32-3ba138ef1f4d", "2022-12-22", "121 ABT", "trip to Tallin zoo", "Tartu-Tallin", "Baltazar", 12554, 2546),
                new TravelLog("6c39d2da-f8d3-4a69-bc31-d3d272832215", "2022-08-15", "121 ABT", "buying a boat", "Naberegni Chelni - Odessa", "Baltazar", 12554, 2546),
                new TravelLog("1121fcb0-0bc0-4417-98ba-5c46dc5e2a86", "2022-08-08", "121 ABT", "travel in Estonia", "Pjarnu-Tartu", "Baltazar", 12554, 2546),
                new TravelLog("a40831a7-ef5f-49f6-ba54-5dfb332cff1e", "2022-01-01", "121 ABT", "gone to STO", "Tartu-Tartu", "Baltazar", 12554, 2546),
                new TravelLog("88d497db-f8b2-4c60-9580-4912bffee1eb", "2022-02-12", "532 TM", "volonteering", "Kiev-Wrotslaw", "Dawidek", 12554, 2546),
                new TravelLog("544a6512-8ef8-4966-9e71-baf6ff77f65d", "2022-07-30", "532 TM", "it was too long", "Orsk-Kotsk", "Dawidek", 12554, 2546),
                new TravelLog("4616c755-31fe-420e-ab0f-2a52dda62430", "2022-04-04", "532 TM", "just wanna go home", "Tartu-Dnepr", "Dawidek", 12554, 2546),
                new TravelLog("e81f5626-3235-4a04-a2c7-938676cb0b2a", "2022-03-10", "532 TM", "sous le siel de Paris", "Tallin-Paris", "Dawidek", 12554, 2546),
                new TravelLog("4a7fb1c4-1994-43a0-add6-27b1190ad614", "2022-11-23", "532 TM", "need to take my cat home", "Nikopol-Tartu", "Dawidek", 12554, 2546)
        );
        travellogs.forEach(log -> travelLogRepository.create(log));
    }

    @DisplayName("Get all logs from DB")
    @Test
    void all() {
        List<TravelLog> logs = travelLogRepository.all();
        assertTrue(logs.size() == 10);
    }

    @DisplayName("Create row with new log in DB")
    @Test
    void create() {
        TravelLog testLog = new TravelLog(
                "test",
                "2022-12-02",
                "121 ABT",
                "going to Tartu",
                "Tallin-Tartu",
                "Baltazar",
                12554,
                2546);
        Integer result = travelLogRepository.create(testLog);

        assertTrue(result == 1);
    }

    @DisplayName("Update log with id")
    @Test
    void update() {
        TravelLog testLog = new TravelLog(
                "b3204c4c-c6f0-4db2-bc32-3ba138ef1f4d",
                "2022-12-02",
                "121 ABT",
                "going to Tartu",
                "Tallin-Tartu",
                "Baltazar",
                12554,
                2546);
                Integer result =  travelLogRepository.update(testLog);

        assertTrue(result == 1);
    }

    @DisplayName("Find user by id")
    @Test
    void findById() {
        TravelLog log = travelLogRepository.find("b3204c4c-c6f0-4db2-bc32-3ba138ef1f4d");
        assertTrue(log != null);
    }

    @DisplayName("Find user by date")
    @Test
    void whereDate() {
        assertTrue(travelLogRepository.whereDate("2022-07-30", "2022-08-15").size() == 3);
    }

    @DisplayName("Find user by owner")
    @Test
    void whereOwner() {
        assertTrue(travelLogRepository.where("owner", "Dawidek").size() == 5);
    }

    @DisplayName("Find user by registration number")
    @Test
    void whereRegistrationNumber() {
        assertTrue(travelLogRepository.where("registrationnumber", "121 ABT").size() == 5);
    }

    @DisplayName("Destroy user by id")
    @Test
    void destroy() {
        Integer result = travelLogRepository.destroy("b3204c4c-c6f0-4db2-bc32-3ba138ef1f4d");
        assertTrue(result == 1);
    }

    @DisplayName("Find data by two criteria")
    @Test
    void whereTwoParams() {
        assertTrue(travelLogRepository
                .whereTwoParams("Dawidek", "532 TM").size() == 5);
    }

    @DisplayName("Find data by date and parameter")
    @Test
    void whereParamAndDate() {
        assertTrue(travelLogRepository
                .whereParamAndDate(
                        "owner", "Dawidek",
                        "2022-07-30", "2022-07-30").size() == 1);

        assertTrue(travelLogRepository
                .whereParamAndDate(
                        "registrationnumber", "532 TM",
                        "2022-07-30", "2022-07-30").size() == 1);
    }

    @DisplayName("Find data by three criteria")
    @Test
    void whereDateOwnerRegnumber() {
        assertTrue(travelLogRepository
                .whereDateOwnerRegnumber(
                        "Dawidek", "532 TM",
                        "2022-03-01", "2022-08-01").size() == 3);
    }

    @AfterEach
    void tearDown(){
        List<TravelLog> logs = travelLogRepository.all();
        logs.forEach(log -> travelLogRepository.destroy(log.getId()));
    }
}