package com.example.mac_os.geoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mUserAnswer;
    private boolean mCheater;

    public Question(int textResId, boolean answerTrue ,boolean userAnswer ,boolean IsCheater) {
        mTextResId  = textResId;
        mAnswerTrue = answerTrue;
        mUserAnswer = userAnswer;
        mCheater    = IsCheater;

    }

    public boolean isCheater() {
        return mCheater;
    }

    public void setCheater(boolean cheater) {
        mCheater = cheater;
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
