package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.enums.ChronoTypes;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Supplier;

public class ChronoType extends Sleeping implements Supplier<ChronoTypes> {

    public ChronoType(LinkedHashSet<SleepingSession> sleepingSessions) {
        super(sleepingSessions);
    }

    @Override
    public ChronoTypes get() {
        List<SleepingSession> owl = new ArrayList<>();
        List<SleepingSession> lark = new ArrayList<>();
        List<SleepingSession> pigeon = new ArrayList<>();

        goodNights().forEach(sleepingSession -> {
            LocalDateTime from1;
            if (sleepingSession.sleepDateTime.getHour() == 0) {
                from1 = LocalDateTime.of(sleepingSession.sleepDateTime.toLocalDate().minusDays(1), LocalTime.of(23, 00));
            } else {
                from1 = LocalDateTime.of(sleepingSession.sleepDateTime.toLocalDate(), LocalTime.of(00, 00));
            }
            LocalDateTime to1 = LocalDateTime.of(sleepingSession.wakeDateTime.toLocalDate(), LocalTime.of(9, 00));
            LocalDateTime from2 = LocalDateTime.of(sleepingSession.sleepDateTime.toLocalDate(), LocalTime.of(22, 00));
            LocalDateTime to2 = LocalDateTime.of(sleepingSession.wakeDateTime.toLocalDate(), LocalTime.of(07, 00));
            boolean afterFrom1 = sleepingSession.sleepDateTime.isEqual(from1) || sleepingSession.sleepDateTime.isAfter(from1);
            boolean afterTo1 = sleepingSession.wakeDateTime.isEqual(to1) || sleepingSession.wakeDateTime.isAfter(to1);
            boolean beforeFrom2 = sleepingSession.sleepDateTime.isEqual(from2) || sleepingSession.sleepDateTime.isBefore(from2);
            boolean beforeTo2 = sleepingSession.wakeDateTime.isEqual(to2) || sleepingSession.wakeDateTime.isBefore(to2);
            if (afterFrom1 && afterTo1) {
                owl.add(sleepingSession);
            } else if (beforeFrom2 && beforeTo2) {
                lark.add(sleepingSession);
            } else {
                pigeon.add(sleepingSession);
            }
        });

        // Определяем, какой тип имеет больше всего сессий
        if (owl.size() > lark.size() && owl.size() > pigeon.size()) {
            return ChronoTypes.OWL;
        } else if (lark.size() > owl.size() && lark.size() > pigeon.size()) {
            return ChronoTypes.LARK;
        } else {
            return ChronoTypes.PIGEON;
        }
    }
}
