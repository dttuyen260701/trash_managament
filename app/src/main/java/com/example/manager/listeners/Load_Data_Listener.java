package com.example.manager.listeners;

import android.graphics.Bitmap;

import com.example.manager.Models.Garbage_Can;

import java.util.ArrayList;

public interface Load_Data_Listener {
    void onPre();
    void onEnd(boolean isSuccess, Garbage_Can garbage_can);
}
