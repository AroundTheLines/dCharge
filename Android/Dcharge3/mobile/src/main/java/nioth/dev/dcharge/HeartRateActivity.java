package nioth.dev.dcharge;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class HeartRateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);
        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HeartRateActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        // creating list of entry
        ArrayList<Entry> entries = new ArrayList<>();
        //entry(amount, entry #)
        //DATA FOR GRAPH
        
        entries.add(new Entry(63f, 0));
        entries.add(new Entry(64f, 1));
        entries.add(new Entry(74f, 2));
        entries.add(new Entry(97f, 3));
        entries.add(new Entry(77f, 4));
        entries.add(new Entry(73f, 5));
        LineDataSet dataset = new LineDataSet(entries, "Beats Per Minute");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("3:05");
        labels.add("4:15");
        labels.add("5:17");
        labels.add("6:23");
        labels.add("7:00");
        labels.add("8:40");
        dataset.setDrawFilled(true);
        LineData data = new LineData(labels, dataset);
        lineChart.setData(data); // set the data and list of lables into chart
        lineChart.animateY(5000);
        lineChart.setDescription("Heart Rate");


//        BarData data = new BarData(labels, dataset);
//        chart.setData(data);
//
//        chart.setDescription("# of times Alice called Bob");
//
//        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        chart.animateY(5000);
        //chart.saveToGallery("mychart.jpg", 85); // 85 is the quality of the image

//        ImageView img = (ImageView) findViewById(R.id.graph_img);
//        Uri uri = Uri.parse("android.resource://nioth.dev.dcharge/drawable/mychart");
//        img.setImageURI(uri);
//
//        try {
//            InputStream stream = getContentResolver().openInputStream(uri);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_heart_rate, menu);
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
