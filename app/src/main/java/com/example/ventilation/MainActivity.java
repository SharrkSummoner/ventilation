package com.example.ventilation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.bluetooth.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Switch switchMsg = findViewById(R.id.switch2);
        switchMsg.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
    }


    public void all(View view) {
        Intent intent = new Intent(this, TabbedActivity.class);
        MainActivity.this.startActivity(intent);
        MainActivity.this.finish();
    }

    class MyOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            String str = "";
            if (b == true) {
                str = "Включено";
            } else {
                str = "Выключено";
            }
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    }
}