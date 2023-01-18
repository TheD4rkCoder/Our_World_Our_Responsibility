package com.example.test;

public class Event {
    String title, text;
    public static boolean hasReadFile = false;
    public static int currentCard = 0;
    public static String language = "de";

    public Event(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
