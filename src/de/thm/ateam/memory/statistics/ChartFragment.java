package de.thm.ateam.memory.statistics;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TitlePageIndicator;

import de.thm.ateam.memory.R;

public class ChartFragment extends FragmentActivity{
  /** maintains the pager adapter*/
  private MyPagerAdapter mPagerAdapter;
  /* (non-Javadoc)
   * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    super.setContentView(R.layout.statistics);
    //initialise the pager
    this.initialisePaging();
  }

  /**
   * Initialise the fragments to be paged
   */
  private void initialisePaging() {

    List<Fragment> fragments = new Vector<Fragment>();
    fragments.add(Fragment.instantiate(this, HitProbabilityChart.class.getName()));
    fragments.add(Fragment.instantiate(this, WinningProbabilityChart.class.getName()));
    this.mPagerAdapter  = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);
    //this.mPagerAdapter = new AwesomePagerAdapter();
    ViewPager pager = (ViewPager)this.findViewById(R.id.viewpager);
    pager.setAdapter(this.mPagerAdapter);
    TitlePageIndicator titleIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
    titleIndicator.setViewPager(pager);
  }




}
