package com.javarush.lesson09.model;

public class Status {

    private String text;

    public Status() {
    }

    public Status(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
