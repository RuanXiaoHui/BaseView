package com.jackson.dialog;

/**
 * Created by Jackson on 2018/2/19.
 */

public  class SheetItem {
    private String name;
    private OnSheetItemClickListener listener;
    private SheetItemColor color;

    public SheetItem(String name,SheetItemColor color,OnSheetItemClickListener listener) {
        this.name = name;
        this.listener = listener;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OnSheetItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnSheetItemClickListener listener) {
        this.listener = listener;
    }

    public SheetItemColor getColor() {
        return color;
    }

    public void setColor(SheetItemColor color) {
        this.color = color;
    }
}
