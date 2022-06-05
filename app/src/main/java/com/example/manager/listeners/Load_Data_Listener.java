package com.example.manager.listeners;

import com.example.manager.Models.Garbage_Can;

public interface Load_Data_Listener {
    public void onPre();
    public void onEnd(Boolean done, Garbage_Can garbage_can);
}
