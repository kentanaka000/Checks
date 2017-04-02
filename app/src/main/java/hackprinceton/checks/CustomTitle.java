package hackprinceton.checks;

import android.os.Bundle;
import android.view.Window;

/**
 * Created by nanxi on 4/2/2017.
 */

public class CustomTitle extends MainActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        setContentView(R.layout.custom_title);

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
    }
}