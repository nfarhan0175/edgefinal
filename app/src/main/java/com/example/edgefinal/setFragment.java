package com.example.edgefinal;

import static android.content.Context.SENSOR_SERVICE;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class setFragment extends Fragment implements SensorEventListener {

    private TextView text, text2, text3;

    public setFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_set, container, false);

        // Initialize TextViews
        text = rootView.findViewById(R.id.text1);
        text2 = rootView.findViewById(R.id.text2);
        text3 = rootView.findViewById(R.id.text3);

        // Get the SensorManager system service
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            // Register listener for the accelerometer sensor
            Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            }

            // Register listener for the light sensor
            Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            if (lightSensor != null) {
                sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }

            // Register listener for the proximity sensor
            Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            if (proximitySensor != null) {
                sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }

        return rootView;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Handle sensor events based on the sensor type
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            text.setText("X: " + sensorEvent.values[0] + " Y: " + sensorEvent.values[1] + " Z: " + sensorEvent.values[2]);
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            if (sensorEvent.values[0] > 0) {
                text2.setText("Light intensity high: " + sensorEvent.values[0]);
            } else {
                text2.setText("Light intensity low: " + sensorEvent.values[0]);
            }
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (sensorEvent.values[0] > 0) {
                text3.setText("Object far");
            } else {
                text3.setText("Object near");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // You can implement sensor accuracy handling if necessary, but it’s not required here
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the sensor listeners to save battery
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }
}
