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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@SuppressWarnings("deprecation")
public class CalculateEmiHomeFinal extends AppCompatActivity {
    private TapBarMenu tapBarMenu;
    Button getemi;
    Spinner spinner,spinner1;
    EditText income,loanAmt,time,suitEmi;
    String empType,propType,mIncome,loanAmount,loanTenure,suitableEmi,name,gender,email,mobile,dob,city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_emi_home);
        getSupportActionBar().hide();

        Bundle extras =  getIntent().getExtras();
        name = extras.getString("name");
        gender = extras.getString("gender");
        dob = extras.getString("dob");
        city = extras.getString("city");
        email = extras.getString("email");
        mobile = extras.getString("mobile");

        getemi      = (Button)findViewById(R.id.next);
        income      = (EditText)findViewById(R.id.edittext);
        loanAmt     = (EditText)findViewById(R.id.edittext2);
        time        = (EditText)findViewById(R.id.edittext3);
        suitEmi     = (EditText)findViewById(R.id.edittext4);
        tapBarMenu=(TapBarMenu)findViewById(R.id.tapBarMenu);
        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });
        // Get reference of widgets from XML layout
        spinner  = (Spinner) findViewById(R.id.spinner);
        spinner1 = (Spinner) findViewById(R.id.spinner1);

        // Initializing a String Array
        String[] emp = new String[]{
                "EmploymentType",
                "Saleried",
                "Buisness"
        };

        // Initializing a String Array
        String[] property = new String[]{
                "PropertyTypes",
                "Movein",
                "Underconstruction",
                "Plot",
                "Bunglo"
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


        final List<String> propertyList = new ArrayList<>(Arrays.asList(property));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                this,R.layout.spinner_item,propertyList){
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
        spinner1.setPrompt("Select ProetyType :");

        spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter(spinnerArrayAdapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                propType = spinner1.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });





     getemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(empType.equals("EmploymentType"))
                {
                    Toast.makeText(getApplicationContext(),"select employment types",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(propType.equals("PropertyTypes"))
                    {
                        Toast.makeText(getApplicationContext(),"select property types",Toast.LENGTH_SHORT).show();

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

                                if( minc<10000)
                                {
                                    Toast.makeText(getApplicationContext(),"Minimum Monthly salary 35000",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    if( lamt <5000000 || lamt > 500000000)
                                    {
                                        Toast.makeText(getApplicationContext(),"Loan Amount between 50 lakh - 50Cr",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        if( ltime > 30)
                                        {
                                            Toast.makeText(getApplicationContext(),"Max tenure is 30 years",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            if( lemi <10000)
                                            {
                                                Toast.makeText(getApplicationContext(),"Emi > 10000",Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {

                                                System.out.println("Validation done");
                                                System.out.println("Values" + mIncome + "\t" + loanAmount + "\t" + loanTenure + "\t" + suitableEmi + "\t" + empType + "\t" + propType);


                                                String homeloaninsertUrl ="http://www.finbucket.com/index.php/android/homeloan?name="+name+"&number="+mobile+"&city="+city+"&loanammount="+loanAmount+"&income="+mIncome+"&dob="+dob+"& email="+ email+"& gender="+gender+"&type_of_property="+propType+"& emi="+ suitableEmi+"&time="+loanTenure+"& emptype=" +empType;

                                                System.out.println(homeloaninsertUrl);



                                                homeloaninsertUrl = homeloaninsertUrl.replaceAll(" ", "%20");

                                                System.out.println("After replace :"+homeloaninsertUrl);

                                                 HomeLoanInsertAsync obj = new HomeLoanInsertAsync();
                                                 obj.execute(homeloaninsertUrl);
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



            }
        });

    }

    public static String GET(String url)
    {
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
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


    private class HomeLoanInsertAsync extends AsyncTask<String, Void, String>
    {

        ProgressDialog pd;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pd = new ProgressDialog(CalculateEmiHomeFinal.this);
            pd.setMessage("please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... urls)
        {
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result)
        {
            pd.dismiss();

            System.out.println("\n get result from sever :" + result.toString());

            if(result.equals("yes"))
            {
                Intent i = new Intent(CalculateEmiHomeFinal.this,HomeCalculatedEmiList.class);
                i.putExtra("LoanAmount", loanAmount);
                i.putExtra("Salary",mIncome);
                System.out.println("Salary"+mIncome);
                i.putExtra("Time",loanTenure);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
            else
            {

                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        }
    }

}
