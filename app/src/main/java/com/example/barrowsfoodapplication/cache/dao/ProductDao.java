package com.example.barrowsfoodapplication.cache.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


import com.example.barrowsfoodapplication.cache.entity.ProductsEntity;
import com.example.barrowsfoodapplication.pojo.ProductModel;

import java.util.List;

@Dao
public interface ProductDao {

    @Transaction
    @Query("SELECT * FROM offlineProductsTable")
    LiveData<List<ProductsEntity>> getAllOfflineData();

    // (onConflict = OnConflictStrategy.REPLACE) = Replace the old data and continue the transaction.
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(ProductsEntity productsEntity);

}
