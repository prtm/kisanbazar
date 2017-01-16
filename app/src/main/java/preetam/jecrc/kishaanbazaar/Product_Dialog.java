package preetam.jecrc.kishaanbazaar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by preetam on 3/26/16.
 */
public class Product_Dialog extends Dialog {
    public Product_Dialog(Context context, int dialogTheme) {
        super(context, dialogTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_retry);
    }
}
