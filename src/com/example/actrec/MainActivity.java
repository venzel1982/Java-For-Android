package com.example.actrec;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.example.actrec.dbentities.User;
import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.ActiveRecordException;
import org.kroz.activerecord.Database;
import org.kroz.activerecord.DatabaseBuilder;
import org.kroz.activerecord.EntitiesHelper;
import org.kroz.activerecord.utils.Logg;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Class under construction
 * 
 * @author VKROZ
 * 
 */
public class MainActivity extends ListActivity {
	// Convenience variables to use with logger
	static final String TAG = Const.TAG;
	static final String CNAME = MainActivity.class.getSimpleName();

	static final User[] DUMMY = new User[10];

	ActiveRecordBase _db;
	List<User> _users;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example_activity);

		// Purge table and populate with test data
		initDb();

	 setListAdapter(new ArrayAdapter<User>(this,
				android.R.layout.simple_list_item_1 ,_users));

		
		
		

		
	}

	/**
	 * Purge table and populate with tets data
	 */
	private void initDb() {
		try {
			
			 DatabaseBuilder builder = new DatabaseBuilder(Const.DATABASE_NAME);      
		        		        builder.addClass(User.class);
		        Database.setBuilder(builder);
			// Open database
			_db = ActiveRecordBase.open(this, Const.DATABASE_NAME,
					Const.DATABASE_VERSION);

			// purge Users table
			_db.delete(User.class, null, null);

			// Insert DUMMY array into Users table
		
			
			
			User user = _db.newEntity(User.class);
            user.firstName = "John";
            user.lastName = "Smith";                  
            user.save();
            
            User user1 = _db.newEntity(User.class);
            user1.firstName = "111";
            user1.lastName = "222";
            user1.save();


			_users =  _db.find(User.class, false, null, null, null, null, null, null);
			 for (User cur : _users)
			 {
			     Logg.i(TAG, "User first name="+cur.firstName+", lastName="+cur.lastName);
			 }

		} catch (ActiveRecordException e) {
			Logg.e(TAG, e, "(%t) %s.initDb(): Error=%s", CNAME, e.getMessage());
		}
	}

	public void addRecord(View v) {
		Toast.makeText(this, "Under construction", Toast.LENGTH_LONG).show();
	}

	public void deleteRecord(View v) {
		Toast.makeText(this, "Under construction", Toast.LENGTH_LONG).show();
	}

	public void populateDb(View v) {
		Toast.makeText(this, "Under construction", Toast.LENGTH_LONG).show();
	}

	public void finishActivity(View view) {
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != _db) {
			_db.close();
		}
	}

	
}