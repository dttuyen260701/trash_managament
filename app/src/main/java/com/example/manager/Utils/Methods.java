package com.example.manager.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Methods {
    private Context context;
    private static Methods Instance;

    private Methods(Context context) {
        this.context = context;
    }

    public static Methods getInstance(Context context){
        if(Instance == null)
            Instance = new Methods(context);
        return Instance;
    }


    //Kiem Tra mang
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
