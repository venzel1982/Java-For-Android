package com.example.json_ex1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
//import com.actionbarsherlock.internal.view.MenuItem;
import com.actionbarsherlock.view.MenuItem;

public class PostListActivity extends SherlockFragmentActivity
        implements PostListFragment.Callbacks {
    public static final String EXTRA_TEXT = "extra_text";
    private static final int REQUEST_NUMBER = 1;

    private View view;
    private String text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
    }
    
    
    @Override
    public void onItemSelected(String id) {
            Intent detailIntent = new Intent(this, PostDetailActivity.class);
            detailIntent.putExtra(PostDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
    }
}
