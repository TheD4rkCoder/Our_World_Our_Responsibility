package com.example.test;

public class Job {
    boolean needsUniversity;
    String name;
    String location;
    int money;
    int salary;
    int ecoScore;
    public static boolean hasReadFile = false;
    public static int currentCard = 0;
    public static int currentCardUni = 0;
    public static String language = "de";

    public Job(boolean needsUniversity, String name, String location, int money, int salary, int ecoScore) {
        this.needsUniversity = needsUniversity;
        this.name = name;
        this.location = location;
        this.money = money;
        this.salary = salary;
        this.ecoScore = ecoScore;
    }
}
