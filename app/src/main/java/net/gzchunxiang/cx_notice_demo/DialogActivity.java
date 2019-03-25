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
