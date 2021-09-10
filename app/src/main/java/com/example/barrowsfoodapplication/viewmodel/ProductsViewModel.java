package com.example.barrowsfoodapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.barrowsfoodapplication.service.WebService;

import java.util.List;
import java.util.concurrent.TimeUnit;

// This class handles ui rotation and also helps with avoiding memory leaks while maintaining data.
public class ProductsViewModel extends AndroidViewModel {

    private WorkManager mWorkManager;
    private String TAG_OUTPUT = "OUTPUT";
    private LiveData<List<WorkInfo>> liveData;

    static final String WORK_NAME = "webservice";

    public ProductsViewModel(@NonNull Application application) {
        super(application);

        mWorkManager = WorkManager.getInstance(application);
        liveData = mWorkManager.getWorkInfosByTagLiveData(TAG_OUTPUT);

        executeWorkerService();
    }

    public void executeWorkerService(){
        // Create network constraint to only run op when there's strong network connection.
        // The perks of using a WorkerManager ;-)
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        //Schedule one-time request meaning it runs only once but it can be upgraded/changed.
        WorkRequest webservice =
                new OneTimeWorkRequest.Builder(WebService.class)
                        // Retry
                        .setBackoffCriteria(
                                BackoffPolicy.LINEAR,
                                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                                TimeUnit.MILLISECONDS)
                        // Only execute if there's a network connection.
                        .setConstraints(constraints)
                        .addTag(TAG_OUTPUT)
                        .build();

        // Actually start the work
        //get the application context to avoid memory leaks.
        WorkManager
                .getInstance(getApplication().getApplicationContext())
                .enqueue(webservice);

    }

    /**
     * Getter method for mSavedWorkInfo
     */
    public LiveData<List<WorkInfo>> getOutputWorkInfo() {
        return liveData;
    }

    /**
     * Cancel work using the work's unique name
     */
    void cancelWork() {
        mWorkManager.cancelUniqueWork(WORK_NAME);
    }

}
