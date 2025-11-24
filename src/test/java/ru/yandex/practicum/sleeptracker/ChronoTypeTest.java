package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.enums.ChronoTypes;
import ru.yandex.practicum.sleeptracker.functions.ChronoType;

import static org.junit.jupiter.api.Assertions.assertEquals;

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