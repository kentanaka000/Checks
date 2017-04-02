package hackprinceton.checks;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by nanxi on 4/1/2017.
 */

public class NewTask implements Item {
    private final String         str1;
    public NewTask(String text1) {
    this.str1 = text1;
}

        @Override
        public int getViewType() {
            return ChecklistAdapter.RowType.NEWTASK_ITEM.ordinal();
        }

        @Override
        public View getView(LayoutInflater inflater, View convertView) {
            View view;
            if (convertView == null) {
                view = (View) inflater.inflate(R.layout.add_new_task, null);
                // Do some initialization
            } else {
                view = convertView;
            }

            TextView text1 = (TextView) view.findViewById(R.id.new_task_content);
            text1.setText(str1);

            return view;
        }

}