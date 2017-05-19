package com.example.welcome.floatingbutton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Updatebarcode extends AppCompatActivity {
    String scanContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatebarcode);


        Button update_scan=(Button)findViewById(R.id.scanbutton);
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();

        update_scan.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
             Intent in=new Intent(getApplicationContext(),Updatedbarcode.class);
                Bundle b=getIntent().getExtras();
                b.putString("oldbarcode",scanContent);
                in.putExtras(b);

                startActivity(in);
            }
        });
       }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try{
            TextView t=(TextView)findViewById(R.id.barcode);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            t.setText(scanContent);
        }
    }catch (Exception e){
            e.printStackTrace();
        }
}}
