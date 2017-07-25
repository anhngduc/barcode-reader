/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.samples.vision.barcodereader;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Looper;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
/**
 * Main activity demonstrating how to pass extra parameters to an activity that
 * reads barcodes.
 */
public class MainActivity extends Activity {

    // use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView barcodeValue;
    private ListView list;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    public  JSONArray jArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusMessage = (TextView)findViewById(R.id.status_message);
        barcodeValue = (TextView)findViewById(R.id.barcode_value);

        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);
        list= (ListView) findViewById(R.id.listinvoice);
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        //Get the data (see above)
        getJson();
        Log.d(TAG,"3");
        //JSONObject  json =jArray;
         do{

        }while (jArray ==null);
        try {
           // JSONArray arr = json.getJSONArray("170724");
            //Loop the Array
            /*
            for(int i=0;i < jArray.length();i++){
                Log.d(TAG,"4");
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject e = jArray.getJSONObject(i);
                map.put("id",  String.valueOf(i));
                map.put("ProductCode", "ProductCode:" + e.getString("ProductCode"));
                map.put("Barcode", "Barcode:" + e.getString("Barcode"));
                map.put("ProductLot", "ProductLot:" + e.getString("ProductLot"));
                map.put("ProductName", "ProductName:" + e.getString("ProductName"));
                map.put("Date", "Date:" + e.getString("Date"));
                map.put("Quantity", "Quantity:" + e.getString("Quantity"));
                map.put("invoiceid", "invoiceid:" + e.getString("invoiceid"));
                mylist.add(map);
                Log.d(TAG,"ProductCode:" + e.getString("ProductCode"));
            }*/

            // Construct the data source
            ArrayList<ProductPack> arrayOfUsers = new ArrayList<ProductPack>();
// Create the adapter to convert the array to views
            MyAdapter adapter = new MyAdapter(this, arrayOfUsers);
// Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.listinvoice);
            listView.setAdapter(adapter);
            ArrayList<ProductPack> productList = ProductPack.fromJson(jArray);
            adapter.addAll(productList);



        } catch (Error e) {
            e.printStackTrace();
        }

       // ListAdapter adapter = new SimpleAdapter(this,mylist,)
       // MyAdapter adapter = new MyAdapter(mylist);
        //list.setAdapter(adapter);

      //  ListView listView = (ListView) findViewById(R.id.mobile_list);
      //  listView.setAdapter(adapter);

        findViewById(R.id.read_barcode).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // launch barcode activity.
                Intent intent = new Intent(MainActivity.this, BarcodeCaptureActivity.class);
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
                intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

                startActivityForResult(intent, RC_BARCODE_CAPTURE);
            }
        });
       findViewById(R.id.send_barcode).setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
               Log.d("send_barcode","Clicked");
               statusMessage.setText("Clicked");
               Log.d(TAG,"Barcode:"+barcodeValue.getText().toString());
               sendJson(barcodeValue.getText().toString(),"1");
           }
       });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }
        if (v.getId() == R.id.send_barcode){
            statusMessage.setText("Clicked");
        }

    }
     */
    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    statusMessage.setText(R.string.barcode_success);
                    barcodeValue.setText(barcode.displayValue);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    protected  void getJson(){
        Thread t = new Thread() {

            public void run() {
                InputStream is = null;
                String result = "";


                Log.d(TAG, "getJson - in");
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();
                String URL = "http://192.168.1.144:5000/todo/api/v1.0/tasks";
                try {
                    Log.d(TAG, "getJson - sended");

                    HttpGet get = new HttpGet(URL);
                    Log.d(TAG, "getJson - connect");

                    response = client.execute(get);
                    Log.d(TAG, "getJson - sended");
                    /*Checking response */

                    if (response != null) {
                        //InputStream in = response.getEntity().getContent(); //Get the data in the entity
                        //Log.d(TAG,"getJson-inputstream: "+ EntityUtils.toString(response.getEntity()));
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                        Log.d(TAG, "getJson-inputstream: " + is);


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "getJson - error " + e.toString());

                    //createDialog("Error", "Cannot Estabilish Connection");
                }
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                } catch (Exception e) {
                    Log.e("log_tag", "Error converting result " + e.toString());
                }
                //try parse the string to a JSON object
                try {
                    Log.d(TAG,"1");
                   // json = new JSONObject(result);
                    jArray= new JSONArray(result);
                    Log.d(TAG,"2");
                    //json.toJSONArray(jArray);
                    //Log.d(TAG,"2");
                } catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
                }
                Looper.loop(); //Loop in the message queue
            }


        };
        t.start();
    }

    protected void sendJson(final String barcodescan ,final String quant) {
        Thread t = new Thread() {

            public void run() {
                InputStream is = null;
                String result = "";
                Log.d(TAG,"sendJson - in:"+barcodescan+";" +quant);
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();
                String URL = "http://192.168.1.144:5000/todo/api/v1.0/tasks";
                try {
                    Log.d(TAG,"sendJson - sended");
                    HttpPost post = new HttpPost(URL);
                    Log.d(TAG,"sendJson - connect");
                    //json.put("email", email);
                    //json.put("password", pwd);



                    json.put("barcode", barcodescan);
                    json.put("quantity", "2711");
                    json.put("id", "2");
                    json.put("ProductCode", "115072");
                    json.put("ProductLot", "20170408");



                    StringEntity se = new StringEntity( json.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    Log.d(TAG,"sendJson - created");
                    ResponseHandler<String> responseHandler=new BasicResponseHandler();
                    String responseBody = client.execute(post, responseHandler);
                    //response = client.execute(post);
                    Log.d(TAG,"sendJson - sended");
                    /*Checking response */
                    if(responseBody!=null){
                        //InputStream in = response.getEntity().getContent(); //Get the data in the entity
                        //Log.d(TAG,"inputstream: "+ in);
                       // HttpEntity entity = response.getEntity();
                       // is = entity.getContent();
                        Log.d(TAG, "getJson-inputstream: " + responseBody);
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                    Log.d(TAG,"sendJson - error " + e.toString());

                    //createDialog("Error", "Cannot Estabilish Connection");
                }

                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();
    }
}
