package com.example.mac_os.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.util.Log;
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
    Button mCheatButton;
    /**
     * we made next and prev button as imagebutton according to challenge 3 ...
     */
    ImageButton mNextButton;
    ImageButton mPrevButton;

    int mCurrentIndex = 0;
    int Grade =0;
    int AllQuestion =0;
    String Key_Index = "Index";
    String Key_Cheater = "Cheater";
    int mRequestCode = 0;
    boolean mIsCheater;
    String TAG = "QuizActivity";
    TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
      new Question(R.string.question_australia,true,false,false),
      new Question(R.string.question_Oceans,true,false,false),
      new Question(R.string.question_mideast,false,false,false),
      new Question(R.string.question_africa,false,false,false),
      new Question(R.string.question_americas,true,false,false),
      new Question(R.string.question_asia,true,false,false),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if(savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt(Key_Index);
            mIsCheater = savedInstanceState.getBoolean(Key_Cheater);
        }
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_btn);
        mPrevButton = (ImageButton) findViewById(R.id.previous_button);
        UpdateQuestion();

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
                Log.d("UserAnswer of " + mQuestionBank[mCurrentIndex].getTextResId() ,"  is  "+ mQuestionBank[mCurrentIndex].isUserAnswer());
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
                Log.d("UserAnswer of " + mQuestionBank[mCurrentIndex].getTextResId() ,"  is  "+ mQuestionBank[mCurrentIndex].isUserAnswer());
                UpdateQuestion();
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new CheatActivity().NewIntent(QuizActivity.this,mQuestionBank[mCurrentIndex].isAnswerTrue());
                startActivityForResult(i,mRequestCode);
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
        if(mQuestionBank[mCurrentIndex].isCheater()) {
            mIsCheater = true;
        }
        else
        {
            mIsCheater = false;
        }
        if(mQuestionBank[mCurrentIndex].isUserAnswer())
        {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }
        else
        {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
    }

    private void CheckAnswer(boolean UserPressed)
    {
        int messageResID= 0;
        if(mIsCheater) {
            messageResID = R.string.judgment_toast;
        }else
        {
            if(UserPressed == mQuestionBank[mCurrentIndex].isAnswerTrue())
            {
                messageResID =R.string.correct_toast;
                Grade ++;
            }
            else
            {
                messageResID = R.string.incorrect_toast;
            }
        }
        mQuestionBank[mCurrentIndex].setUserAnswer(true);
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
        AllQuestion++;
        Toast.makeText(QuizActivity.this,messageResID,Toast.LENGTH_SHORT).show();
        ShowGrade();
    }

    private void ShowGrade() {
        if (mQuestionBank.length == AllQuestion) {
            Toast.makeText(QuizActivity.this, "The Grade is " + Grade, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"OnSaveInstance");
        outState.putInt(Key_Index,mCurrentIndex);
        outState.putBoolean(Key_Cheater,mIsCheater);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == mRequestCode){
            if(data == null)
            {
                return;
            }
            mIsCheater =  new CheatActivity().wasAnswerShown(data);
            mQuestionBank[mCurrentIndex].setCheater(mIsCheater);
        }
    }
}