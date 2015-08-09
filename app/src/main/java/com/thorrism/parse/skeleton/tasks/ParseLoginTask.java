package com.thorrism.parse.skeleton.tasks;

import android.os.AsyncTask;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Lucas Crawford on 8/3/2015.
 */
public class ParseLoginTask extends AsyncTask<String, Void, String> {
    private ParseLoginCallback mCallback;

    public static final String ERROR_EMAIL = "ERROR_EMAIL";
    public static final String ERROR_PASSWORD = "ERROR_PASSWORD";
    public static final String SUCCESS = "SUCCESS";

    public ParseLoginTask(ParseLoginCallback callback){
        mCallback = callback;
    }

    @Override
    protected String doInBackground(String... params){
        String username = params[0];
        String password = params[1];

        ParseQuery<ParseUser> emailQuery = ParseUser.getQuery();
        emailQuery.whereEqualTo("username", username);

        //Determine if entered email exists
        try{
            if(emailQuery.find().size() == 0){
                return ERROR_EMAIL;
            }
        }catch(ParseException e){
            e.printStackTrace();
        }

        //Attempt to login with username and password
        try{
            if(ParseUser.logIn(username, password) == null)
                return SUCCESS;
        }catch(ParseException e){
            e.printStackTrace();
            return ERROR_PASSWORD;
        }

        return SUCCESS;
    }

    @Override
    protected void onPostExecute(String result){
        switch(result){
            case SUCCESS:
                mCallback.onLoginSuccess();
                break;
            default:
                mCallback.onLoginFailure(result);
        }
    }

    public interface ParseLoginCallback{
        public void onLoginSuccess();
        public void onLoginFailure(String message);
    }
}
