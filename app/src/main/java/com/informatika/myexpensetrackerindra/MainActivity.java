package com.informatika.myexpensetrackerindra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    PrefHelper pref;
    ArrayList<ExpenseModel> list;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        pref = new PrefHelper(this);

        findViewById(R.id.btnAdd).setOnClickListener(v -> {
            startActivity(new Intent(this, AddExpenseActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        list = pref.getList();
        if (list == null) list = new ArrayList<>();

        int total = 0;
        for (ExpenseModel m : list){
            total += m.amount;
        }

        ((android.widget.TextView)findViewById(R.id.txtTotal))
                .setText("Total Pengeluaran: Rp " + total);

        RecyclerView rv = findViewById(R.id.rvExpense);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ExpenseAdapter(list, data -> {
            Intent i = new Intent(this, AddExpenseActivity.class);
            i.putExtra("data", data);
            startActivity(i);
        }));
    }
}
