package nioth.dev.dcharge;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dd.morphingbutton.MorphingButton;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import nioth.dev.dcharge.PastQuestions.QuestionsActivity;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    View heartRateButton;
    View responseHistoryButotn;
    View hospitalInfoButton;
    Button logoutButton;
    Button callDoctorButton;

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

        mGoogleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);


        heartRateButton = findViewById(R.id.heart_rate_history_button);
        responseHistoryButotn = findViewById(R.id.response_history_button);
        hospitalInfoButton = findViewById(R.id.hospital_info);
        callDoctorButton = (Button) findViewById(R.id.call_doctor_button);
        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
              //EXIT
            }
        });

        callDoctorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "Doctor Will Be There Shortly";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });



        heartRateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent intent = new Intent(HomeActivity.this, HeartRateActivity.class);
                startActivity(intent);
            }
        });


        responseHistoryButotn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(HomeActivity.this, QuestionsActivity.class);
                startActivity(intent);
            }
        });

        hospitalInfoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "5197819288"));
                startActivity(intent);
            }
        });
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_home, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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

    Emitter.Listener onGraphData = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            //Parse data here and store

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
