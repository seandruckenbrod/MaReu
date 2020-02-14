package com.example.mareu.model;

public class Salle {
    private String name;
    private int lieu;
    private int avatarColor;

    public Salle(String name, int lieu, int avatarColor) {
        this.name = name;
        this.lieu = lieu;
        this.avatarColor = avatarColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLieu() {
        return lieu;
    }

    public void setLieu(int lieu) {
        this.lieu = lieu;
    }

    public int getColor() {
        return avatarColor;
    }

    public void setColor(int avatarColor) {
        this.avatarColor = avatarColor;
    }
}
