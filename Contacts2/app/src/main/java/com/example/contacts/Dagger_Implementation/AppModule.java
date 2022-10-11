package com.example.contacts.Dagger_Implementation;

import android.app.Application;
import android.content.Context;
import android.view.ContextThemeWrapper;

import androidx.annotation.NonNull;

import com.example.contacts.DB.AppDatabase;
import com.example.contacts.DB.DBDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public  AppDatabase getDatabase(){
        return AppDatabase.getDbInstance(context);
    }

    @Singleton
    @Provides
    public DBDao dbDao(@NonNull AppDatabase appDatabase){
        return appDatabase.dbDao();
    }

    @Singleton
    @Provides
    public  Context provideContext(){
        return context;
    }

}

