package saini.ayush.stepcounterandcaloriesburned;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NavActivity extends FragmentActivity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor countSensor;
    int steps, distance, calories;
    Context context;
    TodayFragment todayFragment;
    DatabaseHelper databaseHelper;
    String Date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        context = NavActivity.this;
        getDate();
        BottomNavigationView navView = findViewById(R.id.bottom_nav);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TodayFragment()).commit();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        databaseHelper = new DatabaseHelper(this);
    }

    private void getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        Date = date;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_today:
                    selectedFragment = new TodayFragment();
                    break;
                case R.id.nav_report:
                    selectedFragment = new ReportFragment();
                    break;
                case R.id.nav_settings:
                    selectedFragment = new SettingsFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
           return true;
        }
    };

    public void startCounting(View v){
        todayFragment = (TodayFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(todayFragment!=null) todayFragment.start();
        sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
    }

    public void pauseCounting(View v){
        todayFragment = (TodayFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(todayFragment!=null)todayFragment.pause();
        sensorManager.unregisterListener(this,countSensor);
        Toast.makeText(context,"Paused",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        steps = (int) event.values[0];
        String tSteps = steps + "\nSteps";
        todayFragment = (TodayFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(todayFragment!=null)todayFragment.setTextView(tSteps);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
