package edu.nyu.gh.grubhunter_mob;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.Random;

import edu.nyu.gh.grubhunter_mob.model.Dish;
import edu.nyu.gh.grubhunter_mob.model.Recommendation;

public class SelectedRestaurant extends AppCompatActivity {

    private LinearLayout restaurant;
    private LinearLayout dishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_restaurant);
        restaurant = (LinearLayout)findViewById(R.id.restaurant);
        dishes = (LinearLayout)findViewById(R.id.dishes);
        Bundle extras = getIntent().getExtras();
        String data = extras.getString("data");
        Gson gson = new Gson();
        Recommendation restaurantObject = gson.fromJson(data, Recommendation.class);

        Random randomGenerator = new Random();
        Restaurant widget = new Restaurant(restaurant.getContext());
        widget.setName(restaurantObject.getRestaurantName());
        widget.setAddress(restaurantObject.getAddress());
        widget.setPrice(restaurantObject.getPrice());
        widget.setReview(restaurantObject.getReviews() + " reviews");
        widget.setRating(restaurantObject.getRating());
        widget.setRestId(restaurantObject.getRestaurantId()+"");
        int randomInt = 1 + randomGenerator.nextInt(14);
        final int resource = getResources().getIdentifier("restaurant"+randomInt, "drawable", "edu.nyu.gh.grubhunter_mob");
        widget.setImage(resource);
        restaurant.addView(widget);

        SharedPreferences sp=this.getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=sp.edit();
        ed.putString("selRestName", restaurantObject.getRestaurantName());
        ed.putString("selRestId", restaurantObject.getRestaurantId()+"");
        ed.commit();

        for(Dish dish : restaurantObject.getDishes()){
            DishListing dl = new DishListing(dishes.getContext());
            dl.setDishName(dish.getDishName());
            dl.setDishRate(dish.getRating());
            dl.setDishImage();
            dl.setDishId(dish.getDishId()+"");
            dishes.addView(dl);
        }

    }

}
