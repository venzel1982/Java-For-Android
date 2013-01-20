package com.example.json_ex1;

import java.util.List;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.ActiveRecordException;
import org.kroz.activerecord.Database;
import org.kroz.activerecord.DatabaseBuilder;
import org.kroz.activerecord.utils.Logg;

import com.example.json_ex1.data.FeedContent;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

import com.example.actrec.dbentities.Post;




import db.Const;

public class PostDetailActivity extends SherlockFragmentActivity {
	public static final int OPT_BUTTON_LIKE = 1;
	public static final int OPT_BUTTON_DISLIKE = 2;
	public String postid ;
	static final String TAG = Const.TAG;
	static final String CNAME = PostDetailActivity.class.getSimpleName();
	ActiveRecordBase _db;
	private Menu menu;
	FeedContent.FeedItem mItem;
	List<Post> _post;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_detail);
		//получим по ARG_ITEM_ID тек.элемент 
       
		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			Bundle arguments = new Bundle();
			arguments.putString(PostDetailFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(PostDetailFragment.ARG_ITEM_ID));
			PostDetailFragment fragment = new PostDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.post_detail_container, fragment).commit();
			
			mItem=	FeedContent.ITEM_MAP.get(getIntent().getStringExtra(PostDetailFragment.ARG_ITEM_ID));
			postid=mItem.getId();

		}

		try {
			DatabaseBuilder builder = new DatabaseBuilder(Const.DATABASE_NAME);
			builder.addClass(Post.class);
			Database.setBuilder(builder);
	
			_db = ActiveRecordBase.open(this, Const.DATABASE_NAME,
					Const.DATABASE_VERSION);	
			
		} catch (ActiveRecordException e) {
			Logg.e(TAG, e, "(%t) %s.initDb(): Error=%s", CNAME, e.getMessage());
		}

	}
	 private Intent createShareIntent() {
	        Intent shareIntent = new Intent(Intent.ACTION_SEND);
	        shareIntent.setType("image/*");
	        Uri uri = Uri.fromFile(getFileStreamPath("shared.png"));
	        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
	        return shareIntent;
	    }
	public boolean onCreateOptionsMenu(Menu menu) {
	/*	 getSupportMenuInflater().inflate(R.menu.post_detail_menu, menu);
		 MenuItem actionItem = menu.findItem(R.id.menu_item_share);

	        ShareActionProvider actionProvider = (ShareActionProvider) actionItem.getActionProvider();
	        actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);

	        actionProvider.setShareIntent(createShareIntent());
		//getMenuInflater().inflate(R.menu.post_detail_menu, menu);*/
		this.menu = menu;

		menu.add(0, PostDetailActivity.OPT_BUTTON_LIKE, 0, "Like")
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(0, PostDetailActivity.OPT_BUTTON_DISLIKE, 0, "Dislike")
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		try {
			 Logg.i(TAG,Boolean.toString( PostNotInBase(postid)));
			
		 if	(PostNotInBase(postid)){
			
			menu.findItem(PostDetailActivity.OPT_BUTTON_DISLIKE) .setVisible(false);
			menu.findItem(PostDetailActivity.OPT_BUTTON_LIKE) .setVisible(true);
		}else{	
			menu.findItem(PostDetailActivity.OPT_BUTTON_LIKE) .setVisible(false);
			menu.findItem(PostDetailActivity.OPT_BUTTON_DISLIKE).setVisible(true);
			
		}
			
			
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.onCreateOptionsMenu(menu);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpTo(this,
					new Intent(this, PostListActivity.class));
			return true;
		}

		if (item.getItemId() == PostDetailActivity.OPT_BUTTON_LIKE) {
			try {
				Post post = _db.newEntity(Post.class);
				post.id = mItem.getId();
				post.title = mItem.getTitle();
				post.date = mItem.getDate();
				post.content = mItem.getContent();
				post.save();
				
				ShowAll();
				
				menu.findItem(PostDetailActivity.OPT_BUTTON_DISLIKE) .setVisible(true);
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			item.setVisible(false);

		}
		if (item.getItemId() == PostDetailActivity.OPT_BUTTON_DISLIKE) {
			
			try {
				Post post = _db.newEntity(Post.class);
				post.deleteByColumn(Post.class, "id", postid);				
				ShowAll();
				
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			item.setVisible(false);
			menu.findItem(PostDetailActivity.OPT_BUTTON_LIKE).setVisible(true);
		}

		return super.onOptionsItemSelected(item);
	}
	public void ShowAll() throws ActiveRecordException{
		_post =  _db.find(Post.class, false, null, null, null, null, null, null);
		for (Post cur : _post)
		 {
		     Logg.i(TAG, "title="+cur.title);
		 }
		
	}

	public boolean PostNotInBase(String postid) throws ActiveRecordException{
		//
		Post post = _db.newEntity(Post.class);		
	if 	( _db.findByColumn(Post.class,"id",postid).isEmpty()){		
		return true;
	}else {
		return false;
	}
		
	}
}
