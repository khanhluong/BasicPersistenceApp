package com.hk.basicpersistence.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hk.basicpersistence.R;
import com.hk.basicpersistence.model.Product;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            ProductListFragment fragment = new ProductListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, ProductListFragment.TAG).commit();
        }
    }


    public void show(Product product) {
        ProductFragment productFragment = ProductFragment.forProduct(product.getId());
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("product")
                .replace(R.id.fragment_container, productFragment, null).commit();
    }
}
