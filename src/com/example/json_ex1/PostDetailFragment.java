package com.example.json_ex1;

import com.example.json_ex1.data.FeedContent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class PostDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    FeedContent.FeedItem mItem;

    public PostDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //получим по ARG_ITEM_ID тек.элемент 
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = FeedContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	// если полученное не пусто ложим его в  WebView
        View rootView = inflater.inflate(R.layout.fragment_post_detail, container, false);
        if (mItem != null) {
        	((WebView) rootView.findViewById(R.id.post_detail)).loadDataWithBaseURL("", mItem.getContent(), null, "UTF-8", null);
        }
        return rootView;
    }
}
