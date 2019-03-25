
安卓应用中常用到一些弹出通知，对话框，提示语之类的。
 
 ## 1 Toast
 Toast，吐司（自己望文生义一下），突然出现又自动消失的提示。
 
 新建Empty Activity项目。
 
 修改layout_main.xml
 ```
 <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    tools:context=".MainActivity">



    <Button
        android:onClick="showToast"
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Toast"/>

    <Button
        android:onClick="showPopup"
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Popup"/>

    <Button
        android:onClick="showNotice"
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Notice"/>

    <Button
        android:onClick="toDialogActivity"
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Dialog"/>

</LinearLayout>
 
 ```
 
 编辑MainActivity.java
 ```
 package net.gzchunxiang.cx_notice_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void showToast(View view) {
        Toast.makeText(this,"点击了Toast",Toast.LENGTH_LONG).show();

    }

    public void showPopup(View view) {

    }


    public void showNotice(View view) {
    }

    public void toDialogActivity(View view) {


    }
}

 ```
 可以看到这里并没有通过findViewById绑定View和setOnClickListener。直接在Button元素添加了onClick属性与值。
 
 注意此两行代码
 ```
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".MainActivity"
 ```
 
 运行后点击Toast按钮，效果：
 
 ![image](http://po1d0nnr5.bkt.clouddn.com/QQ20190321-154255.png)
 
 
 ## 2 PopupWindow
 
 PopupWindow可以作为冒出的选择框。内容介绍： [http://www.runoob.com/w3cnote/android-tutorial-popupwindow.html](http://www.runoob.com/w3cnote/android-tutorial-popupwindow.html)
 
 练习一下
 
 在res/anim目录创建anim_pop.xml，这是一个渐显动画的配置。
 ```
 <?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <alpha android:fromAlpha="0"
        android:toAlpha="1"
        android:duration="2000">
    </alpha>
</set> 
```

layout目录下创建弹出的布局item_popup.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#884488aa"
    android:orientation="vertical">

    <Button
        android:id="@+id/btn_xixi"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="5dp"
        android:text="嘻嘻"
        android:textSize="18sp" />

    <Button
        android:id="@+id/btn_hehe"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="5dp"
        android:text="呵呵"
        android:textSize="18sp" />

</LinearLayout>
```
编辑MainActivity.java
```
package net.gzchunxiang.cx_notice_demo;

import android.graphics.drawable.ColorDrawable;
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
        Toast.makeText(this,"点击了Toast",Toast.LENGTH_LONG).show();

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
    }

    public void toDialogActivity(View view) {


    }
}
```

运行效果：

![image](http://po1d0nnr5.bkt.clouddn.com/QQ20190321-155904.png)


## 3 Notification
Notification是状态栏通知。详解 [http://www.runoob.com/w3cnote/android-tutorial-notification.html](http://www.runoob.com/w3cnote/android-tutorial-notification.html)

练习内容：

编辑MainActivity中的showNotice方法
```
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
```

针对不同的系统版本Build.VERSION作了不同处理。


## 4 Dialog
Dialog即对话框，练习使用几种常用对话框。

DialogActivity.java
```
package net.gzchunxiang.cx_notice_demo;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog);
    }

    public void showAlertDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertTitle")
                .setMessage("Message")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this,"点击取消",Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this,"点击确定",Toast.LENGTH_SHORT).show();

                    }
                })
                .show();
    }

    public void showProgressDialog(View view) {
        ProgressDialog dialog = new ProgressDialog(DialogActivity.this);
        dialog.setTitle("title");
        dialog.setMessage("loading...");
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(DialogActivity.this,"onCancel",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    public void showDatePickerDialog(View view) {
        Calendar cale1 = Calendar.getInstance();
        new DatePickerDialog(DialogActivity.this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                //这里获取到的月份需要加上1哦~
                String result = "你选择的是"+year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日";
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }
                ,cale1.get(Calendar.YEAR)
                ,cale1.get(Calendar.MONTH)
                ,cale1.get(Calendar.DAY_OF_MONTH)).show();
    }
}
```
layout_dialog.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_gravity="center_horizontal"
    android:orientation="vertical"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".DialogActivity">

    <Button
        android:onClick="showAlertDialog"
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="alert_dialog"/>

    <Button
        android:onClick="showProgressDialog"
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="progress_dialog"/>

    <Button
        android:onClick="showDatePickerDialog"
        android:layout_margin="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="date_picker"/>

</LinearLayout>
```

运行看效果。

![image](http://po1d0nnr5.bkt.clouddn.com/QQ20190321-165415.png)
![image](http://po1d0nnr5.bkt.clouddn.com/QQ20190321-165321.png)


![image](http://po1d0nnr5.bkt.clouddn.com/QQ20190321-165336.png)


 
