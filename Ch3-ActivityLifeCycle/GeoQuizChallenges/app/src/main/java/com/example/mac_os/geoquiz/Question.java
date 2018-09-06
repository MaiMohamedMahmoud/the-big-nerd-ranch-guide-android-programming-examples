package com.example.mac_os.geoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mUserAnswer;

    public Question(int textResId, boolean answerTrue ) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;

    }

    public boolean isUserAnswer() {
        return mUserAnswer;
    }

    public void setUserAnswer(boolean userAnswer) {
        mUserAnswer = userAnswer;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
