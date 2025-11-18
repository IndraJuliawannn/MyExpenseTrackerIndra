package com.informatika.myexpensetrackerindra;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    ArrayList<ExpenseModel> list;
    OnItemClick listener;

    public interface OnItemClick {
        void onClick(ExpenseModel data);
    }

    public ExpenseAdapter(ArrayList<ExpenseModel> list, OnItemClick listener) {
        this.list = list;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAmount, txtCategory, txtDate;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtDate = itemView.findViewById(R.id.txtDate);
        }

        public void bind(ExpenseModel m){
            txtName.setText(m.name);
            txtAmount.setText("Rp " + m.amount);
            txtCategory.setText(m.category);
            txtDate.setText(m.date);

            itemView.setOnClickListener(v -> listener.onClick(m));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int position) {
        h.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
