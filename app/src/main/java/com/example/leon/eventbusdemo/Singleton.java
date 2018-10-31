package com.example.leon.eventbusdemo;

import android.util.Log;

/**
 * Created by nft on 18-10-25.
 */

public class Singleton {
    private static Singleton singleton = null;
    private Singleton(){
        System.out.println("init Singleton");
    }

    public static Singleton getInstance(){
        if (singleton == null){
            singleton  = new Singleton();
        }
        return singleton;
    }
    public static synchronized Singleton getInstance1(){
        if (singleton ==null){
            singleton = new Singleton();
        }
        return singleton;
    }
    public static Singleton getInstance2(){
        if (singleton == null){
            synchronized (Singleton.class){
                if (singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    private static class InnerSingleTon{
        private static Singleton singleton = new Singleton();
    }
    public static Singleton getInstance3(){
        return InnerSingleTon.singleton;
    }
}
