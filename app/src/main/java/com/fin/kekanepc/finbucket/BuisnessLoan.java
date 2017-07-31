package com.fin.kekanepc.finbucket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.michaldrabik.tapbarmenulib.TapBarMenu;

public class BuisnessLoan extends AppCompatActivity {
    Button GetEmi,CalculateEmi;
    private TapBarMenu tapBarMenu;
    ImageView Apphome,termCond,contact,aboutus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_buisness_loan);

        Apphome=(ImageView)findViewById(R.id.item1);
        termCond=(ImageView)findViewById(R.id.item2);
        contact=(ImageView)findViewById(R.id.item3);
        aboutus=(ImageView)findViewById(R.id.item4);


        final Animation animCalculateovarshoot = AnimationUtils.loadAnimation(this, R.anim.calculateemianimation);
        final Animation animThinkoversoot = AnimationUtils.loadAnimation(this, R.anim.thinkanimation);
        GetEmi=(Button)findViewById(R.id.buttoncalculate);
        CalculateEmi=(Button)findViewById(R.id.think);
        GetEmi.startAnimation(animCalculateovarshoot);
        CalculateEmi.startAnimation(animThinkoversoot);

        tapBarMenu=(TapBarMenu)findViewById(R.id.tapBarMenu);
        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });

        ExpandablePanel panel = (ExpandablePanel)findViewById(R.id.foo);
        panel.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                Button btn = (Button) handle;
                btn.setText("");
            }

            public void onExpand(View handle, View content) {
                Button btn = (Button) handle;
                btn.setText("");
                btn.setBackgroundResource(R.drawable.up);
            }
        });



        GetEmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BuisnessLoan.this, CalculateEmiBuisness.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
        CalculateEmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BuisnessLoan.this, Calculator.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
        Apphome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BuisnessLoan.this, AppHome.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_in_up );
            }
        });

        termCond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BuisnessLoan.this, TermCondition.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BuisnessLoan.this, ContactUs.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BuisnessLoan.this, AboutUs.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
