package edu.nyu.gh.grubhunter_mob;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ScrollingActivity extends AppCompatActivity {
    public String myTitle;
    public String myNum;
    public String myRating;
    public String landmark;
    public String priceRange;
    public String noReviews;
    public String dishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Intent intent = getIntent();
        myTitle = intent.getStringExtra("myTitle");
        myNum = intent.getStringExtra("myNum");
        myRating = intent.getStringExtra("myRating");
        landmark = intent.getStringExtra("landmark");
        priceRange = intent.getStringExtra("priceRange");
        noReviews = intent.getStringExtra("noReviews");
        TextView textView = (TextView) findViewById(R.id.title);
        textView.setText(myTitle);
        textView = (TextView) findViewById(R.id.number);
        textView.setText(landmark);
        textView = (TextView) findViewById(R.id.noReviews);
        textView.setText(noReviews);
        RatingBar rbar=(RatingBar) findViewById(R.id.ratingBar);
        float rating = Float.parseFloat(myRating);
        rbar.setRating(rating);
        textView = (TextView) findViewById(R.id.dishes);
        textView.setText(dishes);
        textView = (TextView) findViewById(R.id.number);
        textView.setText("priceRange");
        String img_url= "http://andnorth.com/wp-content/uploads/2014/10/the_james_wb_9.jpg";
        ImageView iv = (ImageView)findViewById(R.id.RestImage);
        new asyncLoadImage(iv).execute(img_url);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab;
        fab = (FloatingActionButton) findViewById(R.id.fab);


    }
}
