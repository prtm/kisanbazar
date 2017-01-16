package preetam.jecrc.kishaanbazaar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by preetam on 3/26/16.
 */
public class CustomDialogX extends Dialog {
    public CustomDialogX(Context context, int dialogTheme) {
        super(context, dialogTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
