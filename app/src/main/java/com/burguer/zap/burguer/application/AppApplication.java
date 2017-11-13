package com.burguer.zap.burguer.application;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by LucasOrso on 11/12/17.
 */

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
