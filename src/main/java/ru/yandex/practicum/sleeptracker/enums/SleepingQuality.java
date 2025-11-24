package ru.yandex.practicum.sleeptracker.enums;

public enum SleepingQuality {
    GOOD("GOOD"),
    NORMAL("NORMAL"),
    BAD("BAD");

    private final String quality;

    SleepingQuality(String quality) {
        this.quality = quality;
    }

    public String getQuality() {
        return quality;
    }

    @Override
    public String toString() {
        return quality;
    }
}