package nioth.dev.dcharge;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HeartRateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HeartRateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
     public class HeartRateFragment extends android.support.v4.app.Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_heart_rate, container, false);

            return rootView;
        }

    }