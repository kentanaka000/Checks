package hackprinceton.checks;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Kentaro on 4/1/2017.
 */

public interface Item {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
}