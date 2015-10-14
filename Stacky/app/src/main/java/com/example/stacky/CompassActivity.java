package com.example.stacky;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class CompassActivity extends Activity implements SensorEventListener {

	private SensorManager mSensorManager;
	private Sensor sensorAccelerometer;
	private Sensor sensorMagneticField;

	private float[] valuesAccelerometer;
	private float[] valuesMagneticField;

	private float[] matrixR;
	private float[] matrixI;
	private float[] matrixValues;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		// Launch a scheduled maintenance service

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		sensorAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorMagneticField = mSensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	
		valuesAccelerometer = new float[3];
		valuesMagneticField = new float[3];

		matrixR = new float[9];
		matrixI = new float[9];
		matrixValues = new float[3];
	}

	@Override
	protected void onResume() {
		mSensorManager.registerListener(this, sensorAccelerometer,
				SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this, sensorMagneticField,
				SensorManager.SENSOR_DELAY_UI);

		super.onResume();
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		 switch(event.sensor.getType()){
		  case Sensor.TYPE_ACCELEROMETER:
		   for(int i =0; i < 3; i++){
			System.out.println("event value:"+event.values[i]);
		    valuesAccelerometer[i] = event.values[i];
		   }
		   break;
		  case Sensor.TYPE_MAGNETIC_FIELD:
		   for(int i =0; i < 3; i++){
		   System.out.println("event value2:"+event.values[i]);
		    valuesMagneticField[i] = event.values[i];
		   }
		   break;
		  }
		 
		   boolean success = SensorManager.getRotationMatrix(
			       matrixR,
			       matrixI,
			       valuesAccelerometer,
			       valuesMagneticField);
			    
			  if(success){
			   SensorManager.getOrientation(matrixR, matrixValues);
			     
			   double azimuth = Math.toDegrees(matrixValues[0]);
			   double pitch = Math.toDegrees(matrixValues[1]);
			   double roll = Math.toDegrees(matrixValues[2]);
			     
			   System.out.println("orientation[0]: azimut: "+azimuth);
			   System.out.println("orientation[1]: pitch : "+pitch);
			   System.out.println("orientation[2]: roll: "+roll);
			   
			  }

	}

}
