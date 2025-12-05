package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.sessions.SleepingSession;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.function.Supplier;

import static ru.yandex.practicum.sleeptracker.sessions.SleepingConstants.dateTimeFormatter;


public class SleepingSessionsForPeriod implements Supplier<Long> {

    private LinkedHashSet<SleepingSession> sleepingSessions;
    private long sessions = 0;
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    public SleepingSessionsForPeriod(LinkedHashSet<SleepingSession> sleepingSessions, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        this.sleepingSessions = sleepingSessions;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }


    @Override
    public Long get() {
        return sleepingSessions.stream()
                .filter(sleepingSession -> sleepingSession.wakeDateTime.isAfter(sleepingSession.sleepDateTime))
                .filter(sleepingSession -> (sleepingSession.sleepDateTime.isAfter(fromDateTime) || sleepingSession.sleepDateTime.isEqual(fromDateTime))
                        && (sleepingSession.wakeDateTime.isBefore(toDateTime) || sleepingSession.wakeDateTime.isEqual(toDateTime)))
                .count();
    }

    @Override
    public String toString() {
        return String.format("Количество сессий за период %s - %s : %d", fromDateTime.format(dateTimeFormatter), toDateTime.format(dateTimeFormatter), get());
    }
}
