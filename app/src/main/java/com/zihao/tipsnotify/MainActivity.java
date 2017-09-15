package com.zihao.tipsnotify;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.zihao.tipsnotify.utils.NotificationUtil;
import com.zihao.tipsnotify.utils.StatusBarUtil;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Handler statusHandler;
    private int[] colorArrays = new int[]{R.color.colorPrimary,
            R.color.colorPrimaryDark, R.color.colorAccent};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusHandler = new Handler();
        showNotification(this);
    }

    private void showNotification(final Context context) {
        NotificationUtil.getInstance().showNotification(context);
        statusHandler.post(statusRb);
    }

    Runnable statusRb = new Runnable() {
        @Override
        public void run() {
            StatusBarUtil.setStatusBarColor(MainActivity.this, colorArrays[new Random().nextInt(3)]);
            statusHandler.postDelayed(statusRb, 100);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
