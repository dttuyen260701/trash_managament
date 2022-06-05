package com.example.manager.listeners;

public interface Load_Task_Listener {
    public void onPre();
    public void onEnd(boolean done, boolean is_pro);
}
