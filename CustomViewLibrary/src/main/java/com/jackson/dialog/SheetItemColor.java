package com.jackson.dialog;

public enum SheetItemColor {
    BLUE("#037BFF"),
    RED("#FD4A2E"),
    BLACK("#000000"),
    GRAY("#8F8F8F");

    private String name;

     SheetItemColor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}