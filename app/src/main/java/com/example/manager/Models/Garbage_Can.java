package com.example.manager.Models;

public class Garbage_Can {
    private String ip;
    private boolean processing, enb;//processing 1 la dang tai che, processing 0 la k phai tai che
    private float volume_recycle, volume_nonRecycle;
    //true la Mo, false la Dong


    public Garbage_Can(boolean processing, boolean enb, float volume_recycle, float volume_nonRecycle) {
        this.processing = processing;
        this.enb = enb;
        this.volume_recycle = volume_recycle;
        this.volume_nonRecycle = volume_nonRecycle;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    public boolean isEnb() {
        return enb;
    }

    public void setEnb(boolean enb) {
        this.enb = enb;
    }

    public float getVolume_recycle() {
        return volume_recycle;
    }

    public void setVolume_recycle(float volume_recycle) {
        this.volume_recycle = volume_recycle;
    }

    public float getVolume_nonRecycle() {
        return volume_nonRecycle;
    }

    public void setVolume_nonRecycle(float volume_nonRecycle) {
        this.volume_nonRecycle = volume_nonRecycle;
    }

    @Override
    public String toString() {
        return "Garbage_Can{" +
                "ip='" + ip + '\'' +
                ", processing=" + processing +
                ", enb=" + enb +
                ", volume_recycle=" + volume_recycle +
                ", volume_nonRecycle=" + volume_nonRecycle +
                '}';
    }
}
