package com.test.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MarkedSection extends Section {
    private List<String> textArea = new ArrayList<>();

    public MarkedSection(List<String> textArea) {
        this.textArea = textArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkedSection that = (MarkedSection) o;
        return Objects.equals(textArea, that.textArea);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textArea);
    }

    @Override
    public String toString() {
        return textArea.toString();
    }

    public List<String> getTextArea() {
        return textArea;
    }

    public void setTextArea(List<String> textArea) {
        this.textArea = textArea;
    }
}
