package com.example.tu4q2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ManageRecords extends AppCompatActivity {
    private DatabaseManager mydManager;
    private TextView response;
    private EditText studentID, FirstName, LastName, YearOfBirth, Gender;
    private Button addButton, showRecords, clearRecords, cancelRecords;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_records);
        mydManager = new DatabaseManager(this);
        mydManager.openReadable();

        response = findViewById(R.id.response);
        studentID = findViewById(R.id.student_id); // change ID if needed
        FirstName = findViewById(R.id.first_name); // change ID if needed
        LastName = findViewById(R.id.last_name); // make sure you have this ID in XML
        YearOfBirth = findViewById(R.id.dob); // make sure you have this ID in XML
        Gender = findViewById(R.id.gender); // make sure you have this ID in XML


        addButton = findViewById(R.id.add_button);
        showRecords = findViewById(R.id.viewButton);
        cancelRecords = findViewById(R.id.cancelButton);
        clearRecords = findViewById(R.id.clearButton);

        recyclerView = findViewById(R.id.recyclerView); // Make sure you have this in your XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addButton.setOnClickListener(v -> {
            boolean recInserted;
            //insert input taken from the form the database
            recInserted = mydManager.addRow(Integer.parseInt(studentID.getText().toString()), FirstName.getText().toString(), LastName.getText().toString(), YearOfBirth.getText().toString(), Gender.getText().toString());

            if (recInserted) {
                response.setText("The row in the student table is inserted");
            }
            else {
                response.setText("Sorry, errors when inserting to DB");
            }
            //clear the form
            studentID.setText("");
            FirstName.setText("");
            LastName.setText("");
            YearOfBirth.setText("");
            Gender.setText("");
        });

        //when cancel button is clicked, clear the form.
        cancelRecords = findViewById(R.id.cancelButton);
        cancelRecords.setOnClickListener(v->{
            studentID.setText("");
            FirstName.setText("");
            LastName.setText("");
            YearOfBirth.setText("");
            Gender.setText("");
        });

        clearRecords.setOnClickListener(v -> {
            mydManager.clearRecords();
            response.setText("All Records are removed");
        });

        showRecords.setOnClickListener(v -> {
            ArrayList<String> records = mydManager.retrieveRows(); // get list
            adapter = new CustomAdapter(records); // pass list to adapter
            recyclerView.setAdapter(adapter); // bind to recyclerView
        });

    }
}