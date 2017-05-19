package com.example.welcome.floatingbutton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class disposeActivity extends AppCompatActivity {
    Bundle b;
    String jwt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispose);
        b=getIntent().getExtras();
        jwt=b.getString("jwt");
        // Toast.makeText(disposeActivity.this,jwt,Toast.LENGTH_SHORT).show();
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            final  String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            TextView t=(TextView)findViewById(R.id.barcode);



            try {
                // Toast.makeText(LoginActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JSONObject body = new JSONObject();
                body.put("barcode", scanContent);
                //  Toast.makeText(LoginActivity.this, body.toString(), Toast.LENGTH_SHORT).show();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://techwhiz.puritan53.hasura-app.io/barcodedetails",body, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(disposeActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                       /* if(response.optBoolean("status")==false)
                        {
                            Toast.makeText(disposeActivity.this,"Authentication failed!!Invalid Details", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Bundle b=new Bundle();
                            try {
                                // Toast.makeText(LoginActivity.this, response.getString("jwt"), Toast.LENGTH_SHORT).show();
                                b.putString("jwt", response.getString("jwt"));
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }

                            Intent in=new Intent(getApplicationContext(),MainActivity.class);
                            in.putExtras(b);
                            startActivity(in);
                        }*/
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error!", error.toString());
                    }
                }){  @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/json");
                    params.put("auth","Bearer "+jwt);
                    return params;
                }};


                queue.add(jsonObjectRequest);

            } catch (Exception e) {
                e.printStackTrace();
            }









            t.setText("The barcode of the device scanned is"+scanContent+"\nENTER SERVICE DETAILS ");

            final Spinner dt=(Spinner)findViewById(R.id.defecttype);
            final  Spinner p=(Spinner)findViewById(R.id.priority);
            final EditText dd=(EditText)findViewById(R.id.defectdesc);
            Button ok=(Button)findViewById(R.id.ok);


            ok.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    String dtype=dt.getSelectedItem().toString();
                    String pr=p.getSelectedItem().toString();
                    String ddesc=dd.getText().toString();

                    try {
                        // Toast.makeText(LoginActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        // RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        JSONObject body = new JSONObject();
                        body.put("barcode",scanContent);
                        body.put("defect_type",dtype);
                        body.put("defect_description",ddesc);
                        body.put("priority",pr);
                        //  Toast.makeText(LoginActivity.this, body.toString(), Toast.LENGTH_SHORT).show();
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://techwhiz.puritan53.hasura-app.io/service/requests",body, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(disposeActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                try{
                                    String s;
                                    //  s=response.getString("quote");
                                    //  Toast.makeText(Inventory.this, s, Toast.LENGTH_SHORT).show();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley Error!", error.toString());
                            }
                        }){  @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("Content-Type","application/json");
                            params.put("auth","Bearer "+jwt);
                            return params;
                        }};


                        queue.add(jsonObjectRequest);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                    Toast.makeText(disposeActivity.this, "clicked"+dtype+pr+ddesc, Toast.LENGTH_SHORT).show();
                }
            });
        } else {

        }
    }
}