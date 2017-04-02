package hackprinceton.checks;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity{

    protected void onStart()
    {
        super.onStart();

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.enter_name);
        dialog.setTitle("Dialog box");

        Button button = (Button) dialog.findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
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

            List<TaskRow> tasks = db.getTasks(headers.get(i).getDb());
            for (int j = 0; j < tasks.size(); j++) {
                items.add(new ListItem(tasks.get(j).getName(), headers.get(i).getDb(), tasks.get(j).getID()));
            }
            items.add(new NewTask(headers.get(i).getDb()));
        }

        ChecklistAdapter adapter = new ChecklistAdapter(this, items);
        listView.setAdapter(adapter);


        /*
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("hackprinceton.checks@gmail.com",
                            "Dellgrassland113");
                    sender.sendMail("Hello from JavaMail", "Body from JavaMail",
                            "hackprinceton.checks@gmail.com", "hackprincton.checks@gmail.com");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();*/
    }

    public void onAddButtonClick(View v) {
        Intent intent = new Intent(this, HeaderSettingsPage.class);
        startActivity(intent);
    }
    }

