package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.List;

public class SleepTrackerApp {

    public static final LinkedHashSet<SleepingSession> sleepingSessions = new LinkedHashSet<>();
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static void main(String[] args) {

        //String fileName = args[0];
        String fileName = "src/main/resources/sleep_log.txt";
        fillSleepingSessions(fileName);


        LocalDateTime from = LocalDateTime.parse("01.10.25 23:50", dateTimeFormatter);
        LocalDateTime to = LocalDateTime.parse("11.10.25 06:10", dateTimeFormatter);

        System.out.println("Демонстрация");
        System.out.println(String.format("Количество сессий за период %s - %s : %d",
                from.format(dateTimeFormatter), to.format(dateTimeFormatter),
                new SleepingSessionsForPeriod(sleepingSessions).apply(from, to)));

        System.out.println(String.format("Минимальная продолжительность сессии: %d минут",
                new MinSleepingSession(sleepingSessions).get()));

        System.out.println(String.format("Максимальная продплжительность сессии: %d минут",
                new MaxSleepingSession(sleepingSessions).get()));

        System.out.println(String.format("Средняя продплжительность сессии: %s минут",
                new AvgSleepingSession(sleepingSessions).get()));

        System.out.println(String.format("Количество сессий с плохим качеством сна: %d",
                new BadQualitySleepingSession(sleepingSessions).get()));

        System.out.println(String.format("Количество бессонных ночей: %d",
                new SleeplessNights(sleepingSessions).get()));

    }

    private static void fillSleepingSessions(String fileName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            lines.forEach(session -> {
                SleepingSession sleepingSession = new SleepingSession(session);
                if (sleepingSession.wakeDateTime.isAfter(sleepingSession.sleepDateTime)) {
                    sleepingSessions.add(sleepingSession);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}