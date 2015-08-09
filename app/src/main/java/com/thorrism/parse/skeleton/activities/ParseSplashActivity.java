package com.thorrism.parse.skeleton.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thorrism.R;
import com.thorrism.parse.skeleton.LandingActivity;
import com.thorrism.parse.skeleton.tasks.ParseSplashTask;

public class ParseSplashActivity extends AppCompatActivity implements ParseSplashTask.SplashCallback {
    private ParseSplashTask mSplashTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mSplashTask = new ParseSplashTask(this);
        mSplashTask.execute();
    }

    @Override
    public void openStartScreen(){
        startActivity(new Intent(ParseSplashActivity.this, ParseStartActivity.class));
    }

    @Override
    public void openLandingScreen(){
        startActivity(new Intent(ParseSplashActivity.this, LandingActivity.class));
    }

    @Override
    public void onPause(){
        super.onPause();
        if(!mSplashTask.isCancelled())
            mSplashTask.cancel(true);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(mSplashTask.isCancelled()) {
            mSplashTask = new ParseSplashTask(this);
            mSplashTask.execute();
        }

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(!mSplashTask.isCancelled())
            mSplashTask.cancel(true);
    }

}
