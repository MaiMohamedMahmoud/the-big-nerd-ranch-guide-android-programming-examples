package com.example.mac_os.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class QuizActivity extends AppCompatActivity {

    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestionTextView;

    /**
     * we made next and prev button as imagebutton according to challenge 3 ...
     */
    ImageButton mNextButton;
    ImageButton mPrevButton;

    int mCurrentIndex = 0;

    private Question[] mQuestionBank = new Question[]{
      new Question(R.string.question_australia,true),
      new Question(R.string.question_Oceans,true),
      new Question(R.string.question_mideast,false),
      new Question(R.string.question_africa,false),
      new Question(R.string.question_americas,true),
      new Question(R.string.question_asia,true),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_btn);
        mPrevButton = (ImageButton) findViewById(R.id.previous_button);

        UpdateQuestion();

        /**
         * The following is Challenge One in ch2 (MVC).
         * challenge about making textview clickable and view the next question as well
         */

        /**
         *
            mQuestionTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCurrentIndex < (mQuestionBank.length -1) ) {
                        mCurrentIndex++;
                    }
                    else
                    {
                        mCurrentIndex = 0;
                    }
                    UpdateQuestion();
                }
            });

         **/
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex < (mQuestionBank.length -1) ) {
                    mCurrentIndex++;
                }
                else
                {
                    mCurrentIndex = 0;
                }
                UpdateQuestion();

            }
        });
        /**
         * Challenge Number 2 of ch2
         * in this challenge we have to make previous button
         */
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex <= 0) {
                    mCurrentIndex=mQuestionBank.length-1;
                }
                else
                {
                    mCurrentIndex--;
                }
                UpdateQuestion();
            }
        });



        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAnswer(false);
            }
        });

    }

    private void UpdateQuestion() {
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
    }

    private void CheckAnswer(boolean UserPressed)
    {
        int messageResID= 0;

        if(UserPressed == mQuestionBank[mCurrentIndex].isAnswerTrue())
        {
            messageResID =R.string.correct_toast;
        }
        else
        {
            messageResID = R.string.incorrect_toast;
        }

        Toast.makeText(QuizActivity.this,messageResID,Toast.LENGTH_SHORT).show();
    }
}
