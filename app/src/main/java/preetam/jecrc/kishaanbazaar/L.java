package preetam.jecrc.kishaanbazaar;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by preetam on 3/20/16.
 */
public class L {
    public static void tlong(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }

    public static void tshort(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }

    public static void tlog(String msg) {
        Log.d("prtm", msg);
    }
}
