package com.example.mert.leagueoflegendsliginne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView questionTextView;
    ImageView questionImageView;
    Button aButton, bButton, cButton, dButton;
    ProgressBar progressBar;

    int atQuestion;
    int score, biggestScorePossible, trueCount;

    int questionID;
    int imageID;
    int[] answerIDs;


    // 590 x 332
    private Question[] questionBank = new Question[]{
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
        new Question(R.string.question_6, R.drawable.question_image_6, 'd', 2,
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
        new Question(R.string.question_19, R.drawable.question_image_19, 'a', 3,
                    new int[]{R.string.answer_19a, R.string.answer_19b, R.string.answer_19c, R.string.answer_19d}),
        new Question(R.string.question_20, R.drawable.question_image_20, 'd', 2,
                    new int[]{R.string.answer_20a, R.string.answer_20b, R.string.answer_20c, R.string.answer_20d}),
        new Question(R.string.question_21, R.drawable.question_image_21, 'c', 2,
                    new int[]{R.string.answer_21a, R.string.answer_21b, R.string.answer_21c, R.string.answer_21d}),
        new Question(R.string.question_22, R.drawable.question_image_22, 'd', 3,
                    new int[]{R.string.answer_22a, R.string.answer_22b, R.string.answer_22c, R.string.answer_22d}),
        new Question(R.string.question_23, R.drawable.question_image_23, 'b', 2,
                    new int[]{R.string.answer_23a, R.string.answer_23b, R.string.answer_23c, R.string.answer_23d}),
        new Question(R.string.question_24, R.drawable.question_image_24, 'a', 2,
                    new int[]{R.string.answer_24a, R.string.answer_24b, R.string.answer_24c, R.string.answer_24d}),
        new Question(R.string.question_25, R.drawable.question_image_2_25_49, 'a', 2,
                    new int[]{R.string.answer_25a, R.string.answer_25b, R.string.answer_25c, R.string.answer_25d}),
        new Question(R.string.question_26, R.drawable.question_image_26, 'd', 3,
                    new int[]{R.string.answer_26a, R.string.answer_26b, R.string.answer_26c, R.string.answer_26d}),
        new Question(R.string.question_27, R.drawable.question_image_27_41, 'c', 1,
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
        new Question(R.string.question_41, R.drawable.question_image_27_41, 'd', 3,
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
                    new int[]{R.string.answer_54a, R.string.answer_54b, R.string.answer_54c, R.string.answer_54d})
    };


    final int PROGRESS_BAR_INCREMENT = 6;
    final int NUMBER_OF_QUESTIONS = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] questionIndexes = findIndexes();

        Question[] questions = new Question[NUMBER_OF_QUESTIONS];

        for(int i=0; i<NUMBER_OF_QUESTIONS; i++)
            questions[i] = questionBank[questionIndexes[i]];

        questionTextView = findViewById(R.id.question_text_view);
        questionImageView = findViewById(R.id.question_image_view);
        progressBar = findViewById(R.id.progress_bar);
        aButton = findViewById(R.id.button_a);
        bButton = findViewById(R.id.button_b);
        cButton = findViewById(R.id.start_button);
        dButton = findViewById(R.id.button_d);

        answerIDs = questions[atQuestion].getAnswerIDs();
        questionID = questions[atQuestion].getQuestionID();
        imageID = questions[atQuestion].getImageID();

        questionTextView.setText(questionID);
        questionImageView.setImageResource(imageID);
        aButton.setText(answerIDs[0]);
        bButton.setText(answerIDs[1]);
        cButton.setText(answerIDs[2]);
        dButton.setText(answerIDs[3]);

        aButton.setOnClickListener(e -> aButtonClicked(questions));
        bButton.setOnClickListener(e -> bButtonClicked(questions));
        cButton.setOnClickListener(e -> cButtonClicked(questions));
        dButton.setOnClickListener(e -> dButtonClicked(questions));

    }

    private void updateQuestion(Question[] questions){
        atQuestion = (atQuestion + 1) % questions.length;
        progressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        if(atQuestion == 0){
            Intent intent = new Intent(MainActivity.this, FinishActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("biggestScorePossible", biggestScorePossible);
            intent.putExtra("trueCount", trueCount);
            startActivity(intent);
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
    }

    private void checkAnswer(char userSelection, Question[] questions){
        biggestScorePossible += questions[atQuestion].getDifficulty();
        if(userSelection == questions[atQuestion].getAnswer()){
            Toast.makeText(this, R.string.dogru_toast, Toast.LENGTH_SHORT).show();
            score += questions[atQuestion].getDifficulty();
            trueCount++;
        }
        else{
            Toast.makeText(this, R.string.yanlis_toast, Toast.LENGTH_SHORT).show();
        }
    }

    private int[] findIndexes(){
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

    private void aButtonClicked(Question[] questions){
        checkAnswer('a', questions);
        updateQuestion(questions);
    }

    private void bButtonClicked(Question[] questions){
        checkAnswer('b', questions);
        updateQuestion(questions);
    }

    private void cButtonClicked(Question[] questions){
        checkAnswer('c', questions);
        updateQuestion(questions);
    }

    private void dButtonClicked(Question[] questions){
        checkAnswer('d', questions);
        updateQuestion(questions);
    }

}