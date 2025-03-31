package com.example.tu4q2;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //declare global variables
    private DatabaseManager mydManager;
    private TextView response;
    private TextView studentRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.toolbar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //start code

        response = findViewById(R.id.response);
        studentRec = findViewById(R.id.studentRec);
        mydManager = new DatabaseManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.insert_rows){
            insertRec();
            return true;
        }
        else if (id == R.id.list_rows){
            showRec();
            return true;
        }
//        else if (id == R.id.update_row){
//            updateRec();
//            return true;
//        }
//        else if (id == R.id.remove_rows){
//            removeRecs();
//            return true;
//        }
        else
            return super.onOptionsItemSelected(item);
    }

    public boolean insertRec() {

        mydManager.addRow(101, "Aaron", "Phan", "23/08/2005", "Male");
        mydManager.addRow(102, "Scott", "Pham", "26/08/2005", "Male");

        response.setText("The rows in the students table are inserted");
        studentRec.setText("");
        //mydManager.close(); do not close db to view the db in App Inspector
        return true;
    }

    public boolean showRec() {
        mydManager.openReadable();
        String tableContent = mydManager.retrieveRows();
        response.setText("The rows in the students table are: \n");

        studentRec.setText(tableContent);
        //mydManager.close();
        return true;
    }

//    public boolean updateRec(){
//        mydManager.updateRows(102, "Wen", "Pham", "26/08/2005", "Female");
//        return true;
//    }

    public boolean removeRecs() {
        mydManager.clearRecords();
        response.setText("All Records are removed");
        studentRec.setText("");
        return true;
    }

}