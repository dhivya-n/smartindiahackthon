package com.example.welcome.floatingbutton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Updatedbarcode extends AppCompatActivity {
    String scanContent,old,jwt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatedbarcode);
        Bundle b= getIntent().getExtras();
        old=b.getString("oldbarcode");
       jwt=b.getString("jwt");
        //Button update_scan=(Button)findViewById(R.id.scanbutton);
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try{
            TextView t=(TextView)findViewById(R.id.barcode);
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
                scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                t.setText("The barcode is changed from "+old+"to "+scanContent);
                try {
                    // Toast.makeText(LoginActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    // RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    JSONObject body = new JSONObject();
                    body.put("old_barcode",old);
                    body.put("new_barcode",scanContent);
                    //  Toast.makeText(LoginActivity.this, body.toString(), Toast.LENGTH_SHORT).show();
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://techwhiz.puritan53.hasura-app.io/inventory/updatebarcode",body, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(Updatedbarcode.this, "The Barcode is updated", Toast.LENGTH_SHORT).show();
                            Intent in=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(in);
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
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
