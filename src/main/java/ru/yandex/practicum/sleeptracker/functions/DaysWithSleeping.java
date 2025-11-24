package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.sessions.SleepingSession;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class DaysWithSleeping extends Sleeping implements Supplier<Integer> {

    public DaysWithSleeping(LinkedHashSet<SleepingSession> sleepingSessions) {
        super(sleepingSessions);
    }

    @Override
    public Integer get() {
        return sleepingDays().size();
    }
}
