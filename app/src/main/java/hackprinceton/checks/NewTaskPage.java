package hackprinceton.checks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by nanxi on 4/1/2017.
 */

public class NewTaskPage extends AppCompatActivity {
    int tableID;
    int rowID;
    EditText name;
    boolean update;
    TaskRow task;
    TextView label;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_page);

        name = (EditText) findViewById(R.id.editText);
        label = (TextView) findViewById(R.id.textView2);

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
            label.setText("Rename your task?");
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
    public void onDeleteClick(View v) {
        AlertDialog alertDialog = new AlertDialog.Builder(NewTaskPage.this).create();
        alertDialog.setTitle("WAIT!");
        alertDialog.setMessage("Are you sure you want to delete this?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ChecksDbHelper db = new ChecksDbHelper(NewTaskPage.this);
                        db.deleteTask(tableID, task);

                        Intent intent = new Intent(NewTaskPage.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "No!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}