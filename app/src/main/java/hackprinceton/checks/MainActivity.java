package hackprinceton.checks;

import android.app.ListActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    public void redirect(View view)
    {
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
        items.add(new Header("Header 2"));
        items.add(new ListItem("Text 5", "Rabble rabble"));
        items.add(new ListItem("Text 6", "Rabble rabble"));
        items.add(new ListItem("Text 7", "Rabble rabble"));
        items.add(new ListItem("Text 8", "Rabble rabble"));

        ChecklistAdapter adapter = new ChecklistAdapter(this, items);
        setListAdapter(adapter);
        }
    }

