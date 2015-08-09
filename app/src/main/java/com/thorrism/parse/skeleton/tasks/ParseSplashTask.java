package com.thorrism.parse.skeleton.tasks;

import android.os.AsyncTask;

import com.parse.ParseUser;

/**
 * Created by Lucas Crawford on 8/3/2015.
 */
public class ParseSplashTask extends AsyncTask<Void, Void, Void> {
    private SplashCallback mCallback;

    public ParseSplashTask(SplashCallback callback){
        mCallback = callback;
    }

    @Override
    protected Void doInBackground(Void... params){

        try{
            Thread.sleep(2000); //optional...
            ParseUser current = ParseUser.getCurrentUser();

            //user not logged in, go to start screen
            if(current == null){
                mCallback.openStartScreen();
            }

            //user logged in, go to primary landing screen
            else{
                mCallback.openLandingScreen();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Callback for activity to handle opening either the landing screen
     * or start screen.
     */
    public interface SplashCallback{

        public void openStartScreen();
        public void openLandingScreen();
    }
}
