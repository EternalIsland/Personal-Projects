package com.example.baccalculator;

import java.text.DecimalFormat;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.eternalisland.baccalculator.R;

public class BACResultActivity extends ActionBarActivity {

	private TextView result, symptoms;
	private DecimalFormat format;
	private String symptomsByBac;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bacresult);

		format = new DecimalFormat("##.##");

		Bundle bundle = this.getIntent().getExtras();
		double BAC = bundle.getDouble("BAC");

		String BACFormatted = format.format(BAC);

		result = (TextView) findViewById(R.id.resultTv);
		result.setText("Your BAC is " + BACFormatted);

		if (BAC <= 0.02) {
			symptomsByBac = getString(R.string.no_effects);
		}
		if (BAC >= 0.03 && BAC < 0.09) {
			symptomsByBac = getString(R.string.bac_0_03);
		}
		if (BAC >= 0.09 && BAC < 0.18) {
			symptomsByBac = getString(R.string.bac_0_09);
		}
		if (BAC >= 0.18 && BAC < 0.25) {
			symptomsByBac = getString(R.string.bac_0_18);
		}
		if (BAC >= 0.25 && BAC < 0.35) {
			symptomsByBac = getString(R.string.bac_0_25);
		}
		if (BAC >= 0.35 && BAC <= 0.50) {
			symptomsByBac = getString(R.string.bac_0_35);
		}
		if (BAC > 0.50 && BAC < 1.0) {
			symptomsByBac = getString(R.string.bac_over_0_50);
		}
		if (BAC >= 1.0) {
			symptomsByBac = getString(R.string.bac_over_1_0);
		}

		symptoms = (TextView) findViewById(R.id.effectsTv);
		symptoms.setText("You may have these symptoms: \n" + symptomsByBac);

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
		default:
			return super.onOptionsItemSelected(item);
		}
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
	}

}
