package edu.nyu.gh.grubhunter_mob.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.Map;

/**
 * Created by richie on 5/6/16.
 */
public class RESTfulPOST  extends AsyncTask<Map, Void, String> {
    RESTfulResult restFulResult = null;
    ProgressDialog Asycdialog;
    String msg;
    String task;

    public RESTfulPOST(RESTfulResult restFulResult, Context context, String msg,String task) {
        this.restFulResult = restFulResult;
        this.task=task;
        this.msg = msg;
        Asycdialog = new ProgressDialog(context);
    }

    @Override
    protected String doInBackground(Map... params) {
        String responseStr = null;
        Object dataMap = null;

        HttpPost httpost = new HttpPost(params[0].get("url").toString());

        try {
            dataMap = (Object) params[0].get("data");
            Gson gson = new Gson();
            Log.d("data  map", "data map------" + gson.toJson(dataMap));
            httpost.setEntity(new StringEntity(gson.toJson(dataMap)));
            httpost.setHeader("Accept", "application/json");
            httpost.setHeader("Content-type", "application/json");
            DefaultHttpClient httpclient= new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httpost);
            int statusCode = response.getStatusLine().getStatusCode();
            Log.d("resonse code", "----------------" + statusCode);

            if (statusCode == 200)
                responseStr = EntityUtils.toString(response.getEntity());
            if (statusCode == 404) {
                responseStr = "{\n" +
                        "\"status\":\"fail\",\n" +
                        " \"data\":{\n" +
                        "\"ValidUser\":\"Service not available\",\n" +
                        "\"code\":\"404\"\n" +
                        "}\n" +
                        "}";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseStr;
    }

    @Override
    protected void onPreExecute() {
        Asycdialog.setMessage(msg);
        //show dialog
        Asycdialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        Asycdialog.dismiss();
        restFulResult.onResponse(s, task);
    }


}