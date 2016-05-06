package edu.nyu.gh.grubhunter_mob.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by richie on 5/6/16.
 */
public class Util {

    private static Properties properties = null;

    public static String getProperty(String key, Context context) {
        if(properties == null){
            properties = new Properties();
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = null;
            try {
                inputStream = assetManager.open("config.properties");
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(key);
    }

}
