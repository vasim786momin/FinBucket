package com.fin.kekanepc.finbucket;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.daimajia.slider.library.SliderLayout;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class MainActivity extends AppCompatActivity {
    ShimmerTextView shimmerTextView;
    Shimmer shimmer;
    SliderLayout sliderShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Animation animLinear = AnimationUtils.loadAnimation(this, R.anim.linear);
        final Animation animOvershoot = AnimationUtils.loadAnimation(this, R.anim.overshoot);

        shimmerTextView=(ShimmerTextView)findViewById(R.id.shimmer_tv);
        sliderShow = (SliderLayout) findViewById(R.id.slider);
        //shimmerTextView.startAnimation(animLinear);
        shimmerTextView.startAnimation(animOvershoot);
        shimmer = new Shimmer();
        shimmer.start(shimmerTextView);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

       Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(4*1000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(MainActivity.this,AppHome.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(i);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
    }
}
