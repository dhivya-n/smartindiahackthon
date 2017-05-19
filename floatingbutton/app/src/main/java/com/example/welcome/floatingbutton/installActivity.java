package com.example.welcome.floatingbutton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class installActivity extends AppCompatActivity {
    String scanContent;
    Bundle b;
    String jwt;
    Button yes,no;
    TextView t1,t2,t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        t4=(TextView)findViewById(R.id.t4);

        yes=(Button)findViewById(R.id.yes);
        no=(Button)findViewById(R.id.no);

         b=getIntent().getExtras();
       jwt=b.getString("jwt");

        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),install2.class);
                b.putString("type","divert");
                i.putExtras(b);
                startActivity(i);

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),install2.class);
                b.putString("type","buy");
                i.putExtras(b);
                startActivity(i);
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
                scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                //Toast.makeText(installActivity.this, scanContent, Toast.LENGTH_SHORT).show();
                b = getIntent().getExtras();
                jwt = b.getString("jwt");

                try {
                    // Toast.makeText(LoginActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    JSONObject body = new JSONObject();
                    body.put("buy_ref",scanContent);
                  //  body.put("uid", b.getString("uid"));
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://techwhiz.puritan53.hasura-app.io/inventory/install/buyer",body, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                           // Toast.makeText(installActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            try {
                                JSONArray json = response.getJSONArray("result");

                                    JSONObject j = json.getJSONObject(0);
                                    t1.setText(j.getString("model"));
                                t2.setText(j.getString("manufact"));
                                t3.setText(j.getString("cost"));
                                t4.setText(j.getString("date_requested"));
                               // Toast.makeText(installActivity.this, j.getString("uid"), Toast.LENGTH_SHORT).show();
                                b.putString("uid",j.getString("uid"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley Error!", error.toString());
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/json");
                            params.put("auth", "Bearer " + jwt);
                            return params;
                        }
                    };


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
