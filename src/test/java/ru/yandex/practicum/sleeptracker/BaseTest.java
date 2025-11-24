package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.List;

public class BaseTest {
    final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    final LocalDateTime from1 = LocalDateTime.parse("01.10.25 23:50", dateTimeFormatter);
    final LocalDateTime to1 = LocalDateTime.parse("11.10.25 06:10", dateTimeFormatter);
    final LocalDateTime from2 = LocalDateTime.parse("01.10.26 23:50", dateTimeFormatter);
    final LocalDateTime to2 = LocalDateTime.parse("11.10.26 06:10", dateTimeFormatter);
    protected LinkedHashSet<SleepingSession> sessions = new LinkedHashSet<>();
    final String fileName = "src/main/resources/sleep_log.txt";
    final String sleepGood = "src/main/resources/sleep_good.txt";
    final String sleepOneBad = "src/main/resources/sleep_one_bad.txt";
    final String sleepAllBad = "src/main/resources/sleep_two_bad.txt";

    protected void fillSessions(String fileName){
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            lines.forEach(session -> {
                SleepingSession sleepingSession = new SleepingSession(session);
                int years = Period.between(sleepingSession.sleepDateTime.toLocalDate(), sleepingSession.wakeDateTime.toLocalDate()).getYears();
                int months = Period.between(sleepingSession.sleepDateTime.toLocalDate(), sleepingSession.wakeDateTime.toLocalDate()).getMonths();
                int days = Period.between(sleepingSession.sleepDateTime.toLocalDate(), sleepingSession.wakeDateTime.toLocalDate()).getDays();
                boolean validPeriod = (years == 0) && (months == 0) && (days < 2);
                if (sleepingSession.wakeDateTime.isAfter(sleepingSession.sleepDateTime) && validPeriod) {
                    sessions.add(sleepingSession);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
