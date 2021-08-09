package com.example.testkipia2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testkipia2.R;
import com.example.testkipia2.classes.Test;

import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {

    private List<Test> testList;
    private OnTestClickListener onTestClickListener;

    public TestAdapter() {

        testList = new ArrayList<>();

    }

    public void setOnTestClickListener(OnTestClickListener onTestClickListener) {
        this.onTestClickListener = onTestClickListener;
    }

    public interface OnTestClickListener//для реагирования на нажатие в этом адаптере
    {
        void onTestClick(int position);
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_test, parent,false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {

        holder.nameOfTest.setText(testList.get(position).getNameOfTest());
        holder.numberOfTest.setText(testList.get(position).getUniqueId()+"");

    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public List<Test> getTestList() {
        return testList;
    }

    public void setTestList(List<Test> testList) {
        this.testList = testList;
        notifyDataSetChanged();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder{

        private TextView nameOfTest;
        private TextView numberOfTest;


        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfTest = itemView.findViewById(R.id.nameOfTest);
            numberOfTest = itemView.findViewById(R.id.numberOfTest);

            itemView.setOnClickListener(new View.OnClickListener() {//для реагирования на нажатие
                @Override
                public void onClick(View v) {
                    if(onTestClickListener != null){
                        onTestClickListener.onTestClick(getAdapterPosition());
                    }

                }
            });
        }
    }
}
