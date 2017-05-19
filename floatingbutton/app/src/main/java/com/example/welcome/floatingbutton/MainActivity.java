package com.example.welcome.floatingbutton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {
  String jwt;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton logout_button, dispose_button, service_button, request_button,update_barcode,div,install_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       final Bundle b=getIntent().getExtras();
        jwt=b.getString("jwt");

       // Toast.makeText(MainActivity.this, jwt, Toast.LENGTH_SHORT).show();

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        logout_button = (FloatingActionButton) findViewById(R.id.logout);
        dispose_button = (FloatingActionButton) findViewById(R.id.dispose);
        service_button = (FloatingActionButton) findViewById(R.id.serreq);
        request_button = (FloatingActionButton) findViewById(R.id.inreq);
        update_barcode=(FloatingActionButton) findViewById(R.id.barcode);
      //  div=(FloatingActionButton) findViewById(R.id.divert);
        install_button=(FloatingActionButton)findViewById(R.id.indev);


        logout_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent dispose1 = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(dispose1);

            }
        });
     /*   div.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent dispose1 = new Intent(getApplicationContext(),divert.class);
                dispose1.putExtras(b);
                startActivity(dispose1);

            }
*/        dispose_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent dispose1 = new Intent(getApplicationContext(),removalActivity.class);
                dispose1.putExtras(b);
                startActivity(dispose1);//TODO something when floating action menu first item clicked

            }

        });
        service_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                Intent dispose = new Intent(getApplicationContext(),disposeActivity.class);
                dispose.putExtras(b);
                startActivity(dispose);


            }
        });
        install_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent dispose1 = new Intent(getApplicationContext(),installActivity.class);
                dispose1.putExtras(b);
                startActivity(dispose1);

            }
        });
        request_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent inven = new Intent(getApplicationContext(),Inventory.class);
                inven.putExtras(b);
                startActivity(inven);
            }
        });
        update_barcode.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            Intent inven = new Intent(getApplicationContext(),Updatebarcode.class);
            inven.putExtras(b);
            startActivity(inven);
        }
    });}
    }



