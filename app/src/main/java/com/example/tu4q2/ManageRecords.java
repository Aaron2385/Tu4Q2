package com.example.tu4q2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ManageRecords extends AppCompatActivity {
    private DatabaseManager mydManager;
    private TextView response;
    private EditText studentID, FirstName, LastName, YearOfBirth, Gender;
    private Button addButton, showRecords, clearRecords, cancelRecords, showSelectedButton;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private ArrayList<String> selectedItems = new ArrayList<>();
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
        showSelectedButton = findViewById(R.id.button2);

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
            ArrayList<Model> records = mydManager.retrieveRows();
            adapter = new CustomAdapter(records);
            recyclerView.setAdapter(adapter);
        });

        showSelectedButton.setOnClickListener(v -> {
            selectedItems.clear(); // clear old selections

            for (Model model : adapter.getModelList()) {
                if (model.isSelected()) {
                    selectedItems.add(model.getText());
                }
            }

            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "No items selected", Toast.LENGTH_SHORT).show();
            } else {
                // Display all selected items
                StringBuilder result = new StringBuilder();
                for (String item : selectedItems) {
                    result.append(item).append("\n");
                }
                Toast.makeText(this, "Selected:\n" + result.toString(), Toast.LENGTH_LONG).show();
            }
        });


    }
}