package nioth.dev.dcharge;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dd.morphingbutton.MorphingButton;

import nioth.dev.dcharge.PastQuestions.QuestionsActivity;

public class HomeActivity extends AppCompatActivity {
    View heartRateButton;
    View responseHistoryButotn;
    View hospitalInfoButton;
    Button logoutButton;
    Button callDoctorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        heartRateButton = findViewById(R.id.heart_rate_history_button);
        responseHistoryButotn = findViewById(R.id.response_history_button);
        hospitalInfoButton = findViewById(R.id.hospital_info);
        callDoctorButton = (Button) findViewById(R.id.call_doctor_button);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);


        myToolbar.getMenu().clear();
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        myToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_media_play));
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
            }
        });


        callDoctorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "Doctor Will Be There Shortly";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
//                new AlertDialog.Builder(getApplicationContext())
//                        .setTitle("Request Sent")
//                        .setMessage("The Doctor Will Be There Shortly")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // continue with delete
//                            }
//                        })
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
            }
        });



        heartRateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent intent = new Intent(HomeActivity.this, QuestionsActivity.class);
                startActivity(intent);
            }
        });


        responseHistoryButotn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(HomeActivity.this, HeartRateActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
