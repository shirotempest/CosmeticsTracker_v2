package com.gmail.sirotempest.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import com.gmail.sirotempest.data.db.entity.Product;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ProductDao {


    @Query("SELECT * FROM products ORDER BY name ASC")
    LiveData<List<Product>> findAllProducts();

    @Query("SELECT * FROM products")
    List<Product> getAllChannels();

    @Query("SELECT * FROM products WHERE id=:id")
    Product findProductById(String id);

    @Query("SELECT * FROM products WHERE id=:id")
    Product findProduct(long id);

    @Insert(onConflict = IGNORE)
    long insertProduct(Product product);

    @Update
    int updateProduct(Product product);

    @Update
    void updateProduct(List<Product> products);

    @Delete
    void deleteProduct(Product product);

    @Query("DELETE FROM products")
    void deleteAll();
}
