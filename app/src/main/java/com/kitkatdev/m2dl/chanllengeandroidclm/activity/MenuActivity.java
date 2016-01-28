package com.kitkatdev.m2dl.chanllengeandroidclm.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.kitkatdev.m2dl.chanllengeandroidclm.MainActivity;
import com.kitkatdev.m2dl.chanllengeandroidclm.R;
import com.kitkatdev.m2dl.chanllengeandroidclm.service.ConfigurationService;

public class MenuActivity extends AppCompatActivity {

    private Button nouvellePartieButton;
    private ConfigurationService configurationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        configurationService = ConfigurationService.getInstance();
        configurationService.setMediaPlayer(MediaPlayer.create(getBaseContext(), R.raw.music1));
        configurationService.getMediaPlayer().start();

        nouvellePartieButton = (Button) findViewById(R.id.nouvellePartieButton);
        nouvellePartieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                configurationService.setUserName(nouvellePartieButton.getText().toString());

                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });




    }
}
