package com.example.barrowsfoodapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.barrowsfoodapplication.adapter.CategoryAdapter;
import com.example.barrowsfoodapplication.adapter.CategoryListAdapter;
import com.example.barrowsfoodapplication.pojo.ProductModel;
import com.example.barrowsfoodapplication.service.WebService;
import com.example.barrowsfoodapplication.viewmodel.ProductsViewModel;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private ProductsViewModel productsViewModel;
    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Show LoadingProgressBar
        final ContentLoadingProgressBar contentLoadingProgressBar = findViewById(R.id.loadingBar);
        contentLoadingProgressBar.show();

        imageView = findViewById(R.id.image_error);

        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productsViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        initViewModel();

        /*OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(
                WebService.class).build();

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(
                this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if(workInfo != null){
                            contentLoadingProgressBar.hide();
                            Data outputData = workInfo.getOutputData();
                            String str = outputData.getString("data");
                            Log.i(TAG, str);
                        }else{
                            //No data update ui
                            contentLoadingProgressBar.hide();
                            imageView.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );*/
    }

    private void initViewModel(){
        productsViewModel.getOutputWorkInfo().observe(
                this, new Observer<List<WorkInfo>>() {
                    @Override
                    public void onChanged(List<WorkInfo> workInfos) {
                        // If there are no matching work info, do nothing
                        if (workInfos == null || workInfos.isEmpty()) {
                            return;
                        }

                        try {
                            WorkInfo workInfo = workInfos.get(0);

                            Data outputData = workInfo.getOutputData();
                            List<String> str = Collections.singletonList(outputData.getString("data"));
                            categoryAdapter = new CategoryAdapter(getApplicationContext(), str);
                            recyclerView.setAdapter(categoryAdapter);
                            //textView.setText("Hello " + str);
                            Log.i(TAG, str.toString());
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }
        );
    }
}