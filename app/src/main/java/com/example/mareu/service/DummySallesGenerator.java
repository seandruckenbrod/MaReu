package com.example.mareu.service;

import com.example.mareu.R;
import com.example.mareu.model.Salle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class DummySallesGenerator {
    private static List<Salle> DUMMY_SALLES = Arrays.asList(
            new Salle("Mario", 1, R.drawable.ic_salle_mario_24dp),
            new Salle("Luigi", 2, R.drawable.ic_salle_luigi_24dp),
            new Salle("Peach", 3, R.drawable.ic_salle_peach_24dp),
            new Salle("Toad", 4, R.drawable.ic_salle_toad_24dp),
            new Salle("Yoshi", 5, R.drawable.ic_salle_yoshi_24dp),
            new Salle("Bowser", 6, R.drawable.ic_salle_bowser_24dp),
            new Salle("Goomba", 7, R.drawable.ic_salle_goomba_24dp),
            new Salle("Wario", 8, R.drawable.ic_salle_wario_24dp),
            new Salle("Waluigi", 9, R.drawable.ic_salle_waluigi_24dp),
            new Salle("Skelérex", 10, R.drawable.ic_salle_skelerex_24dp)
    );

    public static List<Salle> generateSalles() {
        return new ArrayList<>(DUMMY_SALLES);
    }
}