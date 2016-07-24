package com.example.android.popularmoviesp1.controller;


import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.example.android.popularmoviesp1.R;
public class PopularMoviesMainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return PopularMoviesMainFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }
}
