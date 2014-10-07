package com.example.baccalculator;

public class SI_Units {

	private boolean metric;

	public SI_Units() {
		metric = true;
	}

	public String getWeight() {
		// TODO Auto-generated method stub
		if (metric) {
			return "kilograms";
		} else {
			return "pounds";
		}
	}

	public String getLiquid() {
		// TODO Auto-generated method stub
		if (metric) {
			return "mL";
		} else {
			return "fl oz";
		}
	}

	public void setMeasurementSystem(boolean isMetric) {
		metric = isMetric;
	}

	public boolean isMetric() {
		return metric;
	}

}
