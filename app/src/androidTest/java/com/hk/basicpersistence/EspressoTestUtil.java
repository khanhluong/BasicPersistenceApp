package com.hk.basicpersistence;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.rule.ActivityTestRule;

public class EspressoTestUtil {

    public static void disableAnimations(ActivityTestRule<? extends FragmentActivity> activityTestRule) {
        activityTestRule.getActivity().getSupportFragmentManager()
                .registerFragmentLifecycleCallbacks(
                        new FragmentManager.FragmentLifecycleCallbacks() {
                            @Override
                            public void onFragmentViewCreated(@NonNull FragmentManager fm,
                                                              @NonNull Fragment f,
                                                              @NonNull View v,
                                                              @Nullable Bundle savedInstanceState) {
                                traverseViews(v);
                            }
                        }, true
                );

    }

    private static void traverseViews(View view) {
        if (view instanceof ViewGroup) {
            traverseViewGroup((ViewGroup) view);
        } else {
            if (view instanceof ProgressBar) {
                disableProgressBarAnimation((ProgressBar) view);
            }
        }
    }

    private static void traverseViewGroup(ViewGroup view) {
        if (view instanceof RecyclerView) {
            disableRecyclerViewAnimations((RecyclerView) view);
        } else {
            final int count = view.getChildCount();
            for (int i = 0; i < count; i++) {
                traverseViews(view.getChildAt(i));
            }
        }
    }


    private static void disableRecyclerViewAnimations(RecyclerView view) {
        view.setItemAnimator(null);
    }

    private static void disableProgressBarAnimation(ProgressBar progressBar) {
        progressBar.setIndeterminateDrawable(new ColorDrawable(Color.BLUE));
    }
}

