package com.example.manager.Asyntask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.manager.Utils.Constant_Values;
import com.example.manager.Utils.JsonUtils;
import com.example.manager.listeners.Load_Task_Listener;

import org.json.JSONObject;

import okhttp3.RequestBody;

public class Load_task_AsyncTask extends AsyncTask<String, Void, Boolean> {
    private Load_Task_Listener listener;
    private boolean is_pro = false;

    public Load_task_AsyncTask(Load_Task_Listener listener) {
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try{
            String api_url = strings[0];

            String result = JsonUtils.okhttpGET(api_url);

            JSONObject jsonObject = new JSONObject(result);


            boolean status = jsonObject.getString("status").equals("success");
            is_pro = jsonObject.getString("is_processing").equals("True");
            if(status){
                return true;
            }else {
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.e("TASK", e.toString());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean status) {
        listener.onEnd(status, is_pro);
        super.onPostExecute(status);
    }

    @Override
    protected void onPreExecute() {
        listener.onPre();
        super.onPreExecute();
    }
}