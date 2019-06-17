package com.test.webapp.model;

public enum ContactType {
    TELEPHONE("Тел."),
    SKYPE("Skype"),
    MAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private final String contactName;

    ContactType(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }
}
