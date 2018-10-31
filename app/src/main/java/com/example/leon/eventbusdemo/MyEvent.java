package com.example.leon.eventbusdemo;

/**
 * Created by Leon on 2016/9/5.
 */
public class MyEvent {

    public MyEvent(String msg) {
        this.msg = msg;
    }

    public String msg;
    public MyEvent(){

    }
    public static MyEvent getInstance(){
        return InnerClass.singleton;
    }
    public static class InnerClass{
        private static MyEvent singleton = new MyEvent();
    }
}
