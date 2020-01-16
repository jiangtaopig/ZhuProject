package com.example.za_zhujiangtao.zhupro;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.appbar_test.TestAppBarActivity;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.Rotate;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.container)
    RelativeLayout relativeLayout;

    @BindView(R.id.my_linearlayout)
    LinearLayout linearLayout;

    @BindView(R.id.my_img)
    ImageView img;

    @BindView(R.id.my_btn)
    Button btn;

    @BindView(R.id.text)
    TextView mTv;

    @BindView(R.id.collect_live)
    TextView mCollect;

    @BindView(R.id.image)
    ImageView mImagview;

    private boolean mColorsInverted;

    CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String s_question = "问答题：String是基本类型吗？可以被继承吗？";
        SpannableStringBuilder style = new SpannableStringBuilder(s_question);
        style.setSpan(new BackgroundColorSpan(Color.parseColor("#f04343")), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        mTv.setText(style);

        mImagview.setImageBitmap(BitmapFactory.decodeFile("/storage/emulated/0/DCIM/20181203_182509521_69e22fe06226876716e29fcb5f7087aa.jpg"));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(relativeLayout, new TransitionSet()
                        .addTransition(new ChangeBounds())
                        .addTransition(new Fade().addTarget(linearLayout))
                        .addTransition(new Slide(Gravity.RIGHT).addTarget(img))
                        .addTransition(new Scale(0.5f).addTarget(img))
                        .addTransition(new Recolor().addTarget(btn))
                        .setDuration(2000)
                        .setInterpolator(new DecelerateInterpolator())
                );


                if (linearLayout.getVisibility() == View.VISIBLE) {
                    linearLayout.setVisibility(View.GONE);
                    img.setVisibility(View.GONE);
                    img.setRotation(0);

                    btn.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
                    btn.setTextColor(Color.BLACK);

                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                    img.setVisibility(View.VISIBLE);
                    img.setRotation(90);
                    btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
                    btn.setTextColor(Color.BLUE);
                }
            }
        });

        String a = "1234567";
        a.contains("334");

        mCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TransitionManager.beginDelayedTransition(relativeLayout, new Recolor());
//                mCollect.setBackgroundDrawable(
//                        new ColorDrawable(getResources().getColor(!mColorsInverted ? R.color.colorAccent :
//                                R.color.colorPrimary)));
//                mColorsInverted = !mColorsInverted;

//                new CustomDialog.Builder()
//                        .setOnPositiveClickListener(new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        }).setOnNegativeClickListener(new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                })
//                .build().show(getSupportFragmentManager(), "xxx");

                Intent intent = new Intent(MainActivity.this, TransitionActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                }


            }
        });
    }
}
