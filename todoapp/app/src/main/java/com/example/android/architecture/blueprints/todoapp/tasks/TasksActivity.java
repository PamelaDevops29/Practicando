/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.IdlingResource;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android.architecture.blueprints.todoapp.Injection;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.tasks.tablet.TasksMvpTabletController;
import com.example.android.architecture.blueprints.todoapp.statistics.StatisticsActivity;
import com.example.android.architecture.blueprints.todoapp.util.ActivityUtils;
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdlingResource;

import static com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskActivity.SHOULD_LOAD_DATA_FROM_REPO_KEY;

public class TasksActivity extends AppCompatActivity {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private static final String CURRENT_DETAIL_TASK_ID_KEY = "CURRENT_DETAIL_TASK_ID_KEY";

    private static final String CURRENT_ADDEDIT_TASK_ID_KEY = "CURRENT_ADDEDIT_TASK_ID_KEY";

    private DrawerLayout mDrawerLayout;

    private TasksMvpTabletController mTasksMvpTabletController;

    private TasksPresenter mTasksPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        // Create MVP elements for tablet or phone
        if (ActivityUtils.isTablet(this)) {
            mTasksMvpTabletController = TasksMvpTabletController.createTasksView(this);
        } else {
            createPhoneElements();
        }

        // Restore state after config change
        restoreState(savedInstanceState);
    }

    private void createPhoneElements() {
        TasksFragment tasksFragment = (TasksFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (tasksFragment == null) {
            tasksFragment = TasksFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    tasksFragment, R.id.contentFrame);
        }

        mTasksPresenter = new TasksPresenter(
                Injection.provideTasksRepository(getApplicationContext()), tasksFragment);
        tasksFragment.setPresenter(mTasksPresenter);
    }

    private void restoreState(Bundle savedInstanceState) {
        // Load previously saved state, if available.
        String detailTaskId = null;
        TasksFilterType currentFiltering = null;
        String addEditTaskId = null;
        boolean shouldLoadDataFromRepo = true;
        if (savedInstanceState != null) {
            currentFiltering =
                    (TasksFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            detailTaskId = savedInstanceState.getString(CURRENT_DETAIL_TASK_ID_KEY);
            addEditTaskId = savedInstanceState.getString(CURRENT_ADDEDIT_TASK_ID_KEY);
            shouldLoadDataFromRepo = savedInstanceState.getBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY);
            if (ActivityUtils.isTablet(this)) {
                // Prevent the presenter from loading data from the repository if this is a config change.
                mTasksMvpTabletController.restoreDetailTaskId(detailTaskId);
                mTasksMvpTabletController.restoreAddEditTaskId(
                        addEditTaskId, shouldLoadDataFromRepo);
                mTasksMvpTabletController.setFiltering(currentFiltering);
            } else {
                mTasksPresenter.setFiltering(currentFiltering);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mTasksMvpTabletController != null) {
            outState.putSerializable(
                    CURRENT_FILTERING_KEY, mTasksMvpTabletController.getFiltering());
            outState.putString(
                    CURRENT_DETAIL_TASK_ID_KEY, mTasksMvpTabletController.getDetailTaskId());
            outState.putString(
                    CURRENT_ADDEDIT_TASK_ID_KEY, mTasksMvpTabletController.getAddEditTaskId());
            // Save the state so that next time we know if we need to refresh data.
            outState.putBoolean(
                    SHOULD_LOAD_DATA_FROM_REPO_KEY, mTasksMvpTabletController.isDataMissing());
        } else {
            outState.putSerializable(CURRENT_FILTERING_KEY, mTasksPresenter.getFiltering());
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return false;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                // Do nothing, we're already on that screen
                                break;
                            case R.id.statistics_navigation_menu_item:
                                Intent intent =
                                        new Intent(TasksActivity.this, StatisticsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
