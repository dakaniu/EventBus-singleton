package com.example.leon.eventbusdemo.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.leon.eventbusdemo.MyEvent;
import com.example.leon.eventbusdemo.MyStickyEvent;
import com.example.leon.eventbusdemo.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by nft on 18-10-25.
 */

public class PostFragment extends Fragment implements View.OnClickListener{
    private View mview;
    private Button button1,button2,button3,button4;
    private EventBus mBus;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.post_view,container,false);
        button1 = (Button) mview.findViewById(R.id.post_button1);
        button2 = (Button) mview.findViewById(R.id.post_button2);
        button3 = (Button) mview.findViewById(R.id.post_button3);
        button4 = (Button) mview.findViewById(R.id.post_button4);
        button3.setOnClickListener(this);
        button2.setOnClickListener(this);
        button1.setOnClickListener(this);
        button4.setOnClickListener(this);
        mBus = EventBus.getDefault();
//        mBus.register(this);
        return mview;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.post_button1:
                mBus.post(new MyEvent("button1 主线程事件"));
                break;
            case R.id.post_button2:
                mBus.postSticky(new MyStickyEvent("button2 sticky事件"));
                break;
            case R.id.post_button3:
                new Thread(){
                    @Override
                    public void run() {
                        mBus.post(new MyEvent("button3 子线程事件　:"+Thread.currentThread().getName()));

                    }
                }.start();
                break;
            case R.id.post_button4:
                new Thread(){
                    @Override
                    public void run() {
                        mBus.postSticky(new MyStickyEvent("button4 子线程事件　:"+Thread.currentThread().getName()));
                    }
                }.start();
                break;
        }
    }

    @Override
    public void onDestroy() {
//        mBus.unregister(this);
        super.onDestroy();
    }
}
