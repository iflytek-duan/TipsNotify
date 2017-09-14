package com.zihao.tipsnotify.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.zihao.tipsnotify.MainActivity;
import com.zihao.tipsnotify.R;

/**
 * ClassName：NotificationUtil
 * Description：TODO<通知栏工具类>
 * Author：zihao
 * Date：2017/9/14 10:46
 * Version：v1.0
 */
public class NotificationUtil {

    private static NotificationManager notificationManager;

    private NotificationUtil() {
    }

    private static class NotificationHolders {
        static final NotificationUtil INSTANCE = new NotificationUtil();
    }

    public static NotificationUtil getInstance() {
        return NotificationHolders.INSTANCE;
    }

    public void showNotification(Context context) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        CharSequence contentTitle = "南山大夫";
        CharSequence contentText = "正在进行南山颈舒模式一推拿治疗，点击返回治疗";
        CharSequence tickerText = "tickerText";

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(context.getPackageName(), MainActivity.class.getName());
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);

        Notification notification = builderNotification(context, contentTitle, contentText, tickerText, pi);
        notificationManager.notify(0, notification);
    }

    public void clearNotification(Context context) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        notificationManager.cancelAll();
    }

    private Notification builderNotification(Context context, CharSequence contentTitle,
                                             CharSequence contentText, CharSequence tickerText,
                                             PendingIntent pendingIntent) {
        Notification.Builder builder = new Notification.Builder(context)
                .setTicker(tickerText)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(contentTitle)
                .setContentText(contentText);

        Notification notification = builder.setContentIntent(pendingIntent).build();
        // 将此通知放到通知栏的"Ongoing"即"正在运行"组中
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notification.flags |= Notification.FLAG_INSISTENT;
        notification.flags |= Notification.FLAG_HIGH_PRIORITY;
        // 表明在点击了通知栏中的"清除通知"后，此通知不清除，
        // 经常与FLAG_ONGOING_EVENT一起使用
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        return notification;
    }

}
