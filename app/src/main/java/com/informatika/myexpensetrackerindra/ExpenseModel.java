package com.informatika.myexpensetrackerindra;

import java.io.Serializable;

public class ExpenseModel implements Serializable {
    String id;
    String name;
    int amount;
    String category;
    String date;

    public ExpenseModel(String id, String name, int amount, String category, String date) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
}
