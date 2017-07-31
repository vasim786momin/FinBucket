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
import com.fin.kekanepc.finbucket.Adapter.MortageLoanListAdapter;
import com.fin.kekanepc.finbucket.Data.HomeLoanData;
import com.fin.kekanepc.finbucket.Data.MortageLoanData;
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

public class MortageCalculatedEmiList extends AppCompatActivity {
    private TapBarMenu tapBarMenu;
    JSONArray array1;
    List<MortageLoanData> mortageLoanDataList;
    MortageLoanListAdapter mortageLoanListAdapter;
    MortageLoanData mortageLoanData;
    RecyclerView mRecyclerView;
    String loanAmt,time,salary;
    int Len;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortage_calculated_emi_list);
        getSupportActionBar().hide();

        Bundle extras =  getIntent().getExtras();
        loanAmt = extras.getString("LoanAmount");
        time = extras.getString("Time");
        salary = extras.getString("Salary");

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

        String mortageEmiURL = "http://www.finbucket.com/index.php/android/mortgageloan/fetching?loanammount="+loanAmt+"&income="+salary+"&time="+time;
        mortageEmiURL = mortageEmiURL.replaceAll(" ", "%20");

        System.out.println(mortageEmiURL);


        mortageLoanDataList = new ArrayList<MortageLoanData>();
        mortageLoanListAdapter=new MortageLoanListAdapter(MortageCalculatedEmiList.this,mortageLoanDataList);
        mRecyclerView.setAdapter(mortageLoanListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MortageLoanAsync obj = new MortageLoanAsync();
        obj.execute(mortageEmiURL);
    }

    //Async Task class
    private class MortageLoanAsync extends AsyncTask<String, Void, String>
    {

        ProgressDialog pd;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pd = new ProgressDialog(MortageCalculatedEmiList.this);
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
                array1 = response.getJSONArray("mortgageemi");
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
                // finish();
              /*  Intent i = new Intent(HomeCalculatedEmiList.this, CalculateEmiHome.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);*/
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
                mortageLoanData = new MortageLoanData(bankname,interst,calEmi,loanAmount);
                mortageLoanDataList.add(mortageLoanData);
                System.out.println("\nBank" + bankname);
                System.out.println("\nRate of interst" + interst);
                System.out.println("\nCalculate Emi" + calEmi);
            }
            // mRecyclerView.setAdapter(homeLoanListAdapter);
            mortageLoanListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
