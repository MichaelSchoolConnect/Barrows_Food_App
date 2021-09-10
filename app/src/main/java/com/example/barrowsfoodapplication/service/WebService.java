package com.example.barrowsfoodapplication.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ListenableWorker;
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
import java.util.List;

public class WebService extends Worker {

    private static String TAG = WebService.class.getSimpleName();
    public static String BASE_URL = "http://mobcategories.s3-website-eu-west-1.amazonaws.com/";

    private Data outputData;

    public WebService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * Override this method to do your actual background processing.  This method is called on a
     * background thread - you are required to <b>synchronously</b> do your work and return the
     * {@link Result} from this method.  Once you return from this
     * method, the Worker is considered to have finished what its doing and will be destroyed.  If
     * you need to do your work asynchronously on a thread of your own choice, see
     * {@link ListenableWorker}.
     * <p>
     * A Worker is given a maximum of ten minutes to finish its execution and return a
     * {@link Result}.  After this time has expired, the Worker will
     * be signalled to stop.
     *
     * @return The {@link Result} of the computation; note that
     * dependent work will not execute if you use
     * {@link Result#failure()} or
     *
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

            System.out.println("Repo results: " + result);

            JSONArray jArray = new JSONArray(result);
            for (int i = 0; i < jArray.length(); i++) {

                //Get objects from the JSONArray.
                JSONObject jsonObject = jArray.getJSONObject(i);

                //Initialize an object of the class House so we can append data to it.
                ProductModel pm = new ProductModel();
                pm.name = jsonObject.getString("name");
                pm.productImageUrl = jsonObject.getString("description");

                data.add(pm);

                outputData = new Data.Builder().putString("data", data.toArray().toString()).build();

                Log.i("WebService: ", pm.name);
            }

        }catch (IOException | NullPointerException | JSONException ioe){
            ioe.printStackTrace();
        }
    }
}
