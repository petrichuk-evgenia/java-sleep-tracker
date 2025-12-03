package ru.yandex.practicum.sleeptracker;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.enums.SleepingQuality;
import ru.yandex.practicum.sleeptracker.functions.*;
import ru.yandex.practicum.sleeptracker.sessions.SleepingSession;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest extends BaseTest {

    @BeforeEach
    public void setUp() {
        fillSessions(fileName);
    }

    @Test
    public void shouldReturnZeroWhenNoSessionsInRange() {
        Long result = new SleepingSessionsForPeriod(sessions).apply(from2, to2);
        assertEquals(0L, result);
    }

    @Test
    public void shouldIncludeSessionsOnBoundary() {
        Long result = new SleepingSessionsForPeriod(sessions).apply(from1, to1);
        assertEquals(10, result);
    }

    @Test
    public void shouldReturnAverageDuration_WhenSessionsExist() {
        AvgSleepingSession avgSupplier = new AvgSleepingSession(sessions);
        Double average = avgSupplier.get();
        assertEquals(370.625, average);
    }

    @Test
    public void shouldReturnExactDuration_WhenOneSession() {
        SleepingSession s = sessions.getFirst();
        sessions.clear();
        sessions.add(s);
        AvgSleepingSession avgSupplier = new AvgSleepingSession(sessions);
        Double average = avgSupplier.get();
        assertEquals(495.0, average);
    }

    @Test
    public void get_shouldReturnCountOfBadQualitySessions() {
        assertEquals(2L, new BadQualitySleepingSession(sessions).get(), "Должно быть 2 сессии со сном плохого качества");
    }

    @Test
    public void get_whenNoBadSessions_shouldReturnZero() {
        List<SleepingSession> s = sessions.stream()
                .filter(sleepingSession -> !sleepingSession.quality.equals(SleepingQuality.BAD))
                .collect(Collectors.toList());
        assertEquals(0L, s.stream().filter(sleepingSession -> sleepingSession.quality.equals(SleepingQuality.BAD)).count(), "Должно быть 0 сессий со сном плохого качества");
    }

    @Test
    public void get_whenAllSessionsAreBad_shouldReturnTotalCount() {
        List<SleepingSession> s = sessions.stream()
                .filter(sleepingSession -> sleepingSession.quality.equals(SleepingQuality.BAD))
                .collect(Collectors.toList());
        assertEquals(2, s.size());
        assertEquals(2L, s.stream().filter(sleepingSession -> sleepingSession.quality.equals(SleepingQuality.BAD)).count(), "Должно быть 2 (все) сессии со сном плохого качества");
    }

    @Test
    public void shouldReturnMaxDurationWhenSessionsArePresent() {
        Long result = new MaxSleepingSession(sessions).get();
        assertEquals(650, result); // 10 часов = 600 минут
    }

    @Test
    public void shouldReturnCorrectValueForSingleSession() {
        SleepingSession s = sessions.getFirst();
        sessions.clear();
        sessions.add(s);
        MaxSleepingSession maxSleep = new MaxSleepingSession(sessions);
        Long result = maxSleep.get();
        assertEquals(495, result); // 7 * 60 = 420 минут
    }

    @Test
    public void get_whenSessionsExist_returnsMinDurationInMinutes() {
        MinSleepingSession minSleepingSession = new MinSleepingSession(sessions);
        Long result = minSleepingSession.get();
        assertEquals(45L, result);
    }

    @Test
    public void get_whenSingleSession_returnsItsDuration() {
        SleepingSession s = sessions.getFirst();
        sessions.clear();
        sessions.add(s);
        MinSleepingSession minSleepingSession = new MinSleepingSession(sessions);
        Long result = minSleepingSession.get();
        assertEquals(495, result);
    }

    @Test
    public void testSomeSleeplessNights() {
        assertEquals(2, new SleeplessNights(sessions).get(), "Ожидалось 2 бессонные ночи");
    }
}