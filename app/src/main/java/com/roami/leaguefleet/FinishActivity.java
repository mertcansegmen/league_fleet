package com.roami.leaguefleet;

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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class FinishActivity extends AppCompatActivity {

    //View variables
    Button playAgainButton;
    Button quitButton;
    TextView scoreTextView;
    ImageView leagueImageView;
    TextView leagueTextView;

    //Variables about sound
    SoundPool buttonSounds;
    int buttonSoundID;
    float volume;
    boolean isVolumeOn;

    //Variables about ads
    AdView adView;
    InterstitialAd interstitialAd;
    AdRequest bannerAdRequest;
    AdRequest interstitialAdRequest;

    //Creating an array out of League objects, it holds all the leagues information (league image and its name)
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
            new League(R.drawable.rank_27, R.string.league_tv_r27),
            new League(R.drawable.rank_28, R.string.league_tv_r28),
            new League(R.drawable.rank_29, R.string.league_tv_r29),
            new League(R.drawable.rank_30, R.string.league_tv_r30),
            new League(R.drawable.rank_31, R.string.league_tv_r31),
            new League(R.drawable.rank_32, R.string.league_tv_r32),
            new League(R.drawable.rank_33, R.string.league_tv_r33),
            new League(R.drawable.rank_34, R.string.league_tv_r34),
            new League(R.drawable.rank_35, R.string.league_tv_r35),
            new League(R.drawable.rank_36, R.string.league_tv_r36)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        //Initialize views
        leagueImageView = findViewById(R.id.league_image_view);
        leagueTextView = findViewById(R.id.final_league_text_view);
        playAgainButton = findViewById(R.id.play_again_button);
        quitButton = findViewById(R.id.quit_button);
        scoreTextView = findViewById(R.id.score_text_view);

        MobileAds.initialize(this, Variables.ADMOB_APP_ID);
        showBannerAd();
        loadInterstitialAd();

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
        scoreTextView.setText(trueCount + "/" + Variables.NUMBER_OF_QUESTIONS);

        //Creating the sound pool for button sound
        buttonSounds = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        buttonSoundID = buttonSounds.load(this, R.raw.default_button, 1);

        disableButtonClickSounds();
        checkPreferences();

        //Button listeners
        playAgainButton.setOnClickListener(e -> playAgainButtonClicked());
        quitButton.setOnClickListener(e -> quitButtonClicked());

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Intent intentLaunch = new Intent(FinishActivity.this, LaunchActivity.class);
                startActivity(intentLaunch);
            }
        });
    }

    private void loadInterstitialAd() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(Variables.FINISH_INTERSTITIAL_AD_UNIT_ID);
        interstitialAdRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        interstitialAd.loadAd(interstitialAdRequest);
    }

    // Pause ad view when activity is on pause
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    // Destroy ad view when activity is destroyed
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    // Resume the ads when returning to activity
    @Override
    protected void onResume() {
        super.onResume();

        if (adView != null)
            adView.resume();
    }

    //Goes to launch activity
    private void playAgainButtonClicked() {
        playButtonSound();

        if(interstitialAd.isLoaded())
            interstitialAd.show();
        else{
            Intent intentLaunch = new Intent(FinishActivity.this, LaunchActivity.class);
            startActivity(intentLaunch);
        }

    }

    //Quits app
    private void quitButtonClicked() {
        playButtonSound();

        moveTaskToBack(true);
    }

    //Also goes to main menu when back button is pressed
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    //Checks if the sounds are on or off and plays the sound
    private void playButtonSound(){
        if(isVolumeOn)
            volume = Variables.SOUND_VOLUME;
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
        playAgainButton.setSoundEffectsEnabled(false);
        quitButton.setSoundEffectsEnabled(false);
    }

    //Checks the pref file then sets isVolumeOn variable according to it
    private void checkPreferences() {
        SharedPreferences pref = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        isVolumeOn = pref.getBoolean("volume", false);
    }

    private void showBannerAd() {
        adView = findViewById(R.id.finish_banner_ad_view);
        adView.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
    }


    //Algorithm for configuring users league
    private int findLeague(int score, int biggestScorePossible){

        if(score <= biggestScorePossible*(36/36.0) && score > biggestScorePossible * (35/36.0)){
            return 35;
        }
        else if(score <= biggestScorePossible*(35/36.0) && score > biggestScorePossible * (34/36.0)){
            return 34;
        }
        else if(score <= biggestScorePossible*(34/36.0) && score > biggestScorePossible * (33/36.0)){
            return 33;
        }
        else if(score <= biggestScorePossible*(33/36.0) && score > biggestScorePossible * (32/36.0)){
            return 32;
        }
        else if(score <= biggestScorePossible*(32/36.0) && score > biggestScorePossible * (31/36.0)){
            return 31;
        }
        else if(score <= biggestScorePossible*(31/36.0) && score > biggestScorePossible * (30/36.0)){
            return 30;
        }
        else if(score <= biggestScorePossible*(30/36.0) && score > biggestScorePossible * (29/36.0)){
            return 29;
        }
        else if(score <= biggestScorePossible*(29/36.0) && score > biggestScorePossible * (28/36.0)){
            return 28;
        }
        else if(score <= biggestScorePossible*(28/36.0) && score > biggestScorePossible * (27/36.0)){
            return 27;
        }
        else if(score <= biggestScorePossible*(27/36.0) && score > biggestScorePossible * (26/36.0)){
            return 26;
        }
        else if(score <= biggestScorePossible*(26/36.0) && score > biggestScorePossible * (25/36.0)){
            return 25;
        }
        else if(score <= biggestScorePossible*(25/36.0) && score > biggestScorePossible * (24/36.0)){
            return 24;
        }
        else if(score <= biggestScorePossible*(24/36.0) && score > biggestScorePossible * (23/36.0)){
            return 23;
        }
        else if(score <= biggestScorePossible*(23/36.0) && score > biggestScorePossible * (22/36.0)){
            return 22;
        }
        else if(score <= biggestScorePossible*(22/36.0) && score > biggestScorePossible * (21/36.0)){
            return 21;
        }
        else if(score <= biggestScorePossible*(21/36.0) && score > biggestScorePossible * (20/36.0)){
            return 20;
        }
        else if(score <= biggestScorePossible*(20/36.0) && score > biggestScorePossible * (19/36.0)){
            return 19;
        }
        else if(score <= biggestScorePossible*(19/36.0) && score > biggestScorePossible * (18/36.0)){
            return 18;
        }
        else if(score <= biggestScorePossible*(18/36.0) && score > biggestScorePossible * (17/36.0)){
            return 17;
        }
        else if(score <= biggestScorePossible*(17/36.0) && score > biggestScorePossible * (16/36.0)){
            return 16;
        }
        else if(score <= biggestScorePossible*(16/36.0) && score > biggestScorePossible * (15/36.0)){
            return 15;
        }
        else if(score <= biggestScorePossible*(15/36.0) && score > biggestScorePossible * (14/36.0)){
            return 14;
        }
        else if(score <= biggestScorePossible*(14/36.0) && score > biggestScorePossible * (13/36.0)){
            return 13;
        }
        else if(score <= biggestScorePossible*(13/36.0) && score > biggestScorePossible * (12/36.0)){
            return 12;
        }
        else if(score <= biggestScorePossible*(12/36.0) && score > biggestScorePossible * (11/36.0)){
            return 11;
        }
        else if(score <= biggestScorePossible*(11/36.0) && score > biggestScorePossible * (10/36.0)){
            return 10;
        }
        else if(score <= biggestScorePossible*(10/36.0) && score > biggestScorePossible * (9/36.0)){
            return 9;
        }
        else if(score <= biggestScorePossible*(9/36.0) && score > biggestScorePossible * (8/36.0)){
            return 8;
        }
        else if(score <= biggestScorePossible*(8/36.0) && score > biggestScorePossible * (7/36.0)){
            return 7;
        }
        else if(score <= biggestScorePossible*(7/36.0) && score > biggestScorePossible * (6/36.0)){
            return 6;
        }
        else if(score <= biggestScorePossible*(6/36.0) && score > biggestScorePossible * (5/36.0)){
            return 5;
        }
        else if(score <= biggestScorePossible*(5/36.0) && score > biggestScorePossible * (4/36.0)){
            return 4;
        }
        else if(score <= biggestScorePossible*(4/36.0) && score > biggestScorePossible * (3/36.0)){
            return 3;
        }
        else if(score <= biggestScorePossible*(3/36.0) && score > biggestScorePossible * (2/36.0)){
            return 2;
        }
        else if(score <= biggestScorePossible*(2/36.0) && score > biggestScorePossible * (1/36.0)){
            return 1;
        }
        else{
            return 0;
        }
    }

}
