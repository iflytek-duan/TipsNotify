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

    /**
     * 显示(推送)一个通知
     *
     * @param context      context
     * @param contentTitle 通知的标题
     * @param contentText  通知的内容概述
     * @param tickerText   通知发出且用户还未下拉通知栏时通知在通知栏上展示的信息
     */
    public void showNotification(Context context, CharSequence contentTitle, CharSequence contentText,
                                 CharSequence tickerText) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(context.getPackageName(), MainActivity.class.getName());
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);

        Notification notification = builderNotification(context, contentTitle, contentText, tickerText, pi);
        notificationManager.notify(0, notification);
    }


    /**
     * 根据通知id清除指定的通知
     *
     * @param context context
     * @param id      要清除通知的id
     */
    public void clearNotification(Context context, int id) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        notificationManager.cancel(id);
    }

    /**
     * 清除所有通知
     *
     * @param context context
     */
    public void clearAllNotification(Context context) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        notificationManager.cancelAll();
    }

    /**
     * 构建一个通知对象
     *
     * @param context       context
     * @param contentTitle  通知的标题
     * @param contentText   通知的内容概述
     * @param tickerText    通知发出且用户还未下拉通知栏时通知在通知栏上展示的信息
     * @param pendingIntent pendingIntent 用户定义的填充意向
     * @return Notification
     */
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
//        notification.flags |= Notification.FLAG_NO_CLEAR;

        // 这里因需求变更修改为点击后自动消失
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        return notification;
    }

}
