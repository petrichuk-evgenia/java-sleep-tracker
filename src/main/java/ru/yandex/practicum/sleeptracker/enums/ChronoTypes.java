package ru.yandex.practicum.sleeptracker.enums;

public enum ChronoTypes {
    OWL("Сова"),
    LARK("Жаворонок"),
    PIGEON("Голубь");

    private final String type;

    ChronoTypes(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}