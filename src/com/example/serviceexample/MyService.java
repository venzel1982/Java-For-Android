package com.example.serviceexample;

import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	final String LOG_TAG = "myLogs";
	private Context ctx;
	private boolean previousState;
	private boolean firstRun = false;

	public void onCreate() {
		super.onCreate();
		Log.d(LOG_TAG, "onCreate");
		ctx = this;
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(LOG_TAG, "onStartCommand");
		someTask();
		return super.onStartCommand(intent, flags, startId);
	}

	public void onDestroy() {
		super.onDestroy();
		Log.d(LOG_TAG, "onDestroy");
	}

	public IBinder onBind(Intent intent) {
		Log.d(LOG_TAG, "onBind");
		return null;
	}

	void someTask() {
		// new CheckConnection().execute();

		new Thread(new Runnable() {
			public void run() {

				while (true) {
					boolean curState = MyService.this.isOnline();

					if (curState != previousState || firstRun == true) {
						Intent intent = new Intent(
								"com.example.app.ONLIUINE_STATUS");
						intent.putExtra("OnlineStatus", curState);
						MyService.this.sendBroadcast(intent);

						previousState = curState;
						firstRun = false;
						try {
							//TimeUnit.MINUTES.sleep(1);
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					//stopSelf();
				}
			}
		}).start();
	}

	public boolean isOnline() {
		ConnectivityManager conMgr = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = conMgr.getActiveNetworkInfo();
		if (i == null)
			return false;
		if (!i.isConnected())
			return false;
		if (!i.isAvailable())
			return false;
		return false;
	}

	private class CheckConnection extends AsyncTask<Void, Void, Void> {
		private boolean previousState;
		private boolean firstRun = false;

		@Override
		protected Void doInBackground(Void... params) {
			/*
			 * делаем вечнЫЙ цикл в котором проверяем сосстояние связи если
			 * состояние не равно пред. или мы запускаем первый раз отправим
			 * сигнал sendBroadcast после чего запомним состояние и скажем что
			 * первый раз мы запускались после заснем на время ...
			 */
			while (true) {
				boolean curState = MyService.this.isOnline();

				if (curState != previousState || firstRun == true) {
					Intent intent = new Intent(
							"com.example.app.ONLIUINE_STATUS");
					intent.putExtra("OnlineStatus", curState);
					MyService.this.sendBroadcast(intent);

					previousState = curState;
					firstRun = false;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
}
