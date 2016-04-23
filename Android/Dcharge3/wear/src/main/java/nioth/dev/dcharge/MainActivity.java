package nioth.dev.dcharge;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;


import java.net.URISyntaxException;
import java.util.List;

public class MainActivity extends Activity implements SensorEventListener {

    private TextView mTextView;

    private SensorManager mSensorManager;

    private Sensor mAccelerometer;

    private Sensor mHeartRateSensor;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://42aee835.ngrok.io/heart_rate");
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        mSocket.connect();
    }



    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        mSocket.disconnect();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {

        String TAG = "tag";
        Log.i(TAG, "--------------------------");
        Log.i(TAG, String.valueOf(event.values[0]));
        attemptSend(String.valueOf(event.values[0]));
        Log.i(TAG, ""+ event.sensor.getType());
        Log.i("live","--------------");
    }

    private void attemptSend(String sendMessage) {
        String message = sendMessage;


        mSocket.emit("new message", message);
    }
}
