package com.example.manager.Utils;

import com.example.manager.Models.Garbage_Can;

public class Constant_Values {
    public static Garbage_Can garbage_can;
    public static float Volume_Machine = 33.8f;
    public static int TIME_TO_UPDATE_GARBAGE = 5;
    public static String HTU_Control = "You can open(<|>)/close(>|<) door by first button."
            + "\nYou can open(▼)/close(▲) left/right door by two button next."
            + "\nThe last button can help you turn on(Green)/off(Blue) power of machine."
            + "\nNote: You must turn on machine to open all door.";

    public static String SERVER_URL = "https://6bd1-2401-d800-6f1-9048-2d00-17b6-a14e-6623.ngrok.io";
    public static String HTU_STATUS = SERVER_URL + "/sieuam";
    public static String ENB = SERVER_URL +"/enb/";
    public static String TAKE_PIC = SERVER_URL +"/takepic";
    public static String IMAGE_URL = SERVER_URL +"/static/img/img2.jpg";
    public static String IMAGE_URL2 = SERVER_URL +"/static/img/img.jpg";
    public static String URL_ABOUT_US = "https://www.facebook.com";
    public static String URL_TERM_OF_SERVICE = "https://www.youtube.com";
}
