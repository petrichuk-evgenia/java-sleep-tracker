package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Sleeping {

    protected LinkedHashSet<SleepingSession> sleepingSessions;

    public Sleeping(LinkedHashSet<SleepingSession> sleepingSessions) {
        this.sleepingSessions = sleepingSessions;
    }


    public List<SleepingSession> goodNights() {
        List<SleepingSession> goodNights = new ArrayList<>();
        sleepingSessions.forEach(sleepingSession -> {
            LocalDateTime from;
            if (sleepingSession.sleepDateTime.getHour() == 0) {
                from = LocalDateTime.of(sleepingSession.sleepDateTime.toLocalDate().minusDays(1), LocalTime.of(23, 59));
            } else {
                from = LocalDateTime.of(sleepingSession.sleepDateTime.toLocalDate(), LocalTime.of(23, 59));
            }
            LocalDateTime to = from.plusHours(6).plusMinutes(1);
            //случай за границами
            if (sleepingSession.sleepDateTime.isBefore(from) && sleepingSession.wakeDateTime.isAfter(to)) {
                goodNights.add(sleepingSession);
            }
            //какой-то случай на границе
            if ((sleepingSession.sleepDateTime.isBefore(from) && sleepingSession.wakeDateTime.isEqual(to))
                    || (sleepingSession.sleepDateTime.isEqual(from) && sleepingSession.wakeDateTime.isAfter(to))) {
                goodNights.add(sleepingSession);
            }
            //какой-то случай внутри границы
            if ((sleepingSession.sleepDateTime.isBefore(from)
                    && sleepingSession.wakeDateTime.isBefore(to)
                    && sleepingSession.wakeDateTime.isAfter(from))
                    || (sleepingSession.sleepDateTime.isAfter(from)
                    && sleepingSession.wakeDateTime.isAfter(to))) {
                goodNights.add(sleepingSession);
            }
            //какой-то случай внутри границы и на границе
            if ((sleepingSession.sleepDateTime.isEqual(from)
                    && sleepingSession.wakeDateTime.isBefore(to)
                    && sleepingSession.wakeDateTime.isAfter(from))
                    || (sleepingSession.sleepDateTime.isAfter(from)
                    && sleepingSession.wakeDateTime.isEqual(to))) {
                goodNights.add(sleepingSession);
            }
            //случай на границах
            if (sleepingSession.sleepDateTime.isEqual(from) && sleepingSession.wakeDateTime.isEqual(to)) {
                goodNights.add(sleepingSession);
            }
            //какой-то случай внутри границ
            if (sleepingSession.sleepDateTime.isAfter(from)
                    && sleepingSession.sleepDateTime.isBefore(to)
                    && sleepingSession.wakeDateTime.isBefore(to)
                    && sleepingSession.wakeDateTime.isAfter(from)) {
                goodNights.add(sleepingSession);
            }
        });
        return goodNights;
    }

    protected List<SleepingSession> sleeplessNights() {
        List<SleepingSession> sleeplessNights = new ArrayList<>();

        sleepingSessions.stream()
                .filter(sleepingSession -> !goodNights().contains(sleepingSession))
                .collect(Collectors.toList()).forEach(sleepingSession -> {
                    LocalDateTime from1 = LocalDateTime.of(sleepingSession.sleepDateTime.toLocalDate(), LocalTime.of(7, 00));
                    LocalDateTime to1 = LocalDateTime.of(sleepingSession.wakeDateTime.toLocalDate(), LocalTime.of(11, 01));
                    LocalDateTime from2 = LocalDateTime.of(sleepingSession.sleepDateTime.toLocalDate(), LocalTime.of(17, 00));
                    LocalDateTime to2 = LocalDateTime.of(sleepingSession.wakeDateTime.toLocalDate(), LocalTime.of(23, 01));
                    boolean afterFrom1 = (sleepingSession.sleepDateTime.isEqual(from1) || sleepingSession.sleepDateTime.isAfter(from1)) && sleepingSession.sleepDateTime.isBefore(to1);
                    boolean beforeTo1 = sleepingSession.wakeDateTime.isAfter(from1) && (sleepingSession.wakeDateTime.isEqual(to1) || sleepingSession.wakeDateTime.isBefore(to1));
                    boolean afterFrom2 = (sleepingSession.sleepDateTime.isEqual(from2) || sleepingSession.sleepDateTime.isAfter(from2)) && sleepingSession.sleepDateTime.isBefore(to2);
                    boolean beforeTo2 = sleepingSession.wakeDateTime.isAfter(from2) && (sleepingSession.wakeDateTime.isEqual(to2) || sleepingSession.wakeDateTime.isBefore(to2));
                    if (afterFrom1 && beforeTo1) {
                        sleeplessNights.add(sleepingSession);
                    } else if (afterFrom2 && beforeTo2) {
                        sleeplessNights.add(sleepingSession);
                    }
                });
        return sleeplessNights;
    }

    protected List<SleepingSession> sleepingDays() {
        List<SleepingSession> sleepingDays = new ArrayList<>();

        sleepingSessions.stream()
                .filter(sleepingSession -> !goodNights().contains(sleepingSession))
                .collect(Collectors.toList()).forEach(sleepingSession -> {
                    LocalDateTime from1 = LocalDateTime.of(sleepingSession.sleepDateTime.toLocalDate(), LocalTime.of(7, 00));
                    LocalDateTime to1 = LocalDateTime.of(sleepingSession.wakeDateTime.toLocalDate(), LocalTime.of(11, 01));
                    LocalDateTime from2 = LocalDateTime.of(sleepingSession.sleepDateTime.toLocalDate(), LocalTime.of(17, 00));
                    LocalDateTime to2 = LocalDateTime.of(sleepingSession.wakeDateTime.toLocalDate(), LocalTime.of(23, 01));
                    boolean afterFrom1 = (sleepingSession.sleepDateTime.isEqual(from1) || sleepingSession.sleepDateTime.isAfter(from1)) && sleepingSession.sleepDateTime.isBefore(to1);
                    boolean beforeTo1 = sleepingSession.wakeDateTime.isAfter(from1) && (sleepingSession.wakeDateTime.isEqual(to1) || sleepingSession.wakeDateTime.isBefore(to1));
                    boolean afterFrom2 = (sleepingSession.sleepDateTime.isEqual(from2) || sleepingSession.sleepDateTime.isAfter(from2)) && sleepingSession.sleepDateTime.isBefore(to2);
                    boolean beforeTo2 = sleepingSession.wakeDateTime.isAfter(from2) && (sleepingSession.wakeDateTime.isEqual(to2) || sleepingSession.wakeDateTime.isBefore(to2));
                    if (!afterFrom1 && !beforeTo1) {
                        sleepingDays.add(sleepingSession);
                    } else if (!afterFrom2 && !beforeTo2) {
                        sleepingDays.add(sleepingSession);
                    }
                });
        return sleepingDays;
    }
}
