package com.hk.basicpersistence.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hk.basicpersistence.db.entity.ProductEntity;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> loadAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProductEntity> products);

    @Query("SELECT * FROM products WHERE id = :productId")
    LiveData<ProductEntity> loadProduct(int productId);

    @Query("SELECT * FROM products WHERE id = :productId")
    ProductEntity loadProductSync(int productId);

    @Query("SELECT products.* FROM products JOIN productsFts ON (products.id = productsFts.rowid) "
            + "WHERE productsFts MATCH :query")
    LiveData<List<ProductEntity>> searchAllProducts(String query);
}
