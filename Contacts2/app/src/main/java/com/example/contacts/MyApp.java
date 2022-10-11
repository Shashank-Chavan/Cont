package com.example.contacts;

import android.app.Application;

import com.example.contacts.Dagger_Implementation.AppComponent;
import com.example.contacts.Dagger_Implementation.AppModule;
import com.example.contacts.Dagger_Implementation.DaggerAppComponent;

public class MyApp extends Application {
    AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
    AppComponent getAppComponent(){
        return appComponent;
    }
}
