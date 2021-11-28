package com.example.lv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.example.lv1.adapter.PageSlideAdapter;
import com.example.lv1.fragments.PersonalInfoFragment;
import com.example.lv1.fragments.StudentInfoFragment;
import com.example.lv1.fragments.SummaryFragment;
import com.example.lv1.interfaces.ICameraFragmentToActivity;
import com.example.lv1.interfaces.IOnBackPressed;
import com.example.lv1.models.Storage;
import com.example.lv1.viewModels.SummaryViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CreateNewRecordActivity extends AppCompatActivity implements ICameraFragmentToActivity {

    ViewPager viewPager;
    PageSlideAdapter pagerAdapter;
    private SummaryViewModel viewModelSummary;

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

    @Override
    public void sendUrl(String url) {
        viewModelSummary = new ViewModelProvider(this).get(SummaryViewModel.class);
        Bitmap img = null;
        File f= new File(url);
        try{
            img = BitmapFactory.decodeStream(new FileInputStream(f));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        viewModelSummary.setProfileImage(img, true);
    }

    @Override public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.camera_frame);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void fragmentBackPress(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.remove(f);
        trans.commit();
        fm.popBackStack();
    }
}