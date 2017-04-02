package hackprinceton.checks;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends ListActivity {

    public void redirect(View view) {
        Intent intent = new Intent(this, HeaderSettingsPage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        List<Item> items = new ArrayList<Item>();
        items.add(new Header("Header 1"));
        items.add(new ListItem("Text 1", "Rabble rabble"));
        items.add(new ListItem("Text 2", "Rabble rabble"));
        items.add(new ListItem("Text 3", "Rabble rabble"));
        items.add(new ListItem("Text 4", "Rabble rabble"));
        items.add(new NewTask("Add a new task!"));
        items.add(new Header("Header 2"));
        items.add(new ListItem("Text 5", "Rabble rabble"));
        items.add(new ListItem("Text 6", "Rabble rabble"));
        items.add(new ListItem("Text 7", "Rabble rabble"));
        items.add(new ListItem("Text 8", "Rabble rabble"));
        items.add(new NewTask("Add a new task!"));
        

        ChecksDbHelper db = new ChecksDbHelper(this);
        List<HeaderRow> headers = db.getAllHeaders();

        for (int i = 0; i < headers.size(); i++) {
            items.add(new Header(headers.get(i).getName()));
            headers.get(0).getID();
        }

        ChecklistAdapter adapter = new ChecklistAdapter(this, items);
        setListAdapter(adapter);


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
