package com.finalproject.footballlist;

public class Game {
    private long id;
    private String date;
    private String city;
    private String teamA;
    private String teamB;

    public Game(long id,String date, String city, String teamA, String teamB) {
        this.id = id;
        this.date = date;
        this.city = city;
        this.teamA = teamA;
        this.teamB = teamB;
    }
    public long getId() {
        return id;
    }
    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public String getTeamA() {
        return teamA;
    }

    public String getTeamB() {
        return teamB;
    }


}
