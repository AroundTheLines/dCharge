package nioth.dev.dcharge;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by umeshkhanna on 2016-04-24.
 */

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private ArrayList<Question> listingDataSet;

        public static class MyViewHolder extends RecyclerView.ViewHolder {

             TextView textViewQuestion;
             TextView textViewAnswer;


            public MyViewHolder(View itemView) {
                super(itemView);
                 this.textViewQuestion = (TextView) itemView.findViewById(R.id.question_textview);
                this.textViewAnswer = (TextView) itemView.findViewById(R.id.answer_textview);

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
            TextView textViewHolderAnswer = holder.textViewAnswer;
            String questionString = listingDataSet.get(listPosition).getQuestion() + " THIS IS A SMPLE QUESTION TO SEE HOW BIG IT GETSBED";
            String answerString = String.valueOf(listingDataSet.get(listPosition).getAnswer()) + " Ans";
            textViewHolderQuestion.setText(questionString);
            textViewHolderAnswer.setText(answerString);
        }

        @Override
        public int getItemCount() {
            return listingDataSet.size();
        }
    }