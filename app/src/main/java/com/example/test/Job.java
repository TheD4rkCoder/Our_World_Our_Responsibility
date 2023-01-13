package com.example.test;

public class Job {
    boolean needsUniversity;
    String name;
    String location;
    public static boolean hasReadFile = false;
    public static int currentCard = 0;
    public static int currentCardUni = 0;

    public Job(boolean needsUniversity, String name, String location) {
        this.needsUniversity = needsUniversity;
        this.name = name;
        this.location = location;
    }
}
