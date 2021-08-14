package com.example.testkipia2.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testkipia2.R;
import com.example.testkipia2.classes.Question;
import com.example.testkipia2.utilities.MapOfAnswers;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> questionList;

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
        notifyDataSetChanged();

    }

    public QuestionAdapter() {
        questionList = new ArrayList<>();
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {

        holder.textOfQuestion.setText(questionList.get(position).getTextOfQuestion());

        holder.buttonOf1Answer.setText(questionList.get(position).getAnswers().get(0).getTextOfAnswer());
        holder.buttonOf2Answer.setText(questionList.get(position).getAnswers().get(1).getTextOfAnswer());
        holder.buttonOf3Answer.setText(questionList.get(position).getAnswers().get(2).getTextOfAnswer());
        holder.buttonOf4Answer.setText(questionList.get(position).getAnswers().get(3).getTextOfAnswer());

        //holder.setIsRecyclable(true);


    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        private TextView textOfQuestion;
        private Button buttonOf1Answer;
        private Button buttonOf2Answer;
        private Button buttonOf3Answer;
        private Button buttonOf4Answer;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            textOfQuestion = itemView.findViewById(R.id.textOfQuestion);
            buttonOf1Answer = itemView.findViewById(R.id.buttonOf1Answer);
            buttonOf2Answer = itemView.findViewById(R.id.buttonOf2Answer);
            buttonOf3Answer = itemView.findViewById(R.id.buttonOf3Answer);
            buttonOf4Answer = itemView.findViewById(R.id.buttonOf4Answer);

            buttonOf1Answer.setOnClickListener(new View.OnClickListener() {//делаем кнопки разными цветами, при нажатии
                @Override
                public void onClick(View v) {
                    MapOfAnswers.answers.put(textOfQuestion.getText().toString(), buttonOf1Answer.getText().toString());

                    buttonOf1Answer.setBackgroundColor(0xFFfff90f);
                    buttonOf2Answer.setBackgroundColor(0xFFFFFFFF);
                    buttonOf3Answer.setBackgroundColor(0xFFFFFFFF);
                    buttonOf4Answer.setBackgroundColor(0xFFFFFFFF);
                }
            });

            buttonOf2Answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MapOfAnswers.answers.put(textOfQuestion.getText().toString(), buttonOf2Answer.getText().toString());

                    buttonOf2Answer.setBackgroundColor(0xFFfff90f);
                    buttonOf1Answer.setBackgroundColor(0xFFFFFFFF);
                    buttonOf3Answer.setBackgroundColor(0xFFFFFFFF);
                    buttonOf4Answer.setBackgroundColor(0xFFFFFFFF);
                }
            });
            buttonOf3Answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MapOfAnswers.answers.put(textOfQuestion.getText().toString(), buttonOf3Answer.getText().toString());

                    buttonOf3Answer.setBackgroundColor(0xFFfff90f);
                    buttonOf2Answer.setBackgroundColor(0xFFFFFFFF);
                    buttonOf1Answer.setBackgroundColor(0xFFFFFFFF);
                    buttonOf4Answer.setBackgroundColor(0xFFFFFFFF);
                }
            });
            buttonOf4Answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MapOfAnswers.answers.put(textOfQuestion.getText().toString(), buttonOf4Answer.getText().toString());

                    buttonOf4Answer.setBackgroundColor(0xFFfff90f);
                    buttonOf2Answer.setBackgroundColor(0xFFFFFFFF);
                    buttonOf3Answer.setBackgroundColor(0xFFFFFFFF);
                    buttonOf1Answer.setBackgroundColor(0xFFFFFFFF);

                    Log.d("123123", "onClick: " + MapOfAnswers.answers.toString());
                }
            });
        }
    }

}
