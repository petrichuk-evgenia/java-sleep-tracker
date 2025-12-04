package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.*;
import ru.yandex.practicum.sleeptracker.sessions.SleepingSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Supplier;

import static ru.yandex.practicum.sleeptracker.sessions.SleepingConstants.dateTimeFormatter;

public class SleepTrackerApp<T> {

    public static void main(String[] args) {

        String fileName = args[0];
        //String fileName = "src/main/resources/sleep_log.txt";
        LocalDateTime from = LocalDateTime.parse("01.10.25 23:50", dateTimeFormatter);
        LocalDateTime to = LocalDateTime.parse("11.10.25 06:10", dateTimeFormatter);

        LinkedHashSet<SleepingSession> sleepingSessions = fillSleepingSessions(fileName);

        List<Supplier> functions = new ArrayList<>();
        functions.add(new SleepingSessionsForPeriod(sleepingSessions, from, to));
        functions.add(new MinSleepingSession(sleepingSessions));
        functions.add(new MaxSleepingSession(sleepingSessions));
        functions.add(new AvgSleepingSession(sleepingSessions));
        functions.add(new BadQualitySleepingSession(sleepingSessions));
        functions.add(new SleeplessNights(sleepingSessions));
        functions.add(new DaysWithSleeping(sleepingSessions));
        functions.add(new ChronoType(sleepingSessions));

        System.out.println("Демонстрация");
        functions.forEach(function -> System.out.println(function.toString()));

    }

    /*
     * LinkedHashSet взят потому, чтоб при возможном задвоении записей в файле не создавать дубли сессий в "списке"
     * Отсев подобных ситуаций предпочитаю делать штатными средствами, а не самописными функциями
     * Linked тоже потому, что важен порядок добавления
     */
    private static LinkedHashSet<SleepingSession> fillSleepingSessions(String fileName) {
        LinkedHashSet<SleepingSession> sleepingSessions = new LinkedHashSet<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            lines.forEach(session -> {
                SleepingSession sleepingSession = new SleepingSession(session);
                int years = Period.between(sleepingSession.sleepDateTime.toLocalDate(), sleepingSession.wakeDateTime.toLocalDate()).getYears();
                int months = Period.between(sleepingSession.sleepDateTime.toLocalDate(), sleepingSession.wakeDateTime.toLocalDate()).getMonths();
                int days = Period.between(sleepingSession.sleepDateTime.toLocalDate(), sleepingSession.wakeDateTime.toLocalDate()).getDays();
                boolean validPeriod = (years == 0) && (months == 0) && (days < 2);
                if (sleepingSession.wakeDateTime.isAfter(sleepingSession.sleepDateTime) && validPeriod) {
                    sleepingSessions.add(sleepingSession);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sleepingSessions;
    }

}