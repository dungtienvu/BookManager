package com.example.bookmanager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class AddBookActivity extends AppCompatActivity {

    private ListView listViewBooks;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> bookList;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listViewBooks = findViewById(R.id.listViewBooks);
        dbHelper = new DatabaseHelper(this);
        bookList = new ArrayList<>();

        loadBooks();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookList);
        listViewBooks.setAdapter(adapter);
    }

    private void loadBooks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BOOKS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR));
                String tags = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TAGS));
                bookList.add(title + " - " + author + " - " + tags);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

    }
}