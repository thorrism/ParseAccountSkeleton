package com.thorrism.designtools.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.thorrism.designtools.R;

import java.util.ArrayList;
import java.util.HashMap;

import validators.Validator;

/**
 * Created by Lucas Crawford on 7/21/2015.
 */
public class ShakeEditText extends AppCompatEditText {
    private Validator mValidationListener;
    private TranslateAnimation mAnimation;
    private String mDefaultError;

    public ShakeEditText(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ShakeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ShakeEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShakeEditText, defStyle, 0);
        mDefaultError = a.getString(R.styleable.ShakeEditText_defaultError);
        a.recycle();
        init(context, attrs, defStyle);
    }

    public void setDefaultError(String message){
        mDefaultError = message;
    }

    public void setValidationListener(Validator listener) {
        mValidationListener = listener;
    }

    /**
     * Check if the current input for the EditText is valid using
     * user's defined validation listener with default error.
     * @return true if valid, false if not
     */
    public boolean checkValidInput() {
        if (mValidationListener != null &&
                !mValidationListener.validate()) {
            setInvalid(mDefaultError);
            return false;
        }
        return true;
    }

    /**
     * Check if the current input for the EditText is valid
     * using defined validation listener with custom error.
     * @param message - custom error message for this particular invalid input.
     * @return true if valid false otherwise.
     */
    public boolean checkValidInput(@NonNull String message){
        if (mValidationListener != null &&
                !mValidationListener.validate()) {
            setInvalid(message);
            return false;
        }
        return true;
    }

    /**
     * Shake the EditText and apply the desired error message.
     * @param message - error message we wish to apply
     */
    public void setInvalid(String message){
        shakeView();
        if(message != null)
            setError(message);
        else
            setError("Invalid input!");
        requestFocus();
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShakeEditText, defStyle, 0);
        mDefaultError = a.getString(R.styleable.ShakeEditText_defaultError);
        a.recycle();

        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (EditorInfo.IME_ACTION_DONE == actionId) {
                    checkValidInput();
                }
                return false;
            }
        });

        //set animation
        mAnimation = new TranslateAnimation(0, 8, 0, 0);
        mAnimation.setInterpolator(new CycleInterpolator(5));
        mAnimation.setDuration(300);

    }

    private void shakeView() {
        setAnimation(mAnimation);
        post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
    }
}
