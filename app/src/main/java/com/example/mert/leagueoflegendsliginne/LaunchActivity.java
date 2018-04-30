package com.example.mert.leagueoflegendsliginne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class LaunchActivity extends AppCompatActivity {

    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener( e -> btnClicked());
    }
    private void btnClicked(){
        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
        startActivity(intent);

    }
}
