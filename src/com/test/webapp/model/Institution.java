package com.test.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Institution implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Link homePage;
    private List<Position> positions;

    public Institution(Link homePage, Position... positions) {
        this(homePage, Arrays.asList(positions));
    }

    public Institution(Link homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public String toString() {
        return "Institution{" +
                "homePage=" + homePage +
                ", positions=" + positions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institution that = (Institution) o;
        return Objects.equals(homePage, that.homePage) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }

    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;
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
    }
}
