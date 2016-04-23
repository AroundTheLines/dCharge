package nioth.dev.dcharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {


    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://42aee835.ngrok.io");
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSocket.connect();

    }

    @Override
    protected void onPause() {
        super.onPause();
        attemptSend("Hello");


        mSocket.disconnect();
    }

    private void attemptSend(String sendMessage) {
        String message = sendMessage;

        mSocket.emit("new message", message);
    }
}
