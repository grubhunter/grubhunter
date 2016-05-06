package edu.nyu.gh.grubhunter_mob;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import static edu.nyu.gh.grubhunter_mob.R.*;

/**
 * Created by Student on 5/5/2016.
 */
public class MyAdapter extends ArrayAdapter<MyData> {
    private Context context;
    private int resource;
    private MyData[] objects;
    public MyAdapter(Context context, int resource, MyData[] objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {
        LayoutInflater inflater=
                ((Activity) context).getLayoutInflater();
        View row=inflater.inflate(resource, parent, false);
        TextView title= (TextView)
                row.findViewById(id.title);
        TextView number=(TextView)
                row.findViewById(id.number);
        RatingBar rbar=(RatingBar)
                row.findViewById(id.ratingBar);
        title.setText((CharSequence)
                objects[position].myTitle);
        number.setText((CharSequence)
                objects[position].myNum);
        float rating = Float.parseFloat(objects[position].myRating);
        rbar.setRating(rating);
        title.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.user, 0, 0, 0);
        return row;
    }

    private int getImageResource(ImageView iv) {
        return (Integer) iv.getTag();
    }

}
