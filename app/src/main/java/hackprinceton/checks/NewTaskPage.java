package hackprinceton.checks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;

/**
 * Created by nanxi on 4/1/2017.
 */

public class NewTaskPage extends AppCompatActivity {
    int tableID;
    EditText name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_page);

        tableID = getIntent().getIntExtra("TABLE_ID", -1);
        name = (EditText) findViewById(R.id.editText);
    }

    public void onSaveClick(View v) {

        ChecksDbHelper db = new ChecksDbHelper(this);
        db.addTask(tableID, new TaskRow(name.getText().toString(), ""));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}