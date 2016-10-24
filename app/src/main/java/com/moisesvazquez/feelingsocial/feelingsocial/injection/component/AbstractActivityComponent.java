package com.moisesvazquez.feelingsocial.feelingsocial.injection.component;

import android.app.Activity;

import com.moisesvazquez.feelingsocial.feelingsocial.injection.PerActivity;
import com.moisesvazquez.feelingsocial.feelingsocial.injection.module.ActivityModule;
import com.moisesvazquez.feelingsocial.feelingsocial.navigation.Navigator;

import dagger.Component;

/**
 * @author (c) 2016, Cells
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface AbstractActivityComponent {

    /**
     * Expose the application to sub-graphs.
     * @return Application
     */
    Activity activity(); // Expose the activity to sub-graphs.

    // Exported for navigator
    Navigator navigator();

}