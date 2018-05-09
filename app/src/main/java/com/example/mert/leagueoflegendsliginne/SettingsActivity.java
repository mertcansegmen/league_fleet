package com.example.mert.leagueoflegendsliginne;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    //View variables
    private Switch soundSwitch;
    private Button rateButton;
    private Button contactButton;
    TextView contactTextView;

    //Variables about preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    //Variables about sound
    SoundPool buttonSounds;
    int buttonSoundID;
    float volume;
    boolean isSwitchOn;
    //boolean isVolumeOn; -> instead of this i am using isSwitchOn variable which will give me the same result

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Initialize views
        soundSwitch = findViewById(R.id.sound_switch);
        rateButton = findViewById(R.id.rate_button);
        contactButton = findViewById(R.id.contact_button);
        contactTextView = findViewById(R.id.contact_text_view);

        //Creating the soundpool for button sound
        buttonSounds = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        buttonSoundID = buttonSounds.load(this, R.raw.default_button, 1);

        //Switch listener
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveInfo();
                checkPreferences();
            }
        });

        //Sets the pref file
        pref = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = pref.edit();

        disableButtonClickSounds();
        checkPreferences();

        //Button listeners
        rateButton.setOnClickListener(e -> rateButtonClicked());
        contactButton.setOnClickListener( e -> contactButtonClicked());

    }

    //When contact button is clicked it makes the text field that holds contact info visible
    private void contactButtonClicked() {
        playButtonSound();

        contactTextView.setVisibility(View.VISIBLE);
    }

    //When rate app button is clicked it opens the apps googleplay page
    private void rateButtonClicked() {
        playButtonSound();

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.facebook.katana"));
        startActivity(intent);
    }

    //Checks from the prefs if volume is on or off and sets the switch value
    private void checkPreferences(){
        isSwitchOn = pref.getBoolean("volume",false);
        soundSwitch.setChecked(isSwitchOn);
    }

    //Checks if the sounds are on or off and plays the sound
    private void playButtonSound(){
        if(isSwitchOn)
            volume = 1f;
        else
            volume = 0f;

        buttonSounds.play(buttonSoundID, volume, volume, 0, 0, 1.0f);
    }

    //Checks the switch value and sets the value column of the pref file
    private void saveInfo(){
        editor.putBoolean("volume", soundSwitch.isChecked());
        editor.apply();
    }

    //Disables the default button sounds
    private void disableButtonClickSounds() {
        soundSwitch.setSoundEffectsEnabled(false);
        rateButton.setSoundEffectsEnabled(false);
        contactButton.setSoundEffectsEnabled(false);
    }

}
