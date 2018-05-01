package com.example.mert.leagueoflegendsliginne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class LaunchActivity extends AppCompatActivity {

    Button startButton;
    Button quitButton;
    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        startButton = findViewById(R.id.start_button);
        settingsButton = findViewById(R.id.settings_button);
        quitButton = findViewById(R.id.quit_button);

        startButton.setOnClickListener(e -> startButtonClicked());
        settingsButton.setOnClickListener(e -> settingsButtonClicked());
        quitButton.setOnClickListener(e -> quitButtonClicked());

    }

    private void startButtonClicked(){
        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void settingsButtonClicked() {

    }

    private void quitButtonClicked() {
        moveTaskToBack(true);
    }
}
