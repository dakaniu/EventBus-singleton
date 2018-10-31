package com.example.leon.eventbusdemo.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leon.eventbusdemo.MyStickyEvent;
import com.example.leon.eventbusdemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by nft on 18-10-31.
 */

public class StickyActivity extends Activity {
    private LinearLayout linearLayout;
    private EventBus mBus;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1_sticky);
        linearLayout = (LinearLayout) findViewById(R.id.ll_sticky);
        textView = (TextView) findViewById(R.id.text);
        mBus = EventBus.getDefault();
        mBus.register(this);
    }

    @Subscribe(sticky = true)
    public void onSubscriberStickyevent(MyStickyEvent event) {

        TextView textView = new TextView(getApplicationContext());
        String text = event.msg;
//        Log.i("feiting", "text " + text);
        textView.setText(text);
        textView.setTextSize(20);
        textView.setTextColor(Color.GREEN);
        linearLayout.addView(textView);
        textView.setText(text);
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.POSTING, priority = 1)
    public void onSubscriberPosttingSticky(MyStickyEvent event) {
        mBus.post(event.msg + " ThreadMode.POSTING  " + Thread.currentThread().getName());
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.BACKGROUND, priority = 2)
    public void onSubscriberBackgroundSticky(MyStickyEvent event) {
        mBus.post(event.msg + " ThreadMode.BACKGROUND " + Thread.currentThread().getName());
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.ASYNC, priority = 3)
    public void onSubscriberSyncSticky(MyStickyEvent event) {
        mBus.post(event.msg + " ThreadMode.ASYNC  " + Thread.currentThread().getName());
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onSubscriberMainSticky(MyStickyEvent event) {
        TextView view = new TextView(getApplicationContext());
        view.setText(event.msg + " ThreadMode.MAIN " + Thread.currentThread().getName());
        view.setTextColor(Color.GREEN);
        linearLayout.addView(view);
//    }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackground(String text) {
        TextView view = new TextView(getApplicationContext());
        view.setText(text);
        view.setTextColor(Color.RED);
        linearLayout.addView(view);
    }
    @Override
    protected void onDestroy () {
        super.onDestroy();
        mBus.unregister(this);
    }
}
