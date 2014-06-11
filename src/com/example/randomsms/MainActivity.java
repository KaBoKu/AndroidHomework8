package com.example.randomsms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.R.integer;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private List<String> contactsList = new LinkedList<>();
	private String phoneNumber;
	private EditText eText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {

		}
		getNumber(getContentResolver());
		eText = (EditText) findViewById(R.id.editText1);

		Button sendButton = (Button) findViewById(R.id.btnSubmit);

		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SmsManager smsManager = SmsManager.getDefault();
				Time t = new Time();
				t.setToNow();
				
				Random rd = new Random();
				//rd.setSeed(32523643);
				int size = contactsList.size()-2;
				
				int randomValue = rd.nextInt(/*(size - 0) + 1) + 0;*/size);
				String phoneString = contactsList.get(randomValue);
				contactsList.add(contactsList.get(randomValue));
				contactsList.remove(randomValue);
				Toast.makeText(getApplicationContext(),Long.toString(randomValue)+" "+contactsList.get(randomValue)/*contactsList.get(randomValue)*/, 100).show();
				if(eText.length()==0)Toast.makeText(getApplicationContext(), "Wpisz coœ!", 700).show();
				else
				smsManager.sendTextMessage(phoneString, null, eText.getText().toString(),
						null, null);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void getNumber(ContentResolver cr) {
		Cursor phones = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {
			String name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			phoneNumber = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			contactsList.add(phoneNumber);
		}
		phones.close();// close cursor
	}

}
