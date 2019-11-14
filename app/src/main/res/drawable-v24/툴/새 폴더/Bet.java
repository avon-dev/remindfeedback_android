package com.example.wanna_bet70;

public class Bet {
    String date;
    String people;
    String script;
    String win;
    String reward;

    public Bet(String date, String people, String script, String win, String reward) {

        this.people = people;
        this.script = script;
        this.win = win;
        this.reward = reward;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }
}
