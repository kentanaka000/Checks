package hackprinceton.checks;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Kentaro on 4/1/2017.
 */

public class ListItem implements Item {
    private final String         str1;

    public ListItem(String text1) {
        this.str1 = text1;
    }

    @Override
    public int getViewType() {
        return ChecklistAdapter.RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.my_list_item, null);
            // Do some initialization
        } else {
            view = convertView;
        }
        TextView text1 = (TextView) view.findViewById(R.id.list_content1);
        text1.setText(str1);

        return view;
    }

}