package hackprinceton.checks;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

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
                    GMailSender sender = new GMailSender("hackprinceton.checks@gmail.com",
                            "Dellgrassland113");
                    sender.sendMail("Hello from JavaMail", "Body from JavaMail",
                            "hackprinceton.checks@gmail.com", header.getEmail());
                    header.setNext(header.getNext() + 7 * header.getInterval() * TimeUnit.DAYS.toMillis(1));
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);

                }
            }

        }).start();
    }
}
