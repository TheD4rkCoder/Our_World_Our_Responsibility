package com.example.test;

public class Player {
    private String name;
    private String job;
    private Integer money = 0;
    private Integer salary = 0;
    public Integer ecoScore = 0;
    public boolean university = false;
    private boolean creationCompleted = false;

    public boolean isCreationCompleted() {
        return creationCompleted;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public Integer getMoney() {
        return money;
    }

    public Integer getSalary() {
        return salary;
    }

    public Integer getEcoScore() {
        return ecoScore;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setEcoScore(Integer ecoScore) {
        this.ecoScore = ecoScore;
    }
    public void completeCreation(){
        creationCompleted = true;
    }

}
