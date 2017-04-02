package hackprinceton.checks;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

        final List<Integer> type = new ArrayList<Integer>();
        final List<Integer> idList = new ArrayList<Integer>();

        for (int i = 0; i < headers.size(); i++) {
            items.add(new Header(headers.get(i).getName()));
            type.add(0);
            idList.add(headers.get(i).getID());

            List<TaskRow> tasks = db.getTasks(headers.get(i).getDb());
            for (int j = 0; j < tasks.size(); j++) {
                items.add(new ListItem(tasks.get(j).getName()));
                type.add(1);
                idList.add(tasks.get(j).getID());
            }
            items.add(new NewTask());
            type.add(2);
            idList.add(0);
        }

        ChecklistAdapter adapter = new ChecklistAdapter(this, items);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Log.d("hello", Integer.toString(position));

                switch(type.get(position)) {
                    case 0:
                        Intent intent = new Intent(getBaseContext(), HeaderSettingsPage.class);
                        intent.putExtra("HEADER_ID", idList.get(position));
                        startActivity(intent);
                }
            }
        });

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
