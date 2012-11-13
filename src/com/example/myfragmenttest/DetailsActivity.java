package com.example.myfragmenttest;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.example.myfragmenttest.R;
import com.example.myfragmenttest.DetailsFragment;
import com.example.myfragmenttest.RandNumberFragment;

public class DetailsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        if (savedInstanceState == null) {
            handleIntentExtras(getIntent());
        }
    }

    private void handleIntentExtras(Intent intent) {
        String text = "";
        if (intent.hasExtra(DetailsFragment.EXTRA_TEXT)) {
            text = intent.getStringExtra(DetailsFragment.EXTRA_TEXT);
        }

        DetailsFragment detailsFragment = DetailsFragment.newInstance(text);
        getSupportFragmentManager().beginTransaction().add(R.id.frag_container, detailsFragment).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_container);
        if (fragment instanceof RandNumberFragment) {
            ((RandNumberFragment) fragment).updateDetailsFragment();
        }

        super.onBackPressed();
    }
}
