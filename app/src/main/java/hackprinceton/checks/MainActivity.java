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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);

        List<Item> items = new ArrayList<Item>();
        items.add(new Header("Header 1"));
        items.add(new ListItem("Text 1"));
        items.add(new NewTask());
        items.add(new Header("Header 2"));
        items.add(new ListItem("Text 5"));
        items.add(new NewTask());
        

        ChecksDbHelper db = new ChecksDbHelper(this);
        List<HeaderRow> headers = db.getAllHeaders();

        for (int i = 0; i < headers.size(); i++) {
            items.add(new Header(headers.get(i).getName()));
            headers.get(0).getID();
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
