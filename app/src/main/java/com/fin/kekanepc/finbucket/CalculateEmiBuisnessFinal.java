package com.fin.kekanepc.finbucket;

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
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculateEmiBuisnessFinal extends AppCompatActivity {
    private TapBarMenu tapBarMenu;
    Button getemi;
    Spinner spinner,spinner1;
    EditText income,loanAmt,time,suitEmi,otherEdit;
    String profType,turnOver,dateOfIncorporate,loanAmount,loanTenure,suitableEmi,name,gender,email,mobile,dob,city,prof;
    LinearLayout other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_emi_buisness_final);

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
        otherEdit   = (EditText)findViewById(R.id.edittextother);
        other       = (LinearLayout)findViewById(R.id.other);

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
        String[] profession = new String[]{
                "Select Profession",
                "Architecture",
                "Doctor",
                "CA",
                "CS",
                "Buisness",
                "Others"
        };

        // Initializing a String Array
        String[] turnover = new String[]{
                "Select Turn Over Yearly",
                "Below 10 Lakh",
                "10 Lakh to 1 Crore",
                "Above 1 Crore",

        };

        final List<String> professionList = new ArrayList<>(Arrays.asList(profession));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,professionList){
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
        spinner.setPrompt("Select Profession :");

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                profType = spinner.getSelectedItem().toString();
                other.setVisibility(View.GONE);
                if(profType.equals("Others"))
                {
                    other.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        final List<String> turnoverList = new ArrayList<>(Arrays.asList(turnover));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                this,R.layout.spinner_item,turnoverList){
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
        spinner1.setPrompt("Select TurnOver yearly :");

        spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter(spinnerArrayAdapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                turnOver = spinner1.getSelectedItem().toString();

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

                System.out.println("Professin type"+profType);
                if (profType.equals("Others"))
                {
                    profType=otherEdit.getText().toString();
                    System.out.println("Professin type"+profType);
                }

                if(profType.equals("Select Profession"))
                {
                    Toast.makeText(getApplicationContext(), "select your Profession", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(turnOver.equals("Select Turn Over Yearly"))
                    {
                        Toast.makeText(getApplicationContext(),"select company turnover",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        dateOfIncorporate     =  income.getText().toString();
                        loanAmount  =  loanAmt.getText().toString();
                        loanTenure  =  time.getText().toString();
                        suitableEmi =  suitEmi.getText().toString();

                        if(dateOfIncorporate.equals("")||loanAmount.equals("")||loanTenure.equals("")||suitableEmi.equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Any field can not be empty",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                                try{
                                    int minc = Integer.valueOf(dateOfIncorporate);
                                    int lamt=Integer.valueOf(loanAmount);
                                    int ltime=Integer.valueOf(loanTenure);
                                    int lemi=Integer.valueOf(suitableEmi);

                                    if( minc<1930)
                                    {
                                        Toast.makeText(getApplicationContext(),"Year of incorporation above 1930",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        if( lamt <100000 || lamt > 3000000)
                                        {
                                            Toast.makeText(getApplicationContext(),"Loan Amount between 1 lakh - 30 Lakh",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            if( ltime > 10)
                                            {
                                                Toast.makeText(getApplicationContext(),"Max tenure is 10 years",Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                if( lemi <5000)
                                                {
                                                    Toast.makeText(getApplicationContext(),"Emi > 5000",Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {

                                                    System.out.println("Validation done");
                                                    System.out.println("Values" + dateOfIncorporate + "\t" + loanAmount + "\t" + loanTenure + "\t" + suitableEmi + "\t" + profType + "\t" + turnOver);


                                                    String homeloaninsertUrl ="http://www.finbucket.com/index.php/android/businessloan?name="+name+"&number="+mobile+"&city="+city+"&loanammount="+loanAmount+"&turnover="+turnOver+"&dob="+dob+"& email="+ email+"& gender="+gender+"&date_of_incorporation="+dateOfIncorporate+"& emi="+ suitableEmi+"&time="+loanTenure+"& profession=" +profType;

                                                    System.out.println(homeloaninsertUrl);



                                                    homeloaninsertUrl = homeloaninsertUrl.replaceAll(" ", "%20");

                                                    System.out.println("After replace :"+homeloaninsertUrl);

                                                    BuisnessLoanInsertAsync obj = new BuisnessLoanInsertAsync();
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


    private class BuisnessLoanInsertAsync extends AsyncTask<String, Void, String>
    {

        ProgressDialog pd;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pd = new ProgressDialog(CalculateEmiBuisnessFinal.this);
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

            if(result.equals("\tyes"))
            {
                Intent i = new Intent(CalculateEmiBuisnessFinal.this,BuisnessCalculatedEmiList.class);
                i.putExtra("LoanAmount", loanAmount);
                i.putExtra("Emi",suitableEmi);
                System.out.println("Emi"+suitableEmi);
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
