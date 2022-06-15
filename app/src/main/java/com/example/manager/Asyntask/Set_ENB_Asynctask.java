package com.example.manager.Asyntask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.manager.Utils.JsonUtils;
import com.example.manager.listeners.Load_Task_Listener;

import org.json.JSONObject;

public class Set_ENB_Asynctask extends AsyncTask<String, Void, Boolean> {
    private Load_Task_Listener listener;
    private boolean is_enable = true;

    public Set_ENB_Asynctask(Load_Task_Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        listener.onPre();
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try{
            String api_url = strings[0];

            String result = JsonUtils.okhttpGET(api_url);

            JSONObject jsonObject = new JSONObject(result);


            boolean status = jsonObject.getBoolean("is_enable");
            is_enable = jsonObject.getBoolean("is_enable");
            if(status){
                return true;
            }else {
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.e("AAA", e.toString());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean status) {
        listener.onEnd(status, is_enable);
        super.onPostExecute(status);
    }
}
