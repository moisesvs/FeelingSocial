package com.moisesvazquez.feelingsocial.feelingsocial.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.moisesvazquez.feelingsocial.feelingsocial.navigation.Navigator;

import javax.inject.Inject;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}