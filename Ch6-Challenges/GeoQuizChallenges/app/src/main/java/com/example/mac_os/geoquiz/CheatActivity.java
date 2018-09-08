package com.example.mac_os.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    String CorrectAnswer = "CorrectAnswer";
    String Key_IsCheat ="IsCheat";
    String UserCheat = "UserCheat";
    boolean Answer;
    TextView mAnswerTextView;
    Button mAnswerButton;
    Boolean mIsCheat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

         Answer = getIntent().getBooleanExtra(CorrectAnswer,false);
         if(savedInstanceState != null)
         {
             mIsCheat = savedInstanceState.getBoolean(Key_IsCheat);
         }
         if(mIsCheat)
         {
             CheatFun(mIsCheat);
         }
         mAnswerButton = (Button) findViewById(R.id.answer_button);
         mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

         mAnswerButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                     mAnswerTextView.setText("THE ANSWER TO QUESTION IS " + Answer);
                     mIsCheat = true;
                     CheatFun(mIsCheat);

                     int cx = mAnswerButton.getWidth()/2;
                     int cy = mAnswerButton.getHeight()/2;
                     float radius = mAnswerButton.getWidth();
                 Animator anim = null;
                 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                     anim = ViewAnimationUtils.createCircularReveal(mAnswerButton,cx,cy,radius,0);
                     anim.addListener(new AnimatorListenerAdapter() {
                         @Override
                         public void onAnimationEnd(Animator animation) {
                             super.onAnimationEnd(animation);
                             mAnswerButton.setVisibility(View.INVISIBLE);
                         }
                     });
                     anim.start();
                 }
                 else
                 {
                     mAnswerButton.setVisibility(View.INVISIBLE);
                 }

             }
         });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Key_IsCheat,mIsCheat);

    }

    public boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(UserCheat,false);
    }

    public void CheatFun(boolean ISCheat){
        Intent i = new Intent();
        i.putExtra(UserCheat,ISCheat);
        setResult(RESULT_OK,i);
    }


    public Intent NewIntent(Context packageContext  ,boolean data )
    {
        Intent i = new Intent(packageContext,CheatActivity.class);
        i.putExtra(CorrectAnswer,data);
        return i;
    }
}
