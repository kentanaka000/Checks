package hackprinceton.checks;

/**
 * Created by nanxi on 4/1/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

public class HeaderSettingsPage extends AppCompatActivity {

    static final String ID_PREF = "ID_PREF";

    EditText name;
    EditText email;
    Spinner staticSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header_settings);


        name = (EditText) findViewById(R.id.group_name);
        email = (EditText) findViewById(R.id.email_addresses);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        staticSpinner = (Spinner) findViewById(R.id.static_spinner);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.updates_array,
                        android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

    };

    public void onSaveClick(View v) {

        SharedPreferences prefs = this.getSharedPreferences(
                "hackprinceton.checks", Context.MODE_PRIVATE);
        int next = prefs.getInt(ID_PREF, 1000);
        next++;
        prefs.edit().putInt(ID_PREF, next).apply();



        ChecksDbHelper db = new ChecksDbHelper(this);
        db.addHeader(new HeaderRow(name.getText().toString(), email.getText().toString(), staticSpinner.getSelectedItemPosition(), next));

    }

}