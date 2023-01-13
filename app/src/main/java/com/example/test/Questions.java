package com.example.test;

public class Questions {

    String question;
    String[] answer;
    String[] reward;
    String penalty;
    public static int currentCard = 0;
    public static boolean hasReadFile = false;

    // question;answer1;reward1;penalty1;answer2;answer3;answer4;reward2;reward3;reward4


    public Questions(String question, String[] answer, String[] reward, String penalty) {
        this.question = question;
        this.answer = answer;
        this.reward = reward;
        this.penalty = penalty;
    }
}
