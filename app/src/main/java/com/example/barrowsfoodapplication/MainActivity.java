package com.example.barrowsfoodapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.WorkInfo;

import android.os.Bundle;
import android.util.Log;

import com.example.barrowsfoodapplication.adapter.CategoryListAdapter;
import com.example.barrowsfoodapplication.pojo.ProductModel;
import com.example.barrowsfoodapplication.viewmodel.ProductsViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private ProductsViewModel productsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
        final CategoryListAdapter adapter =
                new CategoryListAdapter(new CategoryListAdapter.ProductDiff()); //check static error
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        productsViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        initViewModel();
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
                            String str = outputData.getString("data");

                            //textView.setText("Hello " + str);
                            Log.i(TAG, str);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }
        );
    }
}