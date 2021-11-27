package com.example.lv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.telephony.SubscriptionManager;

import com.example.lv1.adapter.PageSlideAdapter;
import com.example.lv1.fragments.PersonalInfoFragment;
import com.example.lv1.fragments.StudentInfoFragment;
import com.example.lv1.fragments.SummaryFragment;

public class CreateNewRecordActivity extends AppCompatActivity {

    ViewPager viewPager;
    PageSlideAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_record);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new PageSlideAdapter(
                getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        );
        pagerAdapter.addFragment(new PersonalInfoFragment());
        pagerAdapter.addFragment(new StudentInfoFragment());
        pagerAdapter.addFragment(new SummaryFragment());
        viewPager.setAdapter(pagerAdapter);
    }
}