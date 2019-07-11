package com.test.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InstitutionSection extends AbstractSection {
    private List<Institution> listInst;

    public List<Institution> getListInst() {
        return listInst;
    }

    public InstitutionSection(Institution... institutions) {
        this(Arrays.asList(institutions));
    }

    public InstitutionSection(List<Institution> listInst) {
        Objects.requireNonNull(listInst, "institutions must not be null");
        this.listInst = listInst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstitutionSection that = (InstitutionSection) o;
        return Objects.equals(listInst, that.listInst);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listInst);
    }

    @Override
    public String toString() {
        return listInst.toString();
    }
}
