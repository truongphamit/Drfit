package com.drteam.truongpq.drfit.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drteam.truongpq.drfit.R;
import com.drteam.truongpq.drfit.adapters.ViewPagerAdapter;
import com.drteam.truongpq.drfit.fragments.DayFragment;
import com.drteam.truongpq.drfit.utilities.MySharedPreferences;
import com.drteam.truongpq.drfit.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class TodayActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvDay;
    private ImageView img_left, img_right;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        getSupportActionBar().hide();

        tvDay = (TextView) findViewById(R.id.tv_day);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        createFragment();
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(fragments.size() - 1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                DayFragment dayFragment = (DayFragment) fragments.get(position);
                if (viewPager.getCurrentItem() == fragments.size() - 1) {
                    tvDay.setText(getString(R.string.today));
                } else if (viewPager.getCurrentItem() == fragments.size() - 2) {
                    tvDay.setText(getString(R.string.yesterday));
                } else {
                    tvDay.setText(Utils.dateStringFormat(dayFragment.getMs()));
                }
                setNavigation();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        img_left = (ImageView) findViewById(R.id.img_left);
        img_left.setOnClickListener(this);
        img_right = (ImageView) findViewById(R.id.img_right);
        img_right.setOnClickListener(this);
        setNavigation();
    }

    private void createFragment() {
        fragments = new ArrayList<>();
        long beginDay = Utils.getDayFromMs(MySharedPreferences.getDate(this));
        long today = Utils.getDayFromMs(System.currentTimeMillis());
        for (long i = today; i >= beginDay; i--) {
            fragments.add(0, DayFragment.newInstance(i * 1000 * 60 * 60 * 24));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                setNavigation();
                break;
            case R.id.img_right:
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                setNavigation();
                break;
        }
    }

    private void setNavigation() {
        if (viewPager.getCurrentItem() == fragments.size() - 1) {
            img_right.setVisibility(View.INVISIBLE);
        } else {
            img_right.setVisibility(View.VISIBLE);
        }

        if (viewPager.getCurrentItem() == 0) {
            img_left.setVisibility(View.INVISIBLE);
        } else {
            img_left.setVisibility(View.VISIBLE);
        }
    }
}
