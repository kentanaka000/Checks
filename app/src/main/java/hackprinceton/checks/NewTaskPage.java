package hackprinceton.checks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nanxi on 4/1/2017.
 */

public class NewTaskPage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_page);
        Intent intent = getIntent();
    }
}