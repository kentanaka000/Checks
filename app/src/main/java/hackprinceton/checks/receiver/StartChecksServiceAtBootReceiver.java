package hackprinceton.checks.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import hackprinceton.checks.ChecksService;

/**
 * Created by Kentaro on 4/1/2017.
 */

public class StartChecksServiceAtBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, ChecksService.class);
            context.startService(serviceIntent);
        }
    }
}
