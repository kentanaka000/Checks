package hackprinceton.checks.contract;

import android.provider.BaseColumns;

/**
 * Created by Kentaro on 4/1/2017.
 */

public class TaskContract {
    private TaskContract() {}

    /* Inner class that defines the table contents */
    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DAYS = "days";
    }

}
