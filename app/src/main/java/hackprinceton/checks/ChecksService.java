package hackprinceton.checks;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChecksService extends IntentService {
    public ChecksService() {
        super("ChecksService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        final int id = intent.getIntExtra("HEADER_ID", -1);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ChecksDbHelper db = new ChecksDbHelper(ChecksService.this);
                    HeaderRow header = db.getHeader(id);
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ChecksService.this);
                    String title = "Checks: Update for " + prefs.getString("name", "");
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(header.getNext());
                    String now = dateFormat.format(calendar.getTime());
                    calendar.setTimeInMillis(header.getNext() - header.getInterval() * 7 * TimeUnit.DAYS.toMillis(1));
                    String then = dateFormat.format(calendar.getTime());

                    String body ="Here is their activity log from " + then + " to " + now + ".\n\n";
                    List<TaskRow> tasks = db.getTasks(header.getDb());
                    for (int i = 0; i < tasks.size(); i++) {
                        body += tasks.get(i).getName() + " accomplished:\n";
                        String[] array = convertStringToArray(tasks.get(i).getDays());
                        for (int j = 1; j < array.length - 1; j++) {
                            body+= array[j] + ", ";
                        }
                        body += array[array.length - 1] + "\n\n";
                    }
                    GMailSender sender = new GMailSender("hackprinceton.checks@gmail.com",
                            "Dellgrassland113");
                    sender.sendMail(title , body, "hackprinceton.checks@gmail.com", header.getEmail());
                    header.setNext(header.getNext() + 7 * (header.getInterval() + 1) * TimeUnit.DAYS.toMillis(1));
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);

                }
            }

        }).start();
    }

    public static String strSeparator = "__,__";

    public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }
}
