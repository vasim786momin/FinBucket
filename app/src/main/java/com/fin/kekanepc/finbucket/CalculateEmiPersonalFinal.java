package com.fin.kekanepc.finbucket;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.michaldrabik.tapbarmenulib.TapBarMenu;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
@SuppressWarnings("deprecation")
public class CalculateEmiPersonalFinal extends AppCompatActivity {
    Button GetEmi;
    private TapBarMenu tapBarMenu;
    ImageView Apphome,termCond,contact,aboutus;
    Spinner spinner;
    EditText income,loanAmt,time,suitEmi;
    String empType,mIncome,loanAmount,loanTenure,suitableEmi,name,mobile,email,gender,dob,city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_calculate_emi_personal);
        GetEmi      = (Button)findViewById(R.id.next);
        income      = (EditText)findViewById(R.id.edittext);
        loanAmt     = (EditText)findViewById(R.id.edittext2);
        time        = (EditText)findViewById(R.id.edittext3);
        suitEmi     = (EditText)findViewById(R.id.edittext4);


        Bundle extras = getIntent().getExtras();
        name   = extras.getString("name");
        mobile = extras.getString("mobile");
        email  = extras.getString("email");
        gender = extras.getString("gender");
        dob    = extras.getString("dob");
        city   = extras.getString("city");

        tapBarMenu=(TapBarMenu)findViewById(R.id.tapBarMenu);
        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });
        // Get reference of widgets from XML layout
        spinner  = (Spinner) findViewById(R.id.spinner1);

        // Initializing a String Array
        String[] emp = new String[]{
                "EmploymentType",
                "Saleried",
                "Buisness"
        };


        final List<String> empList = new ArrayList<>(Arrays.asList(emp));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,empList){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position%2 == 1) {
                    // Set the item background color
                    tv.setBackgroundColor(Color.parseColor("#3d043f"));
                }
                else {
                    // Set the alternate item background color
                    tv.setBackgroundColor(Color.parseColor("#3d043f"));
                }
                return view;
            }
        };


        /*
            public void setPrompt (CharSequence prompt)
                Sets the prompt to display when the dialog is shown.
         */
        spinner.setPrompt("Select Employment :");

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                empType = spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        GetEmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(empType.equals("EmploymentType"))
                {
                    Toast.makeText(getApplicationContext(), "select employment types", Toast.LENGTH_SHORT).show();
                }
                else {
                    mIncome     =  income.getText().toString();
                    loanAmount  =  loanAmt.getText().toString();
                    loanTenure  =  time.getText().toString();
                    suitableEmi =  suitEmi.getText().toString();

                    if(mIncome.equals("")||loanAmount.equals("")||loanTenure.equals("")||suitableEmi.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Any field can not be empty",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        try{
                            int minc = Integer.valueOf(mIncome);
                            int lamt=Integer.valueOf(loanAmount);
                            int ltime=Integer.valueOf(loanTenure);
                            int lemi=Integer.valueOf(suitableEmi);

                            if( minc<7000)
                            {
                                Toast.makeText(getApplicationContext(),"Monthly salary minimum 7000",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                if( lamt <50000 || lamt > 5000000)
                                {
                                    Toast.makeText(getApplicationContext(),"Loan Amount between 50 Thousands - 50 Lakh",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    if( ltime > 10)
                                    {
                                        Toast.makeText(getApplicationContext(),"Max tenure is 10 years",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        if( lemi <1000)
                                        {
                                            Toast.makeText(getApplicationContext(), "Emi > 1000",Toast.LENGTH_SHORT).show();
                                        } else {

                                                    System.out.println("Values" + mIncome + "\t" + loanAmount + "\t" + loanTenure + "\t" + suitableEmi + "\t" + empType);
                                                    String personaloaninsertUrl = "http://www.finbucket.com/index.php/android/personalloan?name=" + name + "&number=" + mobile + "&city=" + city + "&loanammount=" + loanAmount + "&income=" + mIncome + "&dob=" + dob + "& email=" + email + "& gender=" + gender + "& emi=" + suitableEmi + "&time=" + loanTenure + "& emptype=" + empType;

                                                System.out.println(personaloaninsertUrl);

                                                personaloaninsertUrl = personaloaninsertUrl.replaceAll(" ", "%20");

                                                System.out.println("After replace :" + personaloaninsertUrl);


                                                PersonalLoanInsertAsyn obj = new PersonalLoanInsertAsyn();
                                                obj.execute(personaloaninsertUrl);

                                        }
                                    }
                                }
                            }
                        }
                        catch(NumberFormatException e){
                            e.printStackTrace();
                        }

                    }


                }
            }

        });

    }

    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
    private class PersonalLoanInsertAsyn extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(CalculateEmiPersonalFinal.this);
            pd.setMessage("please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();

            System.out.println("\n get result from sever :" + result.toString());

            if (result.toString().equals("\tyes"))
            {
                Toast.makeText(getApplicationContext(),"Here success full",Toast.LENGTH_LONG).show();
                //   Toast.makeText(getApplicationContext(),"Successfull"+result.toString(),Toast.LENGTH_LONG).show();
                Intent i = new Intent(CalculateEmiPersonalFinal.this, PersonalCalculatedEmiList.class);
                i.putExtra("LoanAmount", loanAmount);
                i.putExtra("Salary", mIncome);
                i.putExtra("Time", loanTenure);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } else {

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }
    }


}
