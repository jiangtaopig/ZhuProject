package com.example.za_zhujiangtao.zhupro;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jsc.kit.wheel.dialog.DateTimeWheelDialog;

/**
 * Created by za-zhujiangtao on 2018/12/18.
 */

public class TestDatePickDialogActivity extends AppCompatActivity {

    @BindView(R.id.date_result)
    TextView tvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker_layout);
        ButterKnife.bind(this);

        createDialog(DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY_HOUR);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private DateTimeWheelDialog createDialog(int type) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, 2015);
//        calendar.set(Calendar.MONTH, 0);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        Date startDate = calendar.getTime();
//        calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, 2030);
//        Date endDate = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date endDate = null;
        Date startDate = null;
        Date selectedDate = null;
        try {
            startDate = format.parse("2015-01-01");
            endDate = format.parse("2030-12-31");

            Date date = new Date();
            String time = new SimpleDateFormat("yyyy").format(date);
            time += "-01-01";
            selectedDate = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateTimeWheelDialog dialog = new DateTimeWheelDialog(this);
        dialog.show();
        dialog.setTitle("选择时间");
        dialog.setTitleAppearance(R.style.font_15dp_black);
        dialog.setOkAppearance(R.style.font_15dp_black);
        int config = DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY_HOUR_MINUTE;
        switch (type) {
            case 1:
                config = DateTimeWheelDialog.SHOW_YEAR;
                break;
            case 2:
                config = DateTimeWheelDialog.SHOW_YEAR_MONTH;
                break;
            case 3:
                config = DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY;
                break;
            case 4:
                config = DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY_HOUR;
                break;
            case 5:
                config = DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY_HOUR_MINUTE;
                break;
        }
        dialog.configShowUI(config);
        dialog.setCancelButton("取消", null);
        dialog.setOKButton("确定", new DateTimeWheelDialog.OnClickCallBack() {
            @Override
            public boolean callBack(View v, @NonNull Date selectedDate) {
                tvResult.setText(SimpleDateFormat.getInstance().format(selectedDate));
                return false;
            }
        });
        dialog.setDateArea(startDate, endDate, true);
        dialog.updateSelectedDate(selectedDate);
        return dialog;
    }
}
