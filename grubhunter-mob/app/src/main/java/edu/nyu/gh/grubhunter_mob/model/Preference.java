package edu.nyu.gh.grubhunter_mob.model;

import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by richie on 5/8/16.
 */
public class Preference {

    private TextView textView;
    private CheckBox checkBox;

    public Preference(TextView textView, CheckBox checkBox){
        this.checkBox = checkBox;
        this.textView = textView;
    }


    public CheckBox getCheckBox() {
        return checkBox;
    }

    public TextView getTextView() {
        return textView;
    }

}
