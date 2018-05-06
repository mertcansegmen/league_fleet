package com.example.mert.leagueoflegendsliginne;

import android.app.Activity;
import android.app.assist.AssistContent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    ImageView questionImageView;
    TextView questionTextView;
    ProgressBar circularProgressBar;
    TextView questionTimerTextView;
    Button aButton, bButton, cButton, dButton;
    ProgressBar progressBar;

    int atQuestion;
    int score, biggestScorePossible, trueCount;

    Handler handler;
    CountDownTimer counter;

    int questionID;
    int imageID;
    int[] answerIDs;

    SoundPool buttonSounds;
    int trueButtonSoundID;
    int falseButtonSoundID;

    Question[] questionBank = createQuestionBank();
    Question[] questions;


    final int PROGRESS_BAR_MAX = 1000;
    final int PROGRESS_BAR_INCREMENT = 59;
    final int CIRCULAR_PROGRESS_BAR_INCREMENT = -33;
    final int NUMBER_OF_QUESTIONS = 17;
    final int BUTTON_COLOR_CHANGING_TIME = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");

        int[] questionIndexes = findRandomIndexes();

        questions = new Question[NUMBER_OF_QUESTIONS];

        for(int i=0; i<NUMBER_OF_QUESTIONS; i++)
            questions[i] = questionBank[questionIndexes[i]];

        buttonSounds = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        trueButtonSoundID = buttonSounds.load(this, R.raw.correct, 1);
        falseButtonSoundID = buttonSounds.load(this, R.raw.incorrect,1);

        questionImageView = findViewById(R.id.question_image_view);
        questionTextView = findViewById(R.id.question_text_view);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        questionTimerTextView = findViewById(R.id.questionTimerTextView);
        aButton = findViewById(R.id.button_a);
        bButton = findViewById(R.id.button_b);
        cButton = findViewById(R.id.start_button);
        dButton = findViewById(R.id.button_d);
        progressBar = findViewById(R.id.progress_bar);

        disableButtonClickSounds();

        updateQuestion();

        aButton.setOnClickListener(e -> buttonClicked('a', aButton));
        bButton.setOnClickListener(e -> buttonClicked('b', bButton));
        cButton.setOnClickListener(e -> buttonClicked('c', cButton));
        dButton.setOnClickListener(e -> buttonClicked('d', dButton));

    }

    private void buttonClicked(char userSelection, Button button) {
        disableButtons();

        // Storing sum of all the questions difficulties
        biggestScorePossible += questions[atQuestion].getDifficulty();
        progressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        counter.cancel();

        handler = new Handler();

        if(userSelection == questions[atQuestion].getAnswer()){
            // Increases the score depending on difficulty of the question
            buttonSounds.play(trueButtonSoundID, 0.5f, 0.5f, 0, 0, 1.0f);
            button.setBackgroundResource(R.color.colorTrueButton);
            score += questions[atQuestion].getDifficulty();
            trueCount++;

            handler.postDelayed(new Runnable() {
                public void run() {
                    button.setBackgroundResource(R.color.colorButton);
                    atQuestion = (atQuestion + 1);
                    updateQuestion();
                }
            }, BUTTON_COLOR_CHANGING_TIME);

        }

        else{
            buttonSounds.play(falseButtonSoundID, 0.5f, 0.5f, 0, 0, 1.0f);
            button.setBackgroundResource(R.color.colorFalseButton);
            handler.postDelayed(new Runnable() {
                public void run() {
                    button.setBackgroundResource(R.color.colorButton);
                    atQuestion = (atQuestion + 1);
                    updateQuestion();
                }
            }, BUTTON_COLOR_CHANGING_TIME);
        }
    }

    private void updateQuestion(){

        if(atQuestion == NUMBER_OF_QUESTIONS){
            Intent intent = new Intent(MainActivity.this, FinishActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("biggestScorePossible", biggestScorePossible);
            intent.putExtra("trueCount", trueCount);
            startActivity(intent);
            return;
        }

        questionID = questions[atQuestion].getQuestionID();
        imageID = questions[atQuestion].getImageID();
        answerIDs = questions[atQuestion].getAnswerIDs();

        questionTextView.setText(questionID);
        questionImageView.setImageResource(imageID);
        aButton.setText(answerIDs[0]);
        bButton.setText(answerIDs[1]);
        cButton.setText(answerIDs[2]);
        dButton.setText(answerIDs[3]);
        circularProgressBar.setProgress(PROGRESS_BAR_MAX);

        counter = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                questionTimerTextView.setText("" +(millisUntilFinished / 1000));
                circularProgressBar.incrementProgressBy(CIRCULAR_PROGRESS_BAR_INCREMENT);
            }

            @Override
            public void onFinish() {
                atQuestion++;
                updateQuestion();
            }
        };
        counter.start();

        enableButtons();

    }

    private int[] findRandomIndexes(){
        Random rnd = new Random();
        int[] array = new int[NUMBER_OF_QUESTIONS];
        for(int i=0; i<array.length; i++){
            array[i] = rnd.nextInt(questionBank.length);
            for(int j=0; j<i; j++){
                if(array[i] == array[j]){
                    i--;
                    break;
                }
            }
        }
        return array;
    }

    private void disableButtonClickSounds() {
        aButton.setSoundEffectsEnabled(false);
        bButton.setSoundEffectsEnabled(false);
        cButton.setSoundEffectsEnabled(false);
        dButton.setSoundEffectsEnabled(false);
    }

    private void disableButtons(){
        aButton.setClickable(false);
        bButton.setClickable(false);
        cButton.setClickable(false);
        dButton.setClickable(false);
    }

    private void enableButtons(){
        aButton.setClickable(true);
        bButton.setClickable(true);
        cButton.setClickable(true);
        dButton.setClickable(true);

    }

    private Question[] createQuestionBank(){
        return new Question[]{
            new Question(R.string.question_1, R.drawable.question_image_1, 'c', 2,
                        new int[]{R.string.answer_1a, R.string.answer_1b, R.string.answer_1c, R.string.answer_1d}),
            new Question(R.string.question_2, R.drawable.question_image_2_25_49, 'b', 1,
                        new int[]{R.string.answer_2a, R.string.answer_2b, R.string.answer_2c, R.string.answer_2d}),
            new Question(R.string.question_3, R.drawable.question_image_3, 'c', 3,
                        new int[]{R.string.answer_3a, R.string.answer_3b, R.string.answer_3c, R.string.answer_3d}),
            new Question(R.string.question_4, R.drawable.question_image_4, 'd', 1,
                        new int[]{R.string.answer_4a, R.string.answer_4b, R.string.answer_4c, R.string.answer_4d}),
            new Question(R.string.question_5, R.drawable.question_image_5, 'a', 4,
                        new int[]{R.string.answer_5a, R.string.answer_5b, R.string.answer_5c, R.string.answer_5d}),
            new Question(R.string.question_6, R.drawable.question_image_6_27_41, 'd', 2,
                        new int[]{R.string.answer_6a, R.string.answer_6b, R.string.answer_6c, R.string.answer_6d}),
            new Question(R.string.question_7, R.drawable.question_image_7, 'a', 3,
                        new int[]{R.string.answer_7a, R.string.answer_7b, R.string.answer_7c, R.string.answer_7d}),
            new Question(R.string.question_8, R.drawable.question_image_8, 'c', 2,
                        new int[]{R.string.answer_8a, R.string.answer_8b, R.string.answer_8c, R.string.answer_8d}),
            new Question(R.string.question_9, R.drawable.question_image_9_51_52, 'b', 3,
                        new int[]{R.string.answer_9a, R.string.answer_9b, R.string.answer_9c, R.string.answer_9d}),
            new Question(R.string.question_10, R.drawable.question_image_10, 'd', 3,
                        new int[]{R.string.answer_10a, R.string.answer_10b, R.string.answer_10c, R.string.answer_10d}),
            new Question(R.string.question_11, R.drawable.question_image_11, 'c', 2,
                        new int[]{R.string.answer_11a, R.string.answer_11b, R.string.answer_11c, R.string.answer_11d}),
            new Question(R.string.question_12, R.drawable.question_image_12, 'b', 3,
                        new int[]{R.string.answer_12a, R.string.answer_12b, R.string.answer_12c, R.string.answer_12d}),
            new Question(R.string.question_13, R.drawable.question_image_13, 'b', 2,
                        new int[]{R.string.answer_13a, R.string.answer_13b, R.string.answer_13c, R.string.answer_13d}),
            new Question(R.string.question_14, R.drawable.question_image_14_34, 'c', 3,
                        new int[]{R.string.answer_14a, R.string.answer_14b, R.string.answer_14c, R.string.answer_14d}),
            new Question(R.string.question_15, R.drawable.question_image_15_33, 'b', 2,
                        new int[]{R.string.answer_15a, R.string.answer_15b, R.string.answer_15c, R.string.answer_15d}),
            new Question(R.string.question_16, R.drawable.question_image_16, 'a', 1,
                        new int[]{R.string.answer_16a, R.string.answer_16b, R.string.answer_16c, R.string.answer_16d}),
            new Question(R.string.question_17, R.drawable.question_image_17, 'c', 4,
                        new int[]{R.string.answer_17a, R.string.answer_17b, R.string.answer_17c, R.string.answer_17d}),
            new Question(R.string.question_18, R.drawable.question_image_18, 'a', 3,
                        new int[]{R.string.answer_18a, R.string.answer_18b, R.string.answer_18c, R.string.answer_18d}),
            new Question(R.string.question_19, R.drawable.question_image_19_20_21_22_23_24, 'a', 3,
                        new int[]{R.string.answer_19a, R.string.answer_19b, R.string.answer_19c, R.string.answer_19d}),
            new Question(R.string.question_20, R.drawable.question_image_19_20_21_22_23_24, 'd', 2,
                        new int[]{R.string.answer_20a, R.string.answer_20b, R.string.answer_20c, R.string.answer_20d}),
            new Question(R.string.question_21, R.drawable.question_image_19_20_21_22_23_24, 'c', 2,
                        new int[]{R.string.answer_21a, R.string.answer_21b, R.string.answer_21c, R.string.answer_21d}),
            new Question(R.string.question_22, R.drawable.question_image_19_20_21_22_23_24, 'd', 3,
                        new int[]{R.string.answer_22a, R.string.answer_22b, R.string.answer_22c, R.string.answer_22d}),
            new Question(R.string.question_23, R.drawable.question_image_19_20_21_22_23_24, 'b', 2,
                        new int[]{R.string.answer_23a, R.string.answer_23b, R.string.answer_23c, R.string.answer_23d}),
            new Question(R.string.question_24, R.drawable.question_image_19_20_21_22_23_24, 'a', 2,
                        new int[]{R.string.answer_24a, R.string.answer_24b, R.string.answer_24c, R.string.answer_24d}),
            new Question(R.string.question_25, R.drawable.question_image_2_25_49, 'a', 2,
                        new int[]{R.string.answer_25a, R.string.answer_25b, R.string.answer_25c, R.string.answer_25d}),
            new Question(R.string.question_26, R.drawable.question_image_26, 'd', 3,
                        new int[]{R.string.answer_26a, R.string.answer_26b, R.string.answer_26c, R.string.answer_26d}),
            new Question(R.string.question_27, R.drawable.question_image_6_27_41, 'c', 1,
                        new int[]{R.string.answer_27a, R.string.answer_27b, R.string.answer_27c, R.string.answer_27d}),
            new Question(R.string.question_28, R.drawable.question_image_28, 'd', 4,
                        new int[]{R.string.answer_28a, R.string.answer_28b, R.string.answer_28c, R.string.answer_28d}),
            new Question(R.string.question_29, R.drawable.question_image_29, 'a', 4,
                        new int[]{R.string.answer_29a, R.string.answer_29b, R.string.answer_29c, R.string.answer_29d}),
            new Question(R.string.question_30, R.drawable.question_image_30, 'b', 4,
                        new int[]{R.string.answer_30a, R.string.answer_30b, R.string.answer_30c, R.string.answer_30d}),
            new Question(R.string.question_31, R.drawable.question_image_31, 'b', 4,
                        new int[]{R.string.answer_31a, R.string.answer_31b, R.string.answer_31c, R.string.answer_31d}),
            new Question(R.string.question_32, R.drawable.question_image_32, 'b', 2,
                        new int[]{R.string.answer_32a, R.string.answer_32b, R.string.answer_32c, R.string.answer_32d}),
            new Question(R.string.question_33, R.drawable.question_image_15_33, 'c', 2,
                        new int[]{R.string.answer_33a, R.string.answer_33b, R.string.answer_33c, R.string.answer_33d}),
            new Question(R.string.question_34, R.drawable.question_image_14_34, 'd', 3,
                        new int[]{R.string.answer_34a, R.string.answer_34b, R.string.answer_34c, R.string.answer_34d}),
            new Question(R.string.question_35, R.drawable.question_image_35, 'a', 1,
                        new int[]{R.string.answer_35a, R.string.answer_35b, R.string.answer_35c, R.string.answer_35d}),
            new Question(R.string.question_36, R.drawable.question_image_36, 'b', 1,
                        new int[]{R.string.answer_36a, R.string.answer_36b, R.string.answer_36c, R.string.answer_36d}),
            new Question(R.string.question_37, R.drawable.question_image_37, 'b', 1,
                        new int[]{R.string.answer_37a, R.string.answer_37b, R.string.answer_37c, R.string.answer_37d}),
            new Question(R.string.question_38, R.drawable.question_image_38, 'd', 1,
                        new int[]{R.string.answer_38a, R.string.answer_38b, R.string.answer_38c, R.string.answer_38d}),
            new Question(R.string.question_39, R.drawable.question_image_39, 'a', 1,
                        new int[]{R.string.answer_39a, R.string.answer_39b, R.string.answer_39c, R.string.answer_39d}),
            new Question(R.string.question_40, R.drawable.question_image_40, 'c', 1,
                        new int[]{R.string.answer_40a, R.string.answer_40b, R.string.answer_40c, R.string.answer_40d}),
            new Question(R.string.question_41, R.drawable.question_image_6_27_41, 'd', 3,
                        new int[]{R.string.answer_41a, R.string.answer_41b, R.string.answer_41c, R.string.answer_41d}),
            new Question(R.string.question_42, R.drawable.question_image_42, 'c', 1,
                        new int[]{R.string.answer_42a, R.string.answer_42b, R.string.answer_42c, R.string.answer_42d}),
            new Question(R.string.question_43, R.drawable.question_image_43, 'c', 4,
                        new int[]{R.string.answer_43a, R.string.answer_43b, R.string.answer_43c, R.string.answer_43d}),
            new Question(R.string.question_44, R.drawable.question_image_44, 'd', 4,
                        new int[]{R.string.answer_44a, R.string.answer_44b, R.string.answer_44c, R.string.answer_44d}),
            new Question(R.string.question_45, R.drawable.question_image_45, 'b', 3,
                        new int[]{R.string.answer_45a, R.string.answer_45b, R.string.answer_45c, R.string.answer_45d}),
            new Question(R.string.question_46, R.drawable.question_image_46, 'b', 2,
                        new int[]{R.string.answer_46a, R.string.answer_46b, R.string.answer_46c, R.string.answer_46d}),
            new Question(R.string.question_47, R.drawable.question_image_47, 'a', 3,
                        new int[]{R.string.answer_47a, R.string.answer_47b, R.string.answer_47c, R.string.answer_47d}),
            new Question(R.string.question_48, R.drawable.question_image_48, 'a', 1,
                        new int[]{R.string.answer_48a, R.string.answer_48b, R.string.answer_48c, R.string.answer_48d}),
            new Question(R.string.question_49, R.drawable.question_image_2_25_49, 'c', 1,
                        new int[]{R.string.answer_49a, R.string.answer_49b, R.string.answer_49c, R.string.answer_49d}),
            new Question(R.string.question_50, R.drawable.question_image_50, 'd', 4,
                        new int[]{R.string.answer_50a, R.string.answer_50b, R.string.answer_50c, R.string.answer_50d}),
            new Question(R.string.question_51, R.drawable.question_image_9_51_52, 'd', 4,
                        new int[]{R.string.answer_51a, R.string.answer_51b, R.string.answer_51c, R.string.answer_51d}),
            new Question(R.string.question_52, R.drawable.question_image_9_51_52, 'c', 3,
                        new int[]{R.string.answer_52a, R.string.answer_52b, R.string.answer_52c, R.string.answer_52d}),
            new Question(R.string.question_53, R.drawable.question_image_53, 'c', 3,
                        new int[]{R.string.answer_53a, R.string.answer_53b, R.string.answer_53c, R.string.answer_53d}),
            new Question(R.string.question_54, R.drawable.question_image_54, 'c', 4,
                        new int[]{R.string.answer_54a, R.string.answer_54b, R.string.answer_54c, R.string.answer_54d}),
            new Question(R.string.question_55, R.drawable.question_image_55, 'd', 2,
                        new int[]{R.string.answer_55a, R.string.answer_55b, R.string.answer_55c, R.string.answer_55d})
        };
    }


    private static final String TAG = "Logs";

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
        counter.cancel();
        finish();
    }
}