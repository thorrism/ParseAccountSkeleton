package com.thorrism.parse.skeleton.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.thorrism.R;
import com.thorrism.designtools.views.RaisedButton;
import com.thorrism.designtools.views.ShakeEditText;
import com.thorrism.parse.skeleton.LandingActivity;
import com.thorrism.parse.skeleton.tasks.ParseLoginTask;
import com.thorrism.parse.skeleton.validators.NameValidator;
import com.thorrism.parse.skeleton.validators.PasswordValidator;

import interfaces.FromXml;

public class ParseLoginActivity extends AppCompatActivity implements ParseLoginTask.ParseLoginCallback {
    private ShakeEditText mNameEdit;
    private ShakeEditText mPasswordEdit;
    private RaisedButton mLoginBtn;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mNameEdit     = (ShakeEditText) findViewById(R.id.login_user_name_edit);
        mPasswordEdit = (ShakeEditText) findViewById(R.id.login_password_edit);
        mLoginBtn     = (RaisedButton) findViewById(R.id.login_btn);
        setupToolbar();
        setupValidators();
        attachEditListener();
    }

    /**
     * Show the login button when both the name edit and password edit have input
     */
    private void attachEditListener(){
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if(mNameEdit.getText().length() > 0
                        && mPasswordEdit.getText().length() > 0)
                    mLoginBtn.setVisibility(View.VISIBLE);
                else
                    mLoginBtn.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        mPasswordEdit.addTextChangedListener(tw);
        mNameEdit.addTextChangedListener(tw);
    }

    private void setupValidators(){
        mNameEdit.setValidationListener(new NameValidator(mNameEdit));
        mPasswordEdit.setValidationListener(new PasswordValidator(mPasswordEdit));
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
                    finish();
                }
            });

        }
    }

    @Override
    public void onLoginSuccess(){
        //Proceed to the landing screen for app!
        startActivity(new Intent(ParseLoginActivity.this, LandingActivity.class));
    }

    @Override
    public void onLoginFailure(String message){
        if(message.equals(ParseLoginTask.ERROR_EMAIL))
            mNameEdit.setInvalid(getResources().getString(R.string.username_error_message_exists)); //Username / Email does not exist.
        else if(message.equals(ParseLoginTask.ERROR_PASSWORD))
            mPasswordEdit.setInvalid(getResources().getString(R.string.password_error_message_exists));
    }

    @FromXml
    public void attemptLogin(View v){
        if(mNameEdit.checkValidInput() && mPasswordEdit.checkValidInput()) //Check both inputs are valid.
            new ParseLoginTask(this).execute(
                    mNameEdit.getText().toString(),
                    mPasswordEdit.getText().toString());
    }
}
