package nioth.dev.dcharge;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class QuestionsFragment extends android.support.v4.app.Fragment {
    private Button dietButton;
    private Button scanButton;
    private TextView vitaminTypeDisplay;
    String vitaminType;
    Double vitaminValue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_questions, container, false);
        dietButton = (Button) rootView.findViewById(R.id.dietButton);
        scanButton = (Button) rootView.findViewById(R.id.scanButton);
        vitaminTypeDisplay = (TextView) rootView.findViewById(R.id.vitaminType);

        InfoActivity activity = (InfoActivity) getActivity();
        String message = activity.getMyData();

        String[] vitaminInfo = message.split("\\s+");
        vitaminType = vitaminInfo[0];
        vitaminValue = Double.parseDouble(vitaminInfo[1]);
        vitaminTypeDisplay.setText("Vitamin " + vitaminType);


        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TO REMOVE
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }

    public String getVitaminType() {
        return vitaminType;
    }
}

