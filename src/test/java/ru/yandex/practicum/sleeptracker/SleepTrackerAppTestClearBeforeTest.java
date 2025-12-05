package ru.yandex.practicum.sleeptracker;


import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SleepTrackerAppTestClearBeforeTest extends BaseTest {

    @Test
    public void shouldReturnZero_WhenNoSessions() {
        AvgSleepingSession avgSupplier = new AvgSleepingSession(sessions);
        Double average = avgSupplier.get();
        assertEquals(0.0, average);
    }

    @Test
    public void shouldReturnNullWhenNoSessions() {
        MaxSleepingSession maxSleep = new MaxSleepingSession(sessions);
        Long result = maxSleep.get();
        assertNull(result);
    }

    @Test
    public void get_whenEmptySessions_returnsNull() {
        MinSleepingSession minSleepingSession = new MinSleepingSession(sessions);
        Long result = minSleepingSession.get();
        assertNull(result);
    }

    @Test
    public void testNoSleeplessNights() {
        fillSessions(sleepGood);
        assertEquals(0, new SleeplessNights(sessions).get(), "Ожидалось 0 бессонных ночей");
    }

    @Test
    public void testSleeplessNightsEmptyFile() {
        fillSessions(sleepEmpty);
        assertEquals(0, new SleeplessNights(sessions).get(), "Ожидалось 0 бессонных ночей");
    }

    @Test
    public void testAllSleeplessNights() {
        fillSessions(sleepAllBad);
        assertEquals(2, new SleeplessNights(sessions).get(), "Ожидалось 2 бессонные ночи");
    }

    @Test
    public void testOneSleeplessNight() {
        fillSessions(sleepOneBad);
        assertEquals(1, new SleeplessNights(sessions).get(), "Ожидалась 1 бессонная ночь");
    }

    @Test
    public void testSomeSleeplessNights() {
        fillSessions(fileName2);
        assertEquals(2, new SleeplessNights(sessions).get(), "Ожидалось 2 бессонные ночи");
    }

    @Test
    public void shouldIncludeSessionsOnFirstSessionAfterMidnight() {
        fillSessions(fileName2);
        Long result = new SleepingSessionsForPeriod(sessions, from3, to3).get();
        assertEquals(11, result);
    }

    @Test
    public void shouldIncludeSessionsOnBoundaryFromMain() {
        fillSessions(fileName3);
        Long result = new SleepingSessionsForPeriod(sessions, from1, to1).get();
        assertEquals(10, result);
    }
}