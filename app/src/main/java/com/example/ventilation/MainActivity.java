package com.example.ventilation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.bluetooth.*;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    TextView statusBlueDevice, pairedDevice;
    Button onBtn, offBtn, discoverBtn, pairedBtn;

    BluetoothAdapter bluetoothAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchMsg = findViewById(R.id.switch2);
        statusBlueDevice = findViewById(R.id.statusBluetooth);
        pairedDevice = findViewById(R.id.pairedDevice);
        onBtn = findViewById(R.id.onBtn);
        offBtn = findViewById(R.id.offBtn);
        discoverBtn = findViewById(R.id.discoverableBtn);
        pairedBtn = findViewById(R.id.pairedBtn);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            statusBlueDevice.setText("Bluetooth не подключен");
        } else {
            statusBlueDevice.setText("Bluetooth подключен");
        }

        onBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                if (!bluetoothAdapter.isEnabled()) {
                    showToast("Включение bluetooth");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                } else {
                    showToast("Bluetooth уже включен");
                }
            }
        });

        discoverBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                if (!bluetoothAdapter.isDiscovering()) {
                    showToast("Делаем устройство видимым");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });

        offBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                if (bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.disable();
                    showToast("Выключаем Bluetooth");
                } else {
                    showToast("Bluetooth уже выключен");
                }
            }
        });

        pairedBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                if (bluetoothAdapter.isEnabled()) {
                    pairedDevice.setText("Подключенные устройства");
                    Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
                    for (BluetoothDevice device : devices) {
                        pairedDevice.append("\nУстройство " + device.getName() + ", " + device);
                    }
                } else {
                    showToast("Сперва нужно включить bluetooth");
                }
            }
        });


        switchMsg.setOnCheckedChangeListener(new MyOnCheckedChangeListener());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    showToast("Bluetooth включен");
                } else {
                    showToast("Не удалось включить Bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
            if (b) {
                str = "Включено";
            } else {
                str = "Выключено";
            }
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    }
}