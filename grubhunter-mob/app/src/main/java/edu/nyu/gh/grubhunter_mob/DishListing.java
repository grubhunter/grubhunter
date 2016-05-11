package edu.nyu.gh.grubhunter_mob;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import edu.nyu.gh.grubhunter_mob.model.GrubSimpleResponse;
import edu.nyu.gh.grubhunter_mob.model.UpdateRatingsRequest;
import edu.nyu.gh.grubhunter_mob.util.RESTfulPOST;
import edu.nyu.gh.grubhunter_mob.util.RESTfulResult;
import edu.nyu.gh.grubhunter_mob.util.Util;

/**
 * Created by richie on 5/10/16.
 */
public class DishListing extends TableLayout implements RESTfulResult {

    private TextView dishName;
    private TextView dishId;
    private RatingBar dishRate;
    private ImageView dishImage;
    private ImageButton btn;
    private Dialog rankDialog;
    private RatingBar rateDish;
    private TextView ratetext;
    private Button rateButton;

    public DishListing(Context context) {
        super(context);
        ((Activity) getContext()).getLayoutInflater().inflate(R.layout.dish, this);
        setUpView();
    }

    public DishListing(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((Activity) getContext()).getLayoutInflater().inflate(R.layout.dish, this);
        setUpView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setUpView();
    }

    private void setUpView(){
        dishImage = (ImageView)findViewById(R.id.dishpicture);
        dishName = (TextView)findViewById(R.id.dishname);
        dishId = (TextView)findViewById(R.id.dishid);
        dishRate = (RatingBar)findViewById(R.id.dishratingBar);
        btn = (ImageButton)findViewById(R.id.imageButton);
    }

    public void setDishName(String name){
        dishName.setText(name);
    }

    public void setDishRate(String rate){
        dishRate.setRating(Float.parseFloat(rate));
    }

    public void setDishId(String id){
        dishId.setText(id);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rankDialog = new Dialog(getContext(), R.style.FullHeightDialog);
                rankDialog.setContentView(R.layout.rating);
                rankDialog.setCancelable(true);
                rateDish = (RatingBar)rankDialog.findViewById(R.id.ratingBar2);
                ratetext = (TextView)rankDialog.findViewById(R.id.ratetext2);
                rateButton = (Button)rankDialog.findViewById(R.id.button3);
                btn = (ImageButton)findViewById(R.id.imageButton);
                final SharedPreferences sp=DishListing.this.getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
                final String email = sp.getString("user",null);
                rateButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UpdateRatingsRequest request = new UpdateRatingsRequest();
                        request.setEmail(email);
                        request.setDishId(Integer.parseInt(dishId.getText().toString()));
                        request.setRestaurantId(Integer.parseInt(sp.getString("selRestId", "1")));
                        request.setRating(rateDish.getRating() + "");
                        Map<String, Object> loginMap = new HashMap<String, Object>();
                        loginMap.put("url", Util.getProperty("rate_dis_url", getContext()));
                        loginMap.put("data", request);
                        RESTfulPOST restFulPost = new RESTfulPOST(DishListing.this, getContext(), "Please Wait", "Login");
                        restFulPost.execute(loginMap);
                        rankDialog.cancel();
                    }
                });
                Log.d("listing",email);
                ratetext.setText("Rate \"" + sp.getString("selRestName", "Restaurant") + "\" for dish " + dishName.getText().toString());
                rankDialog.show();
            };
        });
    }



    public void setDishImage(){
        String name = dishName.getText().toString().trim();
        if(name.equalsIgnoreCase("dosa")) {
            dishImage.setImageResource(R.drawable.dosa);
        }else if(name.equalsIgnoreCase("paratha")){
            dishImage.setImageResource(R.drawable.paratha);
        }else if(name.equalsIgnoreCase("pizza")){
            dishImage.setImageResource(R.drawable.pizza);
        }else if(name.equalsIgnoreCase("fries")){
            dishImage.setImageResource(R.drawable.fries);
        }else if(name.equalsIgnoreCase("burger")){
            dishImage.setImageResource(R.drawable.burger);
        }else if(name.equalsIgnoreCase("doughnut")){
            dishImage.setImageResource(R.drawable.doughnut);
        }else if(name.equalsIgnoreCase("bacon")){
            dishImage.setImageResource(R.drawable.bacon);
        }else if(name.equalsIgnoreCase("dumpling")){
            dishImage.setImageResource(R.drawable.dumpling);
        }else if(name.equalsIgnoreCase("steak")){
            dishImage.setImageResource(R.drawable.steak);
        }else if(name.equalsIgnoreCase("waffle")){
            dishImage.setImageResource(R.drawable.waffle);
        }else if(name.equalsIgnoreCase("bagel")){
            dishImage.setImageResource(R.drawable.bagel);
        }else if(name.equalsIgnoreCase("tacos")){
            dishImage.setImageResource(R.drawable.tacos);
        }else if(name.equalsIgnoreCase("pastrami")){
            dishImage.setImageResource(R.drawable.pastrami);
        }else {
            dishImage.setImageResource(R.drawable.falafel);
        }
    }

    @Override
    public void onResponse(String result, String responseFor) {
        Gson gson = new Gson();
        GrubSimpleResponse response = gson.fromJson(result, GrubSimpleResponse.class);
        if(response.getStatus().equals("success")) {
            Toast.makeText(getContext(), "Dish rated!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(), "Try after sometime!", Toast.LENGTH_LONG).show();
        }
    }

}
