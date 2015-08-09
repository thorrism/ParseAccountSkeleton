package com.thorrism.parse.skeleton.tasks;

import android.os.AsyncTask;

import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by Lucas Crawford on 8/5/2015.
 */
public class ParseSignupTask extends AsyncTask<String, Void, Void> {
    private SignupCallback mCallback;

    public ParseSignupTask(SignupCallback callback){
        this.mCallback = callback;
    }

    @Override
    protected Void doInBackground(String... params){
        String username = params[0];
        String password = params[1];
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        try{
            user.signUp();
            mCallback.onSignupSuccess();
        }catch(ParseException e){
            e.printStackTrace();
        }

        return null;
    }

    public interface SignupCallback{
        public void onSignupSuccess();
    }
}
