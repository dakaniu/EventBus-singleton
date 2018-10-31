package com.example.leon.eventbusdemo.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leon.eventbusdemo.MyEvent;
import com.example.leon.eventbusdemo.MyStickyEvent;
import com.example.leon.eventbusdemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by nft on 18-10-25.
 */

public class SubscriberFragment extends Fragment {
    private static final String TAG = "SubscriberFragment";
    private View mView;
    private LinearLayout linearLayout;
    private TextView mText;
    private EventBus mBus;
    private Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.scriber_view, container, false);
        linearLayout = (LinearLayout) mView.findViewById(R.id.ll_subscriber);
        mText = (TextView) mView.findViewById(R.id.textview);
        button = (Button) mView.findViewById(R.id.bt_getsticky);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),StickyActivity.class);
                getActivity().startActivity(intent);
            }
        });
        mBus = EventBus.getDefault();
        mBus.register(this);
        return mView;
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 5)
    public void onSubscriberEvent(MyEvent event) {
//        TextView view = new TextView(getActivity());
//        view.setTextSize(20);
//        view.setTextColor(Color.GREEN);
//        view.setText(event.msg+" ThreadMode.POSTING ");
//        linearLayout.addView(view);
//        mBus.cancelEventDelivery(event);
        mBus.post(event.msg + " ThreadMode.POSTING  " + Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, priority = 4)
    public void onSubscriberBackgroundEvent(MyEvent event) {
        MyStickyEvent event1 = mBus.getStickyEvent(MyStickyEvent.class);
        String stickyevent = "333";
        if (event1 != null) {
            stickyevent = event1.msg;
        }
        mBus.post(event.msg + " sticky msg :" + stickyevent + " ThreadMode.BACKGROUND " + Thread.currentThread().getName());
        mBus.cancelEventDelivery(event);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC, priority = 3)
    public void onSubscriberNewThreadEvent(MyEvent event) {
        mBus.post(event.msg + " ThreadMode.ASYNC  " + Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 2)
    public void onSubscriberMainThreadEvent(MyEvent event) {
        TextView view = new TextView(getActivity());
        view.setText(event.msg + " ThreadMode.MAIN " + Thread.currentThread().getName());
        view.setTextColor(Color.GREEN);
        linearLayout.addView(view);
    }


    @Override
    public void onDestroy() {
        mBus.unregister(this);
        mBus.removeAllStickyEvents();
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackground(String text) {
        TextView view = new TextView(getActivity());
        view.setText(text);
        view.setTextColor(Color.GRAY);
        linearLayout.addView(view);
    }

}
