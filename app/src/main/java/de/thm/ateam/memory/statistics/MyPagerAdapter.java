package de.thm.ateam.memory.statistics;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter{
  /** the list of fragments */
  private List<Fragment> fragments;

  private static String[] titles = new String[] { 
    "Pair Hit Percentage", 
    "Win Probability"
  };

  public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
    super(fm);
    this.fragments = fragments;
  }

  @Override
  public Fragment getItem(int arg0) {
    return this.fragments.get(arg0);
  }

  @Override
  public int getCount() {
    return this.fragments.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return titles[position];
  }

}