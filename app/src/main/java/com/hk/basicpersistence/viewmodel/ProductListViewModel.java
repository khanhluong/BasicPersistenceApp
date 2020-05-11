package com.hk.basicpersistence.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;

import com.hk.basicpersistence.BasicPersistenceApp;
import com.hk.basicpersistence.DataRepository;
import com.hk.basicpersistence.db.entity.ProductEntity;

import java.util.List;

import static androidx.lifecycle.Transformations.switchMap;

public class ProductListViewModel extends AndroidViewModel {

    private static final String QUERY_KEY = "QUERY";

    private final SavedStateHandle mSavedStateHandler;
    private final DataRepository mRepository;
    private final LiveData<List<ProductEntity>> mProducts;

    public ProductListViewModel(@NonNull Application application, SavedStateHandle mSavedStateHandler) {
        super(application);
        this.mSavedStateHandler = mSavedStateHandler;
        mRepository = ((BasicPersistenceApp) application).getRepository();

        // Use the savedStateHandle.getLiveData() as the input to switchMap,
        // allowing us to recalculate what LiveData to get from the DataRepository
        // based on what query the user has entered
        mProducts = Transformations.switchMap(
                mSavedStateHandler.getLiveData("QUERY", null),
                (Function<CharSequence, LiveData<List<ProductEntity>>>) query -> {
                    if (TextUtils.isEmpty(query)) {
                        return mRepository.getProducts();
                    }
                    return mRepository.searchProducts("*" + query + "*");
                });
    }

    void setQuery(CharSequence query){
        mSavedStateHandler.set(QUERY_KEY, query);
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<ProductEntity>> getProducts() {
        return mProducts;
    }
}
