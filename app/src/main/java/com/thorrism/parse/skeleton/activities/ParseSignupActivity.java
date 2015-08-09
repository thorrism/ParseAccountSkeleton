package com.thorrism.parse.skeleton.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.thorrism.R;
import com.thorrism.designtools.views.RaisedButton;
import com.thorrism.designtools.views.ShakeEditText;
import com.thorrism.parse.skeleton.LandingActivity;
import com.thorrism.parse.skeleton.tasks.ParseSignupTask;
import com.thorrism.parse.skeleton.validators.NameValidator;
import com.thorrism.parse.skeleton.validators.PasswordValidator;

import interfaces.FromXml;
import validators.Validator;

public class ParseSignupActivity extends AppCompatActivity implements ParseSignupTask.SignupCallback {

    private ShakeEditText mPasswordEdit;
    private ShakeEditText mPasswordConfirmEdit;
    private ShakeEditText mNameEdit;
    private ScrollView mScrollView;
    private RaisedButton mSignupBtn;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mPasswordEdit = (ShakeEditText) findViewById(R.id.signup_password_edit);
        mPasswordConfirmEdit = (ShakeEditText) findViewById(R.id.signup_password_confirm_edit);
        mNameEdit = (ShakeEditText) findViewById(R.id.signup_user_name_edit);
        mScrollView = (ScrollView) findViewById(R.id.signup_scroll_body);
        mSignupBtn = (RaisedButton) findViewById(R.id.signup_btn);
        setupToolbar();
        setupValidators();
        attachEditListener();
    }

    private void setupValidators(){
        mPasswordEdit.setValidationListener(new PasswordValidator(mPasswordEdit));
        mNameEdit.setValidationListener(new NameValidator(mNameEdit));
        mPasswordConfirmEdit.setValidationListener(new Validator() {
            @Override
            public boolean validate() {
                return mPasswordConfirmEdit.getText().toString().equals(
                        mPasswordEdit.getText().toString());
            }
        });
    }

    private void setupToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Test", "test");
                    finish();
                }
            });

        }
    }

    /**
     * Show the login button when both the name edit and password edit have input.
     * Also, scroll the scrollview to the bottom for better visibility.
     */
    private void attachEditListener(){
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if(mNameEdit.getText().length() > 0
                        && mPasswordEdit.getText().length() > 0
                        && mPasswordConfirmEdit.getText().length() > 0)
                    mSignupBtn.setVisibility(View.VISIBLE);
                else
                    mSignupBtn.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        mPasswordEdit.addTextChangedListener(tw);
        mPasswordConfirmEdit.addTextChangedListener(tw);
        mNameEdit.addTextChangedListener(tw);
    }

    @FromXml
    public void attemptSignup(View v){
        if(mNameEdit.checkValidInput() && mPasswordEdit.checkValidInput()
                && mPasswordConfirmEdit.checkValidInput(getResources().getString(R.string.password_error_message_match))){
            ParseSignupTask task = new ParseSignupTask(this);
            task.execute(mNameEdit.getText().toString(),
                    mPasswordEdit.getText().toString());
        }
    }

    @Override
    public void onSignupSuccess(){
        //Go to main activity screen!
        startActivity(new Intent(ParseSignupActivity.this, LandingActivity.class));
    }

}
