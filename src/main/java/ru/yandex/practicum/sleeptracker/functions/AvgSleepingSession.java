package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.sessions.SleepingSession;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class AvgSleepingSession implements Supplier<Double> {

    private LinkedHashSet<SleepingSession> sleepingSessions;

    public AvgSleepingSession(LinkedHashSet<SleepingSession> sleepingSessions) {
        this.sleepingSessions = sleepingSessions;
    }

    @Override
    public Double get() {
        return sleepingSessions.stream()
                .mapToLong(sleepingSession -> Duration.between(sleepingSession.sleepDateTime, sleepingSession.wakeDateTime).toMinutes())
                .average()
                .orElse(0.0);
    }
}
