package hackprinceton.checks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;

/**
 * Created by nanxi on 4/1/2017.
 */

public class NewTaskPage extends AppCompatActivity {
    int tableID;
    int rowID;
    EditText name;
    boolean update;
    TaskRow task;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_page);

        name = (EditText) findViewById(R.id.editText);

        tableID = getIntent().getIntExtra("TABLE_ID", -1);
        rowID = getIntent().getIntExtra("ROW_ID", -1);
        if (rowID == -1) {
            Button delete = (Button) findViewById(R.id.button_delete);
            delete.setVisibility(View.INVISIBLE);
            update = false;
        }
        else {
            ChecksDbHelper db = new ChecksDbHelper(this);
            task = db.getTask(tableID, rowID);
            name.setText(task.getName());
            update = true;
        }
    }

    public void onSaveClick(View v) {
        if (update) {
            ChecksDbHelper db = new ChecksDbHelper(this);
            task.setName(name.getText().toString());
            db.updateTask(tableID, task);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            ChecksDbHelper db = new ChecksDbHelper(this);
            db.addTask(tableID, new TaskRow(name.getText().toString(), ""));

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}