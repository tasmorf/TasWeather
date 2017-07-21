package com.example.metis.tasweather.module;


import io.realm.Realm;

public class RealmModule {

    public static Realm realm() {
        return Realm.getDefaultInstance();
    }
}
