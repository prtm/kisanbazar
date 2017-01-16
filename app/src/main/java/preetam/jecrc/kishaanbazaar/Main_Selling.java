package preetam.jecrc.kishaanbazaar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * Created by preetam on 3/23/16.
 */
public class Main_Selling extends Fragment {
    private final int mThumbIds[] = {0, 1, 2};
    private int i = 0;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_selling, container, false);

        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.view_pager);
        radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        radioGroup.check(R.id.rb1);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rv_company);
        rb1 = (RadioButton) v.findViewById(R.id.rb1);
        rb2 = (RadioButton) v.findViewById(R.id.rb2);
        rb3 = (RadioButton) v.findViewById(R.id.rb3);
        ArrayList<String> arrayList = getArguments().getStringArrayList("list");
        recyclerView.setAdapter(new RecyclerViewCompanys(getActivity(), arrayList));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        gridLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        try {
            recyclerView.smoothScrollToPosition(arrayList.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageFAdapter adapter = new ImageFAdapter(getActivity().getApplicationContext());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    radioGroup.check(R.id.rb1);
                    rb1.setButtonDrawable(R.drawable.radio_black);
                    rb2.setButtonDrawable(R.drawable.radio_white);
                    rb3.setButtonDrawable(R.drawable.radio_white);
                } else if (position == 1) {
                    radioGroup.check(R.id.rb2);
                    rb2.setButtonDrawable(R.drawable.radio_black);
                    rb1.setButtonDrawable(R.drawable.radio_white);
                    rb3.setButtonDrawable(R.drawable.radio_white);
                } else {
                    radioGroup.check(R.id.rb3);
                    rb3.setButtonDrawable(R.drawable.radio_black);
                    rb2.setButtonDrawable(R.drawable.radio_white);
                    rb1.setButtonDrawable(R.drawable.radio_white);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.rb1 == checkedId) {
                    viewPager.setCurrentItem(0);
                } else if (R.id.rb2 == checkedId) {
                    viewPager.setCurrentItem(1);
                } else {
                    viewPager.setCurrentItem(2);
                }
            }
        });


        Runnable r = new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(mThumbIds[i]);
                i++;
                if (i >= mThumbIds.length) {
                    i = 0;
                }
                viewPager.postDelayed(this, 5000);//set to go off again in 3 seconds.
            }
        };
        viewPager.postDelayed(r, 5000);


        return v;
    }


    /*
    private void dialogPhoto() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.zoom_imageview, null);
        dialogBuilder.setView(dialogView);
        mImageView = (ImageView) dialogView.findViewById(R.id.img1);
        Drawable bitmap = ContextCompat.getDrawable(getActivity(), R.drawable.one);
        mImageView.setImageDrawable(bitmap);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomPhoto(mImageView);
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.getWindow().setLayout(400, 600);
        alertDialog.show();
    }


    public void zoomPhoto(ImageView mImageView) {

        // Set the Drawable displayed


        // Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(mImageView);
    }

     */
}
