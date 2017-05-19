package com.example.welcome.floatingbutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class servicedisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicedisplay);

        Bundle b=getIntent().getExtras();
    /*    Toast toast = Toast.makeText(getApplicationContext(),
                b.getCharSequence("content")
        , Toast.LENGTH_SHORT);
        toast.show();*/
    }
}
