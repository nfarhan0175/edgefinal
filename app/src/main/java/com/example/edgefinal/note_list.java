package com.example.edgefinal;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class note_list extends AppCompatActivity {

    RecyclerView recyclerView;
    List<data> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        recyclerView = findViewById(R.id.re);
        MyDBhelper mydbhelper2 = new MyDBhelper(this);

        // Fetching notes from the database
        ArrayList<Notemodel> studentList = mydbhelper2.getNotes();  // Correct method name
        for (Notemodel student : studentList) {  // Loop through studentList
            items.add(new data(student.id, student.name, student.note));  // Add data to items list
        }

        // Setting up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        MyAdapter myAdapter = new MyAdapter(getApplicationContext(), items);
        recyclerView.setAdapter(myAdapter);

        // Adding a divider between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        // Handling system window insets for edge-to-edge support
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
