package edu.nyu.gh.grubhunter_mob;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * Created by richie on 5/10/16.
 */
public class Restaurant extends TableLayout {

    private ImageView image;
    private TextView name;
    private TextView restId;
    private TextView address;
    private TextView review;
    private TextView price;
    private RatingBar rate;
    private TextView data;

    public Restaurant(Context context){
        super(context);
        ((Activity) getContext()).getLayoutInflater().inflate(R.layout.restaurant, this);
        setUpView();
    }

    public Restaurant(Context context, AttributeSet attrs){
        super(context,attrs);
        ((Activity) getContext()).getLayoutInflater().inflate(R.layout.restaurant, this);
        setUpView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setUpView();
    }

    public void setUpView(){
        image = (ImageView)findViewById(R.id.picture);
        name = (TextView)findViewById(R.id.name);
        address = (TextView)findViewById(R.id.address);
        review = (TextView)findViewById(R.id.reviews);
        price = (TextView)findViewById(R.id.price);
        data = (TextView)findViewById(R.id.data);
        rate = (RatingBar)findViewById(R.id.ratingBar);
        data.setVisibility(GONE);
        restId = (TextView)findViewById(R.id.restId);
    }

    public void setImage(int imageId){
        image.setImageResource(imageId);
    }

    public void setName(String name){
        this.name.setText(name);
    }

    public void setAddress(String address){
        this.address.setText(address);
    }

    public void setPrice(String price){
        this.price.setText(price);
    }

    public void setReview(String review){
       this.review.setText(review);
    }

    public void setData(String data){
        this.data.setText(data);
    }

    public void setRating(String rating){
        this.rate.setRating(Float.parseFloat(rating));
    }

    public void setRestId(String id){
        restId.setText(id);
    }

    public String getName(){
        return name.getText().toString();
    }

    public String getData(){
        return data.getText().toString();
    }
}
