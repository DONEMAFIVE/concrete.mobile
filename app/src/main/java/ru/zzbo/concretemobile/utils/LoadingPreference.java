package ru.zzbo.concretemobile.utils;

import android.content.Context;

import androidx.preference.Preference;

import ru.zzbo.concretemobile.R;

public class LoadingPreference extends Preference {
    public LoadingPreference(Context context){
        super(context);
        setLayoutResource(R.layout.preference_loading_placeholder);
    }
}