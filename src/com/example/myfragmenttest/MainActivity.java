package com.example.myfragmenttest;

import com.example.myfragmenttest.R;
import com.example.myfragmenttest.MyListFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
        	
        	/*
        	 * Создадим фрагмент и добавим его к  активити   
        	 * у активити должен быть id 
        	 * используем ф-цию для поддежки 
        	 * 
        	 * 
        	 * 
        	 * */
        	
            //getSupportFragmentManager().beginTransaction().add(R.id.list_frag, new MyListFragment()).commit();
        	  FragmentManager fm       = getSupportFragmentManager();
        	  FragmentTransaction ft=fm.beginTransaction();
        	  ft.add(R.id.list_frag, new MyListFragment());
        	  ft.commit();
        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
