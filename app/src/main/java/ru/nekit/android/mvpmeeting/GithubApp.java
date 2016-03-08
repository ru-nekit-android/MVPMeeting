package ru.nekit.android.mvpmeeting;

import android.app.Application;

import ru.nekit.android.mvpmeeting.di.AppComponent;
import ru.nekit.android.mvpmeeting.di.DaggerAppComponent;

/**
 * Created by ru.nekit.android on 08.03.16.
 */
public class GithubApp extends Application {

    private static AppComponent component;

    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();
    }

    public static AppComponent getComponent(){
        return component;
    }

}
