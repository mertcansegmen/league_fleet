package com.example.mert.leagueoflegendsliginne;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {

    //View variables
    Button menuButton;
    Button quitButton;
    TextView scoreTextView;
    ImageView leagueImageView;
    TextView leagueTextView;

    //Variables about sound
    SoundPool buttonSounds;
    int buttonSoundID;
    float volume;
    boolean isVolumeOn;

    //Creating an array out of League objects, it holds all the leagues informations(league image and its name)
    private League[] leagues = new League[]{
            new League(R.drawable.rank_1, R.string.league_tv_r1),
            new League(R.drawable.rank_2, R.string.league_tv_r2),
            new League(R.drawable.rank_3, R.string.league_tv_r3),
            new League(R.drawable.rank_4, R.string.league_tv_r4),
            new League(R.drawable.rank_5, R.string.league_tv_r5),
            new League(R.drawable.rank_6, R.string.league_tv_r6),
            new League(R.drawable.rank_7, R.string.league_tv_r7),
            new League(R.drawable.rank_8, R.string.league_tv_r8),
            new League(R.drawable.rank_9, R.string.league_tv_r9),
            new League(R.drawable.rank_10, R.string.league_tv_r10),
            new League(R.drawable.rank_11, R.string.league_tv_r11),
            new League(R.drawable.rank_12, R.string.league_tv_r12),
            new League(R.drawable.rank_13, R.string.league_tv_r13),
            new League(R.drawable.rank_14, R.string.league_tv_r14),
            new League(R.drawable.rank_15, R.string.league_tv_r15),
            new League(R.drawable.rank_16, R.string.league_tv_r16),
            new League(R.drawable.rank_17, R.string.league_tv_r17),
            new League(R.drawable.rank_18, R.string.league_tv_r18),
            new League(R.drawable.rank_19, R.string.league_tv_r19),
            new League(R.drawable.rank_20, R.string.league_tv_r20),
            new League(R.drawable.rank_21, R.string.league_tv_r21),
            new League(R.drawable.rank_22, R.string.league_tv_r22),
            new League(R.drawable.rank_23, R.string.league_tv_r23),
            new League(R.drawable.rank_24, R.string.league_tv_r24),
            new League(R.drawable.rank_25, R.string.league_tv_r25),
            new League(R.drawable.rank_26, R.string.league_tv_r26),
            new League(R.drawable.rank_27, R.string.league_tv_r27)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        //Initialize views
        leagueImageView = findViewById(R.id.league_image_view);
        leagueTextView = findViewById(R.id.final_league_text_view);
        menuButton = findViewById(R.id.go_menu_button);
        quitButton = findViewById(R.id.quit_button);
        scoreTextView = findViewById(R.id.score_text_view);

        //Getting the values from main activity
        Intent intent = getIntent();
        int score = intent.getIntExtra("score",0);
        int biggestScorePossible = intent.getIntExtra("biggestScorePossible", 0);
        int trueCount = intent.getIntExtra("trueCount", 0);

        //Configures the deserved league
        int league = findLeague(score, biggestScorePossible);

        //Puts the league image and its name in the screen
        setLayout(league);

        //Displays the number of trues and number of all questions
        scoreTextView.setText(trueCount + "/" + 17);

        //Creating the soundpool for button sound
        buttonSounds = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        buttonSoundID = buttonSounds.load(this, R.raw.default_button, 1);

        disableButtonClickSounds();
        checkPreferences();

        //Button listeners
        menuButton.setOnClickListener(e -> menuButtonClicked());
        quitButton.setOnClickListener(e -> quitButtonClicked());
    }

    //Quits app
    private void quitButtonClicked() {
        playButtonSound();

        moveTaskToBack(true);
    }

    //Goes to main menu
    private void menuButtonClicked(){
        playButtonSound();

        Intent intent = new Intent(FinishActivity.this, LaunchActivity.class);
        startActivity(intent);
    }

    //Also goes to main menu when back button is pressed
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    //Checks if the sounds are on or off and plays the sound
    private void playButtonSound(){
        if(isVolumeOn)
            volume = 1f;
        else
            volume = 0f;

        buttonSounds.play(buttonSoundID, volume, volume, 0, 0, 1.0f);
    }

    //Sets the image and text of the league
    private void setLayout(int league) {
        leagueImageView.setImageResource(leagues[league].getImageID());
        leagueTextView.setText(leagues[league].getLeagueNameID());
    }

    //Disables the default button sounds
    private void disableButtonClickSounds() {
        menuButton.setSoundEffectsEnabled(false);
        quitButton.setSoundEffectsEnabled(false);
    }

    //Checks the pref file then sets isVolumeOn variable according to it
    private void checkPreferences() {
        SharedPreferences pref = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        isVolumeOn = pref.getBoolean("volume", false);
    }

    //Algorithm for configuring users league
    private int findLeague(int score, int biggestScorePossible){

        if(score <= biggestScorePossible*(27/27.0) && score > biggestScorePossible * (26/27.0)){
            return 26;
        }
        else if(score <= biggestScorePossible*(26/27.0) && score > biggestScorePossible * (25/27.0)){
            return 25;
        }
        else if(score <= biggestScorePossible*(25/27.0) && score > biggestScorePossible * (24/27.0)){
            return 24;
        }
        else if(score <= biggestScorePossible*(24/27.0) && score > biggestScorePossible * (23/27.0)){
            return 23;
        }
        else if(score <= biggestScorePossible*(23/27.0) && score > biggestScorePossible * (22/27.0)){
            return 22;
        }
        else if(score <= biggestScorePossible*(22/27.0) && score > biggestScorePossible * (21/27.0)){
            return 21;
        }
        else if(score <= biggestScorePossible*(21/27.0) && score > biggestScorePossible * (20/27.0)){
            return 20;
        }
        else if(score <= biggestScorePossible*(20/27.0) && score > biggestScorePossible * (19/27.0)){
            return 19;
        }
        else if(score <= biggestScorePossible*(19/27.0) && score > biggestScorePossible * (18/27.0)){
            return 18;
        }
        else if(score <= biggestScorePossible*(18/27.0) && score > biggestScorePossible * (17/27.0)){
            return 17;
        }
        else if(score <= biggestScorePossible*(17/27.0) && score > biggestScorePossible * (16/27.0)){
            return 16;
        }
        else if(score <= biggestScorePossible*(16/27.0) && score > biggestScorePossible * (15/27.0)){
            return 15;
        }
        else if(score <= biggestScorePossible*(15/27.0) && score > biggestScorePossible * (14/27.0)){
            return 14;
        }
        else if(score <= biggestScorePossible*(14/27.0) && score > biggestScorePossible * (13/27.0)){
            return 13;
        }
        else if(score <= biggestScorePossible*(13/27.0) && score > biggestScorePossible * (12/27.0)){
            return 12;
        }
        else if(score <= biggestScorePossible*(12/27.0) && score > biggestScorePossible * (11/27.0)){
            return 11;
        }
        else if(score <= biggestScorePossible*(11/27.0) && score > biggestScorePossible * (10/27.0)){
            return 10;
        }
        else if(score <= biggestScorePossible*(10/27.0) && score > biggestScorePossible * (9/27.0)){
            return 9;
        }
        else if(score <= biggestScorePossible*(9/27.0) && score > biggestScorePossible * (8/27.0)){
            return 8;
        }
        else if(score <= biggestScorePossible*(8/27.0) && score > biggestScorePossible * (7/27.0)){
            return 7;
        }
        else if(score <= biggestScorePossible*(7/27.0) && score > biggestScorePossible * (6/27.0)){
            return 6;
        }
        else if(score <= biggestScorePossible*(6/27.0) && score > biggestScorePossible * (5/27.0)){
            return 5;
        }
        else if(score <= biggestScorePossible*(5/27.0) && score > biggestScorePossible * (4/27.0)){
            return 4;
        }
        else if(score <= biggestScorePossible*(4/27.0) && score > biggestScorePossible * (3/27.0)){
            return 3;
        }
        else if(score <= biggestScorePossible*(3/27.0) && score > biggestScorePossible * (2/27.0)){
            return 2;
        }
        else if(score <= biggestScorePossible*(2/27.0) && score > biggestScorePossible * (1/27.0)){
            return 1;
        }
        else{
            return 0;
        }
    }

}
