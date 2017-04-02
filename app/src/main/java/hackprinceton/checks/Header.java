package hackprinceton.checks;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

/**
 * Created by Kentaro on 4/1/2017.
 */

public class Header implements Item {
    private final String name;
    private final int id;

    public Header(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public int getViewType() {
        return ChecklistAdapter.RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        final View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.header, null);
            // Do some initialization
        } else {
            view = convertView;
        }
        TextView text = (TextView) view.findViewById(R.id.separator);
        text.setText(name);

        Button button = (Button) view.findViewById(R.id.button11);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), HeaderSettingsPage.class);
                intent.putExtra("HEADER_ID", id);
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }
}