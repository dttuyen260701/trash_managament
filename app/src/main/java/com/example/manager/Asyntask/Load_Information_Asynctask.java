package com.example.manager.Asyntask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.manager.Models.Garbage_Can;
import com.example.manager.listeners.Load_Data_Listener;

import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Load_Information_Asynctask extends AsyncTask<Void, String, Boolean> {
    Load_Data_Listener listener;
    Garbage_Can garbage_can;

    public Load_Information_Asynctask(Load_Data_Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onPre();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            Request.Builder builder = new Request.Builder();
            Response response;

            return true;
        } catch (Exception e) {
            Log.e("AAA", e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        listener.onEnd(aBoolean, garbage_can);
    }
}
