package com.fin.kekanepc.finbucket;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.michaldrabik.tapbarmenulib.TapBarMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CalculateEmiHome extends AppCompatActivity
{
    private TapBarMenu tapBarMenu;
    EditText eUsername,eMobile,eEmail,eDob,eCity;
    RadioGroup genderGroup;
    RadioButton male,female;
    Button getEmi;
    String name,mobile,email,dob,city, gender = "male";
    String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_emi_home_final);
        getSupportActionBar().hide();

        eUsername   = (EditText)findViewById(R.id.edittext2);
        eMobile     = (EditText)findViewById(R.id.edittext3);
        eEmail      = (EditText)findViewById(R.id.edittext4);
        eDob        = (EditText)findViewById(R.id.edittext5);
        eCity       = (EditText)findViewById(R.id.edittext7);
        genderGroup = (RadioGroup)findViewById(R.id.rg);
        male        = (RadioButton)findViewById(R.id.male);
        female      = (RadioButton)findViewById(R.id.female);
        getEmi      = (Button)findViewById(R.id.get);


        tapBarMenu=(TapBarMenu)findViewById(R.id.tapBarMenu);
        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });

        final Calendar myCalendar = Calendar.getInstance();
        final Calendar limitCalender = Calendar.getInstance();
        limitCalender.add(Calendar.MONTH, -244);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                String myFormat = "dd/MMM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                int compare     =   myCalendar.compareTo(limitCalender);
                if(compare<0)
                    eDob.setText(sdf.format(myCalendar.getTime()));
                else
                    Toast.makeText(getBaseContext(), "You should be 23 year old", Toast.LENGTH_SHORT).show();
            }

        };

        eDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true)
                    new DatePickerDialog(CalculateEmiHome.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male) {
                    gender = "male";
                }
                if (checkedId == R.id.female) {
                    gender = "female";
                }
            }
        });




        getEmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                name       =  eUsername.getText().toString();
                mobile     =  eMobile.getText().toString();
                email      =  eEmail.getText().toString();
                dob        =  eDob.getText().toString();
                city       =  eCity.getText().toString();


                if(name.equals("")||mobile.equals("")||email.equals("")||city.equals("")||dob.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Any field can not be empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(mobile.length()!=10)
                    {
                        Toast.makeText(getApplicationContext(),"Mobile number digit should be 10",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Boolean b = email.matches(EMAIL_REGEX);
                        if(b==true)
                        {

                            // Toast.makeText(getApplicationContext(),"Call Service now with this data",Toast.LENGTH_SHORT).show();
                          //  System.out.println("Values on final 1 " + empStr + "\t" + propStr + "\t" + income);
                           // System.out.println("Values on Second 2" + loanAmt + "\t" + time + "\t" + emi);
                            System.out.println("Values on Second 3" + dob + "\t" + gender + "\t" + city);
                            System.out.println("Values on Second 4" + name + "\t" + mobile + "\t" + email);

                             Intent i = new Intent(CalculateEmiHome.this, CalculateEmiHomeFinal.class);
                              i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                 i.putExtra("name", name);
                                 i.putExtra("mobile", mobile);
                                 i.putExtra("email", email);
                                 i.putExtra("dob", dob);
                                 i.putExtra("city", city);
                                 i.putExtra("gender", gender);
                            startActivity(i);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);


                          //  HomeLoanInsertAsync obj = new HomeLoanInsertAsync();
                           // obj.execute(homeloaninsertUrl);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Wrong email id",Toast.LENGTH_SHORT).show();
                        }
                    }

                }


            }
        });
    }


}
