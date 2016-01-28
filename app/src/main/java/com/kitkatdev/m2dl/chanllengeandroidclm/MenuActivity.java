package com.kitkatdev.m2dl.chanllengeandroidclm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kitkatdev.m2dl.chanllengeandroidclm.service.ConfigurationService;

public class MenuActivity extends AppCompatActivity {

    private Button nouvellePartieButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        nouvellePartieButton = (Button) findViewById(R.id.nouvellePartieButton);
        nouvellePartieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConfigurationService configurationService = ConfigurationService.getInstance();
                configurationService.setUserName(nouvellePartieButton.getText().toString());

                Intent intent = new Intent(MenuActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });




    }
}
