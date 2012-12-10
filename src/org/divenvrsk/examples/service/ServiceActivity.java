package org.divenvrsk.examples.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ServiceActivity extends Activity implements View.OnClickListener {
    Button buttonStart, buttonStop;
    TextView broadcastCounterTextView,
    			onlineStatusTextView;
    int broadcastCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        broadcastCounterTextView = (TextView) findViewById(R.id.broadcastCounterTextView);
        onlineStatusTextView = (TextView) findViewById(R.id.onlineStatusTextView);
        
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
    }

    @Override
	protected void onPause() {
        unregisterReceiver(serviceBroadcastReceiver);
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(serviceBroadcastReceiver,
				new IntentFilter("com.example.ServiceActivity.SERVICE"));
	}

	public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonStart:
                Log.v(this.getClass().getName(), "onClick: Starting service.");
                startService(new Intent(this, ServiceExample.class));
                break;
            case R.id.buttonStop:
                Log.v(this.getClass().getName(), "onClick: Stopping service.");
                stopService(new Intent(this, ServiceExample.class));
                break;
        }
    }
    
	private BroadcastReceiver serviceBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			ServiceActivity.this.receivedBroadcastFromTransferService(intent);
		}
	};
	
	protected void receivedBroadcastFromTransferService(Intent intent) {
		broadcastCount++;
		broadcastCounterTextView.setText(String.valueOf(broadcastCount));
		
		boolean onlineStatus = intent.getBooleanExtra("onlineStatus", false);
		String onlineStatusText = onlineStatus ? "online" : "offline";
		
		onlineStatusTextView.setText(onlineStatusText);
	}
}
