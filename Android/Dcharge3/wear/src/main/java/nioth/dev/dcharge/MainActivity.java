package nioth.dev.dcharge;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;

import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.WatchViewStub;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.net.URISyntaxException;
import java.util.List;

public class MainActivity extends FragmentActivity implements SensorEventListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, QuestionFragment.OnFragmentInteractionListener {

    private TextView mTextView;

    private SensorManager mSensorManager;

    private Sensor mAccelerometer;

    private Sensor mHeartRateSensor;

    private GoogleApiClient mGoogleApiClient;

    private String TAG = "acb";

    private static final int SPEECH_REQUEST_CODE =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(final WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                Button button = (Button) findViewById(R.id.button);

                final String HEART_MONITOR = "/heart_monitor";



                button.setOnClickListener(new View.OnClickListener() {



                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                        removeViews(stub);

                        QuestionFragment questionFragment = QuestionFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,questionFragment).commit();



//                        startActivityForResult(intent, SPEECH_REQUEST_CODE);
                    }

                });

            }
        });


        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor1 : sensors) {
            Log.i(TAG, sensor1.getName() + ": " + sensor1.getType());
        }


    }

    public static void removeViews(WatchViewStub stub)
    {
        View mTextView = stub.findViewById(R.id.text);
        View mButtonView = stub.findViewById(R.id.button);
        mTextView.setVisibility(View.GONE);
        mButtonView.setVisibility(View.GONE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            Log.i("spokenText", spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();
    }


    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
        mSensorManager.unregisterListener(this);

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {

//        String TAG = "tag";
//        Log.i(TAG, "--------------------------");
        Log.i(TAG, String.valueOf(event.values[0]));


        new SendToDataLayerThread("/heart-monitor", String.valueOf(event.values[0])).start();
//        Log.i(TAG, ""+ event.sensor.getType());
//        Log.i("live","--------------");
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.d("test", "onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("test", "Failed to connect to Google API Client");
    }

    class SendToDataLayerThread extends Thread {
        String path;
        String message;

        // Constructor to send a message to the data layer
        SendToDataLayerThread(String p, String msg) {
            path = p;
            message = msg;
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
            for (Node node : nodes.getNodes()) {
                MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(), path, message.getBytes()).await();
                if (result.getStatus().isSuccess()) {
                    Log.v("myTag", "Message: {" + message + "} sent to: " + node.getDisplayName());
                }
                else {
                    // Log an error
                    Log.v("myTag", "ERROR: failed to send Message");
                }
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        Log.i("workd","abcd");
    }


}
