package edu.nyu.gh.grubhunter_mob;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import edu.nyu.gh.grubhunter_mob.model.AuthRequest;
import edu.nyu.gh.grubhunter_mob.model.GrubSimpleResponse;
import edu.nyu.gh.grubhunter_mob.util.RESTfulPOST;
import edu.nyu.gh.grubhunter_mob.util.RESTfulResult;
import edu.nyu.gh.grubhunter_mob.util.Util;

public class Home extends AppCompatActivity implements RESTfulResult {

    private EditText userId;
    private EditText password;
    private Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        appContext = this;

        final TextView register = (TextView) findViewById(R.id.textView);

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent i = new Intent(getApplicationContext(),Registration_page.class);
                startActivity(i);
            }
        });

        userId = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);

    }

    public void login(View v) {
        //Toast.makeText(this, "Clicked on Button", Toast.LENGTH_LONG).show();
        AuthRequest request = new AuthRequest();
        request.setUserId(userId.getText().toString());
        request.setPassword(password.getText().toString());
        Map<String, Object> loginMap = new HashMap<String, Object>();
        loginMap.put("url", Util.getProperty("login_url", appContext));
        loginMap.put("data", request);
        RESTfulPOST restFulPost = new RESTfulPOST(this, appContext, "Please Wait", "Login");
        restFulPost.execute(loginMap);

    }

    @Override
    public void onResponse(String result, String responseFor) {
        Gson gson = new Gson();
        GrubSimpleResponse response = gson.fromJson(result, GrubSimpleResponse.class);
        if(response.getStatus().equals("success")){
            Intent i = new Intent(getApplicationContext(), list.class);
            startActivity(i);
        }else{
            Toast.makeText(this, "Cannot identify user. Register if you are a new user!", Toast.LENGTH_LONG).show();
        }
    }
}
