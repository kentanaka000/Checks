package hackprinceton.checks;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by nanxi on 4/1/2017.
 */

public class NewTask implements Item {
    private final String str1 = "Add a new task!";
    private int id;
    public NewTask(int id) {
        this.id = id;
    }

    @Override
    public int getViewType() {
        return ChecklistAdapter.RowType.NEWTASK_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        final View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.add_new_task, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        TextView text1 = (TextView) view.findViewById(R.id.new_task_content);
        text1.setText(str1);

        Button button = (Button) view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), NewTaskPage.class);
                intent.putExtra("TABLE_ID", id);
                view.getContext().startActivity(intent);
            }
        });
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), NewTaskPage.class);
                intent.putExtra("TABLE_ID", id);
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }




}