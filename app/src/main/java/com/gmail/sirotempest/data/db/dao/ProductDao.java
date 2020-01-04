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
    LiveData<List<Product>> findAllPersons();

    @Query("SELECT * FROM products")
    List<Product> getAllChannels();

    @Query("SELECT * FROM products WHERE id=:id")
    Product findPersonById(String id);

    @Query("SELECT * FROM products WHERE id=:id")
    Product findPerson(long id);

    @Insert(onConflict = IGNORE)
    long insertPerson(Product product);

    @Update
    int updatePerson(Product product);

    @Update
    void updatePerson(List<Product> people);

    @Delete
    void deletePerson(Product product);

    @Query("DELETE FROM products")
    void deleteAll();
}
