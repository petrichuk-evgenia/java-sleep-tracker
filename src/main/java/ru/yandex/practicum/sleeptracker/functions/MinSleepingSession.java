package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.sessions.SleepingSession;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class MinSleepingSession implements Supplier<Long> {

    private LinkedHashSet<SleepingSession> sleepingSessions;

    public MinSleepingSession(LinkedHashSet<SleepingSession> sleepingSessions) {
        this.sleepingSessions = sleepingSessions;
    }

    @Override
    public Long get() {
        return sleepingSessions.stream()
                .map(sleepingSession -> Duration.between(sleepingSession.sleepDateTime, sleepingSession.wakeDateTime).toMinutes())
                .min(Long::compare)
                .orElse(null);
    }

    @Override
    public String toString() {
        return String.format("Минимальная продолжительность сессии: %d минут", get());
    }
}
