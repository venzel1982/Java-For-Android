package com.example.serviceexample;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	final String LOG_TAG = "myLogs";
	 BroadcastReceiver br;
	 private String curCtatus;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // создаем BroadcastReceiver
        br = new BroadcastReceiver() {
          // действия при получении сообщений
          public void onReceive(Context context, Intent intent) {       
            
            boolean status=intent.getBooleanExtra(curCtatus,false);
            Log.d(LOG_TAG, "onReceive: status = 111111111" + status);
          }
          };
        
        
    }
    public void onClickStart(View v) {
        startService(new Intent(this, MyService.class));
      }
      
      public void onClickStop(View v) {
        stopService(new Intent(this, MyService.class));
      }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		/*
		 * остановить прослушку
		 * */
		super.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		/*
		 * начать
		 * */
		super.onResume();
	}
    
    
    
    
    
    
}
