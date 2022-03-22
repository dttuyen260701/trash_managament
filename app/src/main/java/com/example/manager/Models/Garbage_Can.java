package com.example.manager.Models;

public class Garbage_Can {
    private String ip;
    private boolean door, leftDoor, rightDoor, power;
    private float volume;
    //true la Mo, false la Dong
    public Garbage_Can(String ip, float volume) {
        this.ip = ip;
        this.door = false;
        this.leftDoor = false;
        this.rightDoor = false;
        this.power = false;
        this.volume = volume;
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

    public boolean isLeftDoor() {
        return leftDoor;
    }

    public void setLeftDoor(boolean leftDoor) {
        this.leftDoor = leftDoor;
    }

    public boolean isRightDoor() {
        return rightDoor;
    }

    public void setRightDoor(boolean rightDoor) {
        this.rightDoor = rightDoor;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Garbage_Can{" +
                "ip='" + ip + '\'' +
                ", door=" + door +
                ", leftDoor=" + leftDoor +
                ", rightDoor=" + rightDoor +
                ", power=" + power +
                ", volume=" + volume +
                '}';
    }
}
