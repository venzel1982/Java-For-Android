package org.divenvrsk.examples.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class RepeatingAlarmService extends BroadcastReceiver {
	private Context ctx;
    @Override
    
    public void onReceive(Context context, Intent intent) {
   // if	(this.netConnect(ctx)) {
       // Toast.makeText(context, "It's Service Time!", Toast.LENGTH_LONG).show();
        //Log.v(this.getClass().getName(), "Timed alarm onReceive() started at time: " + new java.sql.Timestamp(System.currentTimeMillis()).toString());
 //  	if  (this.isOnline()){    	
    		Toast.makeText(context, "Internet connected ", Toast.LENGTH_LONG).show();
    //		}    	
   //	}else{    	
    	//	Toast.makeText(context, "Internet disconnected ", Toast.LENGTH_LONG).show();
    //	}
    	
  ///  	Toast.makeText(context, "Internet disconnected ", ).show();
    
    	
    	
    }
    public boolean netConnect(Context ctx)
    {

        ConnectivityManager cm;
        NetworkInfo info = null;
        try
        {
            cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            info = cm.getActiveNetworkInfo();
        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
        if (info != null)
        {
            return true;

        }
        else
        {
            return false;
        }
    }
    
   
    
    public boolean isOnline() {
    	  String cs = Context.CONNECTIVITY_SERVICE;
    
		ConnectivityManager cm = (ConnectivityManager)
    			  ctx.getSystemService(cs);
    	  if (cm.getActiveNetworkInfo() == null) {
    	    return false;
    	  }
    	  return     cm.getActiveNetworkInfo().isConnectedOrConnecting();
    	}

    
}
