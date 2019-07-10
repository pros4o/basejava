package com.test.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Position {
    private final LocalDate startPeriod;
    private final LocalDate endPeriod;
    private final String title;
    private final String description;

    public Position(LocalDate startPeriod, LocalDate endPeriod, String title, String description) {
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Position{" +
                "startPeriod=" + startPeriod +
                ", endPeriod=" + endPeriod +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(startPeriod, position.startPeriod) &&
                Objects.equals(endPeriod, position.endPeriod) &&
                Objects.equals(title, position.title) &&
                Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPeriod, endPeriod, title, description);
    }

    public LocalDate getStartPeriod() {
        return startPeriod;
    }

    public LocalDate getEndPeriod() {
        return endPeriod;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
