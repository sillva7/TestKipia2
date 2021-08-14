package com.example.testkipia2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testkipia2.R;
import com.example.testkipia2.classes.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public ResultAdapter() {
        results = new ArrayList<>();
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_result, parent,false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {//метод с присвоением значений для разных элементов айтема

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");//блок для превращения милисекунд в дату
        String dateString = formatter.format(new Date(results.get(position).getTime()));

        holder.nameOfTester.setText(results.get(position).getNameOfTester());
        holder.result.setText(results.get(position).getResult()+"");
        holder.resultTime.setText(dateString);

        if(Integer.parseInt(holder.result.getText().toString())>=9){
            holder.innerConstraint.setBackgroundColor(0xFF4CAF50);
            holder.result.setText(results.get(position).getResult()+"/10");

        }else{
            holder.innerConstraint.setBackgroundColor(0xFFff3829);
            holder.result.setText(results.get(position).getResult()+"/10");

        }



    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder{

        private TextView nameOfTester;
        private TextView result;
        private TextView resultTime;
        private ConstraintLayout innerConstraint;


        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);

            nameOfTester = itemView.findViewById(R.id.nameOfTester);
            result = itemView.findViewById(R.id.result);
            resultTime = itemView.findViewById(R.id.resultTime);
            innerConstraint = itemView.findViewById(R.id.innerConstraint);

        }
    }

}
