package preetam.jecrc.kishaanbazaar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by preetam on 3/23/16.
 */
public class ImageFAdapter extends PagerAdapter {

    Context context;
    private int[] GalImages = new int[]{
            R.drawable.adama_logo,
            R.drawable.advanta_logo,
            R.drawable.logo
    };

    ImageFAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            imageView.setElevation(30);
        }
        imageView.setImageResource(GalImages[position]);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}

