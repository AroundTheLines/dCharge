package nioth.dev.dcharge.PastQuestions;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nioth.dev.dcharge.R;

/**
 * Created by umeshkhanna on 2016-04-24.
 */

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private ArrayList<Question> listingDataSet;

        public static class MyViewHolder extends RecyclerView.ViewHolder {

             TextView textViewQuestion;
             TextView textViewTime;
            ImageView  imageViewAnswer;


            public MyViewHolder(View itemView) {
                super(itemView);
                 this.textViewQuestion = (TextView) itemView.findViewById(R.id.question_textview);
                this.textViewTime = (TextView) itemView.findViewById(R.id.time_textview);
                this.imageViewAnswer = (ImageView) itemView.findViewById(R.id.answer_image);


            }
        }

        public MyAdapter(ArrayList<Question> listing) {
            this.listingDataSet = listing;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cards_layout, parent, false);

            view.setOnClickListener(QuestionsActivity.myOnClickListener);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

            TextView textViewHolderQuestion = holder.textViewQuestion;
            TextView textViewHolderTime = holder.textViewTime;
            ImageView imageViewHolderAnswer = holder.imageViewAnswer;
            String questionString = listingDataSet.get(listPosition).getQuestion() + " THIS IS A SMPLE QUESTION TO SEE HOW BIG IT GETSBED";
            String timeString = String.valueOf(listingDataSet.get(listPosition).getTime()) + " tiime";
            String answerString = String.valueOf(listingDataSet.get(listPosition).getAnswer());

            Uri check = Uri.parse("android.resource://nioth.dev.dcharge/drawable/check");
            Uri cross = Uri.parse("android.resource://nioth.dev.dcharge/drawable/cross");

            if(answerString.equals("YES")){
                imageViewHolderAnswer.setImageURI(check);
            }else{
                imageViewHolderAnswer.setImageURI(cross);

            }
            textViewHolderQuestion.setText(questionString);
            textViewHolderTime.setText(timeString);


        }

        @Override
        public int getItemCount() {
            return listingDataSet.size();
        }
    }