package com.test.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InstitutionsSection extends AbstractSection {
    private List<Institutions> listInst;

    public List<Institutions> getListInst() {
        return listInst;
    }

    public InstitutionsSection(Institutions ... institutions){
        this(Arrays.asList(institutions));
    }

    public InstitutionsSection(List<Institutions> listInst) {
        Objects.requireNonNull(listInst, "institutions must not be null");
        this.listInst = listInst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstitutionsSection that = (InstitutionsSection) o;
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
