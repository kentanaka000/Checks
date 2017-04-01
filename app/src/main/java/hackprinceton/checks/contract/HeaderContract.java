package hackprinceton.checks.contract;

import android.provider.BaseColumns;

/**
 * Created by Kentaro on 4/1/2017.
 */

public class HeaderContract {
    private HeaderContract() {}

    /* Inner class that defines the table contents */
    public static class HeaderEntry implements BaseColumns {
        public static final String TABLE_NAME = "headers";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_INTERVAL = "interval";
        public static final String COLUMN_NAME_DATA = "data";
    }
}
