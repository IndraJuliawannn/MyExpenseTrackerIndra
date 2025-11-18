package com.informatika.myexpensetrackerindra;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {

    EditText edtName, edtAmount, edtCategory, edtDate;
    Button btnSave, btnDelete;
    PrefHelper pref;
    ExpenseModel editData = null;
    ArrayList<ExpenseModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        edtName = findViewById(R.id.edtName);
        edtAmount = findViewById(R.id.edtAmount);
        edtCategory = findViewById(R.id.edtCategory);
        edtDate = findViewById(R.id.edtDate);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        pref = new PrefHelper(this);
        list = pref.getList();
        if (list == null) list = new ArrayList<>();

        // Terima data edit (bisa null)
        editData = (ExpenseModel) getIntent().getSerializableExtra("data");

        if (editData != null) {
            // isi form dengan data lama
            edtName.setText(editData.name);
            edtAmount.setText(String.valueOf(editData.amount));
            edtCategory.setText(editData.category);
            edtDate.setText(editData.date);

            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnDelete.setVisibility(View.GONE);
        }

        btnSave.setOnClickListener(v -> saveData());
        btnDelete.setOnClickListener(v -> deleteData());
    }

    private void saveData() {
        String name = edtName.getText().toString().trim();
        String amountStr = edtAmount.getText().toString().trim();
        String category = edtCategory.getText().toString().trim();
        String date = edtDate.getText().toString().trim();

        // validasi sederhana
        if (TextUtils.isEmpty(name)) {
            edtName.setError("Nama transaksi wajib diisi");
            return;
        }
        if (TextUtils.isEmpty(amountStr)) {
            edtAmount.setError("Nominal wajib diisi");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            edtAmount.setError("Nominal harus berupa angka");
            return;
        }

        // Jika mode edit: hapus item lama berdasarkan id (jika ada),
        // lalu tambahkan item baru dengan ID yang sama (sehingga dianggap update).
        if (editData != null) {
            String targetId = editData.id;
            // hapus berdasarkan id (safe)
            Iterator<ExpenseModel> it = list.iterator();
            while (it.hasNext()) {
                ExpenseModel item = it.next();
                if (item.id != null && item.id.equals(targetId)) {
                    it.remove();
                    break;
                }
            }
            // tambahkan item baru dengan ID lama (preserve id)
            ExpenseModel updated = new ExpenseModel(
                    targetId,
                    name,
                    amount,
                    category,
                    date
            );
            list.add(updated);
        } else {
            // mode tambah baru
            ExpenseModel baru = new ExpenseModel(
                    UUID.randomUUID().toString(),
                    name,
                    amount,
                    category,
                    date
            );
            list.add(baru);
        }

        // simpan dan keluar
        pref.saveList(list);
        Toast.makeText(this, "Data disimpan", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteData() {
        if (editData == null) {
            Toast.makeText(this, "Tidak ada data untuk dihapus", Toast.LENGTH_SHORT).show();
            return;
        }

        String targetId = editData.id;
        Iterator<ExpenseModel> it = list.iterator();
        boolean removed = false;
        while (it.hasNext()) {
            ExpenseModel item = it.next();
            if (item.id != null && item.id.equals(targetId)) {
                it.remove();
                removed = true;
                break;
            }
        }

        if (removed) {
            pref.saveList(list);
            Toast.makeText(this, "Data dihapus", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Item tidak ditemukan (gagal hapus)", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
