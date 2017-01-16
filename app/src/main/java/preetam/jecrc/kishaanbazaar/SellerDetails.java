package preetam.jecrc.kishaanbazaar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by preetam on 3/21/16.
 */
public class SellerDetails extends Activity {

    private de.hdodenhof.circleimageview.CircleImageView circleImageView;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_seller);
        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        animation = AnimationUtils.loadAnimation(this, R.anim.rotate_smooth);
    }

    @Override
    public void onPause() {
        super.onPause();
        animation.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        circleImageView.startAnimation(animation);
    }

    public void dial_phone(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:9413354498"));
        startActivity(intent);
    }

    public void email(View v) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, "infosggroup12@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "About Products");
        intent.putExtra(Intent.EXTRA_TEXT, "");

        startActivity(Intent.createChooser(intent, "Send Email"));
    }
}
