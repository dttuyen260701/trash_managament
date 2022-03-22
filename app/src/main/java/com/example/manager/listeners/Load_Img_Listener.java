package com.example.manager.listeners;

import android.graphics.Bitmap;

import java.util.ArrayList;

public interface Load_Img_Listener {
    void onPre();
    void onEnd(boolean isSuccess, ArrayList<Bitmap> list_result);
}
