package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.enums.ChronoTypes;
import ru.yandex.practicum.sleeptracker.functions.ChronoType;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ChronoTypeTest extends BaseTest {

    final String owl = "src/main/resources/sleep_owl.txt";
    final String lark = "src/main/resources/sleep_lark.txt";
    final String pigeon = "src/main/resources/sleep_pigeon.txt";

    @BeforeEach
    void setUp() {
        sessions.clear();
    }

    @Test
    void shouldReturnOWL() {
        fillSessions(owl);
        assertEquals(ChronoTypes.OWL, new ChronoType(sessions).get());
    }

    @Test
    void shouldReturnLARK() {
        fillSessions(lark);
        assertEquals(ChronoTypes.LARK, new ChronoType(sessions).get());
    }

    @Test
    void shouldReturnPIGEON() {
        fillSessions(pigeon);
        assertEquals(ChronoTypes.PIGEON, new ChronoType(sessions).get());
    }
}