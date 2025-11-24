package ru.yandex.practicum.sleeptracker;


import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SleepTrackerAppTestClearBeforeTest extends BaseTest {

    @Test
    void shouldReturnZero_WhenNoSessions() {
        AvgSleepingSession avgSupplier = new AvgSleepingSession(sessions);
        Double average = avgSupplier.get();
        assertEquals(0.0, average);
    }

    @Test
    void shouldReturnNullWhenNoSessions() {
        MaxSleepingSession maxSleep = new MaxSleepingSession(sessions);
        Long result = maxSleep.get();
        assertNull(result);
    }

    @Test
    void get_whenEmptySessions_returnsNull() {
        MinSleepingSession minSleepingSession = new MinSleepingSession(sessions);
        Long result = minSleepingSession.get();
        assertNull(result);
    }

    @Test
    void testNoSleeplessNights() {
        fillSessions(sleepGood);
        assertEquals(0, new SleeplessNights(sessions).get(), "Ожидалось 0 бессонных ночей");
    }

    @Test
    void testSleeplessNightsEmptyFile() {
        fillSessions(sleepEmpty);
        assertEquals(0, new SleeplessNights(sessions).get(), "Ожидалось 0 бессонных ночей");
    }

    @Test
    void testAllSleeplessNights() {
        fillSessions(sleepAllBad);
        assertEquals(2, new SleeplessNights(sessions).get(), "Ожидалось 2 бессонные ночи");
    }

    @Test
    void testOneSleeplessNight() {
        fillSessions(sleepOneBad);
        assertEquals(1, new SleeplessNights(sessions).get(), "Ожидалась 1 бессонная ночь");
    }

    @Test
    void testSomeSleeplessNights() {
        fillSessions(fileName2);
        assertEquals(2, new SleeplessNights(sessions).get(), "Ожидалось 2 бессонные ночи");
    }

    @Test
    void shouldIncludeSessionsOnFirstSessionAfterMidnight() {
        fillSessions(fileName2);
        Long result = new SleepingSessionsForPeriod(sessions).apply(from3, to3);
        assertEquals(11, result);
    }

    @Test
    void shouldIncludeSessionsOnBoundaryFromMain() {
        fillSessions(fileName3);
        Long result = new SleepingSessionsForPeriod(sessions).apply(from1, to1);
        assertEquals(10, result);
    }
}