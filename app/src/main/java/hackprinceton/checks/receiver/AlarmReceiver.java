package hackprinceton.checks.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import hackprinceton.checks.ChecksDbHelper;
import hackprinceton.checks.ChecksService;
import hackprinceton.checks.HeaderRow;

/**
 * Created by Kentaro on 4/2/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            ChecksDbHelper db = new ChecksDbHelper(context);
            int id = intent.getIntExtra("HEADER_ID", -1);
            HeaderRow header = db.getHeader(id);
            if (header.getNext() < Calendar.getInstance().getTimeInMillis()) {
                Intent serviceIntent = new Intent(context, ChecksService.class);
                serviceIntent.putExtra("HEADER_ID", id);
                context.startService(serviceIntent);
            }
        }
    }
}
