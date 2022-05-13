package com.kodality.travellog;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

import javax.inject.Inject;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class TravelLogServiceTest {

    @Inject
    TravelLogService travelLogService;
    @Mock
    TravelLogRepository travelLogRepository;

    @BeforeEach
    void initUseCase() {
        MockitoAnnotations.openMocks(this);
        travelLogService = new TravelLogService(travelLogRepository);
    }

    @DisplayName("Get all logs")
    @Test
    void getTravelLogs() {
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

        when(travelLogRepository.all()).thenReturn(travellogs);

        assertEquals(10, travelLogService.getTravelLogs().size());
    }

    @DisplayName("Get travellog by ID")
    @Test
    void getTravelLogById() {
        TravelLog travellog = new TravelLog("852a751c-d99f-4eb8-aded-844ddb386ab8", "2022-12-02", "121 ABT", "going to Tartu", "Tallin-Tartu", "Baltazar", 12554, 2546);

        when(travelLogRepository.find("852a751c-d99f-4eb8-aded-844ddb386ab8")).thenReturn(travellog);

        TravelLog result = travelLogService.getTravelLogById("852a751c-d99f-4eb8-aded-844ddb386ab8");

        assertEquals(travellog.getId(),result.getId());
    }

    @DisplayName("Save new travellog")
    @Test
    void saveTravelLog() {
        TravelLog travellog = new TravelLog("", "2022-12-02", "121 ABT", "going to Tartu", "Tallin-Tartu", "Baltazar", 1554, 2546);

        when(travelLogRepository.create(travellog)).thenReturn(1);

        assertNotSame("", travelLogService.saveTravelLog(travellog).getId());
    }

    @DisplayName("Update new travellog")
    @Test
    void updateLog() {
        TravelLog travellog = new TravelLog("852a751c-d99f-4eb8-aded-844ddb386ab8", "2022-12-02", "121 ABT", "going to Tartu", "Tallin-Tartu", "Baltazar", 1554, 2546);

        when(travelLogRepository.update(travellog)).thenReturn(1);

        assertEquals(1, travelLogService.updateLog(travellog));
    }

    @DisplayName("Get travellogs report for one search parameter")
    @Test
    void getTravelLogsReportForOneParameter() {
        TravelLog l1 = new TravelLog("88d497db-f8b2-4c60-9580-4912bffee1eb", "2022-02-12", "532 TM", "volonteering", "Kiev-Wrotslaw", "Dawidek", 1554, 2546);
        TravelLog l2 = new TravelLog("544a6512-8ef8-4966-9e71-baf6ff77f65d", "2022-07-30", "532 TM", "it was too long", "Orsk-Kotsk", "Dawidek", 1554, 2546);

        List<TravelLog> travellogs = new ArrayList<>();
        travellogs.add(l1);
        travellogs.add(l2);

        Map<Object, List<TravelLog>> result = new HashMap<>();
        List<TravelLog> list1 = new ArrayList<>();
        list1.add(l1);
        List<TravelLog> list2 = new ArrayList<>();
        list2.add(l2);
        result.put("2022-02", list1);
        result.put("2022-07", list2);

        TravelLogController.SearchCriteria criteria = new TravelLogController.SearchCriteria("", "Dawidek", "", "");

        when(travelLogRepository.where("owner", "Dawidek")).thenReturn(travellogs);

        Object[] expected = travelLogService.getTravelLogsReport(criteria);

        assertEquals(2, expected.length);
        assertEquals(result, expected[0]);
    }

    @DisplayName("Get travellogs report with only date")
    @Test
    void getTravelLogsReport() {
        TravelLog l1 = new TravelLog("88d497db-f8b2-4c60-9580-4912bffee1eb", "2022-12-12", "532 TM", "volonteering", "Kiev-Wrotslaw", "Dawidek", 1554, 2546);
        TravelLog l2 = new TravelLog("544a6512-8ef8-4966-9e71-baf6ff77f65d", "2022-12-30", "532 TM", "it was too long", "Orsk-Kotsk", "Dawidek", 1554, 2546);
        TravelLog l3 = new TravelLog("4616c755-31fe-420e-ab0f-2a52dda62430", "2022-08-04", "532 TM", "just wanna go home", "Tartu-Dnepr", "Dawidek", 1554, 2546);
        TravelLog l4 = new TravelLog("e81f5626-3235-4a04-a2c7-938676cb0b2a", "2022-08-10", "532 TM", "sous le siel de Paris", "Tallin-Paris", "Dawidek", 1554, 2546);

        List<TravelLog> travellogs = new ArrayList<>();
        travellogs.add(l1);
        travellogs.add(l2);
        travellogs.add(l3);
        travellogs.add(l4);

        List<TravelLog> firstcollection = new ArrayList<>();
        firstcollection.add(l1);
        firstcollection.add(l2);
        List<TravelLog> secondcollection = new ArrayList<>();
        secondcollection.add(l3);
        secondcollection.add(l4);

        Map<Object, List<TravelLog>> result = new HashMap<>();
        result.put("2022-12", firstcollection);
        result.put("2022-08", secondcollection);

        TravelLogController.SearchCriteria criteria = new TravelLogController.SearchCriteria("", "", "2022-02-12", "2022-12-12");

        when(travelLogRepository.whereDate("2022-02-12", "2022-12-12")).thenReturn(travellogs);

        Object[] expected = travelLogService.getTravelLogsReport(criteria);

        assertEquals(2, expected.length);
        assertEquals(result, expected[0]);
    }

    @DisplayName("Get travellogs report with two parameters")
    @Test
    void getTravelLogsReportWithTwoParams() {
        TravelLog l1 = new TravelLog("88d497db-f8b2-4c60-9580-4912bffee1eb", "2022-12-12", "532 TM", "volonteering", "Kiev-Wrotslaw", "Dawidek", 1554, 2546);
        TravelLog l2 = new TravelLog("544a6512-8ef8-4966-9e71-baf6ff77f65d", "2022-12-30", "532 TM", "it was too long", "Orsk-Kotsk", "Dawidek", 1554, 2546);
        TravelLog l3 = new TravelLog("4616c755-31fe-420e-ab0f-2a52dda62430", "2022-08-04", "532 TM", "just wanna go home", "Tartu-Dnepr", "Dawidek", 1554, 2546);
        TravelLog l4 = new TravelLog("e81f5626-3235-4a04-a2c7-938676cb0b2a", "2022-08-10", "532 TM", "sous le siel de Paris", "Tallin-Paris", "Dawidek", 1554, 2546);

        List<TravelLog> travellogs = new ArrayList<>();
        travellogs.add(l1);
        travellogs.add(l2);
        travellogs.add(l3);
        travellogs.add(l4);

        List<TravelLog> firstcollection = new ArrayList<>();
        firstcollection.add(l1);
        firstcollection.add(l2);
        List<TravelLog> secondcollection = new ArrayList<>();
        secondcollection.add(l3);
        secondcollection.add(l4);

        Map<Object, List<TravelLog>> result = new HashMap<>();
        result.put("2022-12", firstcollection);
        result.put("2022-08", secondcollection);

        TravelLogController.SearchCriteria criteria = new TravelLogController.SearchCriteria("", "Dawidek", "2022-02-12", "2022-12-12");

        when(travelLogRepository.whereParamAndDate("owner", "Dawidek", "2022-02-12", "2022-12-12")).thenReturn(travellogs);

        Object[] expected = travelLogService.getTravelLogsReport(criteria);

        assertEquals(2, expected.length);
        assertEquals(result, expected[0]);
    }

    @DisplayName("Get travellogs report with three parameters")
    @Test
    void getTravelLogsReportWithThreeParams() {
        TravelLog l1 = new TravelLog("88d497db-f8b2-4c60-9580-4912bffee1eb", "2022-12-12", "532 TM", "volonteering", "Kiev-Wrotslaw", "Dawidek", 1554, 2546);
        TravelLog l2 = new TravelLog("544a6512-8ef8-4966-9e71-baf6ff77f65d", "2022-12-30", "532 TM", "it was too long", "Orsk-Kotsk", "Dawidek", 1554, 2546);
        TravelLog l3 = new TravelLog("4616c755-31fe-420e-ab0f-2a52dda62430", "2022-08-04", "532 TM", "just wanna go home", "Tartu-Dnepr", "Dawidek", 1554, 2546);
        TravelLog l4 = new TravelLog("e81f5626-3235-4a04-a2c7-938676cb0b2a", "2022-08-10", "532 TM", "sous le siel de Paris", "Tallin-Paris", "Dawidek", 1554, 2546);

        List<TravelLog> travellogs = new ArrayList<>();
        travellogs.add(l1);
        travellogs.add(l2);
        travellogs.add(l3);
        travellogs.add(l4);

        List<TravelLog> firstcollection = new ArrayList<>();
        firstcollection.add(l1);
        firstcollection.add(l2);
        List<TravelLog> secondcollection = new ArrayList<>();
        secondcollection.add(l3);
        secondcollection.add(l4);

        Map<Object, List<TravelLog>> result = new HashMap<>();
        result.put("2022-12", firstcollection);
        result.put("2022-08", secondcollection);

        TravelLogController.SearchCriteria criteria = new TravelLogController.SearchCriteria("532 TM", "Dawidek", "2022-02-12", "2022-12-12");

        when(travelLogRepository.whereDateOwnerRegnumber("Dawidek", "532 TM", "2022-02-12", "2022-12-12")).thenReturn(travellogs);

        Object[] expected = travelLogService.getTravelLogsReport(criteria);

        assertEquals(2, expected.length);
        assertEquals(result, expected[0]);
    }

    @DisplayName("When id is null")
    @Test
    void getTravelLogWithNullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            travelLogService.getTravelLogById(null);
        });

        assertTrue(exception.getMessage().contains("Invalid id"));
    }

    @DisplayName("When id is empty string")
    @Test
    void getTravelLogBWithEmptyStringId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            travelLogService.getTravelLogById("");
        });

        assertTrue(exception.getMessage().contains("Invalid id"));
    }

    @DisplayName("When id is string with one space only")
    @Test
    void getTravelLogBWithOneSpaceId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            travelLogService.getTravelLogById(" ");
        });

        assertTrue(exception.getMessage().contains("Invalid id"));
    }

    @DisplayName("When travellog to save has empty arguments")
    @Test
    void saveTravelLogWithEmptyArgs() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            travelLogService.saveTravelLog(
                    new TravelLog("852a751c-d99f-4eb8-aded-844ddb386ab8",
                            "2021-12-12", "",
                            "going to Tartu", "Tallin-Tartu",
                            "Baltazar", 1554, 2546));
        });

        assertTrue(exception.getMessage().contains("Invalid travellog data"));
    }

    @DisplayName("When travellog to save has wrong date")
    @Test
    void saveTravelLogWithDateDontMatchRegex() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            travelLogService.saveTravelLog(
                    new TravelLog("852a751c-d99f-4eb8-aded-844ddb386ab8",
                            "udfgh", "121 ABT",
                            "going to Tartu", "Tallin-Tartu",
                            "Baltazar", 1554, 2546));
        });

        assertTrue(exception.getMessage().contains("Date don't match YYYY-MM-DD"));
    }

    @DisplayName("When travellog to save has has bad odometer values")
    @Test
    void saveTravelLogWithBadOdometerValues() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            travelLogService.saveTravelLog(
                    new TravelLog("852a751c-d99f-4eb8-aded-844ddb386ab8",
                            "2021-01-01", "121 ABT",
                            "going to Tartu", "Tallin-Tartu",
                            "Baltazar", 12554, 2546));
        });

        assertTrue(exception.getMessage().contains("Initial odometer value is more than end value"));
    }

    @DisplayName("When travellog to update has empty arguments")
    @Test
    void updateLogWithEmptyArgs() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            travelLogService.updateLog(
                    new TravelLog("852a751c-d99f-4eb8-aded-844ddb386ab8",
                            "", null,
                            "going to Tartu", "Tallin-Tartu",
                            "Baltazar", null, 2546));
        });

        assertTrue(exception.getMessage().contains("Invalid travellog data"));
    }

    @DisplayName("When travellog to update has no match ID")
    @Test
    void updateLogWithNoMatchId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            travelLogService.updateLog(
                    new TravelLog("no-match-id",
                            "2021-11-11", "121 ABT",
                            "going to Tartu", "Tallin-Tartu",
                            "Baltazar", 1256, 2546));
        });

        assertTrue(exception.getMessage().contains("Id don't match any log in DB"));
    }
    @DisplayName("When travellog to update has wrong date")
    @Test
    void updateTravelLogWithDateDontMatchRegex() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            travelLogService.updateLog(
                    new TravelLog("852a751c-d99f-4eb8-aded-844ddb386ab8",
                            "udfgh", "121 ABT",
                            "going to Tartu", "Tallin-Tartu",
                            "Baltazar", 1554, 2546));
        });

        assertTrue(exception.getMessage().contains("Date don't match YYYY-MM-DD"));
    }
    @DisplayName("When travellog to update has has bad odometer values")
    @Test
    void updateTravelLogWithBadOdometerValues() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            travelLogService.updateLog(
                    new TravelLog("852a751c-d99f-4eb8-aded-844ddb386ab8",
                            "2021-01-01", "121 ABT",
                            "going to Tartu", "Tallin-Tartu",
                            "Baltazar", 12554, 2546));
        });

        assertTrue(exception.getMessage().contains("Initial odometer value is more than end value"));
    }

    @DisplayName("When cant remove because wrong ID")
    @Test
    void removeLog() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            travelLogService.removeLog(null);
        });

        assertTrue(exception.getMessage().contains("Invalid id"));
    }

    @DisplayName("When no any search criteria")
    @Test
    void getTravelLogsReportWithNoSearch() {
        TravelLogController.SearchCriteria criteria = new TravelLogController.SearchCriteria("", "", "", "");

        Exception exception = assertThrows(NullPointerException.class, () -> {
            travelLogService.getTravelLogsReport(criteria);
        });

        assertTrue(exception.getMessage().contains("No search criteria"));
    }

    @DisplayName("Counting travel length")
    @Test
    void getTravelLength() {
        List<TravelLog> list = new ArrayList<>();
        list.add(new TravelLog("852a751c-d99f-4eb8-aded-844ddb386ab8", "2022-12-02", "121 ABT", "going to Tartu", "Tallin-Tartu", "Baltazar", 2546, 12554));
        list.add(new TravelLog("a40831a7-ef5f-49f6-ba54-5dfb332cff1e", "2022-01-01", "121 ABT", "gone to STO", "Tartu-Tartu", "Baltazar", 2546, 12554));
        list.add(new TravelLog("88d497db-f8b2-4c60-9580-4912bffee1eb", "2022-02-12", "532 TM", "volonteering", "Kiev-Wrotslaw", "Dawidek", 2546, 12554));
        list.add(new TravelLog("544a6512-8ef8-4966-9e71-baf6ff77f65d", "2022-07-30", "532 TM", "it was too long", "Orsk-Kotsk", "Dawidek", 2546, 12554));
        list.add(new TravelLog("4a7fb1c4-1994-43a0-add6-27b1190ad614", "2022-11-23", "532 TM", "need to take my cat home", "Nikopol-Tartu", "Dawidek", 2546, 12554));

        assertEquals(50040, travelLogService.getTravelLength(list));
    }

    @DisplayName("Making sorted map of travellogs")
    @Test
    void sortTravelLogs() {
        TravelLog log1 = new TravelLog("852a751c-d99f-4eb8-aded-844ddb386ab8", "2021-12-02", "121 ABT", "going to Tartu", "Tallin-Tartu", "Baltazar", 2546, 12554);
        TravelLog log2 = new TravelLog("a40831a7-ef5f-49f6-ba54-5dfb332cff1e", "2021-01-01", "121 ABT", "gone to STO", "Tartu-Tartu", "Baltazar", 2546, 12554);
        TravelLog log3 = new TravelLog("88d497db-f8b2-4c60-9580-4912bffee1eb", "2022-02-12", "532 TM", "volonteering", "Kiev-Wrotslaw", "Dawidek", 2546, 12554);
        TravelLog log4 = new TravelLog("544a6512-8ef8-4966-9e71-baf6ff77f65d", "2022-02-30", "532 TM", "it was too long", "Orsk-Kotsk", "Dawidek", 2000, 12554);
        TravelLog log5 = new TravelLog("4a7fb1c4-1994-43a0-add6-27b1190ad614", "2022-11-23", "532 TM", "need to take my cat home", "Nikopol-Tartu", "Dawidek", 2546, 12554);


        List<TravelLog> list = new ArrayList<>();
        list.add(log1);
        list.add(log2);
        list.add(log3);
        list.add(log4);
        list.add(log5);

        List<TravelLog> l1 = new ArrayList<>();
        l1.add(log1);
        List<TravelLog> l2 = new ArrayList<>();
        l2.add(log2);
        List<TravelLog> l3 = new ArrayList<>();
        l3.add(log4);
        l3.add(log3);
        List<TravelLog> l4 = new ArrayList<>();
        l4.add(log5);

        Map<Object, List<TravelLog>> expected = new HashMap<>();
        expected.put("2021-01", l2);
        expected.put("2021-12", l1);
        expected.put("2022-11", l4);
        expected.put("2022-02", l3);

        Map<Object, List<TravelLog>> result = travelLogService.sortTravelLogs(list);

        assertEquals(result, expected);
    }
}