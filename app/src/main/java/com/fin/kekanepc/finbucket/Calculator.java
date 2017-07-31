package com.fin.kekanepc.finbucket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.michaldrabik.tapbarmenulib.TapBarMenu;

public class Calculator extends AppCompatActivity {
    private TapBarMenu tapBarMenu;
    ImageView Apphome,termCond,contact,aboutus;
    LinearLayout resultlayout;
    EditText interst,amount,time;
    Button calculate;
    String roi,amt,tme;
    TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_calculator);


        final Animation animBounceUpLeft = AnimationUtils.loadAnimation(this, R.anim.bounceupperleft);
        final Animation animBounceUpRight = AnimationUtils.loadAnimation(this, R.anim.bounceupperright);
        final Animation animBounceDownLeft = AnimationUtils.loadAnimation(this, R.anim.bouncedownleft);
        final Animation animBounceDownRight = AnimationUtils.loadAnimation(this, R.anim.bouncedownright);

        tapBarMenu=(TapBarMenu)findViewById(R.id.tapBarMenu);
        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });

        resultlayout    =   (LinearLayout)findViewById(R.id.resultlayout);

        calculate =   (Button)findViewById(R.id.calculate);

        interst   =   (EditText)findViewById(R.id.edittext);
        amount    =   (EditText)findViewById(R.id.edittext2);
        time      =   (EditText)findViewById(R.id.edittext3);

        show      =   (TextView)findViewById(R.id.textView4);


        Apphome=(ImageView)findViewById(R.id.item1);
        termCond=(ImageView)findViewById(R.id.item2);
        contact=(ImageView)findViewById(R.id.item3);
        aboutus=(ImageView)findViewById(R.id.item4);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roi=interst.getText().toString();
                amt=amount.getText().toString();
                tme=time.getText().toString();


                System.out.println("Data" + roi + "ampunt " + amt + "time " + tme);

                if(roi.length()!=0 && amt.length()!=0 && tme.length()!=0)
                {
                    double result = calculateEmi(roi,amt,tme);

                    String printResult = Double.toString(result);


                    resultlayout.setVisibility(View.VISIBLE);
                    show.setText(printResult +"  ₹");

                }
                else {
                    Toast.makeText(getApplicationContext(),"Please fill all the detail",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public double calculateEmi(String r,String a,String t)
    {
        double loanAmount     = Double.parseDouble(a);
        double rateOfInterest = Double.parseDouble(r);
        double year           = Double.parseDouble(t);
        double numberOfMonths = year*12;


        double temp = 1200;           //100*numberofmonths(12))
        double interestPerMonth = rateOfInterest/temp;
        //System.out.println(interestPerMonth);

        double onePlusInterestPerMonth = 1 + interestPerMonth;
        //System.out.println(onePlusInterestPerMonth);

        double powerOfOnePlusInterestPerMonth = Math.pow(onePlusInterestPerMonth,numberOfMonths);
        //System.out.println(powerOfOnePlusInterestPerMonth);

        double powerofOnePlusInterestPerMonthMinusOne = powerOfOnePlusInterestPerMonth-1;
        //System.out.println(powerofOnePlusInterestPerMonthMinusOne);

        double divides = powerOfOnePlusInterestPerMonth/powerofOnePlusInterestPerMonthMinusOne;

        double principleMultiplyInterestPerMonth = loanAmount * interestPerMonth;
        //System.out.println(principleMultiplyInterestPerMonth);

        double totalEmi =  principleMultiplyInterestPerMonth*divides;
        System.out.println("EMI per month (Exact) : " + totalEmi);

        double finalValue = Math.round( totalEmi * 100.0 ) / 100.0;

        System.out.println("EMI per month (Rounded) : " + finalValue);

       return  finalValue;
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
