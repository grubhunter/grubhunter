package edu.nyu.gh.grubhunter_mob;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.nyu.gh.grubhunter_mob.model.Dish;
import edu.nyu.gh.grubhunter_mob.model.GrubSimpleResponse;
import edu.nyu.gh.grubhunter_mob.model.Preference;
import edu.nyu.gh.grubhunter_mob.model.UpdatePreferenceRequest;
import edu.nyu.gh.grubhunter_mob.util.RESTfulPOST;
import edu.nyu.gh.grubhunter_mob.util.RESTfulResult;
import edu.nyu.gh.grubhunter_mob.util.Util;

public class UpdatePreference extends AppCompatActivity implements RESTfulResult {

    private Context appContext;
    private LinearLayout layout;
    private List<Preference> preferences = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_preference);

        layout = (LinearLayout)findViewById(R.id.linear);
        appContext = this;

        Map<String, Object> attr = new HashMap<String, Object>();
        attr.put("url", Util.getProperty("dishes_url", appContext));
        attr.put("data", null);
        RESTfulPOST restFulPost = new RESTfulPOST(this, appContext, "Please Wait", "Dishes");
        restFulPost.execute(attr);
    }

    public void updatePref(View view){
        Log.d("UpdatePref","Updating preference");
        UpdatePreferenceRequest request = new UpdatePreferenceRequest();
        SharedPreferences sp=this.getSharedPreferences("Login", Context.MODE_PRIVATE);
        String email = sp.getString("user",null);
        List<Integer> cats = new ArrayList<>();
        for(Preference pref:preferences){
            if(pref.getCheckBox().isChecked()){
                cats.add(Integer.parseInt(pref.getTextView().getText().toString()));
            }
        }
        int[] _cats = new int[cats.size()];
        int cnt = 0;
        for(Integer intVal:cats){
            _cats[cnt]=intVal.intValue();
            cnt++;
        }
        request.setCategories(_cats);
        request.setEmail(email);
        Map<String, Object> attr = new HashMap<String, Object>();
        attr.put("url", Util.getProperty("update_pref_url", appContext));
        attr.put("data", request);
        RESTfulPOST restFulPost = new RESTfulPOST(this, appContext, "Please Wait", "UpdatePreferences");
        restFulPost.execute(attr);
    }

    @Override
    public void onResponse(String result, String responseFor) {
        Gson gson = new Gson();
        if(responseFor.equals("Dishes")){
            List<Dish> response = (List)gson.fromJson(result, new TypeToken<List<Dish>>(){}.getType());
            Log.d("Registration", response.toString());
            if(response.size() > 0){
                TextView textView = new TextView(appContext);
                textView.setText("Dish preferences");
                textView.setTextAppearance(appContext, android.R.style.TextAppearance_DeviceDefault);
                layout.addView(textView);

                View ruler = new View(appContext); ruler.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                layout.addView(ruler,
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 2));


                for (Dish dish:response){
                    CheckBox checkBox = new CheckBox(appContext);
                    checkBox.setText(dish.getDishName());
                    TextView dishId = new TextView(appContext);
                    dishId.setVisibility(View.GONE);
                    dishId.setText(dish.getDishId()+"");
                    layout.addView(checkBox);
                    layout.addView(dishId);
                    preferences.add(new Preference(dishId,checkBox));
                }

            }
        }else if(responseFor.equals("UpdatePreferences")) {
            GrubSimpleResponse response = gson.fromJson(result, GrubSimpleResponse.class);
            if (response.getStatus().equals("success")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(appContext);

                builder.setTitle("Dish Preferences")
                        .setMessage("You have been successfully updated your preferences!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(getApplicationContext(), Listing.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(this, "Could not update your preferences!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
