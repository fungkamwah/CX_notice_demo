package net.gzchunxiang.cx_notice_demo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void showToast(View view) {
        Toast.makeText(this, "点击了Toast", Toast.LENGTH_LONG).show();

    }

    public void showPopup(View view) {
        initPopWindow(view);
    }

    private void initPopWindow(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_popup, null, false);
        Button btn_xixi = (Button) view.findViewById(R.id.btn_xixi);
        Button btn_hehe = (Button) view.findViewById(R.id.btn_hehe);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v, 50, 0);

        //设置popupWindow里的按钮的事件
        btn_xixi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了嘻嘻~", Toast.LENGTH_SHORT).show();
            }
        });
        btn_hehe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了呵呵~", Toast.LENGTH_SHORT).show();
                popWindow.dismiss();
            }
        });
    }


    public void showNotice(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //ChannelId为"001",ChannelName为"my_channel"
            NotificationChannel channel = new NotificationChannel("001",
                    "my_channel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true); //是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.RED); //小红点颜色
            channel.setShowBadge(true);

            //定义一个PendingIntent点击Notification后启动一个Activity
            Intent it = new Intent(this, DialogActivity.class);
            PendingIntent pit = PendingIntent.getActivity(this, 0, it, 0);

            NotificationManager mNManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNManager.createNotificationChannel(channel);
            //设置图片,通知标题,发送时间,提示方式等属性
            Notification.Builder mBuilder = new Notification.Builder(this);
            mBuilder.setContentTitle("标题")                        //标题
                    .setContentText("我是内容啊~")      //内容
                    .setSubText("--小文字")                    //内容下面的一小段文字
                    .setTicker("显示在状态栏的文字~")             //收到信息后状态栏显示的文字信息
                    .setWhen(System.currentTimeMillis())           //设置通知时间
                    .setSmallIcon(R.mipmap.ic_launcher)            //设置小图标
//                .setLargeIcon(R.mipmap.ic_launcher)                     //设置大图标
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
                    .setAutoCancel(true)
                    .setChannelId("001")//设置点击后取消Notification
                    .setContentIntent(pit);                        //设置PendingIntent
            Notification notify1 = mBuilder.build();
            mNManager.notify(1, notify1);

        } else {
            //定义一个PendingIntent点击Notification后启动一个Activity
            Intent it = new Intent(this, DialogActivity.class);
            PendingIntent pit = PendingIntent.getActivity(this, 0, it, 0);

            NotificationManager mNManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            //设置图片,通知标题,发送时间,提示方式等属性
            Notification.Builder mBuilder = new Notification.Builder(this);
            mBuilder.setContentTitle("标题")                        //标题
                    .setContentText("我是内容啊~")      //内容
                    .setSubText("--小文字")                    //内容下面的一小段文字
                    .setTicker("显示在状态栏的文字~")             //收到信息后状态栏显示的文字信息
                    .setWhen(System.currentTimeMillis())           //设置通知时间
                    .setSmallIcon(R.mipmap.ic_launcher)            //设置小图标
//                .setLargeIcon(R.mipmap.ic_launcher)                     //设置大图标
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
                    .setAutoCancel(true)                           //设置点击后取消Notification
                    .setContentIntent(pit);                        //设置PendingIntent
            Notification notify1 = mBuilder.build();
            mNManager.notify(1, notify1);
        }

    }

    public void toDialogActivity(View view) {
        Intent intent = new Intent(this,DialogActivity.class);
        startActivity(intent);

    }
}
