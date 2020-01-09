package com.example.android.architecture.blueprints.todoapp

import com.example.android.architecture.blueprints.todoapp.di.AppComponent
import com.example.android.architecture.blueprints.todoapp.di.DaggerTestAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

class TestTodoApplication : TodoApplication() {

    override fun initializeComponent(): AppComponent {
        return DaggerTestAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }
}
