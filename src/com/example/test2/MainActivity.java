package com.example.test2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //скажем  что нам нужен кнопка с таким id 
        Button 	btn1 = (Button)findViewById(R.id.button1);
     
        
        btn1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			/* создаём  действие по  нажатию  
			 *  по нажатию на кнопку запустим  другое активити  
			  */	
				Intent	intent = new Intent();
				/* скажем что обработчиком будет SecondActivity.class*/
				intent.setClass(MainActivity.this, SecondActivity.class);
				/* запускаем  действие  , если хотим что б закрылось окно MainActivity  надо написать  finish()*/
				startActivity(intent);
			//	finish();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
