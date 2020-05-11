package com.hk.basicpersistence.db;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.hk.basicpersistence.LiveDataTestUtil;
import com.hk.basicpersistence.db.dao.ProductDao;
import com.hk.basicpersistence.db.entity.ProductEntity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.hk.basicpersistence.db.TestData.PRODUCT_ENTITY;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import static com.hk.basicpersistence.db.TestData.PRODUCTS;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ProductDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    private AppDatabase mDatabase;

    private ProductDao mProductDao;


    @Before
    public void initDB() throws Exception {
        mDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase.class)
                .allowMainThreadQueries()
                .build();

        mProductDao = mDatabase.productDao();
    }

    public void closeDb() throws Exception {
        mDatabase.close();
    }


    @Test
    public void getProductWhenNoProductInserted() throws InterruptedException {
        List<ProductEntity> products = LiveDataTestUtil.getValue(mProductDao.loadAllProducts());

        assertTrue(products.isEmpty());
    }

    @Test
    public void getProductsAfterInserted() throws InterruptedException {
        mProductDao.insertAll(PRODUCTS);
        List<ProductEntity> products = LiveDataTestUtil.getValue(mProductDao.loadAllProducts());

        assertThat(products.size(), is(PRODUCTS.size()));
    }


    @Test
    public void getProductById() throws InterruptedException {
        mProductDao.insertAll(PRODUCTS);
        ProductEntity product = LiveDataTestUtil.getValue(mProductDao.loadProduct(PRODUCT_ENTITY.getId()));


        assertThat(product.getId(), is(PRODUCT_ENTITY.getId()));
        assertThat(product.getDescription(), is(PRODUCT_ENTITY.getDescription()));
        assertThat(product.getDescription(), is(PRODUCT_ENTITY.getDescription()));
        assertThat(product.getPrice(), is(PRODUCT_ENTITY.getPrice()));
    }
}
