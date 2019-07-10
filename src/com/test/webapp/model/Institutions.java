package com.test.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Institutions {
    private final Link homePage;
    private List<Position> positions;

    public Institutions(Link homePage, Position ... positions) {
        this(homePage, Arrays.asList(positions));
    }

    public Institutions(Link homePage, List<Position> positions) {
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
        return "Institutions{" +
                "homePage=" + homePage +
                ", positions=" + positions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institutions that = (Institutions) o;
        return Objects.equals(homePage, that.homePage) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }
}
