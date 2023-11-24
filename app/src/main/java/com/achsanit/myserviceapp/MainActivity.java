package com.achsanit.myserviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.achsanit.myserviceapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ServiceCallback {

    private ActivityMainBinding binding;
    private MyService myService;
    private Intent serviceIntent;
    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.LocalBinder binder = (MyService.LocalBinder) iBinder;
            myService = binder.getService();
            myService.setCallback(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            myService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpIntentBindService();

        binding.btnService.setOnClickListener(view -> {
            if (serviceIntent != null) { startService(serviceIntent); }
        });
    }

    private void setUpIntentBindService() {
        serviceIntent = new Intent(this, MyService.class);
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void serviceResult(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        binding.tvArgument.setText(result);
    }
}