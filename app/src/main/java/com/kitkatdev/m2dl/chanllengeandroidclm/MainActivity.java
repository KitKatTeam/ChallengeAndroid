package com.kitkatdev.m2dl.chanllengeandroidclm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private MainJeu MainJeu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManagerInstancier.getInstance().setWindowManager(this.getWindowManager());

        // On cré un objet "MainJeu" qui est le code principal du jeu
        MainJeu=new MainJeu(this);

        // et on l'affiche.
        setContentView(MainJeu);
    }
}
