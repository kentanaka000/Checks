package hackprinceton.checks;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.R.attr.id;

public class MainActivity extends Activity{

    public void redirect(View view) {
        Intent intent = new Intent(this, HeaderSettingsPage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);

        List<Item> items = new ArrayList<Item>();

        ChecksDbHelper db = new ChecksDbHelper(this);
        List<HeaderRow> headers = db.getAllHeaders();

        for (int i = 0; i < headers.size(); i++) {
            items.add(new Header(headers.get(i).getName(), headers.get(i).getID()));
            if (headers.get(i).getNext() > Calendar.getInstance().getTimeInMillis()) {
                Intent serviceIntent = new Intent(this, ChecksService.class);
                serviceIntent.putExtra("HEADER_ID", headers.get(i).getID());
                startService(serviceIntent);
            }

            List<TaskRow> tasks = db.getTasks(headers.get(i).getDb());
            for (int j = 0; j < tasks.size(); j++) {
                items.add(new ListItem(tasks.get(j).getName(), headers.get(i).getDb(), tasks.get(j).getID()));
            }
            items.add(new NewTask(headers.get(i).getDb()));
        }

        ChecklistAdapter adapter = new ChecklistAdapter(this, items);
        listView.setAdapter(adapter);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("name", "");
        if (name == "") {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.enter_name);
            dialog.setTitle("Dialog box");
            final EditText editText = (EditText) dialog.findViewById(R.id.username);
            Button button = (Button) dialog.findViewById(R.id.save_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    preferences.edit().putString("name", editText.getText().toString()).apply();
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

    }

    public void onAddButtonClick(View v) {
        Intent intent = new Intent(this, HeaderSettingsPage.class);
        startActivity(intent);
    }
    }

