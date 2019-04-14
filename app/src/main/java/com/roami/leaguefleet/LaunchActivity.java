package com.roami.leaguefleet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class LaunchActivity extends AppCompatActivity {

    //View variables
    Button startButton;
    Button quitButton;
    Button settingsButton;

    //Variables about sound
    SoundPool buttonSounds;
    int buttonSoundID;
    float volume;
    boolean isVolumeOn;

    //Variables about ads
    AdView adView;
    AdRequest bannerAdRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        //Initialize views
        startButton = findViewById(R.id.start_button);
        settingsButton = findViewById(R.id.settings_button);
        quitButton = findViewById(R.id.quit_button);
        adView = findViewById(R.id.launcher_banner_ad_view);

        MobileAds.initialize(this, Variables.ADMOB_APP_ID);
        showBannerAd();

        //Creating the sound pool for button sound
        buttonSounds = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        buttonSoundID = buttonSounds.load(this, R.raw.default_button, 1);

        disableButtonClickSounds();
        checkPreferences();

        //Button listeners
        startButton.setOnClickListener(e -> startButtonClicked());
        settingsButton.setOnClickListener(e -> settingsButtonClicked());
        quitButton.setOnClickListener(e -> quitButtonClicked());

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

    //When the settings activity is finished by pressing back button, it has to check the preferences because launch activity will not
    //run onCreate() method again
    // Resume the ads when returning to activity
    @Override
    protected void onResume() {
        super.onResume();
        checkPreferences();

        if (adView != null)
            adView.resume();
    }

    //Checks the pref file then sets isVolumeOn variable according to it
    private void checkPreferences() {
        SharedPreferences pref = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        isVolumeOn = pref.getBoolean("volume", false);
    }

    //Starts the quiz
    private void startButtonClicked(){
        playButtonSound();

        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //Goes to settings
    private void settingsButtonClicked() {
        playButtonSound();

        Intent intent = new Intent(LaunchActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    //Quits app
    private void quitButtonClicked() {
        playButtonSound();

        moveTaskToBack(true);
    }

    //Checks if the sounds are on or off and plays the sound
    private void playButtonSound(){
        if(isVolumeOn)
            volume = Variables.SOUND_VOLUME;
        else
            volume = 0;

        buttonSounds.play(buttonSoundID, volume, volume, 0, 0, 1.0f);
    }

    //Disables the default button sounds
    private void disableButtonClickSounds() {
        startButton.setSoundEffectsEnabled(false);
        settingsButton.setSoundEffectsEnabled(false);
        quitButton.setSoundEffectsEnabled(false);
    }

    private void showBannerAd() {
        bannerAdRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(bannerAdRequest);
    }
}
