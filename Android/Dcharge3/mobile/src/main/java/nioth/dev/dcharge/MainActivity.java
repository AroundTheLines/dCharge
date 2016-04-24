package nioth.dev.dcharge;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Set;

import at.markushi.ui.CircleButton;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String HEART_RATE_MONITOR = "heart_rate";
    private GoogleApiClient mGoogleApiClient;

    private TextToSpeech t1;


    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://63168325.ngrok.io");
        } catch (URISyntaxException e) {}
    }

    private String transcriptionNodeId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mSocket.on("heart_rate_test", onNewMessage);

        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });



        CircleButton button2 = (CircleButton) findViewById(R.id.heart_rate_history_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend("Hello");
                t1.speak("How are you feeling right now?", TextToSpeech.QUEUE_FLUSH, null);
            }
        });



        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);



    }


    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            attemptSend(message);
        }
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSocket.connect();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mSocket.disconnect();
        mGoogleApiClient.disconnect();
    }

    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.i("workde", "socketio");

            JSONObject data = (JSONObject) args[0];
            String username;
            String message;
            try {
                username = data.getString("qqq");
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }

            // add the message to view
            Log.i(args[0].toString(), username);
        }
    };


    private void attemptSend(String sendMessage) {
        String message = sendMessage;

        mSocket.emit("heart_rate", message);
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
}
