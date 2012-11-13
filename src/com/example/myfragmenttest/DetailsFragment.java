package com.example.myfragmenttest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.myfragmenttest.R;

public class DetailsFragment extends Fragment {

    public static final String EXTRA_TEXT = "extra_text";

    private static final int REQUEST_NUMBER = 1;

    private View view;
    private String text;

    public static DetailsFragment newInstance(String text) {
        DetailsFragment f = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            text = args.getString(EXTRA_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.details_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            text = savedInstanceState.getString("text");
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) view.findViewById(R.id.text);
                tv.setText(text);
            }
        });

        Button replace = (Button) view.findViewById(R.id.details_btn);
        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RandNumberFragment numberFragment = RandNumberFragment.newInstance(text);
                numberFragment.setTargetFragment(DetailsFragment.this, REQUEST_NUMBER);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frag_container, numberFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", text);
    }

    public void setText(final String number, final int requestCode) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (requestCode) {
                    case 1:
                        text = text + " " + number;
                        break;
                }
            }
        });
    }
}
