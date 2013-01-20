package com.example.json_ex1;


import java.util.List;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;



import com.example.json_ex1.data.FeedContent;
import com.example.json_ex1.data.FeedContent.FeedItem;

import android.R;/*com.example.json_ex1.R;*/
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class PostListFragment extends SherlockListFragment {
    private static final int OPT_BUTTON_ALLLIKES = 3;

    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callbacks mCallbacks = sDummyCallbacks;
    private int mActivatedPosition = ListView.INVALID_POSITION;
    protected ListView list;
    protected View loading;
    protected LinearLayout loading_container;
    private ProgressDialog loadingDialog;

    public interface Callbacks {

        public void onItemSelected(String id);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };
    
    /*
     * создадим Handler который нужен за тем нормально прорисовать листвью   
     * */
    
    Handler		listChangedHandler = new Handler()
    {
    	public void handleMessage(android.os.Message msg) {
    		Log.i("-----------", "handle");
    		if(loadingDialog!=null)loadingDialog.dismiss();
            setListAdapter(new ArrayAdapter<FeedContent.FeedItem>(getActivity(),
                    R.layout.simple_list_item_activated_1,
                    R.id.text1,
                    FeedContent.ITEMS));
                    
    	}
    };

    
    @Override
	public void onResume() {
		super.onResume();
		reloadContent();
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FeedContent.itemsListChangedHandler = listChangedHandler;
        FeedContent.ctx = getActivity();
        //list = (ListView) view.findViewById(R.id.list);
        setHasOptionsMenu(true);
    }

	private void reloadContent() {
    	loadingDialog = ProgressDialog.show(getActivity(), null, "Loading...");
    	FeedContent.refresh();
	}
	
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null && savedInstanceState
                .containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }
    
    public PostListFragment() {
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        mCallbacks.onItemSelected(FeedContent.ITEMS.get(position).getId());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    public void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
   	 	super.onCreateOptionsMenu(menu, inflater);
   	 	if(FeedContent.allLikesMode) {
   	 		menu.add(0, OPT_BUTTON_ALLLIKES,0,"All").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
   	 	} else {
   	 		menu.add(0, OPT_BUTTON_ALLLIKES,0,"All likes").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
   	 	}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == OPT_BUTTON_ALLLIKES)
        {
        	FeedContent.allLikesMode = ! FeedContent.allLikesMode;
        	
        	getActivity().invalidateOptionsMenu();
        	reloadContent();
        }
		return super.onOptionsItemSelected(item);
	}
}
