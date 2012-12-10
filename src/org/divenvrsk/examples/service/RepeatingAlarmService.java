package org.divenvrsk.examples.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class RepeatingAlarmService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
		 Intent i = new Intent("com.example.ServiceActivity.SERVICE");
		 i.putExtra("onlineStatus", isOnline(context));
		 context.sendBroadcast(i);
    }

    public boolean isOnline(Context ctx) {
		final ConnectivityManager conMgr = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo i = conMgr.getActiveNetworkInfo();
		if (i == null) {
			return false;
		}
		if (!i.isConnected()) {
			return false;
		}
		if (!i.isAvailable()) {
			return false;
		}
		return true;
	}

    
}
