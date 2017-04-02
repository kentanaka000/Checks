package hackprinceton.checks;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Kentaro on 4/1/2017.
 */

public class ListItem implements Item {
    private final String         str1;
    private final int table;
    private final int id;

    public ListItem(String text1, int table1, int id1) {
        this.str1 = text1;
        this.table = table1;
        this.id = id1;
    }

    @Override
    public int getViewType() {
        return ChecklistAdapter.RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        final View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.my_list_item, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox2);
        checkBox.setText(str1);

        ChecksDbHelper db = new ChecksDbHelper(view.getContext());
        TaskRow task = db.getTask(table, id);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String[] separated = convertStringToArray(task.getDays());
        String formattedDate = df.format(c.getTime());
        Log.d("current", formattedDate);
        Log.d("prev", separated[separated.length - 1]);
        if (!separated[separated.length - 1].equals(formattedDate)) {
            checkBox.setChecked(false);
        }
        else {
            checkBox.setChecked(true);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if (isChecked) {
                    ChecksDbHelper db = new ChecksDbHelper(view.getContext());
                    TaskRow task = db.getTask(table, id);
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate = df.format(c.getTime());

                    task.setDays(task.getDays() + strSeparator + formattedDate);
                    db.updateTask(table, task);
                }
                else {
                    ChecksDbHelper db = new ChecksDbHelper(view.getContext());
                    TaskRow task = db.getTask(table, id);
                    Log.d("before", task.getDays());

                    task.setDays(task.getDays().substring(0, task.getDays().length() - 15));
                    Log.d("new", task.getDays());
                    db.updateTask(table, task);
                }
            }
        });

        checkBox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(view.getContext(), NewTaskPage.class);
                intent.putExtra("TABLE_ID", table);
                intent.putExtra("ROW_ID", id);
                view.getContext().startActivity(intent);
                return false;
            }
        });

        return view;
    }

    public static String strSeparator = "__,__";
    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
    public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }

}