package edu.nyu.gh.grubhunter_mob;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.nyu.gh.grubhunter_mob.model.GrubSimpleRequest;
import edu.nyu.gh.grubhunter_mob.model.Recommendation;
import edu.nyu.gh.grubhunter_mob.util.RESTfulPOST;
import edu.nyu.gh.grubhunter_mob.util.RESTfulResult;
import edu.nyu.gh.grubhunter_mob.util.Util;

public class Listing extends AppCompatActivity implements RESTfulResult {

    private LinearLayout layout;
    private Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing);
        layout = (LinearLayout)findViewById(R.id.listing);
        appContext = this;

        SharedPreferences sp=this.getSharedPreferences("Login", Context.MODE_PRIVATE);
        String email = sp.getString("user",null);
        GrubSimpleRequest request = new GrubSimpleRequest();
        request.setUserId(email);
        Map<String, Object> params = new HashMap<>();
        params.put("url", Util.getProperty("recommendation_url", appContext));
        params.put("data", request);
        RESTfulPOST restFulPost = new RESTfulPOST(this, appContext, "Please Wait", "Recommendation");
        restFulPost.execute(params);

    }

    public void updatePreference(View view){
        Intent i = new Intent(getApplicationContext(), UpdatePreference.class);
        startActivity(i);
    }

    @Override
    public void onResponse(String result, String responseFor) {
        Gson gson = new Gson();
        Random randomGenerator = new Random();
        List<Recommendation> response = (List)gson.fromJson(result, new TypeToken<List<Recommendation>>(){}.getType());
        for (Recommendation rec : response){
            final Restaurant restaurant = new Restaurant(layout.getContext());
            restaurant.setName(rec.getRestaurantName());
            restaurant.setAddress(rec.getAddress());
            restaurant.setPrice(rec.getPrice());
            restaurant.setReview(rec.getReviews()+" reviews");
            restaurant.setRating(rec.getRating());
            restaurant.setData(gson.toJson(rec));
            restaurant.setRestId(rec.getRestaurantId()+"");
            int randomInt = 1 + randomGenerator.nextInt(14);
            final int resource = getResources().getIdentifier("restaurant"+randomInt, "drawable", "edu.nyu.gh.grubhunter_mob");
            restaurant.setImage(resource);
            restaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(Listing.this, "Clicked " + restaurant.getName(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Listing.this, SelectedRestaurant.class);
                    intent.putExtra("data", restaurant.getData().toString());
                    Listing.this.startActivity(intent);
                }
            });
            layout.addView(restaurant);
        }
    }
}
