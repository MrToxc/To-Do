package com.example.to_do;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "To-Do.txt";
    private final Saver saver = new Saver();
    private final DateValidator dateValidator = new DateValidator();
    private List<String> tasks;
    private ArrayAdapter<String> tasksAdapter;
    private ListView listView;
    private EditText input;
    private EditText date;
    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);
        input = findViewById(R.id.input);

        button.setOnClickListener(this::addTask);

        load();
        tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        listView.setAdapter(tasksAdapter);
        setUpListViewListener();
    }

    private void load() {
        try {
            tasks = saver.load(openFileInput());
        } catch (IOException e) {
            toast("Something went wrong " + e.getMessage());
        }
    }

    private FileInputStream openFileInput() {
        try {
            return openFileInput(FILE_NAME);
        } catch (FileNotFoundException e) {
            // IF IT DOEST EXIST
            return null;
        }
    }

    private FileOutputStream openFileOutput() {
        try {
            return openFileOutput(FILE_NAME, MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            toast("File error " + e.getMessage());
            return null;
        }
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            toast("Item removed");
            tasks.remove(position);
            save();
            tasksAdapter.notifyDataSetChanged();
            return true;
        });
    }

    private void addTask(View view) {
        EditText editText = findViewById(R.id.input);
        EditText editDate = findViewById(R.id.date);
        String dateString = editDate.getText().toString();

        if (!dateValidator.checkDate(dateString)) {
            toast("Invalid date");
            return;
        }

        if (editText.getText().toString().matches("\\s*")) {
            toast("Can't add nothing");
            return;
        }

        String text;
        if (dateString.matches("\\s*")) {
            text = editText.getText().toString();
        } else {
            text = editDate.getText().toString() + " / " + editText.getText().toString();
        }

        tasksAdapter.add(text);
        save();

        editText.setText("");
        editDate.setText("");
    }

    private void save() {
        try {
            saver.save(tasks, openFileOutput());
        } catch (IOException e) {
            toast("Something went wrong " + e.getMessage());
        }
    }

    private void toast(String message) {
        Context context = getApplicationContext();
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}