package com.example.leon.eventbusdemo.view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.leon.eventbusdemo.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by nft on 18-10-25.
 */

public class EventBusActivity extends Activity {
    private Fragment mPostFragment,mSubscriberFragment;
    private EventBus mBus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_activity);

    }

}
