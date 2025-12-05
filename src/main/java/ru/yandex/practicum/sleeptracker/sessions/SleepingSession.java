package ru.yandex.practicum.sleeptracker.sessions;

import ru.yandex.practicum.sleeptracker.enums.SleepingQuality;

import java.time.LocalDateTime;

import static ru.yandex.practicum.sleeptracker.sessions.SleepingConstants.dateTimeFormatter;

public class SleepingSession {

    public final LocalDateTime sleepDateTime;
    public final LocalDateTime wakeDateTime;
    public final SleepingQuality quality;

    public SleepingSession(String session) {
        String[] dataFromSession = session.split("\\;");

        sleepDateTime = LocalDateTime.parse(dataFromSession[0], dateTimeFormatter);
        wakeDateTime = LocalDateTime.parse(dataFromSession[1], dateTimeFormatter);
        quality = SleepingQuality.valueOf(dataFromSession[2]);
    }

    public LocalDateTime getSleepDateTime() {
        return sleepDateTime;
    }

    @Override
    public String toString() {
        return String.format("SleepingSession{sleepDateTime = %s, wakeDateTime = %s, quality = %s}",
                sleepDateTime.format(dateTimeFormatter), wakeDateTime.format(dateTimeFormatter), quality.toString());
    }
}
