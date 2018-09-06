package com.example.mac_os.geoquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CheatActivity extends AppCompatActivity {

    String CorrectAnswer = "CorrectAnswer";
    String UserCheat = "UserChear";
    boolean Answer;
    TextView mAnswerTextView;
    Button mAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

         Answer = getIntent().getBooleanExtra(CorrectAnswer,false);

         mAnswerButton = (Button) findViewById(R.id.answer_button);
         mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

         mAnswerButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                     mAnswerTextView.setText("THE ANSWER TO QUESTION IS " + Answer);
                     CheatFun(true);
             }
         });
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
