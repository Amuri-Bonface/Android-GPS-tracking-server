package com.example.florence.mapsagain;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Retrieve_call_sms extends Activity {
    private TextView textview_call;
    private Button btn_call, btn_messages, btn_drafts;
    private static final String URL = "http://boggy-dispatcher.000webhostapp.com/nannie_track/messages_retrieve.php";
    private static final String URL2 = "http://boggy-dispatcher.000webhostapp.com/nannie_track/call_retrieve.php";
    private String owner_email;
    private String mail;
    private String jsonResponse = "";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
try {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.retrieve_call_sms);

    progressDialog = new ProgressDialog(this);

    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        return;
    }
    Pattern gmailPattern = Patterns.EMAIL_ADDRESS;
    Account[] accounts = AccountManager.get(Retrieve_call_sms.this).getAccounts();
    for (Account account : accounts) {
        if (gmailPattern.matcher(account.name).matches()) {
            owner_email = account.name;
        }
    }
    mail = owner_email;
    //navigation Bar
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    textview_call = (TextView) findViewById(R.id.textview_call);

    btn_messages = (Button) findViewById(R.id.btn_messages);
    btn_messages.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            textview_call.setText("");

            progressDialog.setMessage("Checking Details Please Wait...");
            progressDialog.show();
           new getMessages().execute();

        }
    });
    btn_call = (Button) findViewById(R.id.btn_call);
    btn_call.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            textview_call.setText("");

            progressDialog.setMessage("Checking Details Please Wait...");
            progressDialog.show();
            new getCallLog().execute();

        }
    });
    btn_drafts = (Button) findViewById(R.id.btn_draft);

}catch (Exception e){}
    }

    public class getMessages extends AsyncTask<String, Void, String> {
        ProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(Retrieve_call_sms.this, "please wait", "loading", true);
            pd.setCancelable(true);
        }
        @Override
        protected String doInBackground(String... params) {
            Map<String,String> para=new HashMap<>();
            para.put("owner_email",mail);

         Custom_Volly_Request msgReq = new Custom_Volly_Request(Request.Method.POST,URL,para,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject myMsg = (JSONObject) response.get(i);
                            String phone = myMsg.getString("phone");
                            String typeofsms = myMsg.getString("typeofsms");
                            String smsdaytime = myMsg.getString("smsdaytime");
                            String body = myMsg.getString("body");
                            String email = myMsg.getString("email");

                           jsonResponse += "Email:: " + email + "\n\n";
                            jsonResponse += "Phone Number:: " + phone + "\n\n";
                            jsonResponse += "Message Type:: " + typeofsms + "\n\n";
                            jsonResponse += "Date:: " + smsdaytime + "\n\n";
                            jsonResponse += "Message Body:: " + body + "\n\n";
                            jsonResponse +="------------------"+ "\n\n";
                        }
                        textview_call.setText(jsonResponse);

                    } catch (JSONException e)
                    {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            }){

         }
                 ;
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(msgReq);
            return null;
        }
        @Override
        protected void onPostExecute(String result)
        {
            progressDialog.show(); pd.dismiss();
        }
    }

    public class getCallLog extends AsyncTask<String, Void, String> {

        ProgressDialog pd = null;
               @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(Retrieve_call_sms.this, "please wait", "loading", true);
            pd.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {
            Map<String,String> hashMap=new HashMap<>();
            hashMap.put("owner_email",mail);

            Custom_Volly_Request req = new Custom_Volly_Request(Request.Method.POST,URL2,hashMap,new Response.Listener<JSONArray>() {
            @Override
             public void onResponse(JSONArray response) {
                   try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jdata = (JSONObject) response.get(i);
                            String phone = jdata.getString("phone");
                            String calltype = jdata.getString("calltype");
                            String calldaytime = jdata.getString("calldaytime");
                            String callduration = jdata.getString("callduration");
                            String email = jdata.getString("email");

                            jsonResponse += "Phone Number:: " + phone + "\n\n";
                            jsonResponse += "Call Type:: " + calltype + "\n\n";
                            jsonResponse += "Date:: " + calldaytime + "\n\n";
                            jsonResponse += "Duration:: " + callduration + "seconds"+"\n\n";
                            jsonResponse +="------------------------------------"+"\n\n";
                        }
                        textview_call.setText(jsonResponse);

                    } catch (JSONException e)
                    {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue q = Volley.newRequestQueue(getApplicationContext());
            q.add(req);

            return null;
        }
        @Override
        protected void onPostExecute(String result)
        {
            progressDialog.show(); pd.dismiss();
            // textview_call.setText(jsonResponse);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent myIntent = new Intent(Retrieve_call_sms.this,Retrieving_cordinates.class);
                    startActivity(myIntent);
                    finish();
                    return true;
                case R.id.navigation_dashboard:
                    Intent i = new Intent(Retrieve_call_sms.this,Register.class);
                    startActivity(i);
                    finish();
                    return true;
                case R.id.navigation_personal:
                    Intent x = new Intent(Retrieve_call_sms.this,Retrieve_call_sms.class);
                    startActivity(x);
                    finish();
            }
            return false;
        }

    };
    public void drafts ()
    {
        try {
            Intent smsIntent = new Intent("android.intent.action.VIEW");
            smsIntent.setType("vnd.android-dir/mms-sms");
            //smsIntent.putExtra("address", this.val$phone1);
           // smsIntent.putExtra("sms_body", jsonResponse);
            startActivity(smsIntent);
        }catch (Exception e){}
    }

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS},100);
            return true;
        }
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS},200);
            return true;
        }
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS},300);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                runtime_permissions();

            } else {
                runtime_permissions();
            }
        }
        if (requestCode == 200) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                runtime_permissions();

            } else {
                runtime_permissions();
            }
        }
        if (requestCode == 300) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                runtime_permissions();

            } else {
                runtime_permissions();
            }
        }
    }

}

