package com.example.barrowsfoodapplication.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.barrowsfoodapplication.networking.NetworkUtils;
import com.example.barrowsfoodapplication.pojo.ProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebService extends Worker {

    private static final String TAG = WebService.class.getSimpleName();
    public static String BASE_URL = "http://mobcategories.s3-website-eu-west-1.amazonaws.com/";

    public WebService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    /**
     * This method is called on a background thread
     */
    @NonNull
    @Override
    public Result doWork() {
        Log.i(TAG, "doWork() called.");
        getProducts();
        return Result.success();
    }

    //Get the JSON data from URL
    public void getProducts(){
        List<ProductModel> data = new ArrayList<>();
        URL url = NetworkUtils.buildUrl(BASE_URL);
        String result = null;

        try {
            result = NetworkUtils.getResponseFromHttpUrl(url);

            Log.i("Results: ", result);

            JSONArray jArray = new JSONArray(result);
            for (int i = 0; i < jArray.length(); i++) {

                //Get objects from the JSONArray.
                JSONObject jsonObject = jArray.getJSONObject(i);

                //Initialize an object of the class House so we can append data to it.
                ProductModel pm = new ProductModel();
                pm.name = jsonObject.getString("name");
                pm.productImageUrl = jsonObject.getString("description");

                data.add(pm);

                new Data.Builder().putString("data", Arrays.toString(data.toArray())).build();

                Log.i("WebService: ", pm.name);
            }

        }catch (IOException | NullPointerException | JSONException ioe){
            ioe.printStackTrace();
        }
    }

    private void sendData(Data data){


    }
}
