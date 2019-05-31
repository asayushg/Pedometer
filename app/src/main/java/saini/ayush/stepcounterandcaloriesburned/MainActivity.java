package saini.ayush.stepcounterandcaloriesburned;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    Sensor accelerometer;
    SensorManager sensorManager;
    TextView position,step;
    Sensor countSensor;
    float steps, distance, calories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        position = (TextView) findViewById(R.id.position);
        step = (TextView) findViewById(R.id.step);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
        setDistance();
    }

    private void setDistance() {
        distance = (steps*70)/100;
        calories = 0;
        position.setText("Distance: "+distance + " m\nCalories: "+calories);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        steps = event.values[0];
        step.setText("Steps: "+steps);
        setDistance();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this, countSensor);
    }

    public void onClick(View v){
        Intent intent = new Intent(MainActivity.this,NavActivity.class);
        startActivity(intent);
    }


}
