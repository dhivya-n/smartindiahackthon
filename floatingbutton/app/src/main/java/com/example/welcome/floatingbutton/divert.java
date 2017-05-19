package com.example.welcome.floatingbutton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class divert extends AppCompatActivity {
    String scanContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divert);
        final Bundle b=getIntent().getExtras();
        Button update_scan=(Button)findViewById(R.id.scan);

        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();

        update_scan.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),divert2.class);
                Toast.makeText(divert.this, scanContent, Toast.LENGTH_SHORT).show();
               b.putString("oldbarcode",scanContent);
                in.putExtras(b);
               startActivity(in);

            }
        });


    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try{
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
                scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                Toast.makeText(divert.this, scanContent, Toast.LENGTH_SHORT).show();


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
