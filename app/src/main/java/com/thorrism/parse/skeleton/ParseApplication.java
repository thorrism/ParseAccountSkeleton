package com.thorrism.parse.skeleton;

import android.app.Application;
import android.content.res.AssetManager;

import com.parse.Parse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Lucas Crawford on 8/7/2015.
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        initParse();
    }

    /**
     * Initialize instance of Parse by reading keys from property file.
     */
    public void initParse(){
        Properties keyProps = new Properties();
        AssetManager am = getApplicationContext().getAssets();

        try{
            InputStream is = am.open("parse_keys.properties");
            keyProps.load(is);
        }catch(IOException e){
            e.printStackTrace();
        }

        //initialize Parse instance.
//        Parse.initialize(getApplicationContext(),
//                keyProps.getProperty("APPLICATION_ID"),
//                keyProps.getProperty("CLIENT_KEY"));
        Parse.initialize(getApplicationContext(),
                "kpVXSqTA4cCxBYcDlcz1gGJKPZvMeofiKlWKzcV3",
                "T4FqPFp0ufX4qs8rIUDL8EX8RSluB0wGX51ZpL12");
    }
}
