/*******************************************************************************
 * Copyright (c) 2010 Robert Bermani.
 * All rights reserved. This program and its accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which is included with this distribution and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Robert Bermani - initial API and implementation
 ******************************************************************************/
package rbermani.android.weighthelper;

import com.admob.android.ads.AdManager;
import com.admob.android.ads.AdView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class WeightHelperActivity extends Activity implements DialogInterface.OnClickListener, OnClickListener
{

	private BarbellWeightsView weightWidget;
	private AlertDialog.Builder alert;
	private EditText input;
	private AdView adView;
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {

		case R.id.about:
			Intent aboutActivity = new Intent(getBaseContext(),
					AboutActivity.class);

			startActivity(aboutActivity);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//		AdManager.setTestDevices( new String[] {  
		//	      AdManager.TEST_EMULATOR,      // Android emulator  
		//	      "015A8A3C1501A002", // My Droid X ID 
		//	         } ); 
		//		
		weightWidget = (BarbellWeightsView) findViewById(R.id.barbellhelper);
		adView = (AdView) findViewById(R.id.ad);
		adView.requestFreshAd();

		weightWidget.setOnClickListener(this);

	}


	@Override
	public void onClick(DialogInterface arg0, int whichButton) {
		switch(whichButton){
		case DialogInterface.BUTTON_POSITIVE:
			int tmp = Integer.valueOf(input.getText().toString());
			weightWidget.buildStackUp(tmp);

			break;

		case DialogInterface.BUTTON_NEGATIVE:

			break;
		}


	}


	@Override
	public void onClick(View arg0) {
		if (arg0 == weightWidget){
			input = new EditText(this);
			input.setInputType(InputType.TYPE_CLASS_PHONE);
			
			input.setRawInputType(InputType.TYPE_CLASS_PHONE);
			
			alert = new AlertDialog.Builder(this);
			alert.setTitle("User Input");
			alert.setMessage("Enter Desired Weight (lbs):");
			alert.setView(input);

			alert.setPositiveButton("Ok", this);
			alert.setNegativeButton("Cancel", this);
			alert.show();
		}

	}

}
