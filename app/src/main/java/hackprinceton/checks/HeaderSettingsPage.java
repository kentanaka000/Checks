package hackprinceton.checks;

/**
 * Created by nanxi on 4/1/2017.
 */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
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

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class HeaderSettingsPage extends AppCompatActivity {

    static final String ID_PREF = "ID_PREF";

    EditText name;
    EditText email;
    Spinner staticSpinner;
    boolean update;
    HeaderRow header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header_settings);

        name = (EditText) findViewById(R.id.group_name);
        email = (EditText) findViewById(R.id.email_addresses);

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


        int headerID = getIntent().getIntExtra("HEADER_ID", -1);

        if (headerID != -1) {
            ChecksDbHelper db = new ChecksDbHelper(this);
            header = db.getHeader(headerID);
            name.setText(header.getName());
            email.setText(header.getEmail());
            staticSpinner.setSelection(header.getInterval());
            update = true;
        }
        else {
            Button delete = (Button) findViewById(R.id.button_delete);
            delete.setVisibility(View.INVISIBLE);
            update = false;
        }
    };

    public void onDeleteClick(View v) {
        AlertDialog alertDialog = new AlertDialog.Builder(HeaderSettingsPage.this).create();
        alertDialog.setTitle("WAIT!");
        alertDialog.setMessage("Are you sure you want to delete this?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ChecksDbHelper db = new ChecksDbHelper(HeaderSettingsPage.this);
                        db.deleteHeader(header);

                        Intent intent = new Intent(HeaderSettingsPage.this, MainActivity.class);
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

    public void onSaveClick(View v) {
        if (update) {
            ChecksDbHelper db = new ChecksDbHelper(this);
            header.setName(name.getText().toString());
            header.setEmail(email.getText().toString());
            header.setInterval(staticSpinner.getSelectedItemPosition());
            db.updateHeader(header);

        }
        else {
            SharedPreferences prefs = this.getSharedPreferences(
                    "hackprinceton.checks", Context.MODE_PRIVATE);
            int next = prefs.getInt(ID_PREF, 1000);
            next++;
            prefs.edit().putInt(ID_PREF, next).apply();



            ChecksDbHelper db = new ChecksDbHelper(this);
            Calendar calendar = Calendar.getInstance();


            calendar.set(Calendar.DAY_OF_WEEK,2);
            calendar.set(Calendar.HOUR,9);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            int id = (int) db.addHeader(new HeaderRow(name.getText().toString(), email.getText().toString(), staticSpinner.getSelectedItemPosition(), next, calendar.getTimeInMillis()));

            Intent serviceIntent = new Intent(getBaseContext(), ChecksService.class);
            serviceIntent.setAction("com.checks.propagate");
            serviceIntent.putExtra("id", id);
            PendingIntent pintent = PendingIntent.getService(HeaderSettingsPage.this, 0, serviceIntent, 0);
            AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7*(1 + staticSpinner.getSelectedItemPosition())*TimeUnit.DAYS.toMillis(1), pintent);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}