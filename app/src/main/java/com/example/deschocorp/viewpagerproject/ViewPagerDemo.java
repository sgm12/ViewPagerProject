package com.example.deschocorp.viewpagerproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ViewPagerDemo extends Baseactivity implements ViewPager.OnPageChangeListener, View.OnClickListener{

    protected View view;
    private TextView txtSkip,txtNext;
    private ViewPager intro_images;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private ViewPagerAdapter mAdapter;
    SharedPreferences sp;
    private int[] mImageResources = {
            R.mipmap.abc1,
            R.mipmap.abc2,
            R.mipmap.abc3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // To make activity full screen.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        sp = getPreferences(MODE_PRIVATE);
        String val = sp.getString("isFirstRun","1");
        if(val == "1") {
            SharedPreferences.Editor ed = sp.edit();
            ed.putString("isFirstRun", "0");
            ed.commit();
            setReference();
        }
        else
        {
            setContentView(R.layout.activity_main);
        }

        toolbar.setVisibility(View.GONE);

    }

    @Override
    public void setReference() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_viewpager_demo,container);

        intro_images = (ViewPager) view.findViewById(R.id.pager_introduction);
        txtNext = (TextView) view.findViewById(R.id.txt_next);
        txtSkip = (TextView) view.findViewById(R.id.txt_skip);

        pager_indicator = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);

        txtNext.setOnClickListener(this);
        txtSkip.setOnClickListener(this);

        mAdapter = new ViewPagerAdapter(ViewPagerDemo.this, mImageResources);
        intro_images.setAdapter(mAdapter);
        intro_images.setCurrentItem(0);
        intro_images.setOnPageChangeListener(this);
        setUiPageViewController();
    }

    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_next:
                intro_images.setCurrentItem((intro_images.getCurrentItem() < dotsCount)
                        ? intro_images.getCurrentItem() + 1 : 0);
                break;

            case R.id.txt_skip:
                setContentView(R.layout.activity_main);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }
        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}