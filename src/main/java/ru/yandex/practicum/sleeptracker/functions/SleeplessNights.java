package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SleeplessNights implements Supplier<Integer> {

    private LinkedHashSet<SleepingSession> sleepingSessions;

    public SleeplessNights(LinkedHashSet<SleepingSession> sleepingSessions) {
        this.sleepingSessions = sleepingSessions;
    }

    @Override
    public Integer get() {
        List<SleepingSession> list = new ArrayList<>();
        sleepingSessions.forEach(sleepingSession -> {
            LocalDateTime from = LocalDateTime.of(sleepingSession.sleepDateTime.toLocalDate(), LocalTime.of(23, 59));
            LocalDateTime to = from.plusHours(6).plusMinutes(1);
            //случай за границами
            if (sleepingSession.sleepDateTime.isBefore(from) && sleepingSession.wakeDateTime.isAfter(to)) {
                list.add(sleepingSession);
            }
            //какой-то случай на границе
            if ((sleepingSession.sleepDateTime.isBefore(from) && sleepingSession.wakeDateTime.isEqual(to))
                    || (sleepingSession.sleepDateTime.isEqual(from) && sleepingSession.wakeDateTime.isAfter(to))) {
                list.add(sleepingSession);
            }
            //какой-то случай внутри границы
            if ((sleepingSession.sleepDateTime.isBefore(from)
                    && sleepingSession.wakeDateTime.isBefore(to)
                    && sleepingSession.wakeDateTime.isAfter(from))
                    || (sleepingSession.sleepDateTime.isAfter(from)
                        && sleepingSession.wakeDateTime.isAfter(to))) {
                list.add(sleepingSession);
            }
            //какой-то случай внутри границы и на границе
            if ((sleepingSession.sleepDateTime.isEqual(from)
                    && sleepingSession.wakeDateTime.isBefore(to)
                    && sleepingSession.wakeDateTime.isAfter(from))
                    || (sleepingSession.sleepDateTime.isAfter(from)
                    && sleepingSession.wakeDateTime.isEqual(to))) {
                list.add(sleepingSession);
            }
            //случай на границах
            if (sleepingSession.sleepDateTime.isEqual(from) && sleepingSession.wakeDateTime.isEqual(to)) {
                list.add(sleepingSession);
            }
            //какой-то случай внутри границ
            if (sleepingSession.sleepDateTime.isAfter(from)
                    && sleepingSession.sleepDateTime.isBefore(to)
                    && sleepingSession.wakeDateTime.isBefore(to)
                    && sleepingSession.wakeDateTime.isAfter(from)) {
                list.add(sleepingSession);
            }
        });

        return sleepingSessions.size() - list.size();
    }
}
