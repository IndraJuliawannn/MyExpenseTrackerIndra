package com.informatika.myexpensetrackerindra;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {

    EditText edtName, edtAmount, edtCategory, edtDate;
    PrefHelper pref;
    ExpenseModel editData = null;
    ArrayList<ExpenseModel> list;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_add_expense);

        edtName = findViewById(R.id.edtName);
        edtAmount = findViewById(R.id.edtAmount);
        edtCategory = findViewById(R.id.edtCategory);
        edtDate = findViewById(R.id.edtDate);

        pref = new PrefHelper(this);
        list = pref.getList();
        if (list == null) list = new ArrayList<>();

        editData = (ExpenseModel) getIntent().getSerializableExtra("data");

        if (editData != null){
            edtName.setText(editData.name);
            edtAmount.setText(String.valueOf(editData.amount));
            edtCategory.setText(editData.category);
            edtDate.setText(editData.date);

            findViewById(R.id.btnDelete).setVisibility(View.VISIBLE);
        }

        findViewById(R.id.btnSave).setOnClickListener(v -> saveData());
        findViewById(R.id.btnDelete).setOnClickListener(v -> deleteData());
    }

    void saveData(){
        String name = edtName.getText().toString();
        int amount = Integer.parseInt(edtAmount.getText().toString());
        String category = edtCategory.getText().toString();
        String date = edtDate.getText().toString();

        if (editData != null){
            list.remove(editData);
        }

        list.add(new ExpenseModel(
                editData == null ? UUID.randomUUID().toString() : editData.id,
                name, amount, category, date
        ));

        pref.saveList(list);
        Toast.makeText(this, "Data Disimpan", Toast.LENGTH_SHORT).show();
        finish();
    }

    void deleteData(){
        list.remove(editData);
        pref.saveList(list);
        Toast.makeText(this, "Data Dihapus", Toast.LENGTH_SHORT).show();
        finish();
    }
}
