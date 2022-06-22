package com.example.manager.Asyntask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.manager.Models.Garbage_Can;
import com.example.manager.Utils.Constant_Values;
import com.example.manager.Utils.JsonUtils;
import com.example.manager.listeners.Load_Data_Listener;

import org.json.JSONObject;

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
//            {
//                'is_enable' : is_enable,
//                    'is_processing' : is_processing,
//                    'distance':distance,
//                    'distance2':distance2
//            }
            String result = JsonUtils.okhttpGET(Constant_Values.HTU_STATUS);
            JSONObject jsonObject = new JSONObject(result);
            garbage_can = new Garbage_Can(jsonObject.getBoolean("is_processing"),
                    jsonObject.getBoolean("is_enable"),
                    (jsonObject.getBoolean("is_processing")) ? Constant_Values.garbage_can.getVolume_recycle() :
                    (float)jsonObject.getDouble("distance"),
                    (jsonObject.getBoolean("is_processing")) ? Constant_Values.garbage_can.getVolume_nonRecycle() :
                    (float)jsonObject.getDouble("distance2"));
            return true;
        } catch (Exception e) {
            Log.e("III", e.getMessage());
            garbage_can = Constant_Values.garbage_can;
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        listener.onEnd(aBoolean, garbage_can);
    }
}
