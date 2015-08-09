package com.thorrism.parse.skeleton.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.thorrism.R;

import interfaces.FromXml;

public class ParseStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    @FromXml
    public void userLogin(View v){
        startActivity(new Intent(ParseStartActivity.this, ParseLoginActivity.class));
    }

    @FromXml
    public void userSignup(View v){
        startActivity(new Intent(ParseStartActivity.this, ParseSignupActivity.class));
    }

    @Override
    public void onBackPressed(){
        return;
    }
}
