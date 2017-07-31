package com.fin.kekanepc.finbucket;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fin.kekanepc.finbucket.Adapter.HomeLoanListAdapter;
import com.fin.kekanepc.finbucket.Adapter.PersonalListAdapter;
import com.fin.kekanepc.finbucket.Data.HomeLoanData;
import com.fin.kekanepc.finbucket.Data.PersonalLoanData;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class PersonalCalculatedEmiList extends AppCompatActivity {
    private TapBarMenu tapBarMenu;
    JSONArray array1;
    List<PersonalLoanData>personalLoanDataList;
    PersonalListAdapter personalListAdapter;
    PersonalLoanData personalLoanData;
    RecyclerView mRecyclerView;
    String loanAmt,time,salary;
    int Len;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_calculated_emi_list);
        getSupportActionBar().hide();

        Bundle extras =  getIntent().getExtras();
        loanAmt = extras.getString("LoanAmount");
        time    = extras.getString("Time");
        salary  = extras.getString("Salary");

        System.out.println("Values for fetching data" + loanAmt + "\t" + time + "\t" + salary);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layout        = (RelativeLayout)findViewById(R.id.layout);

        tapBarMenu=(TapBarMenu)findViewById(R.id.tapBarMenu);
        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });
        String homeEmiURL =  "http://www.finbucket.com/index.php/android/personalloan/fetching?loanammount="+loanAmt+"&income="+salary+"&time="+time;
        homeEmiURL = homeEmiURL.replaceAll(" ", "%20");
        System.out.println("URL call check "+homeEmiURL);
        personalLoanDataList = new ArrayList<PersonalLoanData>();
        personalListAdapter=new PersonalListAdapter(PersonalCalculatedEmiList.this,personalLoanDataList);
        mRecyclerView.setAdapter(personalListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        PersonalAsync obj = new PersonalAsync();
        obj.execute(homeEmiURL);
    }


    //Async Task class
    private class PersonalAsync extends AsyncTask<String, Void, String>
    {

        ProgressDialog pd;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pd = new ProgressDialog(PersonalCalculatedEmiList.this);
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
        protected void onPostExecute(String result) {
            pd.dismiss();
            System.out.println("\n get result from sever :" + result.toString());

            try {
                JSONObject response = new JSONObject(result);
                array1 = response.getJSONArray("personalemi");
                Len  = array1.length();



            }catch (JSONException e)
            {
                e.printStackTrace();
            }

            if (Len != 0) {
                if (result!=null)
                {
                    parseJSONResult();
                }
            }
            else
            {

                layout.setBackgroundResource(R.drawable.datanull);
                Toast.makeText(getApplicationContext(), "No Qotation avilable for this search criteria!!!", Toast.LENGTH_SHORT).show();
            }
        }
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
        System.out.println("\nResult before convert to string : " + inputStream);
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
    //method for parsing data
    public void parseJSONResult()
    {
        try {
            for (int i = 0; i < array1.length(); i++) {
                JSONObject jsonObject = (JSONObject) array1.get(i);
                String bankname       = jsonObject.getString("bank");
                String interst        = jsonObject.getString("rate");
                String calEmi         = jsonObject.getString("emi");
                String loanAmount     =jsonObject.getString("lamount");
                personalLoanData = new PersonalLoanData(bankname,interst,calEmi,loanAmount);
                personalLoanDataList.add(personalLoanData);
                System.out.println("\nBank" + bankname);
                System.out.println("\nRate of interst" + interst);
                System.out.println("\nCalculate Emi" + calEmi);
            }
            // mRecyclerView.setAdapter(homeLoanListAdapter);
            personalListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
