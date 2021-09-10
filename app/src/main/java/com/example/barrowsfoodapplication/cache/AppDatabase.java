package com.example.barrowsfoodapplication.cache;

import static java.time.chrono.MinguoChronology.INSTANCE;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.barrowsfoodapplication.cache.dao.ProductDao;
import com.example.barrowsfoodapplication.cache.entity.ProductsEntity;
import com.example.barrowsfoodapplication.pojo.ProductModel;
import com.example.barrowsfoodapplication.threadexecutors.AppExecutors;

import java.util.concurrent.Executors;

@Database(entities = {ProductsEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "foods";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        //.addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract ProductDao productsDao();

    // Sample to populate database pay no attention to it.
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            AppExecutors executors = new AppExecutors();
            executors.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    // Populate the database in the background.
                    // If you want to start with more words, just add them.
                    ProductDao dao = sInstance.productsDao();
                    //dao.deleteAll();

                    /*ProductModel word = new ProductModel("hell", "someone");
                    dao.insertData(word);*/
                }
            });
        }
    };

    }
