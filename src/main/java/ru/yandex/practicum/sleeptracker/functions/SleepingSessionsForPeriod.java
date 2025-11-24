package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.function.BiFunction;


public class SleepingSessionsForPeriod implements BiFunction<LocalDateTime, LocalDateTime, Long> {

    private LinkedHashSet<SleepingSession> sleepingSessions;
    private long sessions = 0;

    public SleepingSessionsForPeriod(LinkedHashSet<SleepingSession> sleepingSessions) {
        this.sleepingSessions = sleepingSessions;
    }


    @Override
    public Long apply(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        sessions = sleepingSessions.stream()
                .filter(sleepingSession -> sleepingSession.wakeDateTime.isAfter(sleepingSession.sleepDateTime))
                .filter(sleepingSession -> (sleepingSession.sleepDateTime.isAfter(fromDateTime) || sleepingSession.sleepDateTime.isEqual(fromDateTime))
                        && (sleepingSession.wakeDateTime.isBefore(toDateTime) || sleepingSession.wakeDateTime.isEqual(toDateTime)))
                .count();

        return sessions;
    }
}
