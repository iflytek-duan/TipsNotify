package com.zihao.tipsnotify;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zihao.tipsnotify.utils.NotificationUtil;

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
            setStatusBar();
            statusHandler.postDelayed(statusRb, 100);
        }
    };

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            // 根据上面设置对状态栏单独设置颜色
            getWindow().setStatusBarColor(getResources().getColor(colorArrays[new Random().nextInt(3)]));
        }
    }
}
