package nioth.dev.dcharge.Old;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nioth.dev.dcharge.R;
import nioth.dev.dcharge.TabsPagerAdapter;

public class InfoActivity extends AppCompatActivity implements
            ActionBar.TabListener {

    private static String EXTRA_MESSAGE = "Vitamin Level";

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private String vitaminType;
    private Double vitaminValue;
    private String vitaminMeasurement;
    String message;
    //private ActionBar actionBar;
    // Tab titles


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        message = intent.getStringExtra(InfoActivity.EXTRA_MESSAGE);
        if (message == null) {
            message = "D 160.2 ng/ml";
        }

        viewPager = (ViewPager) findViewById(R.id.pager);
        //  actionBar = getSupportActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);

        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                //actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    public String getMyData() {
        return message;
    }
}
