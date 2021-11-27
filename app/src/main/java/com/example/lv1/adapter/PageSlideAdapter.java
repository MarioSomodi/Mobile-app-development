package com.example.lv1.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PageSlideAdapter extends FragmentStatePagerAdapter {

	private List<Fragment> fragmentsList = new ArrayList<>();

	public PageSlideAdapter(FragmentManager fm, int behavior) {
		super(fm, behavior);
	}

	@Override
	public Fragment getItem(int position) {
		return fragmentsList.get(position);
	}

	@Override
	public int getCount() {
		return fragmentsList.size() ;
	}

	public void addFragment(Fragment fragment){
		fragmentsList.add(fragment);
	}
}