package com.example.edgefinal;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class noteFragment extends Fragment {

    EditText notetake1, notetake2, notetake11, notetake22, notetake111;
    Button btn, update, delete;
    MyDBhelper mydbhelper;

    public noteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_note, container, false);

        // Initialize views
        notetake1 = rootView.findViewById(R.id.notetake1);
        notetake2 = rootView.findViewById(R.id.notetake2);
        btn = rootView.findViewById(R.id.btn);
        mydbhelper = new MyDBhelper(getActivity());  // Initialize database helper

        // Set OnClickListener to the Add Note button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = notetake1.getText().toString();
                String note = notetake2.getText().toString();

                // Check if fields are not empty
                if (!Name.isEmpty() && !note.isEmpty()) {
                    // Insert note without the ID (since it's auto-incremented)
                    mydbhelper.addnote(Name, note);
                    Toast.makeText(getActivity(), "Note added successfully", Toast.LENGTH_SHORT).show();
                    // Start note list activity
                    Intent intent = new Intent(getActivity(), note_list.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Please enter both fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Initialize views for updating a note
        notetake11 = rootView.findViewById(R.id.notetake11);
        notetake22 = rootView.findViewById(R.id.notetake22);
        update = rootView.findViewById(R.id.btne);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = notetake11.getText().toString();
                String Course = notetake22.getText().toString();

                // Make sure that the name is provided for updating
                if (!Name.isEmpty() && !Course.isEmpty()) {
                    // Call the updated method to update the note based on the name
                    mydbhelper.updateNoteByName(Name, Course);

                    // Notify the user and show the updated list of notes
                    ArrayList<Notemodel> studentList = mydbhelper.getNotes();
                    Toast.makeText(getActivity(), "Note updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), note_list.class);
                    startActivity(intent);

                    for (Notemodel student : studentList) {
                        Log.d("Database_operation_fetched", "Note id: " + student.id + " Name: " + student.name + " Note: " + student.note);
                    }
                } else {
                    Toast.makeText(getActivity(), "Please provide both name and note", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Initialize views for deleting a note by name
        notetake111 = rootView.findViewById(R.id.notetake111);
        delete = rootView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = notetake111.getText().toString();
                if (!Name.isEmpty()) {
                    // Delete the note by name
                    mydbhelper.deleteNote(Name);
                    Toast.makeText(getActivity(), "Note deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), note_list.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Please enter a name to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;  // Return the inflated view
    }
}
