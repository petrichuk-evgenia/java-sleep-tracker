package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.enums.ChronoTypes;
import ru.yandex.practicum.sleeptracker.functions.ChronoType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronoTypeTest extends BaseTest {

    private final String owl = "src/main/resources/sleep_owl.txt";
    private final String lark = "src/main/resources/sleep_lark.txt";
    private final String pigeon = "src/main/resources/sleep_pigeon.txt";
    private final String pigeonAllSessions = "src/main/resources/sleep_pigeon_all_sessions.txt";

    @Test
    public void shouldReturnOWL() {
        fillSessions(owl);
        assertEquals(ChronoTypes.OWL, new ChronoType(sessions).get());
    }

    @Test
    public void shouldReturnLARK() {
        fillSessions(lark);
        assertEquals(ChronoTypes.LARK, new ChronoType(sessions).get());
    }

    @Test
    public void shouldReturnPIGEON() {
        fillSessions(pigeon);
        assertEquals(ChronoTypes.PIGEON, new ChronoType(sessions).get());
    }

    @Test
    public void shouldReturnPIGEON_All() {
        fillSessions(pigeonAllSessions);
        assertEquals(ChronoTypes.PIGEON, new ChronoType(sessions).get());
    }
}