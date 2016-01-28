package com.kitkatdev.m2dl.chanllengeandroidclm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.kitkatdev.m2dl.chanllengeandroidclm.activity.ScoreActivity;
import com.kitkatdev.m2dl.chanllengeandroidclm.model.Score;
import com.kitkatdev.m2dl.chanllengeandroidclm.service.ConfigurationService;

public class MainActivity extends AppCompatActivity {

    private MainJeu MainJeu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ConfigurationService configurationService = ConfigurationService.getInstance();

        super.onCreate(savedInstanceState);




        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManagerInstancier.getInstance().setWindowManager(this.getWindowManager());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        configurationService.setMainActivity(this);

        // On cré un objet "MainJeu" qui est le code principal du jeu
        MainJeu=new MainJeu(this);



        // et on l'affiche.
        setContentView(MainJeu);



    }


    public void  goToScore(){
        Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
        startActivity(intent);
        finish();
    }
}
