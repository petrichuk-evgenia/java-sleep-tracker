package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class SleeplessNights extends Sleeping implements Supplier<Integer> {

    public SleeplessNights(LinkedHashSet<SleepingSession> sleepingSessions) {
        super(sleepingSessions);
    }

    @Override
    public Integer get() {
        return sleeplessNights().size();
    }
}
