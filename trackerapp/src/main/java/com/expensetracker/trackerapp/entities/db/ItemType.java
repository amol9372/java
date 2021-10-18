package com.expensetracker.trackerapp.entities.db;

import java.util.HashMap;

public enum ItemType {

    USER("user_"),
    EXPENSE("expense_"),
    GROUP("group_"),
    CATEGORY("category_");

    private String value;

    ItemType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}