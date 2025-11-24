package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepingQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class BadQualitySleepingSession implements Supplier<Long> {

    private LinkedHashSet<SleepingSession> sleepingSessions;

    public BadQualitySleepingSession(LinkedHashSet<SleepingSession> sleepingSessions) {
        this.sleepingSessions = sleepingSessions;
    }

    @Override
    public Long get() {
        return sleepingSessions.stream()
                .filter(sleepingSession -> sleepingSession.quality.equals(SleepingQuality.BAD))
                .count();
    }
}
