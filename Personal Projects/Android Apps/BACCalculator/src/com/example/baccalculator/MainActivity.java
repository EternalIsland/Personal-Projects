package com.example.baccalculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import com.eternalisland.baccalculator.R;

public class MainActivity extends ActionBarActivity {
	private Spinner sp;
	private EditText drkAmt, weightAmt, timeAmt, customAbv;
	private SI_Units units;
	private Button calcButton;
	private RadioGroup genderGroup;
	private double numDrinks, weight, time;
	private double genderConstant = 0.58;
	private double metabConstant = 0.015;
	private double BAC;
	private boolean imperial;
	private SharedPreferences sharedPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		PreferenceManager.setDefaultValues(this, R.xml.prefs, false);

		units = new SI_Units();
		BAC = 0.0;

		loadUI();
		loadSpinnerElements();
	}

	private void loadSpinnerElements() {
		// TODO Auto-generated method stub
		ArrayList<String> drinks = new ArrayList<String>();
		drinks.add("Beer (5% ABV)");
		drinks.add("Wine (12% ABV)");
		drinks.add("Liquor & Spirits (40% ABV)");
		drinks.add("Custom ABV");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, drinks);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				// TODO Auto-generated method stub

				if (position == 3) {
					customAbv.setVisibility(View.VISIBLE);
				} else {
					customAbv.setVisibility(View.INVISIBLE);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void loadUI() {
		// TODO Auto-generated method stub
		imperial = sharedPref.getBoolean("pref_units", false);

		if (imperial == true) {
			units.setMeasurementSystem(false);
		} else {
			units.setMeasurementSystem(true);
		}

		sp = (Spinner) findViewById(R.id.drinkSpin);

		drkAmt = (EditText) findViewById(R.id.drinkAmountEt);
		drkAmt.setHint("Amount " + " (" + units.getLiquid() + ")");

		weightAmt = (EditText) findViewById(R.id.weightEt);
		weightAmt.setHint("Amount " + " (" + units.getWeight() + ")");

		timeAmt = (EditText) findViewById(R.id.timeEt);
		timeAmt.setHint("Hours since first drink");

		customAbv = (EditText) findViewById(R.id.customAbvEt);

		genderGroup = (RadioGroup) findViewById(R.id.genderRadGrp);

		genderGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				RadioButton checkButton = (RadioButton) findViewById(checkedId);
				String gender = checkButton.getText().toString();

				if (gender.equalsIgnoreCase("male")) {
					genderConstant = 0.58;
					metabConstant = 0.015;
				} else {
					genderConstant = 0.49;
					metabConstant = 0.017;
				}
			}
		});

		calcButton = (Button) findViewById(R.id.calcButton);
		calcButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!(drkAmt.getText().toString().equals(""))
						&& !(weightAmt.getText().toString().equals(""))
						&& !(timeAmt.getText().toString().equals(""))) {
					numDrinks = Double.parseDouble(drkAmt.getText().toString());
					weight = Double.parseDouble(weightAmt.getText().toString());
					time = Double.parseDouble(timeAmt.getText().toString());

					boolean metric = units.isMetric();

					if (sp.getSelectedItem().toString()
							.equalsIgnoreCase("Custom ABV")
							&& !(customAbv.getText().toString().equals(""))) {
						Double custAbv = 0.60 / (Double.parseDouble(customAbv
								.getText().toString()) / 100);
						if (!metric) {
							numDrinks /= custAbv;
						} else {
							numDrinks /= 44.3603;
							numDrinks /= custAbv;
						}
					}

					if (metric) {
						if (sp.getSelectedItem().toString()
								.equalsIgnoreCase("Beer (5% ABV)")) {
							numDrinks /= 355;
						} else if (sp.getSelectedItem().toString()
								.equalsIgnoreCase("Wine (12% ABV)")) {
							numDrinks /= 148;
						} else if (sp.getSelectedItem().toString()
								.equalsIgnoreCase("Liquor & Spirits (40% ABV)")) {
							numDrinks /= 44.3603;
						}

					} else {
						if (sp.getSelectedItem().toString()
								.equalsIgnoreCase("Beer (5% ABV)")) {
							numDrinks /= 12;
						} else if (sp.getSelectedItem().toString()
								.equalsIgnoreCase("Wine (12% ABV)")) {
							numDrinks /= 5;
						} else if (sp.getSelectedItem().toString()
								.equalsIgnoreCase("Liquor & Spirits (40% ABV)")) {
							numDrinks /= 1.5;
						}
						weight /= 2.2;
					}

					Bundle bundle = new Bundle();
					bundle.putDouble("BAC", calcBAC(numDrinks, weight, time));
					Intent intent = new Intent(getApplicationContext(),
							BACResultActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}

			private double calcBAC(double numDrinks, double weight, double time) {
				BAC = ((0.806 * numDrinks * 1.2) / (genderConstant * weight))
						- (metabConstant * time);

				if (BAC < 0.0) {
					BAC = 0.0;
				}

				DecimalFormat twoDForm = new DecimalFormat("#.##");
				BAC = Double.valueOf(twoDForm.format(BAC));

				return BAC;

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_settings:
			openSettings();
			return true;
		case R.id.action_about:
			openAbout();
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void openAbout() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});

		builder.setMessage(R.string.about_string).setTitle(
				R.string.dialog_title);

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void openSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onRestart() {
		super.onRestart();
		loadUI();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
