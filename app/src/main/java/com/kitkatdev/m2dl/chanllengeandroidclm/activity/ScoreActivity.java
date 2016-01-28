package com.kitkatdev.m2dl.chanllengeandroidclm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.kitkatdev.m2dl.chanllengeandroidclm.MainActivity;
import com.kitkatdev.m2dl.chanllengeandroidclm.R;
import com.kitkatdev.m2dl.chanllengeandroidclm.model.Score;
import com.kitkatdev.m2dl.chanllengeandroidclm.service.ConfigurationService;
import com.kitkatdev.m2dl.chanllengeandroidclm.util.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    private ListView listScoreView;
    private ArrayAdapter<Score> adapter;
    private CustomAdapter arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ConfigurationService configurationService = ConfigurationService.getInstance();
        List<Score> scoreList = configurationService.getScoreList();

        listScoreView = (ListView) findViewById(R.id.listScoreView);

        adapter = new CustomAdapter (this, new ArrayList<Score> ());
        listScoreView.setAdapter(adapter);

        for (Score s : scoreList){
            adapter.add(s);
        }

        Button mainmenuButton = (Button) findViewById(R.id.mainmenuButton);
        mainmenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ScoreActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        Button restartButton = (Button) findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });




    }
}
