package com.example.manager.Models;

public class Garbage_Can {
    private String ip;
    private boolean door, power;//door 1 la dang tai che, door 0 la k phai tai che
    private float volume_recycle, volume_nonRecycle;
    //true la Mo, false la Dong

    public Garbage_Can(String ip, float volume_recycle, float volume_nonRecycle) {
        this.ip = ip;
        this.door = false;
        this.power = false;
        this.volume_recycle = volume_recycle;
        this.volume_nonRecycle = volume_nonRecycle;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isDoor() {
        return door;
    }

    public void setDoor(boolean door) {
        this.door = door;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
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
                ", door=" + door +
                ", power=" + power +
                ", volume_recycle=" + volume_recycle +
                ", volume_nonRecycle=" + volume_nonRecycle +
                '}';
    }
}
